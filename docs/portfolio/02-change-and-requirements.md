# 02 — Change and Requirements

**Related notes/labs**

- `02 - ChangeLec.pdf`
- `02 - ChangeReqLec.pdf`
- `02 - ChangeReqLab.pdf`

## Exercise goal

This exercise is about turning a vague maintenance need into a concrete change request. The change should be small enough to implement, but specific enough to test.

## Change request

**Title:** Keep SVG rounded rectangle geometry consistent when corner radii change.

**System:** JHotDraw SVG editor.

**Problem:** `SVGRectFigure` stores rounded rectangle corner radii and also caches derived transformed/hit-test shapes. Updating the arc width or arc height changes the figure geometry, but the old setter behavior did not consistently invalidate derived state. That makes future maintenance risky because callers can update geometry while cached shapes remain stale.

**Requested change:** Update the rounded rectangle radius setters so radius changes invalidate the figure's derived state. Add tests that document the expected behavior.

## User story

> As a diagram author using the SVG editor, I want rectangle corner radii to update consistently when I edit a rounded rectangle, so that the visual shape, hit testing, and saved SVG geometry reflect the same rounded-corner values.

## Acceptance criteria

1. A rectangle stores separate horizontal and vertical corner radii.
2. `setArcWidth(value)` updates the horizontal radius.
3. `setArcHeight(value)` updates the vertical radius.
4. `setArc(width, height)` updates both radii as one geometry change.
5. Radius setters invalidate derived geometry when values actually change.
6. Cloned rectangle figures keep independent radius values.
7. Automated tests cover the radius behavior.
8. Continuous integration runs the Maven tests on push and pull request.

## Maintenance type

This is mostly **corrective/preventive maintenance**:

- Corrective, because geometry mutation should invalidate dependent cached state.
- Preventive, because the tests prevent future edits from breaking radius behavior silently.

It is intentionally not a large feature addition. The point is to practice the maintenance workflow on a controlled change.

## Scope decision

Included:

- `SVGRectFigure` radius setter behavior.
- Unit tests for the selected feature.
- Maven module test dependency.
- GitHub Actions CI workflow.
- Portfolio documentation.

Excluded:

- Redesigning the whole SVG editor.
- Replacing the JHotDraw drawing model.
- Adding a new GUI test framework.
- Changing SVG import/export unless required by the radius behavior.

The scope is deliberately narrow. Maintenance work gets messy fast when the change request is allowed to grow teeth.
