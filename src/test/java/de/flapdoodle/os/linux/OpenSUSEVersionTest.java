package de.flapdoodle.os.linux;

import de.flapdoodle.os.Platform;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OpenSUSEVersionTest {

	@Test
	public void openSUSEVersionIdMustMatchUbuntuVersion() {
		assertVersion("20211127", OpenSUSEVersion.Tumbleweed);
	}

	private static void assertVersion(String versionIdContent, OpenSUSEVersion version) {
		Optional<Version> detectedVersion = detectVersion(LinuxDistributionTest.osReleaseFile_VersionIdIs(versionIdContent), OpenSUSEVersion.values());
		assertThat(detectedVersion).contains(version);
	}

	private static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, Version... values) {
		return Platform.detectVersion(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.<Version>asList(values));
	}

}