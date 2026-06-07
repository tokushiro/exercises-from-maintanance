# Theory recap — mapped to the SVGRectFigure feature

**Submission by:** Tokushiro

This document pulls the named theory from the course (Fowler smells, Rule of Three, SOLID, GRASP, Law of Demeter, low coupling / high cohesion, scattering / entanglement, concept-location techniques) and shows where each one appears in the selected maintenance change.

The selected change: rounded-rectangle radius setters in `SVGRectFigure` now invalidate the figure's derived geometry when radius values change.

---

## 1. The 8 software-change phases

The lecturer asked us to memorise the sequence:

```
Initiation -> Concept Location -> Impact Analysis -> Prefactoring
-> Actualization -> Postfactoring -> Verification -> Conclusion
```

| Phase | Where it is in this repo |
|---|---|
| Initiation | `exercises/02`, `portfolio/02` |
| Concept Location | `exercises/03`, `portfolio/03` |
| Impact Analysis | `exercises/04`, `portfolio/04` |
| Prefactoring | `exercises/06` (Prefactoring section), `portfolio/05` |
| Actualization | `exercises/07`, `portfolio/06` |
| Postfactoring | `exercises/06` (Postfactoring section), `portfolio/05` |
| Verification | `exercises/08`, `portfolio/07` |
| Conclusion | `portfolio/09` |

---

## 2. Code smells (Fowler catalog)

The full catalog the course expects us to recognise:

| Smell | One-line meaning | Present in this change? |
|---|---|---|
| Duplicated code | Same logic in multiple places | Borderline — three setters had the same intent (handled by consolidation). |
| Long method | Method does too much | No. |
| Long parameter list | Too many parameters | No. |
| Large class | Class has too many responsibilities | `SVGRectFigure` is large overall but our change does not enlarge it. |
| Switch statements | Type-by-case branching | No. |
| Primitive obsession | Using primitives instead of small types | Radius values stay primitive; out of scope to wrap. |
| Lazy class | Class no longer pulling its weight | No. |
| Speculative generality | Abstractions for "future" needs | No. |
| Temporary field | Field used only sometimes | The unused `oldValue` locals were the close cousin — removed. |
| Data clumps | Same group of fields travelling together | `arcWidth` + `arcHeight` travel together — handled by keeping `setArc(width, height)` as one operation. |
| Feature envy | Method more interested in another class's data | Putting invalidation in callers would have created this — avoided. |
| Divergent change | One class changed for many reasons | Risk: geometry-mutation reason and cache-invalidation reason both lived in the setter without being linked. Fixed. |
| Shotgun surgery | One change touches many classes | Risk if callers owned invalidation. Avoided. |
| Message chains | `a.getB().getC().getD()` style | No. |
| Middle man | Class only delegates | No. |
| Comments hiding bad code | Comments instead of clarifying code | No. |

The smell that actually applied: **incomplete/unsafe state update** (mutation without invariant maintenance), close to *Divergent Change* with an *Unused Variable* aftertaste.

---

## 3. Rule of Three

> Once is fine, twice is suspicious, three times is a refactor.

Applied here: three radius setters (`setArcWidth`, `setArcHeight`, `setArc`) all need the same "compare → mutate → invalidate" shape. With one or two, leaving the code inline is reasonable; at three, we consolidate the pattern. This is exactly the threshold Fowler describes for moving from acceptance to refactor.

---

## 4. SOLID

| Letter | Principle | In this change |
|---|---|---|
| S | Single Responsibility | `SVGRectFigure` owns rectangle geometry. `SVGRectRadiusHandle` owns user interaction. The fix preserves that split — we did not move drawing/IO concerns into the figure. |
| O | Open/Closed | The public API of `SVGRectFigure` (`setArcWidth`, `setArcHeight`, `setArc`, getters) is unchanged. Callers are not modified. Behaviour is extended through the existing extension points. |
| L | Liskov Substitution | `SVGRectFigure` is still a valid `SVGFigure` / `Figure`. No contract was tightened or broken — calling code that depends on the figure abstraction still works. |
| I | Interface Segregation | SVG rectangle radius logic stays inside SVG rectangle classes. We did not push radius-specific methods into a wider interface that would force unrelated figure types to know about it. |
| D | Dependency Inversion | The editor handle (`SVGRectRadiusHandle`) depends on figure abstractions, not on `SVGRectFigure`'s internal cache fields. The cache invariant is hidden behind the public setters. |

---

## 5. GRASP (Larman)

| GRASP pattern | One-line meaning | In this change |
|---|---|---|
| Information Expert | Give responsibility to the class that has the information | Radius invalidation lives in `SVGRectFigure` because that class holds the radius fields and the cached derived shapes. It is the expert. |
| Creator | The class that uses or aggregates B should create B | `SVGRectFigure` creates and owns its `RoundRectangle2D.Double`. The handle does not. |
| Controller | A non-UI class that receives system events | `SVGRectRadiusHandle` plays the controller role for the radius gesture, translating the drag into setter calls on the figure. |
| Low Coupling | Reduce dependencies between classes | The handle only needs `setArcWidth/Height/Arc`. It does not know about the cache fields. |
| High Cohesion | Keep related responsibilities together | All radius invariants live next to the radius state, in the same class. |
| Polymorphism | Vary behaviour by type without conditionals | `SVGRectFigure` is used through `Figure` / `SVGFigure` in drawing and IO code — we did not add type checks. |
| Pure Fabrication | Invent a class when no domain class fits | Not used here; the existing domain classes were enough. |
| Indirection | Use an intermediate to decouple | The base `invalidate()` method is the indirection point that hides cache details. |
| Protected Variations | Shield from variation behind a stable interface | Cached shape implementation can change without callers noticing because they only touch the public API. |

---

## 6. Law of Demeter (Principle of Least Knowledge)

> A method should only talk to: itself, its fields, parameters, objects it creates, and objects returned by its own methods.

In this change:

- `SVGRectRadiusHandle` talks to `SVGRectFigure` only through its public setters and getters. It never reaches into the figure's cached shape fields.
- `SVGRectFigure` mutates its own `RoundRectangle2D.Double` and calls its own `invalidate()`. It does not chain through other figures to invalidate them.

If callers had been required to do `figure.getCachedTransformedShape().invalidate()`, that would have been a Demeter violation. Encapsulating the invariant in the setter avoids it.

---

## 7. Low coupling, high cohesion

| Quality | Evidence |
|---|---|
| Low coupling | Only `SVGRectFigure` knows about its cache. The handle, IO classes, and tests do not depend on those internals. |
| High cohesion | Radius state, radius invariants, and radius invalidation all live in the same class. |

---

## 8. Concept-location techniques

The course names three:

| Technique | Used here? | How |
|---|---|---|
| Grep / keyword search | Yes | Searched for `SVGRect`, `rect`, `round`, `rounded`, `arcWidth`, `arcHeight`, `radius`. This was sufficient to find the owning class on its own. |
| Dependency search | Yes (lightly) | Followed callers of `SVGRectFigure` setters to `SVGRectRadiusHandle` and SVG IO factories to confirm the feature reach. |
| Runtime debugging | Not needed | The keyword + dependency walk gave a small, confident initial set. Runtime tracing (Featureous-style) would have been the next step if the feature had been more scattered. |

---

## 9. Impact analysis vocabulary

| Term | Meaning | This change |
|---|---|---|
| Initial impact set | The classes we know we have to change | `{ SVGRectFigure }` |
| Estimated impact set | Classes we *might* have to touch after analysis | 5 packages, ~17 classes visited (see `04-impact-analysis.md`) |
| Scattering | One feature spread across many classes | Light: radius is mentioned by the handle and by SVG IO factories, but the invariant has one home. |
| Entanglement | One class mixing many features | Low for the radius behaviour. `SVGRectFigure` is a big class overall, but the part we touched is concerned only with rectangle geometry. |

The shape of the impact (small initial set, modest estimated set, low scattering, low entanglement) is what made this a low-risk change.

---

## 10. Verification vocabulary

| Term | Meaning | Used here |
|---|---|---|
| Test harness | Infrastructure that lets us run tests | JUnit 5 + AssertJ in `jhotdraw-samples-misc`. |
| Baseline testing | Run the existing tests before changing anything, to know what "works" means | The Maven reactor was built clean before the change. |
| Regression testing | Run all tests after changing to catch unintended breakage | `mvn -B test` runs the whole reactor on push/PR via GitHub Actions. |
| Acceptance testing | Verify the user-visible behaviour matches the user story | The four BDD scenarios in `08-bdd.md` map onto the four JUnit tests. |
| BDD (Given/When/Then) | Behaviour described from the user's point of view | All four scenarios use this form. |

---

## 11. Maintenance type (ISO/IEC 14764)

| Type | Meaning | This change |
|---|---|---|
| Corrective | Fix a defect | Yes — radius mutation now correctly invalidates derived state. |
| Adaptive | Adapt to a changed environment | No. |
| Perfective | Improve performance / maintainability | Partial — early-return on no-op writes avoids unnecessary invalidation. |
| Preventive | Reduce future defect risk | Yes — tests + invariant encapsulation make regressions less likely. |

So the change is mostly **corrective + preventive**.
