package de.flapdoodle.os.linux;

import de.flapdoodle.os.Platform;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CentosVersionTest {

	@Test
	public void centosReleaseFileNameMustNotChange() {
		assertThat(CentosVersion.RELEASE_FILE_NAME).isEqualTo("/etc/centos-release");
	}

	@Test
	public void centosVersionIdMustMatchUbuntuVersion() {
		assertVersion("6", CentosVersion.CentOS_6);
		assertVersion("7", CentosVersion.CentOS_7);
		assertVersion("8", CentosVersion.CentOS_8);
	}

	private static void assertVersion(String versionIdContent, CentosVersion version) {
		Optional<Version> detectedVersion = detectVersion(LinuxDistributionTest.releaseFile_VersionIdIs(CentosVersion.RELEASE_FILE_NAME, versionIdContent), CentosVersion.values());
		assertThat(detectedVersion).contains(version);
	}

	private static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, Version... values) {
		return Platform.detectVersion(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.<Version>asList(values));
	}

}