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

import de.flapdoodle.os.common.DistinctPeculiarity;
import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.matcher.Matchers;
import de.flapdoodle.os.common.types.OsReleaseFile;
import de.flapdoodle.os.common.types.OsReleaseFileConverter;

import java.util.regex.Pattern;

public class OsReleaseFiles {
	public static String RELEASE_FILE_NAME = "/etc/os-release";
	public static String NAME="NAME";
	public static String VERSION_ID="VERSION_ID";
	public static String VERSION_CODENAME="VERSION_CODENAME";

	static Attribute<OsReleaseFile> osReleaseFile() {
		return releaseFile(RELEASE_FILE_NAME);
	}

	static Attribute<OsReleaseFile> releaseFile(String path) {
		return de.flapdoodle.os.common.attributes.Attributes.mappedTextFile(path, OsReleaseFileConverter.INSTANCE);
	}

	static DistinctPeculiarity<OsReleaseFile> nameMatches(Attribute<OsReleaseFile> osReleaseFile, String name) {
		return DistinctPeculiarity.of(osReleaseFile, Matchers.osReleaseFileEntry(NAME, ".*" + name + ".*"));
	}

	static DistinctPeculiarity<OsReleaseFile> versionMatches(Attribute<OsReleaseFile> osReleaseFile, String version) {
		return DistinctPeculiarity.of(osReleaseFile, Matchers.osReleaseFileEntry(VERSION_ID, Pattern.quote(version) + ".*"));
	}

	static DistinctPeculiarity<OsReleaseFile> versionIs(Attribute<OsReleaseFile> osReleaseFile, String version) {
		return DistinctPeculiarity.of(osReleaseFile, Matchers.osReleaseFileEntry(VERSION_ID, Pattern.quote(version)));
	}

	static DistinctPeculiarity<OsReleaseFile> versionCodeNameIs(Attribute<OsReleaseFile> osReleaseFile, String version) {
		return DistinctPeculiarity.of(osReleaseFile, Matchers.osReleaseFileEntry(VERSION_CODENAME, Pattern.quote(version)));
	}

	static DistinctPeculiarity<OsReleaseFile> osReleaseFileNameMatches(String name) {
		return nameMatches(osReleaseFile(), name);
	}

	static DistinctPeculiarity<OsReleaseFile> osReleaseFileVersionMatches(String version) {
		return versionMatches(osReleaseFile(), version);
	}

	static DistinctPeculiarity<OsReleaseFile> osReleaseFileVersionIs(String version) {
		return versionIs(osReleaseFile(), version);
	}

	static DistinctPeculiarity<OsReleaseFile> osReleaseFileVersionCodeNameIs(String version) {
		return versionCodeNameIs(osReleaseFile(), version);
	}
}
