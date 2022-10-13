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

class OracleVersionTest {

	@Test
	public void centosVersionIdMustMatchVersion() {
		assertVersion("6", OracleVersion.Oracle_6);
		assertVersion("7", OracleVersion.Oracle_7);
		assertVersion("7.x", OracleVersion.Oracle_7);
		assertVersion("8", OracleVersion.Oracle_8);
	}

	private static void assertVersion(String versionIdContent, OracleVersion version) {
		Optional<Version> detectedOsReleaseVersion = detectVersion(LinuxDistributionTest.releaseFile_VersionIdIs(OsReleaseFiles.RELEASE_FILE_NAME, versionIdContent), OracleVersion.values());
		assertThat(detectedOsReleaseVersion).contains(version);
	}

	private static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, Version... values) {
		return find(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.<Version>asList(values));
	}

}