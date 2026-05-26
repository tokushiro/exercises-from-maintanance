# Exercise 08 — Testing

**Related lab note:** `07 - TestLab1.pdf`  
**Submission by:** Tokushiro

## Lab objective

The testing lab asks us to add JUnit 4 tests for important domain logic methods of the selected feature. It asks for best-case and boundary-case testing where relevant.

## Test dependency

The module POM was updated:

```text
jhotdraw-samples/jhotdraw-samples-misc/pom.xml
```

JUnit was added so the module can run tests.

## Test class

```text
jhotdraw-samples/jhotdraw-samples-misc/src/test/java/org/jhotdraw/samples/svg/figures/SVGRectFigureTest.java
```

## Tested domain logic

The tested domain logic is radius state in `SVGRectFigure`.

| Test | Type | What it verifies |
|---|---|---|
| `setArcUpdatesBothCornerRadii` | Best case | Setting both radii stores both values correctly. |
| `setArcWidthAndHeightCanBeChangedIndependently` | Best/boundary behavior | Horizontal and vertical radius values are independent. |
| `cloneKeepsIndependentRadiusValues` | Boundary/important object behavior | A cloned figure does not share mutable radius state with the original. |
| `translatedBoundsPreserveTheConfiguredRadius` | Boundary behavior | Changing rectangle bounds does not accidentally reset configured radius. |

## Why class-level unit tests are enough here

The selected change is in the domain figure class, not in a full GUI workflow. Testing the class directly is faster, simpler, and less fragile than Swing UI automation.

## Verification

The Maven test suite was run with:

```bash
mvn -B test --file pom.xml
```

In this environment:

```bash
docker run --rm \
  -v /var/lib/docker/volumes/homelab_hermes_data/_data/work/maintenance-exercises/repo:/workspace \
  -w /workspace \
  maven:3.9.9-eclipse-temurin-17 \
  mvn -B test
```

Result: **BUILD SUCCESS**.

## What this proves

The tests prove that the selected feature stores and preserves rounded rectangle radius values correctly. Together with the implementation change, the figure also invalidates derived state when radius values change.
