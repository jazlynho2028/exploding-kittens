package datasource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class IconLoader implements PathLoader {

    private String iconData;

    @Override
    public boolean open(String fileName) {
        this.iconData = loadFile(fileName);
        return true;
    }

    private String loadFile(String fileName) {
        InputStream stream = getClass().getResourceAsStream(fileName);
        checkStreamExistence(stream);

        try (stream) {
            return new String(
                    stream.readAllBytes(),
                    StandardCharsets.UTF_8
            );
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to load file");
        }
    }

    private void checkStreamExistence(InputStream stream) {
        if (stream == null) {
            throw new IllegalArgumentException("The requested file does not exist");
        }
    }

    @Override
    public String getPathData() {
        return iconData;
    }

}
