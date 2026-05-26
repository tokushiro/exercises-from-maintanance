# Exercise 09 — BDD: User Stories to Scenarios

**Related lab note:** `09 - BDDLab.pdf`  
**Submission by:** Tokushiro

## Lab objective

The BDD lab asks us to map user stories to Given-When-Then scenarios. It mentions JGiven and AssertJ. For this repository, the scenarios are written in BDD form and mapped to executable JUnit tests to avoid adding unnecessary framework complexity to an old Maven/Swing project.

## User story 1 — update rounded rectangle radius

> As a diagram author, I want to change the corner radius of an SVG rectangle so that I can create rounded rectangles instead of only sharp-cornered rectangles.

### Scenario 1

```gherkin
Given an SVG rectangle with no rounded corners
When the user sets the rounded-corner radius to width 12 and height 18
Then the rectangle stores arcWidth 12
And the rectangle stores arcHeight 18
```

Implemented by:

```text
SVGRectFigureTest#setArcUpdatesBothCornerRadii
```

## User story 2 — independent horizontal and vertical radius

> As a diagram author, I want horizontal and vertical corner radius values to be stored independently so that I can create elliptical rounded corners.

### Scenario 2

```gherkin
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

## User story 3 — clone independence

> As a developer maintaining JHotDraw, I want cloned rectangle figures to keep independent radius values so that editing a clone does not unexpectedly modify the original figure.

### Scenario 3

```gherkin
Given an SVG rounded rectangle with radius width 10 and height 15
When the figure is cloned
And the clone radius is changed to width 30 and height 35
Then the original figure still has radius width 10 and height 15
And the clone has radius width 30 and height 35
```

Implemented by:

```text
SVGRectFigureTest#cloneKeepsIndependentRadiusValues
```

## User story 4 — preserve radius during bounds changes

> As a diagram author, I want a rectangle to keep its rounded-corner radius when I move or resize it so that editing bounds does not destroy styling.

### Scenario 4

```gherkin
Given an SVG rounded rectangle with configured corner radii
When the rectangle bounds are changed
Then the configured corner radii are preserved
```

Implemented by:

```text
SVGRectFigureTest#translatedBoundsPreserveTheConfiguredRadius
```

## Why not add JGiven here?

The lab mentions JGiven, but this old JHotDraw Maven project is already Swing-based and legacy. Adding JGiven and AssertJ-Swing would increase dependency/setup work without improving the selected domain-level maintenance change.

Instead, the scenarios are documented in BDD form and backed by JUnit tests. This keeps the behavior executable and CI-friendly.
