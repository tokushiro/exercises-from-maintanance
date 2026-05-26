# Software Maintenance Portfolio — JHotDraw SVG Rounded Rectangle Feature

**Submission by:** Tokushiro  
**Repository:** `Tokushiro/Exercises-from-Maintanance`

> This is the original combined portfolio report. For the clearer lecture/lab-separated version, start with [`README.md`](README.md) in this folder.

## 1. Case study and selected feature

**Case study:** JHotDraw, using the Maven-based fork at `wumpz/jhotdraw` because it matches the course lab command (`org.jhotdraw.samples.svg.Main`) and module layout (`jhotdraw-samples-misc`).

**Selected feature:** editing rounded rectangle corner radii in the SVG sample editor.

The relevant domain class is:

- `jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/svg/figures/SVGRectFigure.java`

The relevant UI handle is:

- `jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/svg/figures/SVGRectRadiusHandle.java`

## 2. Change request / user story

**User story**

> As a diagram author using the SVG editor, I want rectangle corner radii to update consistently when I edit a rounded rectangle, so that the visual shape, hit testing, and saved SVG geometry reflect the same rounded-corner values.

**Acceptance criteria**

1. A rectangle can store separate horizontal and vertical corner radii.
2. Updating `arcWidth` invalidates derived/cached geometry.
3. Updating `arcHeight` invalidates derived/cached geometry.
4. Updating both values via `setArc(width, height)` is treated as one coherent geometry update.
5. Cloned rectangle figures keep independent radius values.
6. Unit tests cover the radius update behavior.
7. GitHub Actions builds the Maven project and runs tests for pull requests.

## 3. Concept location result

Dynamic concept location starts from the user action: selecting a rectangle and dragging the rounded-corner handle. From there, the feature crosses from controller/tooling into figure/domain geometry.

| Domain class | Responsibility |
|---|---|
| `SVGRectFigure` | Domain representation of an SVG rectangle. Stores bounds and rounded-corner radii in a `RoundRectangle2D.Double`; exposes `getArcWidth`, `getArcHeight`, `setArcWidth`, `setArcHeight`, and `setArc`. Also owns transformed/hit-shape caching. |
| `SVGRectRadiusHandle` | Editing handle that lets the user manipulate the rectangle radius interactively. It calls the rectangle radius setters. |
| `SVGFigure` | Common SVG figure contract implemented by SVG-specific domain figures. |
| `SVGAttributeKeys` | Defines SVG rendering attributes and default values used by `SVGRectFigure`. |
| `SVGOutputFormat` | Serializes SVG figures. It depends on figure geometry and attributes being internally consistent. |
| `SVGInputFormat` / `DefaultSVGFigureFactory` | Reads SVG elements and creates figure instances, including rounded rectangles. |

## 4. Impact analysis

| Package name | # of classes visited | Comments |
|---|---:|---|
| `org.jhotdraw.samples.svg.figures` | 5 | Main feature package. `SVGRectFigure` owns the rounded-rectangle state; `SVGRectRadiusHandle` changes it; neighboring SVG figure classes show the expected figure conventions. |
| `org.jhotdraw.samples.svg.io` | 4 | Checked persistence impact. SVG import/export must observe the same radius values that editing changes. No direct serialization change was needed for this maintenance task. |
| `org.jhotdraw.samples.svg` | 3 | Checked attribute defaults and the SVG application context. This package establishes the drawing/editor environment. |
| `org.jhotdraw.draw.handle` | 2 | Checked handle conventions. The radius handle follows the general handle/editing pattern. |
| `org.jhotdraw.draw` | 3 | Checked base figure behavior, invalidation, attributes, and geometry contracts used by `SVGRectFigure`. |

**Impact conclusion:** the safest change is local to `SVGRectFigure`: make radius mutation invalidate cached geometry and remove unused setter state. Tests can be added in the same Maven module without changing UI code.

## 5. Refactoring work

### Code smell

`SVGRectFigure#setArcWidth` and `setArcHeight` assigned `oldValue` variables that were never used. More importantly, the setters changed geometry without invalidating cached transformed/hit shapes. That is a maintenance smell: state mutation and cache invalidation were separated, making future bugs easy.

### Refactoring strategy

Applied a small **Encapsulate Update / Consolidate Duplicate Conditional Mutation** style refactoring:

- radius setters now only mutate when the value actually changes;
- every mutation calls `invalidate()`;
- `setArc(width, height)` updates both values and invalidates once instead of calling two setters and potentially invalidating twice;
- unused `oldValue` locals were removed.

### Changed source

- `SVGRectFigure.java`
- `SVGRectFigureTest.java`
- `jhotdraw-samples-misc/pom.xml`

## 6. Clean architecture and SOLID notes

| Principle | Case-study example |
|---|---|
| Single Responsibility | `SVGRectFigure` owns rectangle geometry; `SVGRectRadiusHandle` owns the editing interaction. The refactoring preserves this split. |
| Open/Closed | New tests and safer setter behavior extend confidence without changing callers such as the handle or SVG IO code. |
| Liskov Substitution | `SVGRectFigure` remains usable through the `SVGFigure`/figure abstractions. The public contract is unchanged. |
| Interface Segregation | SVG-specific behavior stays behind SVG figure classes; generic draw packages do not need SVG radius details. |
| Dependency Inversion | Higher-level editor actions operate on figure abstractions and handles instead of hardcoding storage details. |

Clean Architecture in this case is visible as a practical separation between:

- UI/editing handles (`SVGRectRadiusHandle`),
- domain geometry (`SVGRectFigure`),
- persistence (`SVGInputFormat`, `SVGOutputFormat`), and
- framework abstractions (`Figure`, handles, attributes).

The maintenance change belongs in domain geometry, so it was kept there.

## 7. Testing work

Added class-level unit tests for the selected feature:

- `setArcUpdatesBothCornerRadii`
- `setArcWidthAndHeightCanBeChangedIndependently`
- `cloneKeepsIndependentRadiusValues`
- `translatedBoundsPreserveTheConfiguredRadius`

These tests document the expected business/domain behavior of rounded rectangle radius editing.

## 8. BDD scenarios

### Scenario 1 — update both radii

```gherkin
Given an SVG rectangle with no rounded corners
When the user sets the rounded-corner radius to width 12 and height 18
Then the rectangle stores arcWidth 12
And the rectangle stores arcHeight 18
```

### Scenario 2 — independent horizontal and vertical radii

```gherkin
Given an SVG rectangle with equal rounded-corner radii
When the user changes the horizontal radius to 20
And the user changes the vertical radius to 30
Then the horizontal and vertical radii are preserved independently
```

### Scenario 3 — clone independence

```gherkin
Given an SVG rounded rectangle
When the figure is cloned
And the clone radius is changed
Then the original figure keeps its original radius
And the clone has the new radius
```

The repository implements these scenarios as automated JUnit tests. A full JGiven layer would be possible, but for this maintenance task the JUnit tests keep the feature behavior executable without adding a new test framework.

## 9. Continuous integration

The repository contains `.github/workflows/maven.yml` configured to run Maven on pushes and pull requests to `main`.

The intended command is:

```bash
mvn -B test --file pom.xml
```

## 10. Verification

Verified with a Maven Docker image because the agent container itself does not include Java/Maven:

```bash
docker run --rm \
  -v /var/lib/docker/volumes/homelab_hermes_data/_data/work/maintenance-exercises/repo:/workspace \
  -w /workspace \
  maven:3.9.9-eclipse-temurin-17 \
  mvn -B test
```

Result: **BUILD SUCCESS**. The full reactor built successfully and `SVGRectFigureTest` ran 4 tests with 0 failures.
