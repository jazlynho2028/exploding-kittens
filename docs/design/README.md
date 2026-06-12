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
- interface PathLoader.java
  - Responsibility: Defines methods for loading a file and reading its contents. 
- IconLoader.java implements PathLoader
  - Responsibility: Reads an icon file and returns its contents to display icons in the UI. 
- CardMetadataLoader.java implements FileLoader
  - Responsibility: Loads a card metadata JSON file and returns a map of card IDs to CardMetadata objects, providing display information (title, subtitle, description) for each card type. 
- StringsBundleLoader.java
  - Responsibility: Loads a localized strings bundle for the given locale and returns the current ResourceBundle, providing UI strings for the current language.
- StyleSheetLoader.java implements FileLoader
  - Responsibility: Loads a CSS stylesheet file and returns its URL to apply styling to the UI.

# **Additional UI Classes**
These classes support the View and Controller layers. 

- AssetManager.java implements AssetProvider
  - Responsibility: Manages all loaded assets and provides them to the View and Controller layers.
- interface AssetProvider.java
  - Responsibility: Defines methods for accessing loaded assets such as images, strings, stylesheets, and card metadata.
- UIConstants.java
- CardMetadata.java
  - Responsibility: Holds display information for a card, including its title, subtitle, and description. Used by the view to render card fronts.
- ErrorHandler.java
  - Responsibility: Wraps controller actions in a try-catch and routes any thrown exceptions to the error handler callback.

# **View Classes**
These classes build the UI for their screen and pass any button or input events to their paired Controller.

- StartView.java
  - Responsibility: Builds the title screen with two play buttons, one for English and one for Spanish.
- PlayerCreateView.java
  - Responsibility: Builds the player name entry screen. Shows a list of text fields for player names, an add player button that gets disabled once the max number of players is reached, and a confirm button.
- PlayerDecksView.java
  - Responsibility: Builds the main game screen. Shows the players, card piles, the current player's hand, and turn controls. 
- ErrorView.java
  - Responsibility: Builds an error screen that shows a description of the error and a restart button. This screen should never appear unless there is an implementation problem.

# **Controller Classes**
These classes sit between the View and Model layers. They handle events from their corresponding View class and update the Model accordingly

- StartController.java
  - Responsibility: Connects the two language buttons in StartView to their handlers, which tell ExplodingKittensApp which language to load.
- PlayerCreateController.java
  - Responsibility: Handles the player creation screen. Adds name fields up to the max player count, then triggers game initialization on confirm.
- PlayerDecksController.java
  - Responsibility: The main game controller. Handles all player interactions on the game screen and updates the view after each action.
- ErrorController.java
  - Responsibility: Handles the error screen. Binds the restart button to bring the user back to the start screen.

# **Model Classes**
The Model layer contains all game logic.

- Game.java
  - Responsibility: The core game state. Holds the players, the draw pile, and a TurnManager. Deals starting hands, inserts Exploding Kittens when the game starts, and handles drawing and playing cards. Does **not** reference `DeckBuilder`.
- TurnManager.java
  - Responsibility: Tracks whose turn it is, how many draws the current player has left, and the current round. Takes numPlayers as an int to validate and manage currentPlayerIndex. Advances to the next player when a turn ends. 
- DeckBuilder.java
  - Responsibility: Builds the starting draw pile with the correct number of each card type, then shuffles and returns it. Called only by ExplodingKittensApp. Game never touches or interacts with it.
- Player.java
  - Responsibility: Represents a player. Stores their name and hand of cards, and supports adding, removing, and selecting cards so the controller knows what the player wants to play.
- Deck.java
  - Responsibility: Represents an ordered pile of cards. Supports drawing, peeking, inserting at a specific position, and shuffling. Used for both the draw and discard piles.
- Card.java
  - Responsibility: Represents a single card. Holds a unique `cardId` and a `CardType` enum value. Also tracks whether the card is currently selected by the player, which is used to determine what action they want to take on their turn.
- CardType.java
  - Responsibility: An enum that defines all card types in the game. Used throughout the model to identify and categorize cards.
- GameConstants.java
  - Responsibility: Defines constant values used throughout the game logic, such as player limits, starting hand size, and draw counts.

# **Class Relationships**
- `ExplodingKittensApp` creates `DeckBuilder`, gets back a `Deck`, then creates `Game(deck, players, discardPile, turnManager)`
- `Game` owns `TurnManager`. a list of players, and both the draw pile and discard pile `Deck`s
- `TurnManager` takes numPlayers (int), not a players list
- `Deck` stores a collection of `Card`s
- `Card` holds a `CardType`
- Each Controller holds a reference to its paired View; `PlayerDeckController` also holds a reference to `Game`
- `AssetManager` holds instances of `FontLoader`, `ImageLoader`, and `IconLoader` 