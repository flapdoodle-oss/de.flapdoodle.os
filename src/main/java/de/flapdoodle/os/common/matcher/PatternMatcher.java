package de.flapdoodle.os.common.matcher;

import java.util.Optional;

public class PatternMatcher implements Matcher<String, MatchPattern> {

	@Override
	public boolean match(Optional<String> value, MatchPattern match) {
		return value.map(it -> match.pattern().matcher(it).find())
				.orElse(false);
	}
}
