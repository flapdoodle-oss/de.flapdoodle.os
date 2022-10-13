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
package de.flapdoodle.os;

import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.attributes.MappedTextFile;
import de.flapdoodle.os.common.attributes.SystemProperty;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import de.flapdoodle.os.common.types.ImmutableOsReleaseFile;
import de.flapdoodle.os.common.types.OsReleaseFile;
import de.flapdoodle.os.linux.AmazonVersion;
import de.flapdoodle.os.linux.CentosVersion;
import de.flapdoodle.os.linux.LinuxDistribution;
import de.flapdoodle.os.linux.UbuntuVersion;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PlatformTest {

	@Test
	void platformDetectShouldGiveUbuntu1810() {
		AttributeExtractorLookup attributeExtractorLookup = AttributeExtractorLookup
			.with(SystemProperty.any(), it -> {
				if (it.name().equals("os.name")) {
					return Optional.of("Linux");
				}
				if (it.name().equals("os.arch")) {
					return Optional.of("x86");
				}
				return Optional.empty();
			})
			.join(AttributeExtractorLookup.<OsReleaseFile, MappedTextFile<OsReleaseFile>>with(MappedTextFile.any(),
				attribute -> attribute.name().equals("/etc/os-release") ? Optional.of(ImmutableOsReleaseFile.builder()
					.putAttributes("NAME", "Ubuntu")
					.putAttributes("VERSION_ID", "18.10")
					.build()) : Optional.empty()))
			.join(AttributeExtractorLookup.failing());

		MatcherLookup matcherLookup = MatcherLookup.systemDefault();

		Platform result = Platform.detect(attributeExtractorLookup, matcherLookup);

		assertThat(result)
			.isEqualTo(ImmutablePlatform.builder()
				.operatingSystem(OS.Linux)
				.distribution(LinuxDistribution.Ubuntu)
				.version(UbuntuVersion.Ubuntu_18_10)
				.architecture(CommonArchitecture.X86_32)
				.build());
	}

	@Test
	void failToDetectLinuxDistIfMoreThanOneMatchPossible() {
		AttributeExtractorLookup attributeExtractorLookup = AttributeExtractorLookup
			.with(SystemProperty.any(), it -> {
				if (it.name().equals("os.name")) {
					return Optional.of("Linux");
				}
				if (it.name().equals("os.arch")) {
					return Optional.of("amd64");
				}
				if (it.name().equals("os.version")) {
					return Optional.of("4.14.256-197.484.amzn2.x86_64");
				}
				return Optional.empty();
			})
			.join(AttributeExtractorLookup.<OsReleaseFile, MappedTextFile<OsReleaseFile>>with(MappedTextFile.any(),
				attribute -> attribute.name().equals("/etc/os-release") ? Optional.of(ImmutableOsReleaseFile.builder()
					.putAttributes("NAME", "CentOS")
					.putAttributes("VERSION_ID", "7")
					.build()) : Optional.empty()))
			.join(AttributeExtractorLookup.failing());

		MatcherLookup matcherLookup = MatcherLookup.systemDefault();

		Platform result = Platform.detect(attributeExtractorLookup, matcherLookup);

		assertThat(result)
			.isEqualTo(ImmutablePlatform.builder()
				.operatingSystem(OS.Linux)
				.architecture(CommonArchitecture.X86_64)
				.build());
	}

	@Test
	void guessDoesNotFailIfMoreThanOneMatchPossible() {
		AttributeExtractorLookup attributeExtractorLookup = AttributeExtractorLookup
			.with(SystemProperty.any(), it -> {
				if (it.name().equals("os.name")) {
					return Optional.of("Linux");
				}
				if (it.name().equals("os.arch")) {
					return Optional.of("amd64");
				}
				if (it.name().equals("os.version")) {
					return Optional.of("4.14.256-197.484.amzn2.x86_64");
				}
				return Optional.empty();
			})
			.join(AttributeExtractorLookup.<OsReleaseFile, MappedTextFile<OsReleaseFile>>with(MappedTextFile.any(),
				attribute -> attribute.name().equals("/etc/os-release") ? Optional.of(ImmutableOsReleaseFile.builder()
					.putAttributes("NAME", "CentOS")
					.putAttributes("VERSION_ID", "7")
					.build()) : Optional.empty()))
			.join(AttributeExtractorLookup.failing());

		MatcherLookup matcherLookup = MatcherLookup.systemDefault();

		List<Platform> result = Platform.guess(attributeExtractorLookup, matcherLookup);

		assertThat(result)
			.hasSize(2)
			.containsExactlyInAnyOrder(
				ImmutablePlatform.builder()
					.operatingSystem(OS.Linux)
					.architecture(CommonArchitecture.X86_64)
					.distribution(LinuxDistribution.CentOS)
					.version(CentosVersion.CentOS_7)
					.build(),
				ImmutablePlatform.builder()
					.operatingSystem(OS.Linux)
					.architecture(CommonArchitecture.X86_64)
					.distribution(LinuxDistribution.Amazon)
					.version(AmazonVersion.AmazonLinux2)
					.build()
				);
	}

	@Test
	void systemDefaults() {
		Platform result = Platform.detect();
		System.out.println("OS: " + result.operatingSystem());
		System.out.println("Architecture: " + result.architecture());

		result.distribution().ifPresent(distribution -> System.out.println("Distribution: " + distribution));
		result.version().ifPresent(version -> System.out.println("Version: " + version));
	}

	@Test
	void parseOverrideWithOnlyOsAndArchitecture() {
		String override = OS.OS_X.name() + "|" + CommonArchitecture.X86_64;

		Platform result = Platform.parseOverride(override);

		assertThat(override).isEqualTo("OS_X|X86_64");
		
		assertThat(result)
			.isEqualTo(ImmutablePlatform.builder()
				.operatingSystem(OS.OS_X)
				.architecture(CommonArchitecture.X86_64)
				.build());
	}

	@Test
	void parseOverrideWithDistAndVersion() {
		String override = OS.Linux.name() +
			"|" + CommonArchitecture.X86_32 +
			"|" + LinuxDistribution.CentOS +
			"|" + CentosVersion.CentOS_7;

		Platform result = Platform.parseOverride(override);

		assertThat(override).isEqualTo("Linux|X86_32|CentOS|CentOS_7");
		
		assertThat(result)
			.isEqualTo(ImmutablePlatform.builder()
				.operatingSystem(OS.Linux)
				.architecture(CommonArchitecture.X86_32)
        .distribution(LinuxDistribution.CentOS)
        .version(CentosVersion.CentOS_7)
				.build());
	}

	@Test
	void parseOverrideShouldExplainIfFailing() {
		String override = OS.Linux.name() +
			"|" + CommonArchitecture.X86_32 +
			"|" + "Foo" +
			"|" + CentosVersion.CentOS_7;

		Assertions.assertThatThrownBy(() -> Platform.parseOverride(override))
			.isInstanceOf(RuntimeException.class)
			.hasMessageContaining("something went wrong");
	}
}