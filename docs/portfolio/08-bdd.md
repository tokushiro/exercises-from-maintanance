# 08 — Behaviour-Driven Development

**Related notes/labs**

- `09 - BDD.pdf`
- `09 - BDDLab.pdf`

## Exercise goal

The BDD exercise expresses the selected feature as behavior from the user's point of view. The behavior should be understandable before reading implementation details.

## Feature

```gherkin
Feature: SVG rounded rectangle radius editing
  As a diagram author
  I want rounded rectangle radii to update consistently
  So that the displayed shape and stored geometry stay in sync
```

## Scenario 1 — Update both radii

```gherkin
Scenario: Set both rounded-corner radii
  Given an SVG rectangle with no rounded corners
  When the user sets the rounded-corner radius to width 12 and height 18
  Then the rectangle stores arcWidth 12
  And the rectangle stores arcHeight 18
```

Implemented by:

```text
SVGRectFigureTest#setArcUpdatesBothCornerRadii
```

## Scenario 2 — Independent horizontal and vertical radius

```gherkin
Scenario: Change horizontal and vertical radii independently
  Given an SVG rectangle with equal rounded-corner radii
  When the user changes the horizontal radius to 20
  And the user changes the vertical radius to 30
  Then the horizontal radius is 20
  And the vertical radius is 30
```

Implemented by:

```text
SVGRectFigureTest#setArcWidthAndHeightCanBeChangedIndependently
```

## Scenario 3 — Clone independence

```gherkin
Scenario: Clone a rounded rectangle
  Given an SVG rounded rectangle
  When the figure is cloned
  And the clone radius is changed
  Then the original figure keeps its original radius
  And the clone has the new radius
```

Implemented by:

```text
SVGRectFigureTest#cloneKeepsIndependentRadiusValues
```

## Scenario 4 — Preserve radius while moving/resizing

```gherkin
Scenario: Preserve radius when rectangle bounds change
  Given an SVG rounded rectangle with configured corner radii
  When the rectangle bounds are changed
  Then the configured radii remain unchanged
```

Implemented by:

```text
SVGRectFigureTest#translatedBoundsPreserveTheConfiguredRadius
```

## Why JUnit instead of adding a BDD framework

A full JGiven/Cucumber layer would add extra setup and dependencies to an old Maven project. For this exercise, the BDD scenarios are written in Gherkin-style documentation and mapped directly to executable JUnit tests.

That keeps the behavior readable while still making it part of the automated build. Less ceremony, fewer moving parts. A rare win for everyone involved.
