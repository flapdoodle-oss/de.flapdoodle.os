package de.flapdoodle.os.common;

import org.immutables.value.Value.Immutable;

import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.matcher.Match;

@Immutable
public interface Peculiarity<T> {
	Attribute<T> attribute();
	Match<T> match();
}
