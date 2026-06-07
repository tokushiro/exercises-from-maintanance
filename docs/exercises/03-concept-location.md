# Exercise 03 — Concept Location

**Related lab note:** `02 - CLLab1.pdf`  
**Submission by:** Tokushiro

## Lab objective

The lab asks us to use concept location to find the initial set of classes involved in the selected feature. The required portfolio artifact is a table with domain classes and responsibilities.

## Selected concept

The selected concept is:

> Rounded rectangle radius editing in the SVG editor.

## Concept location approach

The course names three concept-location techniques. I used the first two and noted why the third was not needed:

| Technique | Used? | How |
|---|---|---|
| Grep / keyword search | Yes (primary) | Searched for the terms below. This was enough to identify the owning class. |
| Dependency search | Yes (light) | Walked callers of `SVGRectFigure`'s radius setters to confirm reach into the UI handle and SVG I/O code. |
| Runtime / dynamic debugging (e.g. Featureous-style tracing) | Not needed | The keyword + dependency walk already gave a small, confident set. Runtime tracing would have been the next step if the feature had been more scattered. |

### Keyword (grep) search terms

- `SVGRect`
- `rect`
- `round`
- `rounded`
- `arcWidth`
- `arcHeight`
- `radius`

### Dynamic / dependency path

I then followed the feature from the likely user action to the domain object:

1. User selects an SVG rectangle.
2. The editor shows handles.
3. The radius handle (`SVGRectRadiusHandle`) changes the radius.
4. The figure (`SVGRectFigure`) stores the radius values.
5. Drawing, hit testing, and persistence use the figure state.

This is the dependency-search step: starting from the entry point and stepping through callers until the owning class is found.

## Initial set of classes

| Domain class | Responsibility |
|---|---|
| `SVGRectFigure` | Main domain class for SVG rectangles. Stores rectangle bounds and rounded-corner radii using `RoundRectangle2D.Double`. Provides `getArcWidth`, `getArcHeight`, `setArcWidth`, `setArcHeight`, and `setArc`. |
| `SVGRectRadiusHandle` | UI handle used to manipulate the rounded rectangle radius interactively. It is the controller-like entry point for radius editing. |
| `SVGFigure` | Common SVG figure contract used by SVG-specific figure classes. |
| `SVGAttributedFigure` | Base class for SVG figures with attributes. `SVGRectFigure` extends this class. |
| `SVGAttributeKeys` | Defines SVG-related attributes and default values that affect drawing. |
| `SVGInputFormat` | Reads SVG files and creates figures. Relevant because persisted rounded rectangles must map into figure geometry. |
| `SVGOutputFormat` | Writes SVG files. Relevant because exported output depends on figure geometry being consistent. |
| `DefaultSVGFigureFactory` | Creates SVG figure instances while reading SVG data. |

## Result

The main class to modify is `SVGRectFigure`, because it owns the radius state. `SVGRectRadiusHandle` is important for understanding the feature, but changing only the handle would not protect other callers of the figure API.
