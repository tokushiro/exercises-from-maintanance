# Exercise 07 — Actualization, Clean Architecture, and SOLID

**Related lab note:** `05 - ActualizationLab.pdf`  
**Submission by:** Tokushiro

## Lab objective

The actualization lab asks us to explain Clean Architecture and Clean Code principles in the context of the case study. It also asks for SOLID examples in the case study.

## Actualization in this project

Actualization means implementing the selected change and propagating necessary secondary changes.

For this case study, the actualized change was:

- update `SVGRectFigure` radius setter behavior;
- add unit tests for the selected feature;
- add JUnit dependency to the relevant Maven module;
- add CI so the tests run automatically.

## SOLID examples

| SOLID principle | Example in this case study |
|---|---|
| Single Responsibility | `SVGRectFigure` owns rectangle geometry. `SVGRectRadiusHandle` owns user interaction for changing radius. The fix keeps those responsibilities separate. |
| Open/Closed | The behavior is improved without changing the public API used by callers. Existing callers remain valid. |
| Liskov Substitution | `SVGRectFigure` remains usable as an SVG/drawing figure. The change does not break figure contracts. |
| Interface Segregation | Radius-specific behavior stays in SVG rectangle classes instead of being forced into generic drawing interfaces. |
| Dependency Inversion | Higher-level editor behavior works through figure/handle abstractions instead of depending directly on storage details. |

## Clean Architecture explanation

JHotDraw is not written as a strict Clean Architecture example, but the same idea of separating responsibilities can be seen:

| Layer-like responsibility | JHotDraw example | Explanation |
|---|---|---|
| User interaction | `SVGRectRadiusHandle` | Handles direct manipulation from the editor UI. |
| Domain/entity behavior | `SVGRectFigure` | Stores and maintains rectangle geometry. |
| Interface/data boundary | `SVGInputFormat`, `SVGOutputFormat` | Converts between SVG files and internal figures. |
| Framework/application support | Drawing editor, handles, attributes | Provides reusable infrastructure around figures. |

The radius invariant belongs in the domain figure, not in the UI handle. That is why the implementation was placed in `SVGRectFigure`.

## Clean Code principles applied

- Keep the change small.
- Keep the invariant close to the data.
- Avoid surprising side effects for callers.
- Add tests for important behavior.
- Avoid introducing unnecessary frameworks.

## Result

The final design is simple: callers change radius values through the existing API, and the figure keeps itself valid.
