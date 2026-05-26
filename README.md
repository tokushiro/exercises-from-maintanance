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

## Portfolio documents

Start here:

- [`docs/portfolio/README.md`](docs/portfolio/README.md)

Separated by lecture/lab:

- [`01-introduction-and-case-study.md`](docs/portfolio/01-introduction-and-case-study.md)
- [`02-change-and-requirements.md`](docs/portfolio/02-change-and-requirements.md)
- [`03-concept-location.md`](docs/portfolio/03-concept-location.md)
- [`04-impact-analysis-and-ci.md`](docs/portfolio/04-impact-analysis-and-ci.md)
- [`05-refactoring-and-better-code.md`](docs/portfolio/05-refactoring-and-better-code.md)
- [`06-actualization-clean-architecture-and-design.md`](docs/portfolio/06-actualization-clean-architecture-and-design.md)
- [`07-testing.md`](docs/portfolio/07-testing.md)
- [`08-bdd.md`](docs/portfolio/08-bdd.md)
- [`09-conclusion-and-technical-debt.md`](docs/portfolio/09-conclusion-and-technical-debt.md)
- [`coverage-matrix.md`](docs/portfolio/coverage-matrix.md)

The original combined report is also kept:

- [`software-maintenance-portfolio.md`](docs/portfolio/software-maintenance-portfolio.md)

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
