package de.flapdoodle.os.windows;

import de.flapdoodle.os.Distribution;
import de.flapdoodle.os.OS;

public enum WindowsDistribution implements Distribution {
	;

	@Override
	public OS operationSystem() {
		return OS.Windows;
	}

}
