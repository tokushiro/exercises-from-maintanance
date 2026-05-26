# 07 — Testing

**Related notes/labs**

- `07 - Software Testing.pdf`
- `07 - TestLab1.pdf`

## Exercise goal

The testing exercise requires the maintenance change to be protected by automated tests. The tests should describe the expected behavior of the selected feature and be runnable as part of the normal build.

## Test location

Tests were added in the same Maven module as the changed class:

```text
jhotdraw-samples/jhotdraw-samples-misc/src/test/java/org/jhotdraw/samples/svg/figures/SVGRectFigureTest.java
```

The module POM was updated to include JUnit for tests:

```text
jhotdraw-samples/jhotdraw-samples-misc/pom.xml
```

## Test cases

| Test | Purpose |
|---|---|
| `setArcUpdatesBothCornerRadii` | Verifies `setArc(width, height)` stores both radius values. |
| `setArcWidthAndHeightCanBeChangedIndependently` | Verifies horizontal and vertical radii can be changed separately. |
| `cloneKeepsIndependentRadiusValues` | Verifies cloned figures do not share radius state with the original. |
| `translatedBoundsPreserveTheConfiguredRadius` | Verifies bounds/location changes do not accidentally destroy configured radius values. |

## Test level

These are unit tests focused on the domain figure class. They are not full GUI tests. That is a deliberate choice because:

- the maintenance change is inside `SVGRectFigure`;
- GUI tests would be slower and more brittle;
- the behavior can be checked directly through the figure API;
- CI should stay simple and reliable.

## What is covered

Covered:

- radius value storage;
- independent width/height radius behavior;
- clone independence;
- preservation of radius through bounds changes;
- Maven test execution in CI.

Not covered:

- manual dragging in the GUI;
- pixel-perfect rendering;
- full SVG import/export round trip.

Those could be added later, but they are beyond the scope of this focused maintenance task.

## Verification command

```bash
mvn -B test --file pom.xml
```

In this environment, Maven was run through Docker:

```bash
docker run --rm \
  -v /var/lib/docker/volumes/homelab_hermes_data/_data/work/maintenance-exercises/repo:/workspace \
  -w /workspace \
  maven:3.9.9-eclipse-temurin-17 \
  mvn -B test
```

## Result

The Maven reactor completed successfully. The new `SVGRectFigureTest` class ran 4 tests with 0 failures.
