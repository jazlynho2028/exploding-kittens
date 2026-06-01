# **Purpose**
This design document describes the class structure and hierarchy of our Exploding Kittens application.

# **Architecture Overview**
This application follows an MVC (Model - View - Controller) architecture.

# **Key Architectural Constraints**
`ExplodingKittensApp` is the only class that instantiates `DeckBuilder` and passes the resulting `Deck` into `Game`.
`Game` itself never references `DeckBuilder`. This way, Game only needs to know how to execute the game, not the details of how the deck was put together.

# **Entry Point**
- ExplodingKittensApp.java
  - Type: main class
  - Responsibility: Application's entry point. Sets up the AssetManager, handles switching between screens, and builds the game model once players are confirmed.

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

# **Additional UI Classes**
These classes support the View and Controller layers. 

- SceneManager.java
- AssetManager.java
- UIConstants.java

# **View Classes**
These classes build the UI for their screen and pass any button or input events to their paired Controller.

- StartView.java
  - Responsibility: Builds the title screen with two play buttons, one for English and one for Spanish.
- PlayerCreateView.java
  - Responsibility: Builds the player name entry screen. Shows a list of text fields for player names, an add player button that gets disabled once the max number of players is reached, and a confirm button.
- PlayerDecksView.java
  - Responsibility: Builds the main game screen. Shows the players, card piles, the current player's hand, and turn controls. 

# **Controller Classes**
These classes sit between the View and Model layers. They handle events from their corresponding View class and update the Model accordingly

- StartController.java
  - Responsibility: Connects the two language buttons in StartView to their handlers, which tell ExplodingKittensApp which language to load.
- PlayerCreateController.java
  - Responsibility: Handles the player creation screen. Adds name fields up to the max player count, then triggers game initialization on confirm.
- PlayerDecksController.java
  - Responsibility: The main game controller. Handles all player interactions on the game screen and updates the view after each action.

**Model Classes**
- Game.java
- TurnManager.java
- DeckBuilder.java
- Player.java
- Deck.java
- Card.java
- CardType.java

**Class Relationships**