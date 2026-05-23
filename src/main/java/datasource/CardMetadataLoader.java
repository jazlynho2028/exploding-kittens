package datasource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ui.CardMetadata;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;

public class CardMetadataLoader implements FileLoader {

	private URL jsonFile;
	private Map<String, CardMetadata> metadata;

	@Override
	public boolean open(String fileName) {
		this.jsonFile = createFilePointer(fileName);
		this.metadata = loadJson(jsonFile);
		return true;
	}

	private URL createFilePointer(String fileName) {
		URL file = getClass().getResource(fileName);
		checkFileExistence(file);

		if (!fileName.endsWith(".json")) {
			throw new IllegalArgumentException(
					"The requested file is not a .json file"
			);
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

	private Map<String, CardMetadata> loadJson(URL file) {
		try (InputStream is = file.openStream()) {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(is, new TypeReference<>() { });
		}
		catch (Exception e) {
			throw new IllegalStateException("Failed to load card metadata JSON", e);
		}
	}

	@Override
	public URL getFileUrl() {
		return jsonFile;
	}

	public Map<String, CardMetadata> getMetadata() {
		return Map.copyOf(metadata);
	}
}