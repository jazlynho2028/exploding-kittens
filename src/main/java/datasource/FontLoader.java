package datasource;

import java.net.URL;
import java.nio.file.Paths;

public class FontLoader implements FileLoader {

    private URL fontFile;

    @Override
    public boolean open(String fileName) {
        this.fontFile = createFilePointer(fileName);
        return true;
    }

    private URL createFilePointer(String fileName) {
        URL file = getClass().getResource(fileName);
        checkFileExistence(file);
        if (!fileName.endsWith(".ttf")) {
            throw new IllegalArgumentException("The requested file is not a .ttf file");
        }
        return file;
    }

    private void checkFileExistence(URL file) {
        try {
            Paths.get(file.toURI());
        }
        catch (Exception e) {
            throw new NullPointerException("The requested file does not exist");
        }
    }

    @Override
    public URL getFileUrl() {
        return this.fontFile;
    }
}