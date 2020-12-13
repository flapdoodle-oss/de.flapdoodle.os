package de.flapdoodle.os.common.attributes;

import java.util.Optional;

public interface AttributeExtractor<T, A extends Attribute<T>> {
	Optional<T> extract(A attribute);
}
