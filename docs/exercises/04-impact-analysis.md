# Exercise 04 — Impact Analysis

**Related lab note:** `03 - AnalysisLab1.pdf`  
**Submission by:** Tokushiro

## Lab objective

The lab asks us to estimate the impacted set of classes after concept location. The portfolio artifact is a table listing packages, number of classes visited, and comments explaining what each package contributes to the feature.

## Change being analyzed

The planned change is to make rounded rectangle radius setters in `SVGRectFigure` invalidate derived geometry when radius values change.

## Estimated impact set

| Package name | # of classes visited | Comments |
|---|---:|---|
| `org.jhotdraw.samples.svg.figures` | 5 | Main feature package. `SVGRectFigure` owns the radius state. `SVGRectRadiusHandle` changes the radius through user interaction. Other SVG figure classes were checked to understand common geometry conventions. |
| `org.jhotdraw.samples.svg.io` | 4 | SVG import/export code. This package matters because saved SVG output depends on the figure geometry. No direct change was required because the public radius values remain available through the same methods. |
| `org.jhotdraw.samples.svg` | 3 | SVG application and attribute context. This package defines defaults and editor behavior around SVG figures. No direct change was needed. |
| `org.jhotdraw.draw.handle` | 2 | General handle/editing package. Checked to understand how handles should mutate figures. The existing radius handle can keep using the same setter methods. |
| `org.jhotdraw.draw` | 3 | Base drawing and figure behavior. Important because `SVGRectFigure` participates in drawing, invalidation, and hit testing through JHotDraw abstractions. |

## Impact conclusion

The safest implementation is local to:

```text
SVGRectFigure.java
```

Reason:

- It owns the `RoundRectangle2D.Double` radius values.
- It owns the cached transformed/hit-test shapes.
- Existing callers already use its public setters.
- Keeping the fix local avoids unnecessary UI or persistence changes.

## Risk and mitigation

| Risk | Mitigation |
|---|---|
| Stale cached geometry after radius change | Radius setters call `invalidate()` when values change. |
| Breaking callers | Public method names and parameters are unchanged. |
| Missing regression coverage | Added JUnit tests for radius behavior. |
| CI not catching failures | Added GitHub Actions Maven workflow. |
