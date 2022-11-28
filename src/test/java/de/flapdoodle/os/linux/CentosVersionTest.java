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
package de.flapdoodle.os.linux;

import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static de.flapdoodle.os.common.PeculiarityInspector.find;
import static org.assertj.core.api.Assertions.assertThat;

class CentosVersionTest {

	@Test
	public void centosReleaseFileNameMustNotChange() {
		assertThat(CentosVersion.RELEASE_FILE_NAME).isEqualTo("/etc/centos-release");
	}

	@Test
	public void centosVersionIdMustMatchVersion() {
		assertVersion("6", CentosVersion.CentOS_6);
		assertVersion("7", CentosVersion.CentOS_7);
		assertVersion("8", CentosVersion.CentOS_8);
		assertVersion("9", CentosVersion.CentOS_9);
	}

	private static void assertVersion(String versionIdContent, CentosVersion version) {
		Optional<Version> detectedCentosVersion = detectVersion(LinuxDistributionTest.releaseFile_VersionIdIs(CentosVersion.RELEASE_FILE_NAME, versionIdContent), CentosVersion.values());
		Optional<Version> detectedOsReleaseVersion = detectVersion(LinuxDistributionTest.releaseFile_VersionIdIs(OsReleaseFiles.RELEASE_FILE_NAME, versionIdContent), CentosVersion.values());
		assertThat(detectedCentosVersion).contains(version);
		assertThat(detectedOsReleaseVersion).contains(version);
	}

	private static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, Version... values) {
		return find(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.<Version>asList(values));
	}

}