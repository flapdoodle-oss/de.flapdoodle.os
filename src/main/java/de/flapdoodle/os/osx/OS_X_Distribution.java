package de.flapdoodle.os.osx;

import de.flapdoodle.os.Distribution;
import de.flapdoodle.os.OS;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.Peculiarity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public enum OS_X_Distribution implements Distribution {
	;

	@Override
	public List<Peculiarity<?>> pecularities() {
		return Collections.emptyList();
	}

	@Override
	public Optional<Version> version() {
		return Optional.empty();
	}
}
