package ui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.util.List;

public class PlayerDeckView {

    private final AssetProvider assetProvider;

    public final HBox playerNamesContainer;
    public final Button drawPileButton;
    public final HBox handCardsContainer;
    public final Button handVisibilityButton;
    public final Button startGameButton;
    public final Button playCardsButton;
    public final Button endTurnButton;

    private final StackPane root;
    private final HBox turnControlSection;

    public PlayerDeckView(AssetProvider assetProvider) {
        this.assetProvider = assetProvider;

        root = new StackPane();
        playerNamesContainer = new HBox();
        drawPileButton = new Button();
        handCardsContainer = new HBox();
        handVisibilityButton = new Button();
        startGameButton = new Button();
        playCardsButton = new Button();
        endTurnButton = new Button();
        turnControlSection = new HBox();

        buildUI();
    }

    public Scene createPlayerDeckScene() {
        return new Scene(root, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);
    }

    public void buildAddRenderPlayerNameTags(
            List<String> playerNames,
            int currentPlayerIndex,
            boolean isGameOngoing
    ) {
        buildAndAddPlayerNameTags(playerNames);
        renderPlayerNameTags(currentPlayerIndex, isGameOngoing);
    }

    public void buildAndAddPlayerNameTags(List<String> playerNames) {
        for (String playerName : playerNames) {
            ToggleButton nameTag = buildNameTag(playerName);

            playerNamesContainer.getChildren().add(nameTag);
        }
    }

    public void renderPlayerNameTags(int currentPlayerIndex, boolean isGameOngoing) {
        ObservableList<Node> nameTagButtons = playerNamesContainer.getChildren();

        for (int i = 0; i < nameTagButtons.size(); i++) {
            ToggleButton nameTagButton = (ToggleButton) nameTagButtons.get(i);

            boolean isAtCurrentPlayerIndex = (i == currentPlayerIndex);
            nameTagButton.setSelected(isAtCurrentPlayerIndex);

            nameTagButton.setDisable(isGameOngoing);
        }
    }

    public void renderDrawPile(boolean canDraw, boolean isDrawPileEmpty) {
        drawPileButton.setDisable(!canDraw);
        drawPileButton.setVisible(!isDrawPileEmpty);
    }

    public void renderHandVisibilityButton(boolean isFaceUp) {
        if (isFaceUp) {
            handVisibilityButton.setText(UIConstants.HIDE_HAND_LABEL);
        }
        else {
            handVisibilityButton.setText(UIConstants.SHOW_HAND_LABEL);
        }
    }

    public void buildAndAddPlayerHandCards(
            List<String> currentPlayerHand,
            boolean isFaceUp,
            boolean isBeforeDraw
    ) {
        handCardsContainer.getChildren().clear();

        for (String cardName : currentPlayerHand) {
            ToggleButton handCardButton = buildHandCardButton(
                    cardName,
                    isFaceUp,
                    isBeforeDraw
            );
            handCardsContainer.getChildren().add(handCardButton);
        }
    }

    public void buildAndRenderTurnControlSection(
            boolean isGameOngoing,
            boolean canPlaySelected,
            boolean canEndTurn
    ) {
        buildTurnControlSection(isGameOngoing);
        renderTurnControlSection(canPlaySelected, canEndTurn);
    }

    public void buildTurnControlSection(boolean isGameOngoing) {
        turnControlSection.getChildren().clear();

        turnControlSection.setAlignment(Pos.CENTER_RIGHT);
        turnControlSection.getStyleClass().add("turn-control-section");

        if (isGameOngoing) {
            buildAndAddTurnControlButtonsAfterGameStart();
        }
        else {
            buildAndAddTurnControlButtonsBeforeGameStart();
        }
    }

    public void renderTurnControlSection(boolean canPlaySelected, boolean canEndTurn) {
        playCardsButton.setDisable(!canPlaySelected);
        endTurnButton.setDisable(!canEndTurn);
    }

    public boolean isSelectedHandCard(int handCardIndex) {
        ObservableList<Node> handCardButtons = handCardsContainer.getChildren();
        ToggleButton handCardButton = (ToggleButton) handCardButtons.get(handCardIndex);

        return handCardButton.isSelected();
    }

    private void buildUI() {
        StackPane gameScreen = buildGameScreen();
        root.getChildren().add(gameScreen);
    }

    private StackPane buildGameScreen() {
        StackPane gameScreen = new StackPane();

        VBox contentSection = buildContentSection();
        StackPane overlayLayer = buildOverlayLayer();

        gameScreen.getChildren().addAll(
                contentSection,
                overlayLayer);

        return gameScreen;
    }

    private VBox buildContentSection() {
        VBox contentSection = new VBox();
        contentSection.getStyleClass().add("content-section");

        VBox gameBoardSection = buildGameBoardSection();
        VBox playerChoiceSection = buildPlayerChoiceSection();

        VBox.setVgrow(gameBoardSection, Priority.ALWAYS);
        contentSection.getChildren().addAll(
                gameBoardSection,
                playerChoiceSection
        );

        return contentSection;
    }

    private VBox buildGameBoardSection() {
        VBox gameBoardSection = new VBox();
        gameBoardSection.getStyleClass().add("game-board-section");

        VBox playerHeaderSection = buildPlayerHeaderSection();
        HBox cardPileSection = buildCardPilesSection();

        gameBoardSection.getChildren().addAll(
                playerHeaderSection,
                cardPileSection
        );

        return gameBoardSection;
    }

    private VBox buildPlayerHeaderSection() {
        VBox playerHeaderSection = new VBox();
        playerHeaderSection.setAlignment(Pos.CENTER);
        playerHeaderSection.getStyleClass().add("player-header-section");

        renderPlayerNamesContainer();
        Text playerHeaderCaption = buildCaption(UIConstants.PLAYER_HEADER_CAPTION);

        playerHeaderSection.getChildren().addAll(
                playerNamesContainer,
                playerHeaderCaption
        );

        return playerHeaderSection;
    }

    private void renderPlayerNamesContainer() {
        playerNamesContainer.getStyleClass().add("player-names-container");
    }

    private ToggleButton buildNameTag(String playerName) {
        ToggleButton nameTag = new ToggleButton(playerName);
        nameTag.getStyleClass().addAll(
                "name-tag",
                "h4"
        );
        return nameTag;
    }

    private Text buildCaption(String text) {
        Text caption = new Text(text);
        caption.getStyleClass().add("caption");

        return caption;
    }

    private HBox buildCardPilesSection() {
        HBox cardPileSection = new HBox();
        cardPileSection.setAlignment(Pos.CENTER);
        cardPileSection.getStyleClass().add("card-piles-section");

        VBox drawPileSection = buildDrawPileSection();
        VBox discardPileSection = buildDiscardPileSection();
        cardPileSection.getChildren().addAll(
                drawPileSection,
                discardPileSection
        );

        return cardPileSection;
    }

    private VBox buildDrawPileSection() {
        VBox drawPileSection = new VBox();
        drawPileSection.setAlignment(Pos.CENTER);
        drawPileSection.getStyleClass().add("card-pile-section");

        StackPane drawPileContainer = buildDrawPileContainer();
        drawPileButton.setDisable(true);

        Text drawPileCaption = buildCaption(UIConstants.DRAW_PILE_CAPTION);

        drawPileSection.getChildren().addAll(
                drawPileContainer,
                drawPileCaption
        );

        return drawPileSection;
    }

    private StackPane buildDrawPileContainer() {
        StackPane drawPileContainer = new StackPane();

        VBox emptyCard = buildEmptyPile();
        buildDrawPileButton();

        drawPileContainer.getChildren().addAll(
                emptyCard,
                drawPileButton
        );

        return drawPileContainer;
    }

    private void buildDrawPileButton() {
        drawPileButton.getStyleClass().add("card");

        VBox drawPile = buildCardBack();
        drawPileButton.getStyleClass().add("back");

        drawPileButton.setGraphic(drawPile);
        drawPileButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    private VBox buildEmptyPile() {
        VBox discardPile = new VBox();
        discardPile.setAlignment(Pos.CENTER);
        discardPile.getStyleClass().addAll(
                "card",
                "empty"
        );

        return discardPile;
    }

    private VBox buildCardBack() {
        VBox drawPile = new VBox();
        drawPile.setAlignment(Pos.CENTER);

        ImageView cardBackIconView = buildCardBackIconView();
        VBox explodingKittensText = buildExplodingKittensText();

        drawPile.getChildren().addAll(
                cardBackIconView,
                explodingKittensText
        );

        return drawPile;
    }

    private ImageView buildCardBackIconView() {
        Image cardBackIcon = assetProvider.getImage("placeholder");
        ImageView cardBackIconView = new ImageView(cardBackIcon);

        cardBackIconView.setFitWidth(UIConstants.CARD_BACK_ICON_WIDTH);
        cardBackIconView.setPreserveRatio(true);

        return cardBackIconView;
    }

    private VBox buildExplodingKittensText() {
        VBox explodingKittensText = new VBox();
        explodingKittensText.setAlignment(Pos.CENTER);
        explodingKittensText.getStyleClass().add("exploding-kittens-text");

        Text explodingText = buildExplodingText(UIConstants.EXPLODING_TEXT);
        Text kittensText = buildKittensText(UIConstants.KITTENS_TEXT);

        explodingKittensText.getChildren().addAll(
                explodingText,
                kittensText
        );

        return explodingKittensText;
    }

    private Text buildExplodingText(String text) {
        Text explodingText = new Text(text);
        explodingText.getStyleClass().addAll(
                "exploding-text",
                "h5"
        );

        return explodingText;
    }

    private Text buildKittensText(String text) {
        Text kittensText = new Text(text);
        kittensText.getStyleClass().addAll(
                "kittens-text",
                "h3"
        );

        return kittensText;
    }

    private VBox buildDiscardPileSection() {
        VBox discardPileSection = new VBox();
        discardPileSection.setAlignment(Pos.CENTER);
        discardPileSection.getStyleClass().add("card-pile-section");

        StackPane discardPileContainer = buildDiscardPileContainer();
        Text discardPileCaption = buildCaption(UIConstants.DISCARD_PILE_CAPTION);

        discardPileSection.getChildren().addAll(
                discardPileContainer,
                discardPileCaption
        );

        return discardPileSection;
    }

    private StackPane buildDiscardPileContainer() {
        StackPane discardPileContainer = new StackPane();

        VBox emptyCard = buildEmptyPile();

        discardPileContainer.getChildren().add(emptyCard);

        return discardPileContainer;
    }

    private VBox buildPlayerChoiceSection() {
        VBox playerChoiceSection = new VBox();

        VBox playerHandSection = buildPlayerHandSection();
        buildTurnControlSection(false);

        playerChoiceSection.getChildren().addAll(
                playerHandSection,
                turnControlSection
        );

        return playerChoiceSection;
    }

    private VBox buildPlayerHandSection() {
        VBox playerHandSection = new VBox();
        playerHandSection.setAlignment(Pos.CENTER);

        renderHandVisibilityToggle();
        ScrollPane handScrollPane = buildHandScrollPane();
        Text handCaption = buildCaption(UIConstants.HAND_CAPTION);

        playerHandSection.getChildren().addAll(
                handVisibilityButton,
                handScrollPane,
                handCaption
        );

        return playerHandSection;
    }

    private void renderHandVisibilityToggle() {
        handVisibilityButton.setText(UIConstants.SHOW_HAND_LABEL);
        handVisibilityButton.getStyleClass().addAll(
                "hand-visibility-toggle",
                "h6"
        );
    }

    private ScrollPane buildHandScrollPane() {
        ScrollPane handScrollPane = new ScrollPane();
        handScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        handScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        handScrollPane.getStyleClass().add("scroll-pane");

        renderHandCardsContainer();
        handScrollPane.setContent(handCardsContainer);

        return handScrollPane;
    }

    private void renderHandCardsContainer() {
        handCardsContainer.setAlignment(Pos.CENTER);
        handCardsContainer.setMinWidth(UIConstants.SCENE_WIDTH);
        handCardsContainer.getStyleClass().add("hand-cards-container");
    }

    private ToggleButton buildHandCardButton(
            String cardName,
            boolean isFaceUp,
            boolean isBeforeDraw
    ) {
        ToggleButton handCardButton = new ToggleButton();
        handCardButton.getStyleClass().add("card");

        VBox handCard;

        if (isFaceUp) {
            handCard = buildCardFront(cardName);

            handCardButton.setDisable(!isBeforeDraw);
            handCardButton.getStyleClass().add("front");
        }
        else {
            handCard = buildCardBack();

            handCardButton.getStyleClass().add("back");
        }

        handCardButton.setGraphic(handCard);
        handCardButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        return handCardButton;
    }

    private VBox buildCardFront(String cardName) {
        VBox cardFront = new VBox();

        VBox cardFrontContent = buildCardFrontContent(cardName);

        cardFront.getChildren().add(cardFrontContent);
        return cardFront;
    }

    private VBox buildCardFrontContent(String cardName) {
        VBox cardFrontContent = new VBox();
        cardFrontContent.getStyleClass().add("card-front-content");

        HBox cardHeader = buildCardHeader(cardName);
        StackPane cardVisualSection = buildCardVisualSection(cardName);

        cardFrontContent.getChildren().addAll(
                cardHeader,
                cardVisualSection
        );
        return cardFrontContent;
    }

    private HBox buildCardHeader(String cardName) {
        HBox cardHeader = new HBox();
        cardHeader.setAlignment(Pos.CENTER_LEFT);
        cardHeader.getStyleClass().add("card-header");

        StackPane cardCircle = buildCardCircle();
        VBox cardTitleSection = buildCardTitleSection(cardName);

        cardHeader.getChildren().addAll(
                cardCircle,
                cardTitleSection
        );
        return cardHeader;
    }

    private StackPane buildCardCircle() {
        StackPane cardCircle = new StackPane();
        cardCircle.getStyleClass().add("card-circle");
        return cardCircle;
    }

    private VBox buildCardTitleSection(String cardName) {
        VBox cardTitleSection = new VBox();
        cardTitleSection.getStyleClass().add("card-title-section");

        Text cardTitle = buildCardTitle(cardName);
        Text cardSubtitle = buildCardSubtitle(cardName);

        cardTitleSection.getChildren().addAll(
                cardTitle,
                cardSubtitle
        );
        return cardTitleSection;
    }

    private Text buildCardTitle(String title) {
        Text cardTitle = new Text(title);
        cardTitle.getStyleClass().addAll(
                "card-title",
                "b1"
        );

        return cardTitle;
    }

    private Text buildCardSubtitle(String subtitle) {
        Text cardSubtitle = new Text(subtitle);
        cardSubtitle.getStyleClass().addAll(
                "card-subtitle",
                "b2"
        );

        return cardSubtitle;
    }

    private StackPane buildCardVisualSection(String description) {
        StackPane cardVisualSection = new StackPane();

        ImageView cardImageView = buildCardImageView();
        HBox cardDescriptionSection = buildCardDescriptionSection(description);

        Insets inset = new Insets(
                0,
                0,
                UIConstants.CARD_IMAGE_BOTTOM_PADDING,
                0
        );
        StackPane.setMargin(cardDescriptionSection, inset);

        cardVisualSection.getChildren().addAll(
                cardImageView,
                cardDescriptionSection
        );
        return cardVisualSection;
    }

    private ImageView buildCardImageView() {
        Image cardImage = assetProvider.getImage("placeholder");
        ImageView cardImageView = new ImageView(cardImage);

        cardImageView.setFitWidth(UIConstants.CARD_IMAGE_WIDTH);
        cardImageView.setFitHeight(UIConstants.CARD_IMAGE_HEIGHT);

        return cardImageView;
    }

    private HBox buildCardDescriptionSection(String description) {
        HBox cardDescriptionSection = new HBox();
        cardDescriptionSection.setAlignment(Pos.BOTTOM_CENTER);
        cardDescriptionSection.getStyleClass().add("card-description-section");

        SVGPath leftBracketIcon = buildLeftBracketIcon();
        Text cardDescription = buildCardDescription(description);
        SVGPath rightBracketIcon = buildRightBracketIcon();

        cardDescriptionSection.getChildren().addAll(
                leftBracketIcon,
                cardDescription,
                rightBracketIcon
        );
        return cardDescriptionSection;
    }

    private SVGPath buildLeftBracketIcon() {
        SVGPath leftBracketIcon = buildIcon("left-bracket");
        leftBracketIcon.getStyleClass().add("bracket-icon");

        return leftBracketIcon;
    }

    private SVGPath buildRightBracketIcon() {
        SVGPath rightBracketIcon = buildLeftBracketIcon();
        rightBracketIcon.setScaleX(-1);

        return rightBracketIcon;
    }

    private Text buildCardDescription(String description) {
        Text cardDescription = new Text(description);

        cardDescription.setWrappingWidth(UIConstants.CARD_DESCRIPTION_WRAPPING_WIDTH);
        cardDescription.getStyleClass().addAll(
                "card-description",
                "b2"
        );

        return cardDescription;
    }

    private void buildAndAddTurnControlButtonsAfterGameStart() {
        renderTurnControlButton(playCardsButton, UIConstants.PLAY_CARDS_LABEL);
        renderTurnControlButton(endTurnButton, UIConstants.END_TURN_LABEL);

        turnControlSection.getChildren().addAll(
                playCardsButton,
                endTurnButton
        );
    }

    private void buildAndAddTurnControlButtonsBeforeGameStart() {
        renderTurnControlButton(startGameButton, UIConstants.START_GAME_LABEL);

        turnControlSection.getChildren().add(startGameButton);
    }

    private void renderTurnControlButton(Button turnControlButton, String label) {
        turnControlButton.setText(label);
        turnControlButton.getStyleClass().addAll(
                "turn-control-button",
                "h5"
        );
    }

    private StackPane buildOverlayLayer() {
        StackPane overlayLayer = new StackPane();
        overlayLayer.setPickOnBounds(false);

        Button restartButton = buildRestartButton();
        overlayLayer.getChildren().add(restartButton);

        Insets insets = new Insets(
                UIConstants.SCREEN_OVERLAY_INSETS,
                UIConstants.SCREEN_OVERLAY_INSETS,
                0,
                0
        );
        StackPane.setMargin(restartButton, insets);
        StackPane.setAlignment(restartButton, Pos.TOP_RIGHT);

        return overlayLayer;
    }

    private Button buildRestartButton() {
        Button restartButton = new Button();
        restartButton.getStyleClass().add("icon-button");

        SVGPath restartIcon = buildIcon("restart");
        restartButton.setGraphic(restartIcon);

        return restartButton;
    }

    private SVGPath buildIcon(String key) {
        SVGPath icon = new SVGPath();
        icon.setContent(assetProvider.getSvg(key));
        icon.getStyleClass().add(String.format("%s-icon", key));

        return icon;
    }

}
