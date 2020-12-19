package de.flapdoodle.os.linux;

import de.flapdoodle.os.Distribution;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.PeculiarityInspector;
import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.attributes.Attributes;
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
		this.versions = Arrays.asList(versionClazz.getEnumConstants());
	}

	@Override
	public List<Peculiarity<?>> pecularities() {
		return peculiarities;
	}

	public Optional<Version> version() {
		return version(AttributeExtractorLookup.systemDefault(), MatcherLookup.systemDefault());
	}

	@Override
	public List<Version> versions() {
		return this.versions;
	}

	// VisibleForTesting
	protected Optional<Version> version(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup) {
		List<Version> matching = PeculiarityInspector.matching(attributeExtractorLookup, matcherLookup, versions);
		return matching.size()==1
						? Optional.of(matching.get(0))
						: Optional.empty();
	}

	private static Peculiarity<OsReleaseFile> osReleaseFileNameMatches(String name) {
		return Peculiarity.of(osReleaseFile(), Matchers.osReleaseFileEntry("NAME", ".*" + name + ".*"));
	}

	static Attribute<OsReleaseFile> osReleaseFile() {
		return Attributes.mappedTextFile("/etc/os-release", OsReleaseFileConverter::convert);
	}

}
