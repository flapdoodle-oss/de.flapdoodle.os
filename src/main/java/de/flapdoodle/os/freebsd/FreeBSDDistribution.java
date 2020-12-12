package de.flapdoodle.os.freebsd;

import de.flapdoodle.os.Distribution;
import de.flapdoodle.os.OS;

public enum FreeBSDDistribution implements Distribution {
	;

	@Override
	public OS operationSystem() {
		return OS.FreeBSD;
	}

}
