package de.flapdoodle.os.linux;

import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;

import java.util.List;

/**
 NAME="openSUSE Tumbleweed"
 # VERSION="20211127"
 ID="opensuse-tumbleweed"
 ID_LIKE="opensuse suse"
 VERSION_ID="20211127"
 PRETTY_NAME="openSUSE Tumbleweed"
 ANSI_COLOR="0;32"
 CPE_NAME="cpe:/o:opensuse:tumbleweed:20211127"
 BUG_REPORT_URL="https://bugs.opensuse.org"
 HOME_URL="https://www.opensuse.org/"
 DOCUMENTATION_URL="https://en.opensuse.org/Portal:Tumbleweed"
 LOGO="distributor-logo-Tumbleweed"
 */
public enum OpenSUSEVersion implements Version {
	Tumbleweed(OsReleaseFiles.osReleaseFileVersionMatches("20211127"));

	private final List<Peculiarity<?>> peculiarities;
	
	OpenSUSEVersion(Peculiarity ... peculiarities) {
		this.peculiarities  = HasPecularities.asList(peculiarities);
	}

	@Override public List<Peculiarity<?>> pecularities() {
		return peculiarities;
	}
}
