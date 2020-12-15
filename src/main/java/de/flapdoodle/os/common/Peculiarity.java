package de.flapdoodle.os.common;

import de.flapdoodle.os.common.matcher.ImmutableMatchPattern;
import org.immutables.value.Value.Immutable;

import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.matcher.Match;

@Immutable
public interface Peculiarity<T> {
	Attribute<T> attribute();
	Match<T> match();

	static <T> Peculiarity<T> of(Attribute<T> attribute, Match<T> match) {
		return ImmutablePeculiarity.<T>builder()
						.attribute(attribute)
						.match(match)
						.build();
	}
}
