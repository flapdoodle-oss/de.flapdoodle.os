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

import de.flapdoodle.os.AttributeExtractorLookups;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import de.flapdoodle.os.common.types.ImmutableOsReleaseFile;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static de.flapdoodle.os.common.PeculiarityInspector.find;
import static org.assertj.core.api.Assertions.assertThat;

class AlmaVersionTest {

	@Test
	public void almaReleaseFileNameMustNotChange() {
		assertThat(AlmaVersion.RELEASE_FILE_NAME).isEqualTo("/etc/almalinux-release");
	}

	@Test
	public void almaVersionIdMustMatchVersion() {
		assertVersion("8", AlmaVersion.Alma_8);
		assertVersion("9", AlmaVersion.Alma_9);
	}

	private static void assertVersion(String versionIdContent, AlmaVersion version) {

		Optional<Version> detectedAlmaVersion = detectVersion(
			AttributeExtractorLookups.releaseFile(AlmaVersion.RELEASE_FILE_NAME, ImmutableOsReleaseFile.builder()
				.putAttributes(OsReleaseFiles.VERSION_ID, versionIdContent)
				.build()), AlmaVersion.values());

		Optional<Version> detectedOsReleaseVersion = detectVersion(
			AttributeExtractorLookups.releaseFile(OsReleaseFiles.RELEASE_FILE_NAME, ImmutableOsReleaseFile.builder()
				.putAttributes(OsReleaseFiles.VERSION_ID, versionIdContent)
				.build()), AlmaVersion.values());
		assertThat(detectedAlmaVersion).contains(version);
		assertThat(detectedOsReleaseVersion).contains(version);
	}

	private static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, Version... values) {
		return find(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.asList(values));
	}

}