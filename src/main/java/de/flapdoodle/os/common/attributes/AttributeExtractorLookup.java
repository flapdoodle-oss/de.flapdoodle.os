/*
 * Copyright (C) 2020
 *   Michael Mosmann <michael@mosmann.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.os.common.attributes;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

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

	static <T, A extends Attribute<T>> AttributeExtractorLookup with(Predicate<? super A> attributeTypeCheck, AttributeExtractor<T, A> extractor) {
		return new AttributeExtractorLookup() {
			@Override
			public <_T, _A extends Attribute<_T>> Optional<AttributeExtractor<_T, _A>> extractor(_A attribute) {
				return attributeTypeCheck.test((A) attribute)
								? Optional.of((AttributeExtractor<_T, _A>) extractor)
								: Optional.empty();
			}
		};
	}

	static AttributeExtractorLookup failing() {
		return new AttributeExtractorLookup() {
			@Override
			public <T, A extends Attribute<T>> Optional<AttributeExtractor<T, A>> extractor(A attribute) {
				throw new IllegalArgumentException("no attribute extractor for "+attribute.getClass());
			}
		};
	}

	static AttributeExtractorLookup systemDefault() {
		return forType(TextFile.class, new TextFileResolver())
						.join(forType(SystemProperty.class, new SystemPropertyResolver()))
						.join(forType(MappedTextFile.class, new MappedTextFileResolver()))
						.join(failing());
	}
}
