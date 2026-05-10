package ui;

import datasource.*;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AssetManager implements AssetProvider {

    public AssetManager() { }

    private final Map<String, Image> images = new HashMap<>();
    private final Map<String, String> svgPaths = new HashMap<>();
    private final Map<String, CardMetadata> cardMetaData = new HashMap<>();

    private String cssUrl;

    public void loadGlobalFiles() {
        loadCSS();
        loadImages();
        loadIcon("restart",
                "/icons/restart.txt"
        );
        loadIcon("left-bracket",
                "/icons/left-bracket.txt"
        );

        loadFont("/fonts/koulen-regular.ttf");
        loadFont("/fonts/national-park.ttf");

        loadCardMetadata();
    }

    private void loadCSS() {
        FileLoader loader = new StyleSheetLoader();
        loader.open("/styles.css");
        String cssUrl = loader.getFileUrl().toExternalForm();
        setStylesheet(cssUrl);
    }

    private void loadImages() {
        FileLoader loader = new ImageLoader();
        loader.open("/images/placeholder.png");
        String imageUrl = loader.getFileUrl().toExternalForm();
        addImage("placeholder", imageUrl);
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

    private void loadCardMetadata() {
        CardMetadataLoader loader = new CardMetadataLoader();
        loader.open("/card-metadata.json");
        cardMetaData.putAll(loader.getMetadata());
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

    public CardMetadata getCardMetaData(String key) {
        return cardMetaData.get(key);
    }

}