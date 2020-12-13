package de.flapdoodle.os.common.matcher;

import java.util.regex.Pattern;

import org.immutables.value.Value.Auxiliary;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
public interface MatchPattern extends Match<String> {
	@Parameter
	Pattern pattern();
}
