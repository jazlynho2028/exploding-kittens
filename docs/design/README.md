# **Purpose**
This design document describes the class structure and hierarchy of our Exploding Kittens application.

# **Architecture Overview**
This application follows an MVC (Model - View - Controller) architecture.

# **Key Architectural Constraints**
`ExplodingKittensApp` is the only class that instantiates `DeckBuilder` and passes the resulting `Deck` into `Game`.
`Game` itself never references `DeckBuilder`. This way, Game only needs to know how to execute the game, not the details of how the deck was put together.

**Entry Point**
- ExplodingKittensApp.java
  - Type: main class
  - Responsibility: Application's entry point. Initializes `AssetManager` and handles screen navigation throughout the app. Responsible for building the game model once player setup is complete.

# **Datasource**
This layer handles all the file loading.
- interface FileLoader.java
  - Responsibility: Defines methods for loading a file and getting its URL. 
- FontLoader.java implements FileLoader
  - Responsibility: Loads a font file and returns its URL. 
- ImageLoader.java implements FileLoader
  - Responsibility: Loads an image file and returns its URL. 
- PathLoader.java implements FileLoader
- interface PathLoader.java
  - Responsibility: Defines methods for loading a file and reading its contents. 
- IconLoader.java implements PathLoader
  - Responsibility: Reads an icon file and returns its contents to display icons in the UI. 

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

**Class Relationships**