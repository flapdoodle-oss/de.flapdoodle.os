package de.flapdoodle.os.common.attributes;

import java.util.Optional;

public interface AttributeExtractorLookup {
	<T, A extends Attribute<T>> Optional<AttributeExtractor<T, A>> extractor(A attribute);
}
