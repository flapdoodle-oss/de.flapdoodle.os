package de.flapdoodle.os.common.attributes;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

import java.nio.charset.Charset;

@Immutable
public interface TextFile extends Attribute<String> {
	@Parameter
	String name();

	@Value.Default
	default Charset charset() {
		return Charset.defaultCharset();
	}
}
