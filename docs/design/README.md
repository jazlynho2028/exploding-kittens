# **Purpose**
This design document describes the class structure and hierarchy of our Exploding Kittens application.

# **Architecture Overview**
This application follows an MVC (Model - View - Controller) architecture.

# **Key Architectural Constraints**
`ExplodingKittensApp` is the only class that instantiates `DeckBuilder` and passes the resulting `Deck` into `Game`.
`Game` itself never references `DeckBuilder`. This way, Game only needs to know how to execute the game, not the details of how the deck was put together.

**Entry Point**
- ExplodingKittensApp.java

**Datasource**
- interface FileLoader.java
- FontLoader.java implements FileLoader
- ImageLoader.java implements FileLoader
- PathLoader.java implements FileLoader
- interface PathLoader.java
- IconLoader.java implements PathLoader

**Additional UI Classes**
- SceneManager.java
- AssetManager.java
- UIConstants.java

**View Classes**
- StartView.java
- PlayerCreateView.java
- PlayerDecksView.java

**Controller Classes**
- StartController.java
- PlayerCreateController.java
- PlayerDecksController.java

**Model Classes**
- Game.java
- TurnManager.java
- DeckBuilder.java
- Player.java
- Deck.java
- Card.java
- CardType.java
