package datasource;

public interface PathLoader {

    boolean open(String fileName);

    String getPathData();
}