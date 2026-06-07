# Exercise 06 — Refactoring (Prefactoring and Postfactoring)

**Related lab note:** `04 - RefactoringLab1.pdf`
**Submission by:** Tokushiro

## Lab objective

The refactoring lab asks us to identify a bad code smell in JHotDraw based on the selected change request, then apply suitable refactoring patterns and explain the strategy.

The course splits this work into two distinct phases in the 8-phase software-change sequence:

1. **Prefactoring** — restructure *before* implementing, to make the change easier and safer to apply.
2. **Postfactoring** — clean up *after* implementing, so the final code is no harder to maintain than the code we started with.

This document maps the change against both phases.

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

### Where this fits in Fowler's catalog

| Catalog smell | How it shows up here |
|---|---|
| Mysterious name / unused variable | The `oldValue` locals were assigned and never read. |
| Divergent change (risk of) | One reason to change radius state; another reason to invalidate derived shapes. Both reasons lived in the same setter but weren't tied together. |
| Shotgun surgery (risk avoided) | If invalidation lived in callers, every new caller would have to remember it. Keeping it in the setter avoids that. |

## Prefactoring (before the change)

Goal of prefactoring: leave the class in a shape where the planned change is small, local, and obviously correct.

Concrete prefactoring steps:

1. **Read both setters and `setArc`** to confirm they really were the only mutation paths into `arcWidth` / `arcHeight`.
2. **Remove the unused `oldValue` locals** so the setters' control flow is the actual control flow, not noise.
3. **Confirm the existing `invalidate()` method on the figure base class** already covers cached transformed/hit shapes — so the new behaviour can reuse it instead of inventing a new mechanism.
4. **Leave the public API alone**: `setArcWidth(double)`, `setArcHeight(double)`, `setArc(double, double)`, plus the getters. The shape of callers does not change.

After prefactoring the setters look syntactically cleaner but behave the same as before. That is the point: behaviour is preserved (so existing callers still pass), and the next step can focus only on the new invariant.

## Actualization step (the change itself)

Inside each setter:

- compare the existing radius with the new radius;
- mutate only when the value actually changes;
- call `invalidate()` when mutation happens;
- in `setArc(width, height)`, update both fields and invalidate **once** instead of calling the two single-axis setters and risking a double invalidation.

This is documented in `07-actualization-clean-architecture-solid.md`.

## Postfactoring (after the change)

Goal of postfactoring: with the new behaviour in place, leave the class no worse than we found it.

Concrete postfactoring steps:

1. **Consolidate the conditional mutation** so the three setters have the same shape: "compare → mutate → invalidate". This is a small **Consolidate Duplicate Conditional Fragments** refactor (Fowler).
2. **Push the invariant into the state owner.** Callers (`SVGRectRadiusHandle`, SVG IO, tests, any future tool) do not need hidden knowledge that "after I change radius I must call invalidate()". The class enforces it. This is **Encapsulate Field/Update**.
3. **Stop early on no-op writes** (`if (newValue == arc.archeight) return;`-style guards) so unnecessary invalidations don't ripple through the drawing pipeline.
4. **Pin the new behaviour with tests** (`SVGRectFigureTest`) so a future "cleanup" doesn't silently re-introduce the smell.

## Refactoring strategy summary

The applied strategy is close to **Encapsulate Update** / **Consolidate Duplicate Conditional Mutation**:

- the state owner (`SVGRectFigure`) becomes responsible for its invariant;
- callers do not need to remember to invalidate the figure manually;
- duplicate setter behavior is made consistent.

## Applied change (combined view)

The updated behavior is:

```text
if arc width changes  -> update value -> invalidate figure
if arc height changes -> update value -> invalidate figure
if either value changes in setArc -> update both -> invalidate once
```

## Rule of Three

We applied the **Rule of Three** here in the small: three setter methods (`setArcWidth`, `setArcHeight`, `setArc`) all needed the same "compare → mutate → invalidate" shape. The third occurrence is the point at which we stop tolerating duplication and consolidate the pattern. With only one or two occurrences, inline duplication is fine; at three, refactor.

## Reasoning

This is safer than changing `SVGRectRadiusHandle`, because the handle is only one caller. The figure setters may also be used by SVG input code, tests, future tools, or other editor actions. Keeping the fix in the state owner respects the **Law of Demeter** (callers don't need to know about the figure's cache fields) and **high cohesion / low coupling** (the figure's geometry invariant lives in one place).

## Result

The refactoring reduces maintenance risk while preserving the existing public methods. Both phases — prefactoring to prepare the class, and postfactoring to lock the new shape in — produced a smaller diff than implementing the change blind would have.
