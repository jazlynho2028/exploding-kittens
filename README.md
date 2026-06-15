![Gradle Build](https://github.com/nu-cs-sqe/course-project-20252603-team-14-20252603/actions/workflows/main.yml/badge.svg)

[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23583898)
# Exploding Kittens

## Contributors
- Irene Ha
- Jazlyn Ho
- Ejean Kuo
- Dong Joo Kwon
- Luke Meyer

## Dependencies
- JDK 11
- JUnit 5.10
- Gradle 8.10

## Acknowledgements
REFERENCES, SOURCE OF HELP ETC

## Special Decisions and Exceptions
**(1) Unachievable 100% Line Coverage due to Empty Lambdas**
Our project does not achieve 100% code coverage due to the use of
intentionally empty lambda expressions in several controller classes.
These lambdas are used as default callback implementations before a
controllers event handlers are configured.

Example `src/main/java/ui/PlayerCreateController.java` Line 30
```java
this.onError = message -> { };
```

Because the lambda is empty, executing it doesn't cause anything to happen.
As a result, coverage tools may report it as uncovered even though it is
functioning as designed. Team 14 believes this to be a justified use.

**(2) Unachievable 100% Line Coverage due to Default Throw**
Our project does not achieve 100% code coverage in model class Game.java
because of the use of a default throw, of which no implementation will ever 
reach. 

Example `src/main/java/domain/Game.java` Line 418
```
 throw new IllegalStateException("error.currentPlayerNoCardOfCardtype");
``` 
We chose to keep this throw as a defensive fail-safe to ensure 
the game never enters an undefined state.

**SpotBug Suppressions**
There were several SpotBug warnings intentionally suppressed as they
conflicted with project design requirements or testability

**SpotBug Suppressions**
There were several SpotBug warnings intentionally suppressed as they
conflicted with project design requirements or testability

- EI_EXPOSE_REP2
  These classes utilize Dependency Injection to receive live, shared references to
  mutable domain models, configurations, or UI views. Performing defensive copying
  inside these constructors is intentionally omitted for two primary reasons:
    - One, It would decouple the class from the centralized state, breaking real-time updates
      across the architecture.
    - Two, It would sever the link to the exact mock objects injected by our testing framework
      (EasyMock), making it impossible to verify method interactions or stub dynamic behaviors during unit testing.
      List of affected files below.
        - `src/main/java/ui/StartController.java`
        - `src/main/java/ui/PlayerCreateController.java`
        - `src/main/java/ui/PlayerDeckController.java`
        - `src/main/java/ui/ErrorController.java`
        - `src/main/java/domain/Game.java`
        - `src/main/java/domain/Deck.java`
        - `src/main/java/domain/DeckBuilder.java`

- CT_CONSTRUCTOR_THROW
  Constructors perform validation of inputs and may throw an
  IllegalArgumentException when invalid data is provided. This is intentional
  as validating constructor arguments is the responsibility of the respective
  classes, and they cannot be made final because doing so would reduce testability.
  List of affected files below.
    - `src/main/java/domain/Game.java`
    - `src/main/java/domain/TurnManager.java`

**Game Rule Clarifications**
There are two rules pertaining to cards that we want to specify as
special exceptions.

- Exploding Kitten cards are not playable cards, they cannot be selected
  and played during a player's turn
- Defuses are not considered playable cards unless a player draws an
  Exploding Kitten and has to defuse it

**Constructor Design Decisions**
The `src/main/java/domain/Game.java` constructor accepts four parameters:

```java
public Game(List<Player> players,
            Deck drawPile,
            Deck discardPile,
            TurnManager turnManager)
```

This was intentionally done to support dependency injection and
unit testing. Providing these dependencies through the constructor
allows for us to utilize mocks when verifying `Game.java` behavior

We realize that constructors with many parameters can complicate things,
but our team decided that all dependencies are necessary in Game.java
and constructor injection leads to the most testable design.

