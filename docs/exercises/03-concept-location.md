# Exercise 03 — Concept Location

**Related lab note:** `02 - CLLab1.pdf`  
**Submission by:** Tokushiro

## Lab objective

The lab asks us to use concept location to find the initial set of classes involved in the selected feature. The required portfolio artifact is a table with domain classes and responsibilities.

## Selected concept

The selected concept is:

> Rounded rectangle radius editing in the SVG editor.

## Concept location approach

I searched the source code using terms related to the feature:

- `SVGRect`
- `rect`
- `round`
- `rounded`
- `arcWidth`
- `arcHeight`
- `radius`

Then I followed the feature from the likely user action to the domain object:

1. User selects an SVG rectangle.
2. The editor shows handles.
3. The radius handle changes the radius.
4. The figure stores the radius values.
5. Drawing, hit testing, and persistence use the figure state.

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
