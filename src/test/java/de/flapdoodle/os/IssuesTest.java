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
import de.flapdoodle.os.common.types.OsReleaseFileConverter;
import de.flapdoodle.os.linux.AmazonVersion;
import de.flapdoodle.os.linux.LinuxDistribution;
import de.flapdoodle.os.linux.OsReleaseFiles;
import de.flapdoodle.os.linux.UbuntuVersion;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class IssuesTest {

	@Test
	void ubuntu24_04_NotDetected() {
		AttributeExtractorLookup releaseFile = AttributeExtractorLookup.with(MappedTextFile.any(), r -> {
			switch (r.name()) {
				case "/etc/os-release": return Optional.of(releaseFile("/samples/ubuntu/ubuntu24_04/etc/os-release"));
			}
			return Optional.empty();
		});

		AttributeExtractorLookup extractorLookup = AttributeExtractorLookup.with(
				SystemProperty.any(), attribute -> {
					switch (attribute.name()) {
						case "os.name": return Optional.of("Linux");
						case "os.arch": return Optional.of("x86_64");
						default: return Optional.empty();
					}
				})
			.join(releaseFile)
			.join(AttributeExtractorLookup.failing());
//    CommonOS os = match(extractorLookup, MatcherLookup.systemDefault(), CommonOS.values());

		Platform result = Platform.detect(CommonOS.list(), extractorLookup, MatcherLookup.systemDefault());

		assertThat(result.operatingSystem()).isEqualTo(CommonOS.Linux);
		assertThat(result.architecture()).isEqualTo(CommonArchitecture.X86_64);
		assertThat(result.distribution()).contains(LinuxDistribution.Ubuntu);
		assertThat(result.version()).contains(UbuntuVersion.Ubuntu_24_04);
	}

	@Test
	void ubuntu24_10_NotDetected() {
		AttributeExtractorLookup releaseFile = AttributeExtractorLookup.with(MappedTextFile.any(), r -> {
			switch (r.name()) {
				case "/etc/os-release": return Optional.of(releaseFile("/samples/ubuntu/ubuntu24_10/etc/os-release"));
			}
			return Optional.empty();
		});

		AttributeExtractorLookup extractorLookup = AttributeExtractorLookup.with(
				SystemProperty.any(), attribute -> {
					switch (attribute.name()) {
						case "os.name": return Optional.of("Linux");
						case "os.arch": return Optional.of("x86_64");
						default: return Optional.empty();
					}
				})
			.join(releaseFile)
			.join(AttributeExtractorLookup.failing());
//    CommonOS os = match(extractorLookup, MatcherLookup.systemDefault(), CommonOS.values());

		Platform result = Platform.detect(CommonOS.list(), extractorLookup, MatcherLookup.systemDefault());

		assertThat(result.operatingSystem()).isEqualTo(CommonOS.Linux);
		assertThat(result.architecture()).isEqualTo(CommonArchitecture.X86_64);
		assertThat(result.distribution()).contains(LinuxDistribution.Ubuntu);
		assertThat(result.version()).contains(UbuntuVersion.Ubuntu_24_10);
	}

	private static OsReleaseFile releaseFile(String resourceName) {
		try {
			URI resourcePath = Objects.requireNonNull(IssuesTest.class.getResource(resourceName)).toURI();
			byte[] byteContent = Files.readAllBytes(Paths.get(resourcePath));
			String content = new String(byteContent, StandardCharsets.UTF_8);
			return OsReleaseFileConverter.convert(content);
		}
		catch (URISyntaxException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}
