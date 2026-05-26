# 09 — Conclusion and Technical Debt

**Related notes/labs**

- `10 - Example of software change.pptx.pdf`
- `10 - Conclusion of software change.pdf`
- `11 - BeyondTechnicalDebt.pdf`

## Exercise goal

The final notes connect the concrete maintenance task to the bigger picture: software change, long-term maintainability, and technical debt.

## What changed

The repository now contains:

- the JHotDraw case-study source;
- a concrete maintenance change in `SVGRectFigure`;
- tests for the changed behavior;
- CI workflow for Maven tests;
- separated portfolio documentation by lecture/lab note.

## Maintenance conclusion

The selected change is small, but it demonstrates a complete maintenance workflow:

1. select a real legacy/open-source codebase;
2. identify a feature area;
3. write a change request and user story;
4. locate the concept in source code;
5. analyze impact;
6. refactor/implement the change;
7. add tests;
8. express expected behavior with BDD scenarios;
9. verify with CI.

## Technical debt discussion

The original risk was a small form of technical debt: geometry state and cache invalidation were not clearly tied together in the radius setters.

That debt is dangerous because it is not visually obvious. A setter that changes a number looks harmless. But in this class, the number affects derived shapes used for rendering and hit testing.

The fix reduces debt by making the invariant explicit:

```text
if radius geometry changes, the figure is invalidated
```

## Remaining debt / future work

Possible future improvements:

- Add SVG import/export round-trip tests for rounded rectangles.
- Add GUI-level tests for dragging the radius handle.
- Review other figure classes for similar cache invalidation patterns.
- Add documentation comments explaining the relationship between geometry and cached shapes.
- Modernize the old Maven/JUnit setup if the project were maintained long-term.

## Final verification

The project was verified with Maven and the build passed:

```text
BUILD SUCCESS
```

The implemented work is therefore not only documented; it is also executable and tested.
