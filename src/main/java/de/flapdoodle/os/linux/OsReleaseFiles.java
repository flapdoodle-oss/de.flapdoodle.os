package de.flapdoodle.os.linux;

import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.matcher.Matchers;
import de.flapdoodle.os.common.types.OsReleaseFile;
import de.flapdoodle.os.common.types.OsReleaseFileConverter;

public class OsReleaseFiles {

	static Attribute<OsReleaseFile> osReleaseFile() {
		return releaseFile("/etc/os-release");
	}

	static Attribute<OsReleaseFile> releaseFile(String path) {
		return de.flapdoodle.os.common.attributes.Attributes.mappedTextFile(path, OsReleaseFileConverter::convert);
	}

	static Peculiarity<OsReleaseFile> nameMatches(Attribute<OsReleaseFile> osReleaseFile, String name) {
		return Peculiarity.of(osReleaseFile, Matchers.osReleaseFileEntry("NAME", ".*" + name + ".*"));
	}

	static Peculiarity<OsReleaseFile> versionMatches(Attribute<OsReleaseFile> osReleaseFile, String version) {
		return Peculiarity.of(osReleaseFile, Matchers.osReleaseFileEntry("VERSION_ID", ".*" + version + ".*"));
	}

	static Peculiarity<OsReleaseFile> osReleaseFileNameMatches(String name) {
		return nameMatches(osReleaseFile(), name);
	}

	static Peculiarity<OsReleaseFile> osReleaseFileVersionMatches(String version) {
		return versionMatches(osReleaseFile(), version);
	}
}
