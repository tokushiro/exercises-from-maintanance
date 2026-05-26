# 04 — Impact Analysis and Continuous Integration

**Related notes/labs**

- `03 - ImpactAnalysis.pdf`
- `03 - AnalysisLab1.pdf`
- `03 - ContinuesIntegration.pdf`
- `03 - CILab.pdf`
- `03 - IntroSwProcesses.pdf`
- `03 - TeamProcesses.pdf`

## Exercise goal

This part answers two related maintenance questions:

1. What code may be affected by the requested change?
2. How do we make the change safe to integrate repeatedly?

## Impact analysis method

The analysis followed both code structure and likely runtime behavior:

- locate the owning class;
- inspect callers and neighboring classes;
- inspect import/export boundaries;
- check base figure invalidation behavior;
- add tests close to the changed behavior;
- run the Maven test suite.

## Impact table

| Area/package | Classes checked | Impact level | Notes |
|---|---:|---|---|
| `org.jhotdraw.samples.svg.figures` | 5 | High | Main feature package. `SVGRectFigure` owns the radius state. `SVGRectRadiusHandle` changes it through the public setters. |
| `org.jhotdraw.samples.svg.io` | 4 | Medium | SVG import/export must observe consistent geometry. No direct I/O change was required because the public radius values remain the same. |
| `org.jhotdraw.samples.svg` | 3 | Low/medium | Provides SVG defaults and application context. No direct change needed. |
| `org.jhotdraw.draw.handle` | 2 | Low | Handle conventions were checked to understand how editing operations should update figures. |
| `org.jhotdraw.draw` | 3 | Medium | Base figure behavior and invalidation contracts matter because `SVGRectFigure` caches transformed/hit shapes. |

## Risk analysis

| Risk | Mitigation |
|---|---|
| Cached transformed/hit shape becomes stale after radius change | Radius setters now call `invalidate()` when values change. |
| A future edit changes radius behavior silently | Added JUnit tests in the same Maven module. |
| CI does not run tests for the case study | Added GitHub Actions Maven workflow. |
| Fix becomes too broad | Kept implementation local to `SVGRectFigure`. |
| UI behavior changes unexpectedly | Public setter API remains unchanged; handle code still calls the same methods. |

## Continuous integration setup

The CI workflow is defined at:

```text
.github/workflows/maven.yml
```

It runs on:

- pushes to `main`;
- pull requests targeting `main`.

The workflow uses:

- `actions/checkout@v4`;
- Java 17 with Temurin;
- Maven dependency caching;
- `mvn -B test`.

## Why CI matters here

The change is small, but it is inside an old GUI framework. Manual verification would be fragile. CI gives repeatable feedback that the Maven reactor still compiles and the selected behavior remains tested.

## Verification result

The Maven test suite was verified locally through Docker:

```bash
docker run --rm \
  -v /var/lib/docker/volumes/homelab_hermes_data/_data/work/maintenance-exercises/repo:/workspace \
  -w /workspace \
  maven:3.9.9-eclipse-temurin-17 \
  mvn -B test
```

Result: **BUILD SUCCESS**.
