package de.flapdoodle.os.common;

import java.util.regex.Pattern;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
public interface SystemPropertyMatch extends Peculiarity {
	@Parameter
	String name();
	
	@Parameter
	Pattern regex();
}
