package de.flapdoodle.os.common.matcher;

import java.util.Optional;

public interface Matcher<T, M extends Match<T>> {
	boolean match(Optional<T> value,M match);
}
