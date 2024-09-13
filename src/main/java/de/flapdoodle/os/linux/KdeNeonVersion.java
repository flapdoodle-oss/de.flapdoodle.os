package de.flapdoodle.os.linux;

import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.DistinctPeculiarity;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;

import java.util.List;

public enum KdeNeonVersion implements Version {
	KDE_NEON_6_0(UbuntuVersion.Ubuntu_22_04, OsReleaseFiles.osReleaseFileVersionIs("22.04"));

	private final UbuntuVersion matchingUbuntuVersion;
	private final List<Peculiarity> peculiarities;

	KdeNeonVersion(final UbuntuVersion matchingUbuntuVersion, final DistinctPeculiarity<?>... peculiarities) {
		this.matchingUbuntuVersion = matchingUbuntuVersion;
		this.peculiarities = HasPecularities.asList(peculiarities);
	}

	public UbuntuVersion matchingUbuntuVersion() {
		return matchingUbuntuVersion;
	}

	@Override
	public List<Peculiarity> pecularities() {
		return peculiarities;
	}
}