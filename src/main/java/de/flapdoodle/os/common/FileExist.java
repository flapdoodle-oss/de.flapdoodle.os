package de.flapdoodle.os.common;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
public interface FileExist extends Peculiarity {
	@Parameter
	String fileName();
}
