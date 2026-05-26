# Software Maintenance Exercises

**Submission by:** Tokushiro  
**Repository:** `Tokushiro/Exercises-from-Maintanance`  
**Case study:** JHotDraw SVG editor

This folder separates the portfolio into one file per exercise/lab. This is the easiest version to read if the teacher checks the assignment against the lab notes.

## Exercise files

| Exercise | File | Main artifact |
|---:|---|---|
| 01 | [`01-intro-lab.md`](01-intro-lab.md) | Case-study setup, Maven/GitHub workflow |
| 02 | [`02-change-request-and-user-stories.md`](02-change-request-and-user-stories.md) | Change request, selected feature, user stories |
| 03 | [`03-concept-location.md`](03-concept-location.md) | Initial domain-class table |
| 04 | [`04-impact-analysis.md`](04-impact-analysis.md) | Estimated impact set and package table |
| 05 | [`05-continuous-integration.md`](05-continuous-integration.md) | GitHub Actions CI setup |
| 06 | [`06-refactoring.md`](06-refactoring.md) | Code smell, refactoring plan, applied refactoring |
| 07 | [`07-actualization-clean-architecture-solid.md`](07-actualization-clean-architecture-solid.md) | SOLID and Clean Architecture explanation |
| 08 | [`08-testing.md`](08-testing.md) | JUnit tests and verification |
| 09 | [`09-bdd-user-stories-to-scenarios.md`](09-bdd-user-stories-to-scenarios.md) | User stories mapped to Given-When-Then scenarios |

## Implemented feature

The implemented maintenance feature is **SVG rounded rectangle radius editing**.

Main changed files:

- `jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/svg/figures/SVGRectFigure.java`
- `jhotdraw-samples/jhotdraw-samples-misc/src/test/java/org/jhotdraw/samples/svg/figures/SVGRectFigureTest.java`
- `jhotdraw-samples/jhotdraw-samples-misc/pom.xml`
- `.github/workflows/maven.yml`

## Verification

The project was verified with Maven in Docker:

```bash
docker run --rm \
  -v /var/lib/docker/volumes/homelab_hermes_data/_data/work/maintenance-exercises/repo:/workspace \
  -w /workspace \
  maven:3.9.9-eclipse-temurin-17 \
  mvn -B test
```

Result: **BUILD SUCCESS**.
