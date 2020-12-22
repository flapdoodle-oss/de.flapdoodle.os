/**
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

import de.flapdoodle.os.Distribution;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.PeculiarityInspector;
import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.attributes.Attributes;
import de.flapdoodle.os.common.collections.Immutables;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import de.flapdoodle.os.common.matcher.Matchers;
import de.flapdoodle.os.common.types.OsReleaseFile;
import de.flapdoodle.os.common.types.OsReleaseFileConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum LinuxDistribution implements Distribution {
	Ubuntu(UbuntuVersion.class, osReleaseFileNameMatches("Ubuntu")),
	CentOS(CentosVersion.class, osReleaseFileNameMatches("Centos")),
	;

	private final List<Peculiarity<?>> peculiarities;
	private final List<Version> versions;

	<T extends Enum<T> & Version> LinuxDistribution(Class<T> versionClazz, Peculiarity<?> ... peculiarities) {
		this.peculiarities = HasPecularities.asList(peculiarities);
		this.versions = Immutables.asList(versionClazz.getEnumConstants());
	}

	@Override
	public List<Peculiarity<?>> pecularities() {
		return peculiarities;
	}

	@Override
	public List<Version> versions() {
		return this.versions;
	}

	private static Peculiarity<OsReleaseFile> osReleaseFileNameMatches(String name) {
		return Peculiarity.of(osReleaseFile(), Matchers.osReleaseFileEntry("NAME", ".*" + name + ".*"));
	}

	static Attribute<OsReleaseFile> osReleaseFile() {
		return Attributes.mappedTextFile("/etc/os-release", OsReleaseFileConverter::convert);
	}
}
