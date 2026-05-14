package datasource;

import java.util.Locale;
import java.util.ResourceBundle;

public class StringsBundleLoader {

	private static final String bundleName = "languages/strings";
	private static Locale currentLocale = Locale.ENGLISH;

	public void open(String localeName) {
		Locale newLocale = new Locale(localeName);
		load(newLocale);
	}

	private void load(Locale locale) {
		try {
			ResourceBundle.getBundle(bundleName, locale);
			currentLocale = locale;
		}
		catch (Exception e) {
			throw new IllegalArgumentException("The bundle does not exist.");
		}
	}

	public ResourceBundle getBundle() {
		return ResourceBundle.getBundle(bundleName, currentLocale);
	}

}