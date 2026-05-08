# PoloGraphPlan

A Java Swing application implementing the **Graphplan** automated planning algorithm.

> Describe a problem as a graph of conditions (operators), define the Initial State and the Goal State — PoloGraphPlan will extract in seconds the sequence of steps needed to reach it.

Academic project for the course *Planning Systems: Knowledge Representation and Reasoning* @ UNIRIO (2011–2014).

---

## How it works

Graphplan builds a **planning graph** — a layered structure alternating between *state levels* and *action levels* — and then extracts a valid plan through backward search.

Key concepts:
- **Mutex relations between actions**: Inconsistent Effects, Interference, Competing Needs
- **Mutex relations between states**: Inconsistent Support
- **No-Op (maintenance) actions** to persist states across levels
- **Closed World Assumption (CWA)** for automatic initial state completion
- **ADL input format** for problem description

---

## Getting started

**Requirements:** Java 6 or later.

```bash
cd dist
java -jar PoloGraphPlan.jar
```

Or build with NetBeans and run from the IDE.

### Usage

1. Click **Load** to open an `.adl` problem file from `problemas/`
2. Click **Run** to execute the planner
3. The output panel shows numbered plan steps and elapsed time
4. Enable **debug mutex** to trace mutex relation details

---

## ADL problem format

Problems are described in a subset of ADL (Action Description Language):

```
Facts(Type(instance) ^ ...)       # optional — typed object declarations

Init(Predicate(args) ^ ...)       # initial state
Goal(Predicate(args) ^ ...)       # goal state

Action(
    Name(param: Type, ...)
        PRECOND: Predicate(args) ^ ...
        EFFECT:  Predicate(args) ^ ¬Predicate(args) ^ ...
)
```

Negation uses `¬`. The `^` operator separates conjuncts.

---

## Example problems

### Cooking (`problemas/Cozinhar.adl`)

**Init:** dirty kitchen · clean hands · silence  
**Goal:** clean kitchen · gift wrapped · dinner ready

```
Init(has(cozinhaSuja) ^ has(maoLimpa) ^ has(silencio))
Goal(¬has(cozinhaSuja) ^ has(presente) ^ has(janta))

Action(
    Cozinhar
        PRECOND: has(maoLimpa)
        EFFECT: has(Janta)
)
Action(
    Embrulhar
        PRECOND: has(silencio)
        EFFECT: has(presente)
)
Action(
    Carry
        PRECOND:
        EFFECT: ¬has(cozinhaSuja) ^ ¬has(maoLimpa)
)
```

### Flat tire (`problemas/Pneus.adl`)

**Init:** flat tire on axle · spare in trunk  
**Goal:** spare tire on axle

Uses CWA literals — the planner infers missing initial conditions automatically.

### Airports (`problemas/Aeroportos.adl`)

**Init:** cargo C1/C2 and planes P1/P2 at SFO and JFK  
**Goal:** cargo swapped between airports

```
Facts(Cargo(C1) ^ Cargo(C2) ^ Airport(JFK) ^ Airport(SFO) ^ Plane(P1) ^ Plane(P2))

Init(At(C1,SFO) ^ At(C2,JFK) ^ At(P1,SFO) ^ At(P2,JFK))
Goal(At(C1,JFK) ^ At(C2,SFO))

Action(
    Load(c: Cargo, p: Plane, a: Airport)
        PRECOND: At(c,a) ^ At(p,a)
        EFFECT: ¬At(c,a) ^ In(c,p)
)
Action(
    Unload(c: Cargo, p: Plane, a: Airport)
        PRECOND: In(c,p) ^ At(p,a)
        EFFECT: At(c,a) ^ ¬In(c,p)
)
Action(
    Fly(p: Plane, from: Airport, to: Airport)
        PRECOND: At(p,from)
        EFFECT: ¬At(p,from) ^ At(p,to)
)
```

### Rockets (`problemas/Rockets.adl`)

**Init:** cargo A and B, rocket R — all at location L, rocket has fuel  
**Goal:** cargo A and B at location P

---

## Project structure

```
src/
└── com/extrapolo/graphplan/
    ├── control/
    │   ├── Planner.java   # planning graph construction and solution extraction
    │   └── Util.java      # ADL parser and combinatorics utilities
    ├── model/
    │   ├── Mutexable.java # base class — id, level, mutex tracking
    │   ├── Action.java    # action model with mutex detection
    │   └── State.java     # state/literal model
    └── view/
        └── Screen.java    # Swing UI
problemas/                 # sample ADL problem files
PoloPlan/                  # course slides and Graphplan reference paper
```

---

## Algorithm outline

1. **Expand graph** — build alternating state/action layers from the initial state; add no-op actions for each state
2. **Detect mutex** — at each layer, mark action/state pairs that cannot coexist
3. **Check goal** — when all goal states appear without mutex in a state layer, attempt extraction
4. **Extract solution** — backward search filtering mutex-free action combinations at each level; reverse the result

---

## References

- Blum, A. L., & Furst, M. L. (1997). Fast planning through planning graph analysis. *Artificial Intelligence*, 90(1–2), 281–300. ([PDF](PoloPlan/graphplan.pdf))
- Russell, S., & Norvig, P. *Artificial Intelligence: A Modern Approach*.
