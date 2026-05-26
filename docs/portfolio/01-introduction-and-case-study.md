# 01 — Introduction and Case Study

**Related notes/labs**

- `01 - IntroductionSB5MAI.pdf`
- `01 - IntroLab.pdf`
- `02 - JHotDraw.pdf`

## Exercise goal

The first exercise is about selecting and preparing a realistic existing software system for maintenance work. The system should be large enough that maintenance activities make sense: understanding existing code, locating a feature, assessing impact, changing code, testing it, and setting up continuous integration.

## Selected system

The selected system is **JHotDraw**, using the Maven-based fork:

```text
https://github.com/wumpz/jhotdraw
```

This fork was selected because it matches the course material clues:

- Maven project structure.
- SVG sample application.
- Main class reference compatible with the lab material: `org.jhotdraw.samples.svg.Main`.
- Separate Maven modules, including `jhotdraw-samples-misc`.

## Why this is a suitable maintenance case

JHotDraw is a good maintenance exercise target because it contains:

- a non-trivial GUI/editor architecture;
- domain objects for figures and drawings;
- import/export code;
- UI handles and tools;
- reusable framework abstractions;
- enough existing code to make concept location and impact analysis meaningful.

## Selected feature area

The selected maintenance area is **rounded rectangle radius editing** in the SVG editor.

The key class is:

```text
jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/svg/figures/SVGRectFigure.java
```

The supporting UI handle is:

```text
jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/svg/figures/SVGRectRadiusHandle.java
```

## Initial understanding

The SVG rectangle figure stores its rectangle bounds and rounded-corner values inside a `RoundRectangle2D.Double`. The horizontal radius is stored as `arcwidth`, and the vertical radius is stored as `archeight`.

The editor also uses cached transformed and hit-test shapes. That means geometry changes must invalidate cached derived state. Otherwise the saved state, visual state, and hit-test state can drift apart.

## Result of this exercise

The case study was set up as a Maven project inside this repository. A focused maintenance change was selected so that the later exercises could be answered with concrete code instead of only theory.
