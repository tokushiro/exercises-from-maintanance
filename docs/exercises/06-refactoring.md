# Exercise 06 — Refactoring

**Related lab note:** `04 - RefactoringLab1.pdf`  
**Submission by:** Tokushiro

## Lab objective

The refactoring lab asks us to identify a bad code smell in JHotDraw based on the selected change request, then apply suitable refactoring patterns and explain the strategy.

## Code smell

The relevant code was in:

```text
SVGRectFigure.java
```

The methods were:

```java
setArcWidth(double newValue)
setArcHeight(double newValue)
setArc(double width, double height)
```

The smell was **incomplete/unsafe state update**:

- the radius values are part of the figure geometry;
- the class also stores cached transformed/hit-test shapes;
- changing radius values should invalidate dependent geometry;
- without that, object state can become inconsistent.

There were also unnecessary old-value local variables in the original setter style, which made the code look like it intended to handle change events but did not use the old values.

## Refactoring plan

The plan was:

1. Keep the public API unchanged.
2. Move the invalidation responsibility into the setters that mutate radius state.
3. Only invalidate when a value actually changes.
4. Update `setArc(width, height)` so both radius values are changed as one operation.
5. Add tests for the behavior.

## Refactoring strategy

The applied strategy is close to **Encapsulate Update** / **Consolidate Duplicate Conditional Mutation**:

- the state owner (`SVGRectFigure`) becomes responsible for its invariant;
- callers do not need to remember to invalidate the figure manually;
- duplicate setter behavior is made consistent.

## Applied change

The updated behavior is:

```text
if arc width changes -> update value -> invalidate figure
if arc height changes -> update value -> invalidate figure
if either value changes in setArc -> update both -> invalidate once
```

## Reasoning

This is safer than changing `SVGRectRadiusHandle`, because the handle is only one caller. The figure setters may also be used by SVG input code, tests, future tools, or other editor actions.

## Result

The refactoring reduces maintenance risk while preserving the existing public methods.
