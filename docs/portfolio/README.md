# Software Maintenance Portfolio

**Student / repository owner:** Tokushiro  
**Case study:** JHotDraw SVG editor  
**Repository:** `Tokushiro/Exercises-from-Maintanance`

This folder contains the Software Maintenance exercise answers separated by lecture/lab note. The work follows the course material sequence instead of hiding everything in one giant report.

## Reading order

| Order | File | What it covers |
|---:|---|---|
| 1 | [`01-introduction-and-case-study.md`](01-introduction-and-case-study.md) | IntroLab, course setup, JHotDraw case-study choice |
| 2 | [`02-change-and-requirements.md`](02-change-and-requirements.md) | Change lectures, change request, user story, acceptance criteria |
| 3 | [`03-concept-location.md`](03-concept-location.md) | Concept location exercise for the rounded rectangle feature |
| 4 | [`04-impact-analysis-and-ci.md`](04-impact-analysis-and-ci.md) | Impact analysis and continuous integration setup |
| 5 | [`05-refactoring-and-better-code.md`](05-refactoring-and-better-code.md) | Refactoring, better code, code smell, concrete source change |
| 6 | [`06-actualization-clean-architecture-and-design.md`](06-actualization-clean-architecture-and-design.md) | Actualization, OO principles, design principles, Clean Architecture notes |
| 7 | [`07-testing.md`](07-testing.md) | Software testing lab and implemented JUnit tests |
| 8 | [`08-bdd.md`](08-bdd.md) | BDD scenarios and mapping to executable tests |
| 9 | [`09-conclusion-and-technical-debt.md`](09-conclusion-and-technical-debt.md) | Conclusion, software change example, technical debt reflection |
| 10 | [`theory-recap.md`](theory-recap.md) | Named theory (Fowler smells, Rule of Three, SOLID, GRASP, Law of Demeter, scattering/entanglement, concept-location techniques) mapped to the change |
| 11 | [`coverage-matrix.md`](coverage-matrix.md) | Checklist showing how each note/lab is represented |

The original single-file version is kept as [`software-maintenance-portfolio.md`](software-maintenance-portfolio.md), but the separated files above are the clearer submission version.

For checking the assignment one exercise at a time, use the exercise files here:

- [`../exercises/README.md`](../exercises/README.md)

## Implemented maintenance change

The implemented change is in:

- `jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/svg/figures/SVGRectFigure.java`

The issue addressed is that the rounded rectangle arc setters updated geometry values without invalidating cached derived shapes. The fix makes `setArcWidth`, `setArcHeight`, and `setArc` invalidate the figure when radius values change.

## Verification

The full Maven test suite was verified with:

```bash
docker run --rm \
  -v /var/lib/docker/volumes/homelab_hermes_data/_data/work/maintenance-exercises/repo:/workspace \
  -w /workspace \
  maven:3.9.9-eclipse-temurin-17 \
  mvn -B test
```

Result: **BUILD SUCCESS**.
