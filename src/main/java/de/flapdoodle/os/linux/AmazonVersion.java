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

import de.flapdoodle.os.Version;
import de.flapdoodle.os.VersionWithPriority;
import de.flapdoodle.os.common.*;
import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.attributes.Attributes;
import de.flapdoodle.os.common.matcher.Matchers;

import java.util.List;

import static de.flapdoodle.os.linux.OsReleaseFiles.versionMatches;

public enum AmazonVersion implements VersionWithPriority {
	// amzn2
	// os.version=4.9.76-3.78.amzn1.x86_64
	AmazonLinux(-1, osVersionMatches(".*amzn1.*")),
	AmazonLinux2(0, OneOf.of(
		AllOf.of(
			OsReleaseFiles.osReleaseFileNameMatches("Amazon Linux"),
			versionMatches(OsReleaseFiles.osReleaseFile(), "2")
		),
		osVersionMatches(".*amzn2(?!023).*"))
	),
	AmazonLinux2023(1, osVersionMatches(".*amzn2023.*"));

	private final int priority;
	private final List<Peculiarity> peculiarities;

	AmazonVersion(int priority, Peculiarity... peculiarities) {
		this.priority = priority;
		this.peculiarities = HasPecularities.asList(peculiarities);
	}

	@Override
	public List<Peculiarity> pecularities() {
		return peculiarities;
	}

	/**
	 * as we rely on 'os.version' only, this detection is pretty weak
	 * it should have a lower priority than others
	 */
	@Override
	public int priority() {
		return priority;
	}

	static DistinctPeculiarity<String> osVersionMatches(String name) {
		return DistinctPeculiarity.of(osVersion(), Matchers.matchPattern(name));
	}

	static Attribute<String> osVersion() {
		return Attributes.systemProperty("os.version");
	}
}
