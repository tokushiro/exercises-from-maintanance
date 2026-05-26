# Coverage Matrix — Course Notes and Exercises

This matrix shows how the repository answers the Software Maintenance notes/labs.

| Course material | Covered in repository | Evidence |
|---|---|---|
| `01 - IntroductionSB5MAI.pdf` | Yes | Case-study setup and maintenance workflow explained in `01-introduction-and-case-study.md`. |
| `01 - IntroLab.pdf` | Yes | JHotDraw selected and prepared as the maintenance system. |
| `02 - JHotDraw.pdf` | Yes | Maven-based JHotDraw fork used as the case study. |
| `02 - ChangeLec.pdf` | Yes | Change type and scope explained in `02-change-and-requirements.md`. |
| `02 - ChangeReqLec.pdf` | Yes | Change request, user story, and acceptance criteria written. |
| `02 - ChangeReqLab.pdf` | Yes | The selected radius consistency change is specified as a lab-style change request. |
| `02 - ConceptLocation.pdf` | Yes | Concept location method and result documented in `03-concept-location.md`. |
| `02 - CLLab1.pdf` | Yes | Rounded rectangle radius concept traced to figure, handle, and I/O classes. |
| `03 - ImpactAnalysis.pdf` | Yes | Impact table and risk analysis in `04-impact-analysis-and-ci.md`. |
| `03 - AnalysisLab1.pdf` | Yes | Affected packages/classes listed with impact levels. |
| `03 - ContinuesIntegration.pdf` | Yes | CI explanation and GitHub Actions setup documented. |
| `03 - CILab.pdf` | Yes | `.github/workflows/maven.yml` runs Maven tests. |
| `03 - IntroSwProcesses.pdf` | Yes | The workflow follows an incremental maintenance process: request → analysis → implementation → test → CI. |
| `03 - TeamProcesses.pdf` | Partly | Team-process concepts are reflected through Git/GitHub/CI workflow, though this is a single-student repository. |
| `04 - Refactoring1.pdf` | Yes | Refactoring described in `05-refactoring-and-better-code.md`. |
| `04 - HighLevelRefactoring.pdf` | Yes | Scope kept local; no unnecessary architecture rewrite. |
| `04 - BetterCode.pdf` | Yes | Better-code rationale and invariant cleanup documented. |
| `04 - RefactoringLab1.pdf` | Yes | Concrete refactoring implemented in `SVGRectFigure`. |
| `05 - Actualization.pdf` | Yes | Actual implemented source change documented in `06-actualization-clean-architecture-and-design.md`. |
| `05 - ActualizationLab.pdf` | Yes | Source code, tests, and verification included. |
| `05 - OOPrinciples.pdf` | Yes | OO/SOLID principles mapped to the case study. |
| `05 - Clean Architecture.pdf` | Yes | Layer-style mapping from UI handle to domain figure to persistence. |
| `06 - CleanCode.pdf` | Yes | Clean-code notes applied to local invariant and simple implementation. |
| `06 - DesignPrinciplesAndPatterns.pdf` | Yes | Figure/handle/editor pattern and design principle discussion included. |
| `07 - Software Testing.pdf` | Yes | Testing approach documented in `07-testing.md`. |
| `07 - TestLab1.pdf` | Yes | JUnit tests implemented in `SVGRectFigureTest`. |
| `09 - BDD.pdf` | Yes | Gherkin-style scenarios written in `08-bdd.md`. |
| `09 - BDDLab.pdf` | Yes | BDD scenarios mapped to executable JUnit tests. |
| `10 - Example of software change.pptx.pdf` | Yes | Concrete software change summarized in `09-conclusion-and-technical-debt.md`. |
| `10 - Conclusion of software change.pdf` | Yes | Maintenance workflow conclusion included. |
| `11 - BeyondTechnicalDebt.pdf` | Yes | Technical debt reflection and future work included. |
| `[Litt] Literature List.pdf` | Referenced conceptually | No separate literature review was required for the implemented exercise portfolio. |

## Summary

All exercise-oriented notes are represented. Some lecture-only notes are covered as theory sections rather than separate code tasks, because they do not each require a separate implementation artifact.
