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
package de.flapdoodle.os;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.PeculiarityInspector;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.attributes.Attributes;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import de.flapdoodle.os.common.matcher.Matchers;
import de.flapdoodle.os.freebsd.FreeBSDDistribution;
import de.flapdoodle.os.linux.*;
import de.flapdoodle.os.osx.OS_X_Distribution;
import de.flapdoodle.os.solaris.SolarisDistribution;
import de.flapdoodle.os.windows.WindowsDistribution;

public enum OS implements HasPecularities {
	Linux(LinuxDistribution.class, osNameMatches("Linux")),
	Windows(WindowsDistribution.class, osNameMatches("Windows.*")),
	OS_X(OS_X_Distribution.class, osNameMatches("Mac OS X")),
	Solaris(SolarisDistribution.class, osNameMatches(".*SunOS.*")),
	FreeBSD(FreeBSDDistribution.class, osNameMatches("FreeBSD"));

	private final List<Peculiarity<?>> peculiarities;
	private final List<Distribution> distributions;

	<T extends Enum<T> & Distribution> OS(
					Class<T> clazz,
					Peculiarity<?>... peculiarities
	) {
		this.peculiarities  = HasPecularities.asList(peculiarities);
		this.distributions = Arrays.asList(clazz.getEnumConstants());
	}

	public List<Distribution> distributions() {
		return distributions;
	}

	@Override
	public List<Peculiarity<?>> pecularities() {
		return peculiarities;
	}

	private static Peculiarity<String> osNameMatches(String pattern) {
		return Peculiarity.of(Attributes.systemProperty("os.name"), Matchers.matchPattern(pattern));
	}
}
