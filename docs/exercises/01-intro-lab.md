# Exercise 01 — Intro Lab

**Related lab note:** `01 - IntroLab.pdf`  
**Submission by:** Tokushiro

## Lab objective

The lab asks us to get started with the course case study, Maven, and GitHub workflow.

The original lab tasks are:

1. Create a GitHub fork/repository for the JHotDraw project.
2. Follow GitHub flow for future work.
3. Install/use Maven with a suitable JDK.
4. Build the project.
5. Run the SVG sample application with `org.jhotdraw.samples.svg.Main`.

## Answer

For this portfolio I used the Maven-based JHotDraw fork:

```text
https://github.com/wumpz/jhotdraw
```

It was copied into my own repository:

```text
Tokushiro/Exercises-from-Maintanance
```

The project is a multi-module Maven project and contains the SVG sample application required by the lab.

## GitHub workflow used

The work was done in a Git repository and pushed to GitHub on the `main` branch. The repository contains both:

- source code for the JHotDraw case study;
- portfolio documentation for the Software Maintenance exercises.

The final commit is authored as:

```text
Tokushiro <Tokushiro@users.noreply.github.com>
```

## Maven build verification

The course lab suggests Maven commands such as:

```bash
mvn clean install -DskipTests
```

For the final repository, the important verification command is:

```bash
mvn -B test --file pom.xml
```

In this environment Maven was run through Docker:

```bash
docker run --rm \
  -v /var/lib/docker/volumes/homelab_hermes_data/_data/work/maintenance-exercises/repo:/workspace \
  -w /workspace \
  maven:3.9.9-eclipse-temurin-17 \
  mvn -B test
```

Result: **BUILD SUCCESS**.

## Selected feature for later exercises

The selected feature is **rounded rectangle radius editing** in the SVG editor.

Main class:

```text
jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/svg/figures/SVGRectFigure.java
```

Related UI handle:

```text
jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/svg/figures/SVGRectRadiusHandle.java
```
