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

import de.flapdoodle.os.Distribution;
import de.flapdoodle.os.Platform;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.attributes.*;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import de.flapdoodle.os.common.types.ImmutableOsReleaseFile;
import de.flapdoodle.os.common.types.OsReleaseFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LinuxDistributionTest {

	@Test
	public void selectUbuntuIfReleaseFileContainsNameWithUbuntu() {
		Optional<Distribution> dist = detectDistribution(osReleaseFile_NameIs("ignoreThis_Ubuntu_andThat"), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.Ubuntu);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(UbuntuVersion.values());
	}

	@Test
	public void selectCentosIfReleaseFileContainsNameWithUbuntu() {
		Optional<Distribution> dist = detectDistribution(centosReleaseFile_NameIs("ignoreThis_CentOS_andThat"), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.CentOS);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(CentosVersion.values());
	}

	@Test
	public void selectDebianIfReleaseFileContainsNameWithDebian() {
		Optional<Distribution> dist = detectDistribution(osReleaseFile_NameIs("ignoreThis_Debian_andThat"), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.Debian);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(DebianVersion.values());
	}

	@Test
	public void selectOpenSUSEIfReleaseFileContainsNameWithOpenSUSE() {
		Optional<Distribution> dist = detectDistribution(osReleaseFile_NameIs("ignoreThis_openSUSE_andThat"), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.OpenSUSE);
	}

	@Test
	public void selectLinuxMintIfReleaseFileContainsNameWithLinuxMint() {
		Optional<Distribution> dist = detectDistribution(osReleaseFile_NameIs("ignoreThis_Linux Mint_andThat"), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.LinuxMint);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(LinuxMintVersion.values());
	}

	@Test
	public void selectCentosIfReleaseFileContainsNameWithCentos() {
		Optional<Distribution> dist = detectDistribution(centosReleaseFile_NameIs("CentOS Linux"), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.CentOS);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(CentosVersion.values());
	}

	@Test
	public void selectAmazonIfOsVersionContainsAmzn() {
		Optional<Distribution> dist = detectDistribution(osVersion_Is("4.9.76-3.78.amzn1.x86_64"), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.Amazon);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(AmazonVersion.values());
	}

	private static Optional<Distribution> detectDistribution(AttributeExtractorLookup attributeExtractorLookup, Distribution... values) {
		return Platform.detectDistribution(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.asList(values));
	}

	private static AttributeExtractorLookup osReleaseFile_NameIs(String content) {
		return releaseFile_NameIs("/etc/os-release", content);
	}

	private static AttributeExtractorLookup centosReleaseFile_NameIs(String content) {
		return releaseFile_NameIs(CentosVersion.RELEASE_FILE_NAME, content);
	}

	private static AttributeExtractorLookup osVersion_Is(String content) {
		return AttributeExtractorLookup.with(
				SystemProperty.any(), attribute -> attribute.name().equals("os.version") ? Optional.of(content) : Optional.empty())
			.join(AttributeExtractorLookup.with(MappedTextFile.any(),
					(AttributeExtractor<OsReleaseFile, MappedTextFile<OsReleaseFile>>) attribute -> Optional.empty()))
			.join(AttributeExtractorLookup.failing());
	}

	private static AttributeExtractorLookup releaseFile_NameIs(String releaseFileName, String content) {

		return AttributeExtractorLookup.with(MappedTextFile.any(),
				(AttributeExtractor<OsReleaseFile, MappedTextFile<OsReleaseFile>>) attribute -> attribute.name().equals(releaseFileName)
					? Optional.of(ImmutableOsReleaseFile.builder()
					.putAttributes("NAME", content)
					.build())
					: Optional.empty())
			.join(AttributeExtractorLookup.with(SystemProperty.any(), it -> Optional.empty()))
			.join(AttributeExtractorLookup.failing());
	}

	static AttributeExtractorLookup osReleaseFile_VersionIdIs(String content) {
		return releaseFile_VersionIdIs(OsReleaseFiles.RELEASE_FILE_NAME, content);
	}

	static AttributeExtractorLookup releaseFile_VersionIdIs(String releaseFileName, String content) {

		return AttributeExtractorLookup.with(MappedTextFile.any(), (AttributeExtractor<OsReleaseFile, MappedTextFile<OsReleaseFile>>) attribute ->
				attribute.name().equals(releaseFileName)
					? Optional.of(ImmutableOsReleaseFile.builder()
					.putAttributes("VERSION_ID", content)
					.build())
					: Optional.empty())
			.join(AttributeExtractorLookup.failing());
	}
}