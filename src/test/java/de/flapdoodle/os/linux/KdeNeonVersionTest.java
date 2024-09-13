package de.flapdoodle.os.linux;

import de.flapdoodle.os.AttributeExtractorLookups;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static de.flapdoodle.os.common.PeculiarityInspector.find;
import static org.assertj.core.api.Assertions.assertThat;

class KdeNeonVersionTest {

	@Test
	public void ubuntuVersionIdMustMatchUbuntuVersion() {
		assertVersion("22.04", KdeNeonVersion.KDE_NEON_6_1, UbuntuVersion.Ubuntu_22_04);
	}

	private static void assertVersion(String versionIdContent, KdeNeonVersion version, UbuntuVersion matchingUbuntuVersion) {
		Optional<Version> detectedVersion = detectVersion(AttributeExtractorLookups.osReleaseFileVersionIdIs(versionIdContent), KdeNeonVersion.values());
		assertThat(detectedVersion).contains(version);
		assertThat(((KdeNeonVersion) detectedVersion.get()).matchingUbuntuVersion()).isEqualTo(matchingUbuntuVersion);

	}

	private static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, Version... values) {
		return find(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.<Version>asList(values));
	}

}