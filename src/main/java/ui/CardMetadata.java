package ui;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CardMetadata {

	private final String title;
	private final String subtitle;
	private final String description;
	private final String imageUrl;

	@JsonCreator
	public CardMetadata(
			@JsonProperty("title") String title,
			@JsonProperty("subtitle") String subtitle,
			@JsonProperty("description") String description,
			@JsonProperty("imageUrl") String imageUrl
	) {
		this.title = title;
		this.subtitle = subtitle;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public String getDescription() {
		return description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

}