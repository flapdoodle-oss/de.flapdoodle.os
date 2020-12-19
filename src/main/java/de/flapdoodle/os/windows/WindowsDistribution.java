package de.flapdoodle.os.windows;

import de.flapdoodle.os.Distribution;
import de.flapdoodle.os.OS;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.matcher.MatcherLookup;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public enum WindowsDistribution implements Distribution {
	;

	@Override
	public List<Peculiarity<?>> pecularities() {
		return Collections.emptyList();
	}

	@Override
	public List<Version> versions() {
		return Collections.emptyList();
	}
}
