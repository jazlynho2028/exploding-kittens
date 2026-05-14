package ui;

import javafx.scene.image.Image;

public interface AssetProvider {

	Image getImage(String key);

	String getSvg(String key);

	String getStylesheet();

	String getString(String key);

}