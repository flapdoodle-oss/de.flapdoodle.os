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
import de.flapdoodle.os.common.attributes.SystemProperty;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import de.flapdoodle.os.common.types.ImmutableOsReleaseFile;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static de.flapdoodle.os.common.PeculiarityInspector.find;
import static org.assertj.core.api.Assertions.assertThat;

class AmazonVersionTest {

	@Test
	public void osVersionMustMatchAmazonVersion() {
		assertVersion("4.9.76-3.78.amzn1.x86_64", AmazonVersion.AmazonLinux);
		assertVersion("4.14.186-146.268.amzn2.x86_64", AmazonVersion.AmazonLinux2);
		assertVersion("4.14.186-146.268.amzn2023.x86_64", AmazonVersion.AmazonLinux2023);
	}

	@Test
	public void detectAmazon2IfNameAndVersionContainedInOsRelease() {
		Optional<Version> detectedVersion = detectVersion(AttributeExtractorLookups.osReleaseFile(ImmutableOsReleaseFile.builder()
			.putAttributes(OsReleaseFiles.NAME, "Amazon Linux")
			.putAttributes(OsReleaseFiles.VERSION_ID, "2")
			.build()), AmazonVersion.values());
		assertThat(detectedVersion).contains(AmazonVersion.AmazonLinux2);
	}

	private static void assertVersion(String versionIdContent, AmazonVersion version) {
		Optional<Version> detectedVersion = detectVersion(osVersion(versionIdContent), AmazonVersion.values());
		assertThat(detectedVersion).contains(version);
	}

	private static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, Version... values) {
		return find(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.<Version>asList(values));
	}

	static AttributeExtractorLookup osVersion(String content) {
		return AttributeExtractorLookup.with(
				SystemProperty.any(), attribute -> attribute.name().equals("os.version") ? Optional.of(content) : Optional.empty())
			.join(AttributeExtractorLookups.osReleaseFileVersionIdIs("unused"))
			.join(AttributeExtractorLookup.failing());
	}
}