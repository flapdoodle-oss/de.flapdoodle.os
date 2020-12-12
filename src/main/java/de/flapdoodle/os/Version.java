package de.flapdoodle.os;

import java.util.Optional;

public interface Version {
	Distribution distribution();
	
	@FunctionalInterface
	interface Detector<T extends Version> {
		Optional<T> detect();
	}
}
