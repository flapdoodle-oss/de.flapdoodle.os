package de.flapdoodle.os.linux;

import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;

import java.util.List;

public enum ManjaroVersion implements Version {
	MANJARO_24_0_8(LsbReleaseFiles.lsbReleaseFileVersionIs("24.0.8")),;

	private final List<Peculiarity> peculiarities;

	ManjaroVersion(final Peculiarity... peculiarities) {
		this.peculiarities = HasPecularities.asList(peculiarities);
	}

	@Override
	public List<Peculiarity> pecularities() {
		return peculiarities;
	}

}
