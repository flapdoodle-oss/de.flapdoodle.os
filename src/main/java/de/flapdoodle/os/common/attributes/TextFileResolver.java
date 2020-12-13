package de.flapdoodle.os.common.attributes;

import java.util.Optional;

public class TextFileResolver implements AttributeExtractor<String, TextFile> {

	@Override
	public Optional<String> extract(TextFile attribute) {
		return Optional.empty();
	}
}
