# 03 — Concept Location

**Related notes/labs**

- `02 - ConceptLocation.pdf`
- `02 - CLLab1.pdf`

## Exercise goal

Concept location is about finding where a user-visible concept is implemented in the source code. The selected concept is:

> editing the rounded-corner radius of an SVG rectangle.

## Search strategy

The course names three concept-location techniques. This work used the first two and noted why the third was not needed:

| Technique | Used? | Notes |
|---|---|---|
| Grep / keyword search | Yes (primary) | Searched the codebase for the domain/implementation terms below. |
| Dependency search | Yes (secondary) | Followed callers of `SVGRectFigure`'s radius setters back to the handle and to the SVG IO factories. |
| Runtime debugging (dynamic tracing, Featureous-style) | Not needed | The keyword + dependency walk already produced a small, confident initial set. A runtime trace would have been the next step if scattering had been higher. |

### Keyword (grep) terms

- `rect`
- `rectangle`
- `round`
- `rounded`
- `arcWidth`
- `arcHeight`
- `radius`
- `SVGRect`

### Dependency search

After the grep located `SVGRectFigure`, the search followed call relationships from the figure class to UI handles and SVG I/O code, and back the other way (callers of the radius setters).

## Located classes

| Class/file | Role in the concept |
|---|---|
| `SVGRectFigure.java` | Main domain class. Stores rectangle bounds and rounded-corner radii in `RoundRectangle2D.Double`. Provides `getArcWidth`, `getArcHeight`, `setArcWidth`, `setArcHeight`, and `setArc`. |
| `SVGRectRadiusHandle.java` | UI/editor handle for changing rectangle radii interactively. It is the user-facing entry point for radius editing. |
| `SVGFigure.java` | Common interface/contract for SVG figures. |
| `SVGAttributeKeys.java` | Defines SVG-related drawing attributes and defaults. |
| `SVGOutputFormat.java` | Writes SVG output. It depends on figure geometry being internally consistent. |
| `SVGInputFormat.java` / `DefaultSVGFigureFactory.java` | Reads SVG input and creates SVG figures, including rectangles. |

## Dynamic concept path

A likely runtime path is:

1. User selects an SVG rectangle.
2. The editor displays resize/radius handles.
3. The user drags the radius handle.
4. `SVGRectRadiusHandle` calculates the new radius.
5. The handle calls the relevant setter on `SVGRectFigure`.
6. `SVGRectFigure` updates its `RoundRectangle2D.Double` state.
7. The figure invalidates derived cached shape data.
8. Repaint/hit testing/export now use consistent geometry.

## Static concept result

The concept is mainly implemented in `org.jhotdraw.samples.svg.figures`. The rest of the codebase is impacted only indirectly through normal figure drawing, hit testing, and serialization.

## Conclusion

The best maintenance location is `SVGRectFigure`. Changing the handle would treat the symptom from the UI side. Changing import/export would treat persistence but not the geometry model. The actual concept belongs to the figure's geometry state, so the fix belongs there.
