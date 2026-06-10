package ui;

import datasource.*;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AssetManager implements AssetProvider {

    public AssetManager() { }

    private final Map<String, Image> images = new HashMap<>();
    private final Map<String, String> svgPaths = new HashMap<>();
    private final Map<String, CardMetadata> cardMetadata = new HashMap<>();
    private ResourceBundle languageBundle;

    private String cssUrl;

    public void loadGlobalFiles(String language) {
        loadCSS();

        loadIcon("restart",
                "/icons/restart.txt"
        );
        loadIcon("left-bracket",
                "/icons/left-bracket.txt"
        );
        loadIcon("skull",
                "/icons/skull.txt"
        );

        loadFont("/fonts/koulen-regular.ttf");
        loadFont("/fonts/national-park.ttf");

        loadLanguageAndCardMetadata(language);

        loadImage("placeholder", "/images/placeholder.png");
        loadImage("explosion", "/images/explosion.png");
        loadImage("card_back_cat", "/images/card_back_cat.png");

        for (Map.Entry<String, CardMetadata> entry : cardMetadata.entrySet()) {
            String id = entry.getKey();
            String imageUrl = "/images/" + entry.getValue().getImageUrl();
            loadImage(id, imageUrl);
        }

    }

    private void loadCSS() {
        FileLoader loader = new StyleSheetLoader();
        loader.open("/styles.css");
        String cssUrl = loader.getFileUrl().toExternalForm();
        setStylesheet(cssUrl);
    }

    private void loadImage(String key, String fileName) {
        FileLoader loader = new ImageLoader();
        loader.open(fileName);
        String imageUrl = loader.getFileUrl().toExternalForm();
        addImage(key, imageUrl);
    }

    private void loadIcon(String key, String fileName) {
        IconLoader loader = new IconLoader();
        loader.open(fileName);
        String pathData = loader.getPathData();
        addSvg(key, pathData);
    }

    private void loadFont(String fileName) {
        FileLoader loader = new FontLoader();
        loader.open(fileName);
        InputStream fontStream = getClass().getResourceAsStream(fileName);
        Font.loadFont(fontStream, UIConstants.LOADED_FONT_SIZE);
    }

    public void loadLanguageAndCardMetadata(String language) {
        loadLanguage(language);
        loadCardMetadata();
    }

    private void loadLanguage(String language) {
        StringsBundleLoader loader = new StringsBundleLoader();
        loader.open(language);
        languageBundle = loader.getCurrentBundle();
    }

    private void loadCardMetadata() {
        CardMetadataLoader loader = new CardMetadataLoader();
        loader.open("/card-metadata.json");

        cardMetadata.putAll(loader.getMetadata());
        convertPropertiesToString();
    }

    private void convertPropertiesToString() {
        cardMetadata.replaceAll((id, metadata) -> new CardMetadata(
                getString(metadata.getTitle()),
                getString(metadata.getSubtitle()),
                getString(metadata.getDescription()),
                metadata.getImageUrl()
        ));
    }

    public void addImage(String key, String imageUrl) {
        Image image = new Image(imageUrl);
        images.put(key, image);
    }

    public Image getImage(String key) {
        return images.get(key);
    }

    public void addSvg(String key, String pathData) {
        svgPaths.put(key, pathData);
    }

    public String getSvg(String key) {
        return svgPaths.get(key);
    }

    public void setStylesheet(String url) {
        this.cssUrl = url;
    }

    public String getStylesheet() {
        return cssUrl;
    }

    public String getString(String key) {
        return languageBundle.getString(key);
    }

    public CardMetadata getCardMetadata(String key) {
        return cardMetadata.get(key);
    }

}
