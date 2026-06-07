# Exercise 04 — Impact Analysis

**Related lab note:** `03 - AnalysisLab1.pdf`  
**Submission by:** Tokushiro

## Lab objective

The lab asks us to estimate the impacted set of classes after concept location. The portfolio artifact is a table listing packages, number of classes visited, and comments explaining what each package contributes to the feature.

## Change being analyzed

The planned change is to make rounded rectangle radius setters in `SVGRectFigure` invalidate derived geometry when radius values change.

## Initial impact set

The initial impact set is the smallest set of classes we already know must change to implement the request.

```
Initial impact set = { SVGRectFigure }
```

This is the class that owns the radius fields and the cached derived shapes. Concept location already pointed here.

## Estimated impact set

| Package name | # of classes visited | Comments |
|---|---:|---|
| `org.jhotdraw.samples.svg.figures` | 5 | Main feature package. `SVGRectFigure` owns the radius state. `SVGRectRadiusHandle` changes the radius through user interaction. Other SVG figure classes were checked to understand common geometry conventions. |
| `org.jhotdraw.samples.svg.io` | 4 | SVG import/export code. This package matters because saved SVG output depends on the figure geometry. No direct change was required because the public radius values remain available through the same methods. |
| `org.jhotdraw.samples.svg` | 3 | SVG application and attribute context. This package defines defaults and editor behavior around SVG figures. No direct change was needed. |
| `org.jhotdraw.draw.handle` | 2 | General handle/editing package. Checked to understand how handles should mutate figures. The existing radius handle can keep using the same setter methods. |
| `org.jhotdraw.draw` | 3 | Base drawing and figure behavior. Important because `SVGRectFigure` participates in drawing, invalidation, and hit testing through JHotDraw abstractions. |

## Scattering and entanglement

| Term | Meaning | This feature |
|---|---|---|
| Scattering | One feature spread across many classes | **Low.** The radius concept shows up in the handle and in SVG IO factories, but the invariant has a single owner (`SVGRectFigure`). |
| Entanglement | One class mixing many unrelated features | **Low** for the part we touched. `SVGRectFigure` is a large class overall, but the radius logic is concerned only with rectangle geometry — it does not mix with rendering, IO, or unrelated attribute logic. |

Because scattering and entanglement are both low, the gap between the initial impact set (1 class) and the estimated impact set (5 packages visited, none requiring changes) is justified by *checking* rather than *changing*: we visited those packages to confirm we didn't need to touch them.

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
