package de.flapdoodle.os.common.attributes;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
public interface TextFile extends Attribute<String> {
	@Parameter
	String name();
}
