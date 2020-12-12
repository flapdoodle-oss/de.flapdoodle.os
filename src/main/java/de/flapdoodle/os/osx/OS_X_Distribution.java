package de.flapdoodle.os.osx;

import de.flapdoodle.os.Distribution;
import de.flapdoodle.os.OS;

public enum OS_X_Distribution implements Distribution {
	;

	@Override
	public OS operationSystem() {
		return OS.OS_X;
	}

}
