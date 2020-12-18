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
