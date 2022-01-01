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

import de.flapdoodle.os.common.matcher.Match;
import de.flapdoodle.os.common.matcher.Matcher;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public abstract class LoggingWrapper {
	private LoggingWrapper() {
		// no instance
	}

	public static AttributeExtractorLookup wrap(AttributeExtractorLookup delegate) {
		return new AttributeExtractorLookupWrapper(delegate);
	}

	public static MatcherLookup wrap(MatcherLookup delegate) {
		return new MatcherLookupWrapper(delegate);
	}

	static class AttributeExtractorLookupWrapper implements AttributeExtractorLookup {
		private static Logger logger = LoggerFactory.getLogger(AttributeExtractorLookupWrapper.class);

		private final AttributeExtractorLookup delegate;

		private AttributeExtractorLookupWrapper(AttributeExtractorLookup delegate) {
			this.delegate = delegate;
		}

		@Override
		public <T, A extends Attribute<T>> Optional<AttributeExtractor<T, A>> extractor(A attribute) {
			Optional<AttributeExtractor<T, A>> extractor = delegate.extractor(attribute);
			logger.info("extractor for attribute {}: {}", attribute, extractor);
			return extractor;
		}
	}

	static class MatcherLookupWrapper implements MatcherLookup {
		private static Logger logger = LoggerFactory.getLogger(MatcherLookupWrapper.class);

		private final MatcherLookup delegate;

		private MatcherLookupWrapper(MatcherLookup delegate) {
			this.delegate = delegate;
		}

		@Override
		public <T, M extends Match<T>> Optional<Matcher<T, M>> matcher(M match) {
			Optional<Matcher<T, M>> matcher = delegate.matcher(match);
			logger.info("matcher for attribute {}: {}", match, matcher);
			return matcher.map(MatcherWrapper::new);
		}
	}

	static class MatcherWrapper<T, M extends Match<T>> implements Matcher<T,M> {
		private static Logger logger = LoggerFactory.getLogger(MatcherWrapper.class);

		private final Matcher<T, M> delegate;
		public MatcherWrapper(Matcher<T,M> delegate) {
			this.delegate = delegate;
		}

		@Override public boolean match(Optional<T> value, M match) {
			boolean matches = delegate.match(value, match);
			logger.info("value {} matches {}: {}", value, match, matches);
			return matches;
		}
	}
}
