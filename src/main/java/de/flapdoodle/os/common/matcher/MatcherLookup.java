/**
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
package de.flapdoodle.os.common.matcher;

import java.util.Optional;

@FunctionalInterface
public interface MatcherLookup {
	<T, M extends Match<T>> Optional<Matcher<T, M>> matcher(M match);

	default MatcherLookup join(MatcherLookup other) {
		MatcherLookup that = this;
		return new MatcherLookup() {
			@Override
			public <T, M extends Match<T>> Optional<Matcher<T, M>> matcher(M match) {
				Optional<Matcher<T, M>> first = that.matcher(match);
				if (first.isPresent()) return first;
				return other.matcher(match);
			}
		};
	}

	static <T, M extends Match<T>> MatcherLookup forType(Class<M> type, Matcher<T, M> matcher) {
		return new MatcherLookup() {
			@Override
			public <_T, _M extends Match<_T>> Optional<Matcher<_T, _M>> matcher(_M match) {
				if (type.isInstance(match)) {
					return Optional.of((Matcher<_T, _M>) matcher);
				}
				return Optional.empty();
			}
		};
	}

	static MatcherLookup failing() {
		return new MatcherLookup() {
			@Override
			public <T, M extends Match<T>> Optional<Matcher<T, M>> matcher(M match) {
				throw new IllegalArgumentException("no attribute matcher for " + match.getClass());
			}
		};
	}

	static MatcherLookup systemDefault() {
		return MatcherLookup.forType(MatchPattern.class, new PatternMatcher())
						.join(forType(OsReleaseFileMapEntry.class, new OsReleaseFileEntryMatcher()))
						.join(failing());
	}
}
