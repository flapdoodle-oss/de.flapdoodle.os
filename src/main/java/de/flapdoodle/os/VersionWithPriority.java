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
