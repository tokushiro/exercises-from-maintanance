# Exercises from Software Maintenance

**Submission by:** Tokushiro  
**Repository:** `Tokushiro/Exercises-from-Maintanance`

This repository contains the completed Software Maintenance lab/portfolio work based on the JHotDraw case study.

## What is included

- A Maven-based JHotDraw case-study codebase.
- A selected maintenance feature: **SVG rounded rectangle radius editing**.
- A small refactoring in `SVGRectFigure` to make radius mutation invalidate cached geometry.
- Automated tests for the selected feature.
- A GitHub Actions Maven workflow.
- Portfolio documentation answering the course lab prompts.
- A lecture/lab-separated documentation structure so the work is easier to follow.

## Exercise documents

Start here if checking the assignment exercise-by-exercise:

- [`docs/exercises/README.md`](docs/exercises/README.md)

Separate file for each exercise:

- [`01-intro-lab.md`](docs/exercises/01-intro-lab.md)
- [`02-change-request-and-user-stories.md`](docs/exercises/02-change-request-and-user-stories.md)
- [`03-concept-location.md`](docs/exercises/03-concept-location.md)
- [`04-impact-analysis.md`](docs/exercises/04-impact-analysis.md)
- [`05-continuous-integration.md`](docs/exercises/05-continuous-integration.md)
- [`06-refactoring.md`](docs/exercises/06-refactoring.md)
- [`07-actualization-clean-architecture-solid.md`](docs/exercises/07-actualization-clean-architecture-solid.md)
- [`08-testing.md`](docs/exercises/08-testing.md)
- [`09-bdd-user-stories-to-scenarios.md`](docs/exercises/09-bdd-user-stories-to-scenarios.md)

## Portfolio documents

The broader portfolio-style version is here:

- [`docs/portfolio/README.md`](docs/portfolio/README.md)
- [`docs/portfolio/coverage-matrix.md`](docs/portfolio/coverage-matrix.md)
- [`docs/portfolio/software-maintenance-portfolio.md`](docs/portfolio/software-maintenance-portfolio.md)

## Main changed files

- `jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/svg/figures/SVGRectFigure.java`
- `jhotdraw-samples/jhotdraw-samples-misc/src/test/java/org/jhotdraw/samples/svg/figures/SVGRectFigureTest.java`
- `jhotdraw-samples/jhotdraw-samples-misc/pom.xml`
- `.github/workflows/maven.yml`

## Build/test command

```bash
mvn -B test --file pom.xml
```

The included GitHub Actions workflow runs this command on pushes and pull requests to `main`.
