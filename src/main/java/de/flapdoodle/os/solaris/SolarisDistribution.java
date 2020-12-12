package de.flapdoodle.os.solaris;

import de.flapdoodle.os.Distribution;
import de.flapdoodle.os.OS;

public enum SolarisDistribution implements Distribution {
	;

	@Override
	public OS operationSystem() {
		return OS.Solaris;
	}

}
