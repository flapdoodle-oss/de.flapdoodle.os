/*
 * Copyright (C) 2020
 *   Michael Mosmann <michael@mosmann.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
