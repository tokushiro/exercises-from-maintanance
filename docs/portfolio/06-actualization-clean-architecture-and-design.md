# 06 — Actualization, Clean Architecture, and Design Principles

**Related notes/labs**

- `05 - Actualization.pdf`
- `05 - ActualizationLab.pdf`
- `05 - OOPrinciples.pdf`
- `05 - Clean Architecture.pdf`
- `06 - CleanCode.pdf`
- `06 - DesignPrinciplesAndPatterns.pdf`

## Exercise goal

This part connects the implemented maintenance change to design principles. The purpose is to show that the code change is not random: it follows the structure and responsibilities of the existing system.

## Actualized change

The planned maintenance change was implemented in the source code:

```text
SVGRectFigure.java
```

The actual behavior now is:

- `setArcWidth` updates horizontal radius and invalidates the figure;
- `setArcHeight` updates vertical radius and invalidates the figure;
- `setArc` updates both values and invalidates once;
- unchanged values do not trigger unnecessary invalidation.

## Object-oriented principle notes

| Principle | How it applies in this case |
|---|---|
| Encapsulation | `SVGRectFigure` owns its geometry state and keeps its cached derived state valid. |
| Single Responsibility | The figure handles geometry; the handle handles user interaction. The fix respects that separation. |
| Information hiding | Callers do not need to know about cached transformed/hit shapes. They call the public setters. |
| Polymorphism | The figure remains usable through JHotDraw figure abstractions. |

## SOLID notes

| SOLID principle | Case-study interpretation |
|---|---|
| Single Responsibility | `SVGRectFigure` owns rectangle geometry and radius state. `SVGRectRadiusHandle` owns interaction. |
| Open/Closed | Behavior is made safer without changing the public API or requiring caller changes. |
| Liskov Substitution | `SVGRectFigure` still behaves as a normal figure. No superclass/interface contract is broken. |
| Interface Segregation | SVG radius behavior stays in SVG-specific figure code instead of leaking into generic drawing interfaces. |
| Dependency Inversion | Editor actions work through figure/handle abstractions rather than hardcoding storage details. |

## Clean Architecture mapping

This is not a textbook Clean Architecture codebase, but the same dependency idea can still be seen:

| Layer-like area | JHotDraw example | Relevance |
|---|---|---|
| UI/editor interaction | `SVGRectRadiusHandle` | User drags or edits the radius. |
| Domain model | `SVGRectFigure` | Owns rectangle geometry and radius values. |
| Persistence boundary | `SVGInputFormat`, `SVGOutputFormat` | Reads/writes SVG based on figure state. |
| Framework abstractions | `Figure`, handles, attributes | Provide reusable drawing/editor infrastructure. |

The maintenance fix belongs in the domain model area, because that is where the geometry invariant lives.

## Design-pattern observation

JHotDraw uses a figure/handle/tool style common in drawing editors:

- figures represent drawable domain objects;
- handles expose direct manipulation affordances;
- tools/editors coordinate user interaction;
- input/output formats serialize the model.

The rounded rectangle radius change follows that design instead of bypassing it.

## Clean-code result

The final implementation is intentionally boring. Boring is good here. It keeps the invariant close to the data and avoids spreading cache invalidation rules through the UI layer.
