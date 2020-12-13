package de.flapdoodle.os.common.matcher;

import java.util.Optional;

public interface MatcherLookup {
	<T, M extends Match<T>> Optional<Matcher<T, M>> extractor(M match);
}
