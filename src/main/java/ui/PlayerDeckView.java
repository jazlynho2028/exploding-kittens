package ui;

import javafx.beans.binding.Bindings;
import domain.CardType;
import domain.DeckBuilder;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
import java.util.Set;
import java.util.function.Consumer;

import static ui.PlayerCreateView.buildBackgroundImage;
import static ui.PlayerCreateView.buildIcon;

public class PlayerDeckView {

    private final AssetProvider assetProvider;

    private final HBox playerNamesContainer;
    private final Button drawPileButton;
    private final Button discardPileButton;
    private final HBox handCardsContainer;
    private final Button handVisibilityButton;
	private final HBox turnControlSection;
	private final Button startGameButton;
	private final Button playCardsButton;
	private final Button endTurnButton;

    private final StackPane root;

    private final StackPane overlayLayer;
	private final Button explodeButton;
	private final Button defuseButton;
	private final Slider defuseSlider;
    private final Button godcatConfirmButton;
    private CardType selectedGodcatCardType;
    private final Button playAgainButton;

    public PlayerDeckView(AssetProvider assetProvider) {
        this.assetProvider = assetProvider;

        root = new StackPane();
        playerNamesContainer = new HBox();
        drawPileButton = new Button();
        discardPileButton = new Button();
        handCardsContainer = new HBox();
        handVisibilityButton = new Button();
        startGameButton = new Button();
        playCardsButton = new Button();
        endTurnButton = new Button();
        turnControlSection = new HBox();

        overlayLayer = new StackPane();
		explodeButton = new Button();
		defuseButton = new Button();
		defuseSlider = new Slider();
        godcatConfirmButton = new Button();
        selectedGodcatCardType = CardType.ATTACK;;
        playAgainButton = new Button();

        buildUI();
    }

    public void bindDrawPileButton(Runnable handler) {
        drawPileButton.setOnMouseClicked(e -> handler.run());
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

        bindListOfNodes(handler, nameTagButtons);
    }

    public void bindPlayerHandCardButtons(Consumer<Integer> handler) {
        ObservableList<Node> handCards = handCardsContainer.getChildren();

        bindListOfNodes(handler, handCards);
    }

    private void bindListOfNodes(Consumer<Integer> handler, ObservableList<Node> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            int index = i;
            nodes.get(i).setOnMouseClicked((e ->
                    handler.accept(index)
            ));
        }
    }

	public void bindDefuseButton(Runnable handler) {
		defuseButton.setOnMouseClicked(e -> handler.run());
	}

	public void bindExplodeButton(Runnable handler) {
		explodeButton.setOnMouseClicked(e -> handler.run());
	}

	public void bindGodcatConfirmButton(Runnable handler) {
		godcatConfirmButton.setOnMouseClicked(e -> handler.run());
	}

    public void bindPlayAgainButton(Runnable handler) {
        playAgainButton.setOnMouseClicked(e -> handler.run());
    }

    public Scene createPlayerDeckScene() {
        return new Scene(root, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);
    }

    public void buildAddRenderPlayerNameTags(
            List<String> playerNames, int currentPlayerIndex,
            boolean isEnabled, Set<Integer> aliveIndices) {

        buildAndAddPlayerNameTags(playerNames);
        renderPlayerNameTags(currentPlayerIndex, isEnabled, aliveIndices);
    }

    public void buildAndAddPlayerNameTags(List<String> playerNames) {
        for (String playerName : playerNames) {
            ToggleButton nameTag = buildNameTag(playerName);

            playerNamesContainer.getChildren().add(nameTag);
        }
    }

    public void renderPlayerNameTags(
			int currentPlayerIndex, boolean enableOtherPlayers,
            Set<Integer> aliveIndices) {

        ObservableList<Node> nameTagButtons = playerNamesContainer.getChildren();

        for (int i = 0; i < nameTagButtons.size(); i++) {
            ToggleButton nameTagButton = (ToggleButton) nameTagButtons.get(i);

            boolean isAtCurrentPlayerIndex = (i == currentPlayerIndex);
            nameTagButton.setSelected(isAtCurrentPlayerIndex);

            boolean isDead = !aliveIndices.contains(i);

            nameTagButton.setDisable(
					isAtCurrentPlayerIndex || !enableOtherPlayers || isDead);

            if (isDead) {
                renderDeadPlayerNameTag(nameTagButton);
            }
        }
    }

    private void renderDeadPlayerNameTag(ToggleButton nameTagButton) {
        if (!nameTagButton.getStyleClass().contains("dead")) {
            nameTagButton.getStyleClass().add("dead");

            SVGPath skullIcon = buildIcon(assetProvider, "skull");
            nameTagButton.setGraphic(skullIcon);
            nameTagButton.setContentDisplay(ContentDisplay.LEFT);
        }
    }

    public void renderDrawPile(boolean canDraw, boolean isDrawPileEmpty) {
        drawPileButton.setDisable(!canDraw);
        drawPileButton.setVisible(!isDrawPileEmpty);
    }

    public void renderDiscardPile(String topCardId) {
        boolean isEmpty = Objects.equals(
                topCardId, assetProvider.getString("global.empty"));

        if (isEmpty) {
            discardPileButton.setVisible(false);
            return;
        }

        VBox cardFront = buildCardFront(topCardId);
        discardPileButton.setGraphic(cardFront);
        discardPileButton.setVisible(true);
        discardPileButton.setDisable(false);
    }

    public void renderHandVisibilityButton(boolean isFaceUp, boolean isEnabled) {
        if (isFaceUp) {
            handVisibilityButton.setText(
                    assetProvider.getString("playerDeckScreen.hideHandLabel"));
        }
        else {
            handVisibilityButton.setText(
                    assetProvider.getString("playerDeckScreen.showHandLabel"));
        }
        handVisibilityButton.setDisable(!isEnabled);
    }

    public void buildAndAddPlayerHandCards(
            List<String> currentPlayerHand, boolean isFaceUp, boolean isEnabled) {

        handCardsContainer.getChildren().clear();

        for (String cardId : currentPlayerHand) {
            ToggleButton handCardButton = buildHandCardButton(
                    cardId,
                    isFaceUp,
                    isEnabled
            );
            handCardsContainer.getChildren().add(handCardButton);
        }
    }

    public void buildAndRenderTurnControlSection(
            boolean isGameOngoing, boolean canPlaySelected, boolean canEndTurn) {

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
        buildOverlayLayer();

        gameScreen.getChildren().addAll(
                backgroundImage, contentSection, overlayLayer);

        return gameScreen;
    }

    private VBox buildContentSection() {
        VBox contentSection = new VBox();
        contentSection.getStyleClass().add("content-section");

        VBox gameBoardSection = buildGameBoardSection();
        VBox playerChoiceSection = buildPlayerChoiceSection();

        VBox.setVgrow(gameBoardSection, Priority.ALWAYS);
        contentSection.getChildren().addAll(
                gameBoardSection, playerChoiceSection);

        return contentSection;
    }

    private VBox buildGameBoardSection() {
        VBox gameBoardSection = new VBox();
        gameBoardSection.getStyleClass().add("game-board-section");

        VBox playerHeaderSection = buildPlayerHeaderSection();
        HBox cardPileSection = buildCardPilesSection();

        gameBoardSection.getChildren().addAll(
                playerHeaderSection, cardPileSection);

        return gameBoardSection;
    }

    private VBox buildPlayerHeaderSection() {
        VBox playerHeaderSection = new VBox();
        playerHeaderSection.getStyleClass().add("player-header-section");

        renderPlayerNamesContainer();
        Text playerHeaderCaption = buildCaption(
                assetProvider.getString("playerDeckScreen.playerHeaderCaption"));

        playerHeaderSection.getChildren().addAll(
                playerNamesContainer, playerHeaderCaption);

        return playerHeaderSection;
    }

    private void renderPlayerNamesContainer() {
        playerNamesContainer.getStyleClass().add("player-names-container");
    }

    private ToggleButton buildNameTag(String playerName) {
        ToggleButton nameTag = new ToggleButton(playerName);
        nameTag.getStyleClass().addAll(
				"name-tag", "h4");

        return nameTag;
    }

    private Text buildCaption(String text) {
        Text caption = new Text(text);
        caption.getStyleClass().add("caption");

        return caption;
    }

    private HBox buildCardPilesSection() {
        HBox cardPileSection = new HBox();
        cardPileSection.getStyleClass().add("card-piles-section");

        VBox drawPileSection = buildDrawPileSection();
        VBox discardPileSection = buildDiscardPileSection();
        cardPileSection.getChildren().addAll(
                drawPileSection, discardPileSection);

        return cardPileSection;
    }

    private VBox buildDrawPileSection() {
        VBox drawPileSection = new VBox();
        drawPileSection.getStyleClass().add("card-pile-section");

        StackPane drawPileContainer = buildDrawPileContainer();
        drawPileButton.setDisable(true);

        Text drawPileCaption = buildCaption(
                assetProvider.getString("playerDeckScreen.drawPileFromTopCaption"));

        drawPileSection.getChildren().addAll(
                drawPileContainer, drawPileCaption);

        return drawPileSection;
    }

    private StackPane buildDrawPileContainer() {
        StackPane drawPileContainer = new StackPane();

        VBox emptyCard = buildEmptyPile();
        buildDrawPileButton();

        drawPileContainer.getChildren().addAll(
				emptyCard, drawPileButton);

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
        discardPile.getStyleClass().addAll(
				"card", "empty");

        return discardPile;
    }

    private VBox buildCardBack() {
        VBox drawPile = new VBox();
        drawPile.getStyleClass().add("draw-pile");

        ImageView cardBackIconView = buildCardBackIconView();
        VBox explodingKittensText = buildExplodingKittensText();

        drawPile.getChildren().addAll(
				cardBackIconView, explodingKittensText);

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
        explodingKittensText.getStyleClass().add("exploding-kittens-text");

        Text explodingText = buildExplodingText(
                assetProvider.getString("playerDeckScreen.exploding"));
        Text kittensText = buildKittensText(
                assetProvider.getString("playerDeckScreen.kittens"));

        explodingKittensText.getChildren().addAll(
				explodingText, kittensText);

        return explodingKittensText;
    }

    private Text buildExplodingText(String text) {
        Text explodingText = new Text(text);
        explodingText.getStyleClass().addAll(
				"exploding-text", "h5");

        return explodingText;
    }

    private Text buildKittensText(String text) {
        Text kittensText = new Text(text);
        kittensText.getStyleClass().addAll(
				"kittens-text", "h3");

        return kittensText;
    }

    private VBox buildDiscardPileSection() {
        VBox discardPileSection = new VBox();
        discardPileSection.getStyleClass().add("card-pile-section");

        StackPane discardPileContainer = buildDiscardPileContainer();
        Text discardPileCaption = buildCaption(
                assetProvider.getString("playerDeckScreen.discardPileCaption"));

        discardPileSection.getChildren().addAll(
                discardPileContainer, discardPileCaption);

        return discardPileSection;
    }

    private StackPane buildDiscardPileContainer() {
        StackPane discardPileContainer = new StackPane();

        VBox emptyCard = buildEmptyPile();
        buildDiscardPileButton();

        discardPileContainer.getChildren().addAll(
                discardPileButton, emptyCard);

        return discardPileContainer;
    }

    private void buildDiscardPileButton() {
        discardPileButton.getStyleClass().addAll(
				"card", "front");
        discardPileButton.setVisible(false);
        discardPileButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    private VBox buildPlayerChoiceSection() {
        VBox playerChoiceSection = new VBox();

        VBox playerHandSection = buildPlayerHandSection();
        buildTurnControlSection(false);

        playerChoiceSection.getChildren().addAll(
                playerHandSection, turnControlSection);

        return playerChoiceSection;
    }

    private VBox buildPlayerHandSection() {
        VBox playerHandSection = new VBox();
        playerHandSection.getStyleClass().add("player-hand-section");

        renderHandVisibilityToggle();
        ScrollPane handScrollPane = buildCardScrollPane(handCardsContainer);
        Text handCaption = buildCaption(
                assetProvider.getString("playerDeckScreen.handCaption"));

        playerHandSection.getChildren().addAll(
                handVisibilityButton, handScrollPane, handCaption);

        return playerHandSection;
    }

    private void renderHandVisibilityToggle() {
        handVisibilityButton.setText(
                assetProvider.getString("playerDeckScreen.showHandLabel"));
        handVisibilityButton.getStyleClass().addAll(
                "hand-visibility-toggle", "h6");
    }

    private ScrollPane buildCardScrollPane(HBox content) {
        ScrollPane cardScrollPane = new ScrollPane(content);
        cardScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        cardScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        cardScrollPane.getStyleClass().add("scroll-pane");

        content.setMinWidth(UIConstants.SCENE_WIDTH);
        content.getStyleClass().add("card-options");

        return cardScrollPane;
    }

    private ToggleButton buildHandCardButton(
            String cardId, boolean isFaceUp, boolean isEnabled) {

        ToggleButton handCardButton = new ToggleButton();
        handCardButton.getStyleClass().add("card");

        VBox handCard;

        if (isFaceUp) {
            handCard = buildCardFront(cardId);

            handCardButton.setDisable(!isEnabled);
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
                cardHeader, spacer, cardVisualSection);

        return cardFrontContent;
    }

    private Region buildSpacer() {
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        return spacer;
    }

    private void addCardStyleById(Node cardFrontContent, String cardId) {
        cardFrontContent.getStyleClass().add(
                cardIdToCssClass(cardId));
    }

    private String cardIdToCssClass(String cardId) {
        return cardId.toLowerCase().replaceAll("[0-9]?_[0-9]+$", "");
    }

    private HBox buildCardHeader(String cardId) {
        HBox cardHeader = new HBox();
        cardHeader.getStyleClass().add("card-header");

        StackPane cardCircle = buildCardCircle(cardId);
        VBox cardTitleSection = buildCardTitleSection(cardId);

        cardHeader.getChildren().addAll(
				cardCircle, cardTitleSection);

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
				"card-title", "b1");

        return cardTitle;
    }

    private Text buildCardSubtitle(String subtitle) {
        Text cardSubtitle = new Text(subtitle);
        cardSubtitle.setWrappingWidth(UIConstants.CARD_HEADER_WRAPPING_WIDTH);
        cardSubtitle.getStyleClass().addAll(
				"card-subtitle", "b2");

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
                cardImageView, cardDescriptionSection);

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
        cardDescriptionSection.getStyleClass().add("card-description-section");

        SVGPath leftBracketIcon = buildLeftBracketIcon();
        Text cardDescription = buildCardDescription(description);
        SVGPath rightBracketIcon = buildRightBracketIcon();

        cardDescriptionSection.getChildren().addAll(
                leftBracketIcon, cardDescription, rightBracketIcon);

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
				"card-description", "b2");

        return cardDescription;
    }

    private void buildTurnControlSection(boolean isGameOngoing) {
        turnControlSection.getChildren().clear();

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
                playCardsButton, endTurnButton);
    }

    private void buildAndAddTurnControlButtonsBeforeGameStart() {
        renderTurnControlButton(startGameButton,
                assetProvider.getString("playerDeckScreen.startGameLabel"));

        turnControlSection.getChildren().add(startGameButton);
    }

    private void renderTurnControlButton(Button turnControlButton, String label) {
        turnControlButton.setText(label);
        turnControlButton.getStyleClass().addAll(
				"turn-control-button", "h5");
    }

    private void buildOverlayLayer() {
        overlayLayer.getStyleClass().add("overlay");

        hideOverlay();
    }

    public int getExplodingKittenInsertIndex() {
        return (int) defuseSlider.getValue();
    }

    public void buildExplodeOverlay(boolean hasDefuse, String explodingCardId, int drawPileSize) {
        VBox content = buildOverlayContent();

        Button explodingKittenCard = buildExplodingKittenCard(explodingCardId);
        content.getChildren().add(explodingKittenCard);

        Button overlayButton;

        if (hasDefuse) {
            VBox defuseOptions = buildDefuseOptions(drawPileSize);
            content.getChildren().add(defuseOptions);

            overlayButton = renderExplodingOverlayButton(defuseButton,
                    "playerDeckScreen.defuseLabel", "defuse");
        }
        else {
            overlayButton = renderExplodingOverlayButton(explodeButton,
                    "playerDeckScreen.explodeLabel", "explode");
        }

        content.getChildren().add(overlayButton);

        overlayLayer.getChildren().setAll(content);
        showOverlay();
    }

    private VBox buildOverlayContent() {
        VBox content = new VBox();
        content.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        content.getStyleClass().add("overlay-content");

        return content;
    }

    private VBox buildDefuseOptions(int drawPileSize) {
        VBox defuseOptions = new VBox();
        defuseOptions.getStyleClass().add("defuse-options");

        renderDrawPileIndexSlider(drawPileSize);
        Text caption = buildDefuseSliderCaption(defuseSlider);

        defuseOptions.getChildren().addAll(defuseSlider, caption);

        return defuseOptions;
    }

    private Text buildDefuseSliderCaption(Slider slider) {
        Text caption = new Text();
        caption.getStyleClass().addAll("caption", "defuse-caption");

        caption.textProperty().bind(
                Bindings.createStringBinding(
                        () -> formatDefuseCaption(slider),
                        slider.valueProperty()
                )
        );
        return caption;
    }

    private String formatDefuseCaption(Slider slider) {
        return String.format(
                assetProvider.getString("playerDeckScreen.defuseAtIndexCaption"),
                (int) slider.getValue()
        );
    }

    private void renderDrawPileIndexSlider(int drawPileSize) {
        defuseSlider.setMin(0);
        defuseSlider.setMax(drawPileSize);
        defuseSlider.setValue(0);
        defuseSlider.setMajorTickUnit(1);
        defuseSlider.setMinorTickCount(0);
        defuseSlider.setBlockIncrement(1);
        defuseSlider.setSnapToTicks(true);
    }

    private Button buildExplodingKittenCard(String explodingCardId) {
        Button explodingKittenCard = new Button();

        explodingKittenCard.getStyleClass().addAll("card", "front");

        VBox cardFront = buildCardFront(explodingCardId);
        explodingKittenCard.setGraphic(cardFront);
        explodingKittenCard.setDisable(true);

        return explodingKittenCard;
    }

    private Button renderExplodingOverlayButton(Button button, String key, String styleClass) {
        button.setText(assetProvider.getString(key));
        button.getStyleClass().addAll(
                "overlay-button", styleClass, "h4");
        return button;
    }

    public void buildSeeTheFutureOverlay(List<String> topCardIds) {
        VBox content = buildOverlayContent();

        HBox topCardsContainer = buildTopCardsContainer(topCardIds);
        topCardsContainer.getStyleClass().add("card-options");

        Button closeButton = buildAndBindCloseButton();

        content.getChildren().addAll(topCardsContainer, closeButton);

        overlayLayer.getChildren().setAll(content);
        showOverlay();
    }

    private HBox buildTopCardsContainer(List<String> topCardIds) {
        HBox topCardsContainer = new HBox();

        for (String cardId : topCardIds) {
            ToggleButton handCardButton = buildHandCardButton(
                    cardId,
                    true,
                    false
            );
            topCardsContainer.getChildren().add(handCardButton);
        }

        return topCardsContainer;
    }

    private Button buildAndBindCloseButton() {
        Button closeButton = new Button();

        closeButton.setText(assetProvider.getString("playerDeckScreen.closeLabel"));
        closeButton.getStyleClass().addAll("overlay-button", "h5");
        closeButton.setOnMouseClicked(e -> hideOverlay());

        return closeButton;
    }

    public void buildGodcatOverlay(List<CardType> cardTypeOptions) {
        buildCardSelectOverlay(cardTypeOptions, godcatConfirmButton,
                assetProvider.getString("playerDeckScreen.godcatCaption"));
    }

    private void buildCardSelectOverlay(
            List<CardType> cardTypes, Button confirmButton, String titleText) {

        VBox content = buildOverlayContent();

        Text title = buildOverlayTitle(titleText);
        ScrollPane cardScrollPane = buildCardSelectScrollPane(cardTypes);

        confirmButton.setText(assetProvider.getString("playerDeckScreen.confirmLabel"));
        confirmButton.setDisable(true);
        confirmButton.getStyleClass().addAll("overlay-button", "h5");

        content.getChildren().addAll(title, cardScrollPane, confirmButton);
        overlayLayer.getChildren().setAll(content);
        showOverlay();
    }

    private Text buildOverlayTitle(String text) {
        Text title = new Text(text);
        title.getStyleClass().addAll("h3", "overlay-title");
        return title;
    }

    private ScrollPane buildCardSelectScrollPane(List<CardType> cardTypes) {
        HBox cardOptions = buildCardOptions(cardTypes);
        return buildCardScrollPane(cardOptions);
    }

    private HBox buildCardOptions(List<CardType> cardTypes) {
        HBox cardOptions = new HBox();

        for (CardType cardType : cardTypes) {
            ToggleButton cardButton = buildCardOptionButton(cardType, cardOptions);
            cardOptions.getChildren().add(cardButton);
        }

        return cardOptions;
    }

    private ToggleButton buildCardOptionButton(CardType cardType, HBox cardOptions) {
        String cardId = DeckBuilder.createCardId(cardType, 1);
        ToggleButton cardButton = new ToggleButton();
        cardButton.getStyleClass().addAll("card", "front");
        cardButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        VBox cardFront = buildCardFront(cardId);
        cardButton.setGraphic(cardFront);

        cardButton.setOnMouseClicked(e -> {
            selectedGodcatCardType = cardType;
            cardOptions.getChildren().forEach(button ->
                    ((ToggleButton) button).setSelected(false));
            cardButton.setSelected(true);
            godcatConfirmButton.setDisable(false);
        });

        return cardButton;
    }

    public void buildWinOverlay(String winnerName) {
        VBox content = buildOverlayContent();

        String winMsg = String.format(
                assetProvider.getString("playerDeckScreen.winTitle"), winnerName);
        Text winTitle = new Text(winMsg);
        winTitle.getStyleClass().addAll("win-title", "h1");

        playAgainButton.setText(
                assetProvider.getString("playerDeckScreen.playAgainLabel"));
        playAgainButton.getStyleClass().addAll("play-button", "h2");

        content.getChildren().addAll(winTitle, playAgainButton);

        overlayLayer.getChildren().setAll(content);
        showOverlay();
    }

    private void showOverlay() {
        overlayLayer.setVisible(true);
        overlayLayer.setMouseTransparent(false);
    }

    public void hideOverlay() {
        overlayLayer.setVisible(false);
        overlayLayer.setMouseTransparent(true);
        overlayLayer.getChildren().clear();
    }

    public CardType getSelectedGodcatCardType() {
        return selectedGodcatCardType;
    }

}
