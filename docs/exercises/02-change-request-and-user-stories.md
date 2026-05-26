# Exercise 02 — Change Request and User Stories

**Related lab note:** `02 - ChangeReqLab.pdf`  
**Submission by:** Tokushiro

## Lab objective

The lab asks us to select an existing JHotDraw feature and write user stories for it. The selected user story becomes an artifact for the portfolio.

## Selected existing feature

The selected existing feature is:

> Editing rounded rectangle corner radii in the SVG editor.

This feature exists in JHotDraw because `SVGRectFigure` supports `arcWidth` and `arcHeight`, and `SVGRectRadiusHandle` provides an editing handle for changing the radius.

## Change request

**Title:** Keep SVG rounded rectangle radius changes consistent with cached geometry.

**Problem:**  
`SVGRectFigure` stores the rounded rectangle geometry in `RoundRectangle2D.Double`. The class also caches transformed and hit-test shapes. When radius values change, the figure should invalidate derived state so drawing and hit testing stay consistent.

**Requested change:**  
Update the radius setter methods so that changing `arcWidth`, `arcHeight`, or both values invalidates the figure. Add tests that verify the expected domain behavior.

**Maintenance type:** Corrective/preventive maintenance.

- Corrective because radius mutation should update derived figure state correctly.
- Preventive because tests make future regressions less likely.

## User stories

### User story 1 — selected portfolio story

> As a diagram author, I want to change the corner radius of an SVG rectangle so that I can create rounded rectangles instead of only sharp-cornered rectangles.

**Acceptance criteria**

- The rectangle has a horizontal radius value.
- The rectangle has a vertical radius value.
- The user can update both values.
- The figure keeps the updated values after the change.

### User story 2 — consistency after editing

> As a diagram author, I want the visual shape and hit testing of a rounded rectangle to update when I change its radius so that the editor behaves consistently.

**Acceptance criteria**

- Changing `arcWidth` invalidates cached geometry.
- Changing `arcHeight` invalidates cached geometry.
- Changing both values invalidates the figure once as one geometry update.

### User story 3 — independent horizontal and vertical radius

> As a diagram author, I want horizontal and vertical corner radius values to be stored independently so that I can create elliptical rounded corners.

**Acceptance criteria**

- Updating the horizontal radius does not overwrite the vertical radius.
- Updating the vertical radius does not overwrite the horizontal radius.
- Tests verify independent values.

### User story 4 — clone behavior

> As a developer maintaining JHotDraw, I want cloned rectangle figures to keep independent radius values so that editing a clone does not unexpectedly modify the original figure.

**Acceptance criteria**

- A cloned figure starts with the same radius as the original.
- Changing the clone radius does not change the original radius.

## Selected story for implementation

The implemented story is mainly User Story 2, supported by User Stories 1, 3, and 4 through tests.

## Scope

Included:

- radius setters in `SVGRectFigure`;
- unit tests for radius behavior;
- CI workflow for Maven tests.

Excluded:

- full GUI automation;
- redesigning the SVG editor;
- changing SVG import/export behavior.
