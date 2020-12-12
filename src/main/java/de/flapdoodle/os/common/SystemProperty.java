package de.flapdoodle.os.common;

import java.util.regex.Pattern;

public abstract class SystemProperty {
	
	public static SystemPropertyMatch matches(String name, Pattern regex) {
		return ImmutableSystemPropertyMatch.of(name, regex);
	}
	
	public static SystemPropertyMatch matches(String name, String regex) {
		return matches(name, Pattern.compile(regex));
	}
}
