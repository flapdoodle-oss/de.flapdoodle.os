package de.flapdoodle.os.linux;

import de.flapdoodle.os.Distribution;
import de.flapdoodle.os.OS;

public enum LinuxDistribution implements Distribution {
	Ubuntu,
	CentOS,
	;

	@Override
	public OS operationSystem() {
		return OS.Linux;
	}

}
