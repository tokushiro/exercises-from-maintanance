# Exercise 05 — Continuous Integration

**Related lab note:** `03 - CILab.pdf`  
**Submission by:** Tokushiro

## Lab objective

The CI lab asks us to set up a simple CI pipeline using GitHub Actions and Maven. The workflow should build the project and run tests automatically.

## Implemented CI workflow

Workflow file:

```text
.github/workflows/maven.yml
```

The workflow runs on:

- push to `main`;
- pull request to `main`.

## Workflow behavior

The CI pipeline:

1. checks out the repository;
2. sets up Java 17 using Temurin;
3. caches Maven dependencies;
4. runs Maven tests.

Main command:

```bash
mvn -B test
```

## Why this satisfies the lab

The lab asks for a `.yml` file under:

```text
.github/workflows/
```

This repository contains that file and uses it to run the Maven test suite automatically.

## Local verification

The same test command was verified locally through Docker:

```bash
docker run --rm \
  -v /var/lib/docker/volumes/homelab_hermes_data/_data/work/maintenance-exercises/repo:/workspace \
  -w /workspace \
  maven:3.9.9-eclipse-temurin-17 \
  mvn -B test
```

Result: **BUILD SUCCESS**.

## CI value for this maintenance task

CI is useful here because the project is a multi-module Maven codebase. Even a small local change can break another module. Running the test suite automatically makes integration safer.
