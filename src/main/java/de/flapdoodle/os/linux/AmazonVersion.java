package de.flapdoodle.os.linux;

import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.DistinctPeculiarity;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.attributes.Attributes;
import de.flapdoodle.os.common.matcher.Matchers;

import java.util.List;

public enum AmazonVersion implements Version {
	// amzn2
	// os.version=4.9.76-3.78.amzn1.x86_64
	AmazonLinux(osVersionMatches(".*amzn1.*")),
	AmazonLinux2(osVersionMatches(".*amzn2.*"));

	private final List<Peculiarity> peculiarities;

	AmazonVersion(Peculiarity... peculiarities) {
		this.peculiarities  = HasPecularities.asList(peculiarities);
	}

	@Override
	public List<Peculiarity> pecularities() {
		return peculiarities;
	}

	static DistinctPeculiarity<String> osVersionMatches(String name) {
		return DistinctPeculiarity.of(osVersion(), Matchers.matchPattern(name));
	}

	static Attribute<String> osVersion() {
		return Attributes.systemProperty("os.version");
	}
}
