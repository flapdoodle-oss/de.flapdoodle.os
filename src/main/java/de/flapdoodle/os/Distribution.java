package de.flapdoodle.os;

import java.util.Optional;

public interface Distribution {
	OS operationSystem();
	
	
	interface Detector<T extends Distribution> {
		Optional<T> distribution();

		static <T extends Distribution> Detector<T> noop() {
			return () -> Optional.empty();
		}
	}
}
