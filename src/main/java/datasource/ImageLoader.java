package datasource;

import java.net.URL;
import java.nio.file.Paths;

public class ImageLoader implements FileLoader {

    private URL imageFile;

    @Override
    public boolean open(String fileName) {
        this.imageFile = createFilePointer(fileName);
        return true;
    }

    private URL createFilePointer(String fileName) {
        URL file = getClass().getResource(fileName);
        checkFileExistence(file);
        if (!fileName.endsWith(".png")) {
            throw new IllegalArgumentException("The requested file is not a png");
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
        return this.imageFile;
    }

}