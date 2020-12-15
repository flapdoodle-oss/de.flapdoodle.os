package de.flapdoodle.os.common.attributes;

import java.util.Optional;

@FunctionalInterface
public interface AttributeExtractorLookup {
	<T, A extends Attribute<T>> Optional<AttributeExtractor<T, A>> extractor(A attribute);

	default AttributeExtractorLookup join(AttributeExtractorLookup other) {
		AttributeExtractorLookup that = this;
		return new AttributeExtractorLookup() {
			@Override
			public <T, A extends Attribute<T>> Optional<AttributeExtractor<T, A>> extractor(A attribute) {
				Optional<AttributeExtractor<T, A>> first = that.extractor(attribute);
				if (first.isPresent()) return first;
				return other.extractor(attribute);
			}
		};
	}

	static <T, A extends Attribute<T>> AttributeExtractorLookup forType(Class<A> attributeType, AttributeExtractor<T, A> extractor) {
		return new AttributeExtractorLookup() {
			@Override
			public <_T, _A extends Attribute<_T>> Optional<AttributeExtractor<_T, _A>> extractor(_A attribute) {
				return attributeType.isInstance(attribute)
								? Optional.of((AttributeExtractor<_T, _A>) extractor)
								: Optional.empty();
			}
		};
	}
}
