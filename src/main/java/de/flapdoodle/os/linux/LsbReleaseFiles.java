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
import de.flapdoodle.os.common.types.LsbReleaseFile;
import de.flapdoodle.os.common.types.LsbReleaseFileConverter;

import java.util.regex.Pattern;

public class LsbReleaseFiles {
	public static String RELEASE_FILE_NAME = "/etc/lsb-release";
	public static String NAME="DISTRIB_ID";
	public static String VERSION_ID="DISTRIB_RELEASE";
	public static String VERSION_CODENAME="DISTRIB_CODENAME";

	static Attribute<LsbReleaseFile> lsbReleaseFile() {
		return releaseFile(RELEASE_FILE_NAME);
	}

	static Attribute<LsbReleaseFile> releaseFile(String path) {
		return de.flapdoodle.os.common.attributes.Attributes.mappedTextFile(path, LsbReleaseFileConverter.INSTANCE);
	}

	static DistinctPeculiarity<LsbReleaseFile> nameMatches(Attribute<LsbReleaseFile> osReleaseFile, String name) {
		return DistinctPeculiarity.of(osReleaseFile, Matchers.lsbReleaseFileEntry(NAME, ".*" + name + ".*"));
	}

	static DistinctPeculiarity<LsbReleaseFile> versionMatches(Attribute<LsbReleaseFile> osReleaseFile, String version) {
		return DistinctPeculiarity.of(osReleaseFile, Matchers.lsbReleaseFileEntry(VERSION_ID, Pattern.quote(version) + ".*"));
	}

	static DistinctPeculiarity<LsbReleaseFile> versionIs(Attribute<LsbReleaseFile> osReleaseFile, String version) {
		return DistinctPeculiarity.of(osReleaseFile, Matchers.lsbReleaseFileEntry(VERSION_ID, Pattern.quote(version)));
	}

	static DistinctPeculiarity<LsbReleaseFile> versionCodeNameIs(Attribute<LsbReleaseFile> osReleaseFile, String version) {
		return DistinctPeculiarity.of(osReleaseFile, Matchers.lsbReleaseFileEntry(VERSION_CODENAME, Pattern.quote(version)));
	}

	static DistinctPeculiarity<LsbReleaseFile> lsbReleaseFileNameMatches(String name) {
		return nameMatches(lsbReleaseFile(), name);
	}

	static DistinctPeculiarity<LsbReleaseFile> lsbReleaseFileVersionMatches(String version) {
		return versionMatches(lsbReleaseFile(), version);
	}

	static DistinctPeculiarity<LsbReleaseFile> lsbReleaseFileVersionIs(String version) {
		return versionIs(lsbReleaseFile(), version);
	}

	static DistinctPeculiarity<LsbReleaseFile> lsbReleaseFileVersionCodeNameIs(String version) {
		return versionCodeNameIs(lsbReleaseFile(), version);
	}
}
