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
import de.flapdoodle.os.Distribution;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.attributes.AttributeExtractor;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.attributes.MappedTextFile;
import de.flapdoodle.os.common.attributes.SystemProperty;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import de.flapdoodle.os.common.types.ImmutableOsReleaseFile;
import de.flapdoodle.os.common.types.OsReleaseFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static de.flapdoodle.os.common.PeculiarityInspector.find;
import static org.assertj.core.api.Assertions.assertThat;

class LinuxDistributionTest {

	@Test
	public void selectUbuntuIfReleaseFileContainsNameWithUbuntu() {
		Optional<Distribution> dist = detectDistribution(osReleaseFile_NameIs(wrapWithRandomString("Ubuntu")), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.Ubuntu);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(UbuntuVersion.values());
	}

	@Test
	public void selectCentosIfReleaseFileContainsNameWithCentos() {
		Optional<Distribution> dist = detectDistribution(osReleaseFile_NameIs(wrapWithRandomString("CentOS")), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.CentOS);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(CentosVersion.values());
	}

	@Test
	public void selectRedhatIfReleaseFileContainsNameWithRedHat() {
		Optional<Distribution> dist = detectDistribution(osReleaseFile_NameIs(wrapWithRandomString("Red Hat")), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.Redhat);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(RedhatVersion.values());
	}

	@Test
	public void selectFedoraIfReleaseFileContainsNameWithFedora() {
		Optional<Distribution> dist = detectDistribution(osReleaseFile_NameIs(wrapWithRandomString("Fedora")), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.Fedora);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(FedoraVersion.values());
	}

	@Test
	public void selectOracleIfReleaseFileContainsNameWithOracle() {
		Optional<Distribution> dist = detectDistribution(osReleaseFile_NameIs(wrapWithRandomString("Oracle")), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.Oracle);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(OracleVersion.values());
	}

	@Test
	public void selectCentosIfCentosReleaseFileContainsNameWithCentos() {
		Optional<Distribution> dist = detectDistribution(centosReleaseFile_NameIs(wrapWithRandomString("CentOS")), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.CentOS);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(CentosVersion.values());
	}

	@Test
	public void selectDebianIfReleaseFileContainsNameWithDebian() {
		Optional<Distribution> dist = detectDistribution(osReleaseFile_NameIs(wrapWithRandomString("Debian")), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.Debian);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(DebianVersion.values());
	}

	@Test
	public void selectOpenSUSEIfReleaseFileContainsNameWithOpenSUSE() {
		Optional<Distribution> dist = detectDistribution(osReleaseFile_NameIs(wrapWithRandomString("openSUSE")), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.OpenSUSE);
	}

	@Test
	public void selectLinuxMintIfReleaseFileContainsNameWithLinuxMint() {
		Optional<Distribution> dist = detectDistribution(osReleaseFile_NameIs(wrapWithRandomString("Linux Mint")), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.LinuxMint);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(LinuxMintVersion.values());
	}

	@Test
	public void selectAmazonIfOsVersionContainsAmzn() {
		Optional<Distribution> dist = detectDistribution(osVersion_Is("4.9.76-3.78.amzn1.x86_64"), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.Amazon);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(AmazonVersion.values());
	}

	@Test
	public void selectAmazon2IfOsVersionContainsAmzn() {
		Optional<Distribution> dist = detectDistribution(osVersion_Is("4.14.256-197.484.amzn2.x86_64"), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.Amazon);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(AmazonVersion.values());
	}

	@Test
	public void selectAmazonIfReleaseFileContainsNameWithAmazon() {
		Optional<Distribution> dist = detectDistribution(osReleaseFile_NameIs(wrapWithRandomString("Amazon Linux")), LinuxDistribution.values());
		assertThat(dist).contains(LinuxDistribution.Amazon);
		Assertions.<Version>assertThat(dist.get().versions())
			.containsExactlyInAnyOrder(AmazonVersion.values());
	}

	private static Optional<Distribution> detectDistribution(AttributeExtractorLookup attributeExtractorLookup, Distribution... values) {
		return find(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.asList(values));
	}

	private static AttributeExtractorLookup osReleaseFile_NameIs(String content) {
		return AttributeExtractorLookups.releaseFile(OsReleaseFiles.RELEASE_FILE_NAME, ImmutableOsReleaseFile.builder()
			.putAttributes(OsReleaseFiles.NAME, content)
			.build());
	}

	private static AttributeExtractorLookup centosReleaseFile_NameIs(String content) {
		return AttributeExtractorLookups.releaseFile(CentosVersion.RELEASE_FILE_NAME, ImmutableOsReleaseFile.builder()
			.putAttributes(OsReleaseFiles.NAME, content)
			.build());
	}

	private static AttributeExtractorLookup osVersion_Is(String content) {
		return AttributeExtractorLookup.with(
				SystemProperty.any(), attribute -> attribute.name().equals("os.version") ? Optional.of(content) : Optional.empty())
			.join(AttributeExtractorLookup.with(MappedTextFile.any(),
					(AttributeExtractor<OsReleaseFile, MappedTextFile<OsReleaseFile>>) attribute -> Optional.empty()))
			.join(AttributeExtractorLookup.failing());
	}

	private static final String sampleChars="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_+";

	private static String randomString() {
		int len = ThreadLocalRandom.current().nextInt() % 3 == 0
			? 0
			: ThreadLocalRandom.current().nextInt(0, 7);

		char[] chars=new char[len];
		for (int i=0;i<chars.length;i++) {
			chars[i]=sampleChars.charAt(ThreadLocalRandom.current().nextInt(0,sampleChars.length()));
		}
		
		return new String(chars);
	}
	private static String wrapWithRandomString(String match) {
		return randomString() + match + randomString();
	}
}