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

	public Optional<Distribution> distribution() {
		return distribution(AttributeExtractorLookup.systemDefault(), MatcherLookup.systemDefault());
	}

	// VisibleForTesting
	protected Optional<Distribution> distribution(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup) {
		List<Distribution> matching = PeculiarityInspector.matching(attributeExtractorLookup, matcherLookup, distributions);
		return matching.size()==1
						? Optional.of(matching.get(0))
						: Optional.empty();
	}

	@Override
	public List<Peculiarity<?>> pecularities() {
		return peculiarities;
	}

	private static Peculiarity<String> osNameMatches(String pattern) {
		return Peculiarity.of(Attributes.systemProperty("os.name"), Matchers.matchPattern(pattern));
	}

	public static OS detect(
					AttributeExtractorLookup attributeExtractorLookup,
					MatcherLookup matcherLookup
	) {
		return PeculiarityInspector.match(attributeExtractorLookup,matcherLookup, OS.values());
	}

	public static OS detect() {
		return detect(AttributeExtractorLookup.systemDefault(), MatcherLookup.systemDefault());
	}
}
