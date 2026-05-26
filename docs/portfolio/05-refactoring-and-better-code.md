# 05 — Refactoring and Better Code

**Related notes/labs**

- `04 - Refactoring1.pdf`
- `04 - HighLevelRefactoring.pdf`
- `04 - BetterCode.pdf`
- `04 - RefactoringLab1.pdf`

## Exercise goal

This exercise applies refactoring and better-code principles to the selected maintenance change. The goal is not to rewrite JHotDraw. The goal is to improve a concrete piece of code while preserving behavior and adding tests.

## Code smell found

In `SVGRectFigure`, the radius setters were responsible for mutating geometry state:

```java
setArcWidth(double newValue)
setArcHeight(double newValue)
setArc(double width, double height)
```

The problem was that radius mutation affects derived geometry. The class has cached fields:

```java
private transient Shape cachedTransformedShape;
private transient Shape cachedHitShape;
```

Changing the radius without invalidation risks stale derived state. That is a maintenance smell because future code can look correct at the setter level while the object remains internally inconsistent.

## Refactoring applied

The refactoring is small and local:

- remove unnecessary old-value locals;
- compare the existing radius with the new radius;
- mutate only when the value changes;
- call `invalidate()` when mutation happens;
- make `setArc(width, height)` update both radius values and invalidate once.

## Before/after intent

Before:

```text
setter changes radius value
but cache invalidation is not consistently tied to that mutation
```

After:

```text
setter changes radius value
and invalidates the figure as part of the same operation
```

## Changed source

```text
jhotdraw-samples/jhotdraw-samples-misc/src/main/java/org/jhotdraw/samples/svg/figures/SVGRectFigure.java
```

The changed methods are:

```java
public void setArcWidth(double newValue)
public void setArcHeight(double newValue)
public void setArc(double width, double height)
```

## Why this is a refactoring-compatible change

The public API did not change. Existing callers still use the same methods. The internal behavior is safer because the class now preserves its own invariants: geometry mutation and invalidation happen together.

## Better-code principles used

| Principle | Application |
|---|---|
| Locality | The change is kept in the class that owns the state. |
| Encapsulation | `SVGRectFigure` is responsible for keeping its geometry valid. |
| Low risk | No UI caller or SVG I/O API had to change. |
| Testability | Radius behavior is now covered by JUnit tests. |
| Simplicity | No new framework or abstraction was introduced. |

## Result

The code is easier to maintain because a future developer can call radius setters without needing hidden knowledge about cache invalidation.
