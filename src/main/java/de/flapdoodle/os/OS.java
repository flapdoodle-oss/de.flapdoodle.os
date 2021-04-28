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

import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.attributes.Attributes;
import de.flapdoodle.os.common.collections.Immutables;
import de.flapdoodle.os.common.matcher.Matchers;
import de.flapdoodle.os.freebsd.FreeBSDDistribution;
import de.flapdoodle.os.linux.LinuxDistribution;
import de.flapdoodle.os.osx.OS_X_Distribution;
import de.flapdoodle.os.solaris.SolarisDistribution;
import de.flapdoodle.os.windows.WindowsDistribution;

import java.util.List;

public enum OS implements HasPecularities {
	Linux(CommonArchitecture.class, LinuxDistribution.class, osNameMatches("Linux")),
	Windows(CommonArchitecture.class, WindowsDistribution.class, osNameMatches("Windows.*")),
	OS_X(CommonArchitecture.class, OS_X_Distribution.class, osNameMatches("Mac OS X")),
	Solaris(CommonArchitecture.class, SolarisDistribution.class, osNameMatches(".*SunOS.*")),
	FreeBSD(CommonArchitecture.class, FreeBSDDistribution.class, osNameMatches("FreeBSD"));

	private final List<Peculiarity<?>> peculiarities;
	private final List<Distribution> distributions;
	private final List<Architecture> architectures;

	<A extends Enum<A> & Architecture, T extends Enum<T> & Distribution> OS(
					Class<A> architecureClazz,
					Class<T> clazz,
					Peculiarity<?>... peculiarities
	) {
		this.peculiarities  = HasPecularities.asList(peculiarities);
		this.architectures = Immutables.asList(architecureClazz.getEnumConstants());
		this.distributions = Immutables.asList(clazz.getEnumConstants());
	}

	public List<Distribution> distributions() {
		return distributions;
	}

	public List<Architecture> architectures() {
		return  architectures;
	}

	@Override
	public List<Peculiarity<?>> pecularities() {
		return peculiarities;
	}

	private static Peculiarity<String> osNameMatches(String pattern) {
		return Peculiarity.of(Attributes.systemProperty("os.name"), Matchers.matchPattern(pattern));
	}
}
