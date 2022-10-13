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

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface VersionWithPriority extends Version {
	int priority();

	static int priorityOf(Optional<? extends Version> version) {
		return version
			.map(v -> (v instanceof VersionWithPriority) ? ((VersionWithPriority) v).priority() : 0)
			.orElse(0);
	}

	static List<Platform> sorteByPriority(List<? extends Platform> list) {
		return list.stream()
			.sorted((Comparator<Platform>) (a, b) -> Integer.compare(
				VersionWithPriority.priorityOf(b.version()),
				VersionWithPriority.priorityOf(a.version())))
			.collect(Collectors.toList());
	}
}
