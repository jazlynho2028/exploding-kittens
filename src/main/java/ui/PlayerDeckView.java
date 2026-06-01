package ui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static ui.PlayerCreateView.buildBackgroundImage;
import static ui.PlayerCreateView.buildIcon;

public class PlayerDeckView {

    private final AssetProvider assetProvider;

    public final Button restartButton;
    public final HBox playerNamesContainer;
    public final Button drawPileButton;
    public final Button discardPileButton;
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
        restartButton = new Button();
        playerNamesContainer = new HBox();
        drawPileButton = new Button();
        discardPileButton = new Button();
        handCardsContainer = new HBox();
        handVisibilityButton = new Button();
        startGameButton = new Button();
        playCardsButton = new Button();
        endTurnButton = new Button();
        turnControlSection = new HBox();

        buildUI();
    }

    public void bindDrawPileButton(Runnable handler) {
        drawPileButton.setOnMouseClicked(e -> handler.run());
    }

    public void bindDiscardPileButton(Runnable handler) {
        discardPileButton.setOnMouseClicked(e -> handler.run());
    }

    public void bindHandVisibilityButton(Runnable handler) {
        handVisibilityButton.setOnMouseClicked(e -> handler.run());
    }

    public void bindStartGameButton(Runnable handler) {
        startGameButton.setOnMouseClicked(e -> handler.run());
    }

    public void bindPlayCardsButton(Runnable handler) {
        playCardsButton.setOnMouseClicked(e -> handler.run());
    }

    public void bindEndTurnButton(Runnable handler) {
        endTurnButton.setOnMouseClicked(e -> handler.run());
    }

    public void bindNameTags(Consumer<Integer> handler) {
        ObservableList<Node> nameTagButtons = playerNamesContainer.getChildren();

        for (int i = 0; i < nameTagButtons.size(); i++) {
            int index = i;
            nameTagButtons.get(i).setOnMouseClicked((e ->
                    handler.accept(index)
            ));
        }
    }

    public void bindPlayerHandCardButtons(Consumer<Integer> handler) {
        ObservableList<Node> handCards = handCardsContainer.getChildren();

        for (int i = 0; i < handCards.size(); i++) {
            int index = i;
            handCards.get(i).setOnMouseClicked((e ->
                    handler.accept(index)
            ));
        }
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

            nameTagButton.setDisable(isAtCurrentPlayerIndex || isGameOngoing);
        }
    }

    public void renderDrawPile(boolean canDraw, boolean isDrawPileEmpty) {
        drawPileButton.setDisable(!canDraw);
        drawPileButton.setVisible(!isDrawPileEmpty);
    }

    public void renderDiscardPile(boolean canDraw, String topCardId) {
        if (topCardId.isEmpty()) {
            discardPileButton.setVisible(false);
            return;
        }

        VBox cardFront = buildCardFront(topCardId);
        discardPileButton.setGraphic(cardFront);
        discardPileButton.setVisible(true);
        discardPileButton.setDisable(!canDraw);
    }

    public void renderHandVisibilityButton(boolean isFaceUp) {
        if (isFaceUp) {
            handVisibilityButton.setText(
                    assetProvider.getString("playerDeckScreen.hideHandLabel"));
        }
        else {
            handVisibilityButton.setText(
                    assetProvider.getString("playerDeckScreen.showHandLabel"));
        }
    }

    public void buildAndAddPlayerHandCards(
            List<String> currentPlayerHand,
            boolean isFaceUp,
            boolean isBeforeDraw
    ) {
        handCardsContainer.getChildren().clear();

        for (String cardId : currentPlayerHand) {
            ToggleButton handCardButton = buildHandCardButton(
                    cardId,
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

    public void renderTurnControlSection(boolean canPlaySelected, boolean canEndTurn) {
        playCardsButton.setDisable(!canPlaySelected);
        endTurnButton.setDisable(!canEndTurn);
    }

    private void buildUI() {
        StackPane gameScreen = buildGameScreen();
        root.getChildren().add(gameScreen);
    }

    private StackPane buildGameScreen() {
        StackPane gameScreen = new StackPane();

        ImageView backgroundImage = buildBackgroundImage(assetProvider);
        VBox contentSection = buildContentSection();
        StackPane overlayLayer = buildOverlayLayer();

        gameScreen.getChildren().addAll(
                backgroundImage,
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
        Text playerHeaderCaption = buildCaption(
                assetProvider.getString("playerDeckScreen.playerHeaderCaption"));

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

        Text drawPileCaption = buildCaption(
                assetProvider.getString("playerDeckScreen.drawPileFromTopCaption"));

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
        Image cardBackIcon = assetProvider.getImage("card_back_cat");
        ImageView cardBackIconView = new ImageView(cardBackIcon);

        cardBackIconView.setFitWidth(UIConstants.CARD_BACK_ICON_WIDTH);
        cardBackIconView.setPreserveRatio(true);

        return cardBackIconView;
    }

    private VBox buildExplodingKittensText() {
        VBox explodingKittensText = new VBox();
        explodingKittensText.setAlignment(Pos.CENTER);
        explodingKittensText.getStyleClass().add("exploding-kittens-text");

        Text explodingText = buildExplodingText(
                assetProvider.getString("playerDeckScreen.exploding"));
        Text kittensText = buildKittensText(
                assetProvider.getString("playerDeckScreen.kittens"));

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
        Text discardPileCaption = buildCaption(
                assetProvider.getString("playerDeckScreen.discardPileCaption"));

        discardPileSection.getChildren().addAll(
                discardPileContainer,
                discardPileCaption
        );

        return discardPileSection;
    }

    private StackPane buildDiscardPileContainer() {
        StackPane discardPileContainer = new StackPane();

        VBox emptyCard = buildEmptyPile();
        buildDiscardPileButton();

        discardPileContainer.getChildren().addAll(
                discardPileButton,
                emptyCard
        );

        return discardPileContainer;
    }

    private void buildDiscardPileButton() {
        discardPileButton.getStyleClass().addAll("card", "front");
        discardPileButton.setVisible(false);
        discardPileButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
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
        Text handCaption = buildCaption(
                assetProvider.getString("playerDeckScreen.handCaption"));

        playerHandSection.getChildren().addAll(
                handVisibilityButton,
                handScrollPane,
                handCaption
        );

        return playerHandSection;
    }

    private void renderHandVisibilityToggle() {
        handVisibilityButton.setText(
                assetProvider.getString("playerDeckScreen.showHandLabel"));
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
            String cardId,
            boolean isFaceUp,
            boolean isBeforeDraw
    ) {
        ToggleButton handCardButton = new ToggleButton();
        handCardButton.getStyleClass().add("card");

        VBox handCard;

        if (isFaceUp) {
            handCard = buildCardFront(cardId);

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

    private VBox buildCardFront(String cardId) {
        VBox cardFront = new VBox();

        VBox cardFrontContent = buildCardFrontContent(cardId);

        VBox.setVgrow(cardFrontContent, Priority.ALWAYS);
        cardFront.getChildren().add(cardFrontContent);
        return cardFront;
    }

    private VBox buildCardFrontContent(String cardId) {
        VBox cardFrontContent = new VBox();
        cardFrontContent.getStyleClass().add("card-front-content");
        addCardStyleById(cardFrontContent, cardId);

        HBox cardHeader = buildCardHeader(cardId);
        Region spacer = buildSpacer();
        StackPane cardVisualSection = buildCardVisualSection(cardId);

        cardFrontContent.getChildren().addAll(
                cardHeader,
                spacer,
                cardVisualSection
        );
        return cardFrontContent;
    }

    private Region buildSpacer() {
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        return spacer;
    }

    private void addCardStyleById(Node cardFrontContent, String cardId) {
        cardFrontContent.getStyleClass().add(
                cardIdToCssClass(cardId)
        );
    }

    private String cardIdToCssClass(String cardId) {
        return cardId.toLowerCase().replaceAll("[0-9]?_[0-9]+$", "");
    }

    private HBox buildCardHeader(String cardId) {
        HBox cardHeader = new HBox();
        cardHeader.setAlignment(Pos.CENTER_LEFT);
        cardHeader.getStyleClass().add("card-header");

        StackPane cardCircle = buildCardCircle(cardId);
        VBox cardTitleSection = buildCardTitleSection(cardId);

        cardHeader.getChildren().addAll(
                cardCircle,
                cardTitleSection
        );
        return cardHeader;
    }

    private StackPane buildCardCircle(String cardId) {
        StackPane cardCircle = new StackPane();
        cardCircle.getStyleClass().add("card-circle");
        addCardStyleById(cardCircle, cardId);
        return cardCircle;
    }

    private VBox buildCardTitleSection(String cardId) {
        VBox cardTitleSection = new VBox();
        cardTitleSection.setAlignment(Pos.CENTER_LEFT);
        cardTitleSection.getStyleClass().add("card-title-section");

        CardMetadata cardMetadata = assetProvider.getCardMetadata(cardId);

        Text cardTitle = buildCardTitle(cardMetadata.getTitle());
        cardTitleSection.getChildren().add(cardTitle);

        if (!Objects.equals(cardMetadata.getSubtitle(), "")) {
            Text cardSubtitle = buildCardSubtitle(cardMetadata.getSubtitle());
            cardTitleSection.getChildren().add(cardSubtitle);
        }

        return cardTitleSection;
    }

    private Text buildCardTitle(String title) {
        Text cardTitle = new Text(title);
        cardTitle.setWrappingWidth(UIConstants.CARD_HEADER_WRAPPING_WIDTH);
        cardTitle.getStyleClass().addAll(
                "card-title",
                "b1"
        );

        return cardTitle;
    }

    private Text buildCardSubtitle(String subtitle) {
        Text cardSubtitle = new Text(subtitle);
        cardSubtitle.setWrappingWidth(UIConstants.CARD_HEADER_WRAPPING_WIDTH);
        cardSubtitle.getStyleClass().addAll(
                "card-subtitle",
                "b2"
        );

        return cardSubtitle;
    }

    private StackPane buildCardVisualSection(String cardId) {
        StackPane cardVisualSection = new StackPane();

        CardMetadata cardMetadata = assetProvider.getCardMetadata(cardId);

        ImageView cardImageView = buildCardImageView(cardId);
        HBox cardDescriptionSection = buildCardDescriptionSection(
                cardMetadata.getDescription()
        );

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

    private ImageView buildCardImageView(String cardId) {
        Image cardImage = assetProvider.getImage(cardId);

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
        SVGPath leftBracketIcon = buildIcon(assetProvider, "left-bracket");
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

    private void buildTurnControlSection(boolean isGameOngoing) {
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

    private void buildAndAddTurnControlButtonsAfterGameStart() {
        renderTurnControlButton(playCardsButton,
                assetProvider.getString("playerDeckScreen.playCardsLabel"));
        renderTurnControlButton(endTurnButton,
                assetProvider.getString("playerDeckScreen.endTurnLabel"));

        turnControlSection.getChildren().addAll(
                playCardsButton,
                endTurnButton
        );
    }

    private void buildAndAddTurnControlButtonsBeforeGameStart() {
        renderTurnControlButton(startGameButton,
                assetProvider.getString("playerDeckScreen.startGameLabel"));

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

        return overlayLayer;
    }

}
