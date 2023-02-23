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
package de.flapdoodle.os;

import de.flapdoodle.os.common.DistinctPeculiarity;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.attributes.Attributes;
import de.flapdoodle.os.common.collections.Enums;
import de.flapdoodle.os.common.matcher.Matchers;
import de.flapdoodle.os.freebsd.FreeBSDDistribution;
import de.flapdoodle.os.linux.LinuxDistribution;
import de.flapdoodle.os.osx.OS_X_Distribution;
import de.flapdoodle.os.solaris.SolarisDistribution;
import de.flapdoodle.os.windows.WindowsDistribution;

import java.util.Arrays;
import java.util.List;

public enum CommonOS implements OS {
	Linux(OSType.Linux, CommonArchitecture.class, LinuxDistribution.class, osNameMatches("Linux")),
	Windows(OSType.Windows, CommonArchitecture.class, WindowsDistribution.class, osNameMatches("Windows.*")),
	OS_X(OSType.OS_X, CommonArchitecture.class, OS_X_Distribution.class, osNameMatches("Mac OS X")),
	Solaris(OSType.Solaris, CommonArchitecture.class, SolarisDistribution.class, osNameMatches(".*SunOS.*")),
	FreeBSD(OSType.FreeBSD, CommonArchitecture.class, FreeBSDDistribution.class, osNameMatches("FreeBSD"));

	private OSType type;
	private final List<Peculiarity> peculiarities;
	private final List<? extends Distribution> distributions;
	private final List<? extends Architecture> architectures;

	<A extends Enum<A> & Architecture, T extends Enum<T> & Distribution> CommonOS(
					OSType type,
					Class<A> architecureClazz,
					Class<T> clazz,
					DistinctPeculiarity<?>... peculiarities
	) {
		this.type = type;
		this.peculiarities  = HasPecularities.asList(peculiarities);
		this.architectures = Enums.valuesAsList(architecureClazz);
		this.distributions = Enums.valuesAsList(clazz);
	}

	@Override
	public List<? extends Distribution> distributions() {
		return distributions;
	}

	@Override
	public List<? extends Architecture> architectures() {
		return  architectures;
	}

	@Override
	public OSType type() {
		return type;
	}

	@Override
	public List<Peculiarity> pecularities() {
		return peculiarities;
	}

	private static DistinctPeculiarity<String> osNameMatches(String pattern) {
		return DistinctPeculiarity.of(Attributes.systemProperty("os.name"), Matchers.matchPattern(pattern));
	}

	public static List<? extends OS> list() {
		return Arrays.asList(values());
	}
}
