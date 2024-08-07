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
import de.flapdoodle.os.common.attributes.TypeCheckPredicate;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import de.flapdoodle.os.common.types.ImmutableOsReleaseFile;
import de.flapdoodle.os.common.types.OsReleaseFile;
import de.flapdoodle.os.linux.AmazonVersion;
import de.flapdoodle.os.linux.LinuxDistribution;
import de.flapdoodle.os.linux.OracleVersion;
import de.flapdoodle.os.linux.OsReleaseFiles;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static de.flapdoodle.os.common.PeculiarityInspector.match;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommonOSTest {

  @Test
  void detectLinuxIfOsNameIsLinux() {
		CommonOS os = match(osNameIs("Linux"), MatcherLookup.systemDefault(), CommonOS.values());
    assertThat(os).isEqualTo(CommonOS.Linux);
    assertThat(os.type()).isEqualTo(OSType.Linux);
  }

  @Test
  void detectWindowsIfOsNameStartsWithWindows() {
		CommonOS os = match(osNameIs("WindowsFooBarBlub"), MatcherLookup.systemDefault(), CommonOS.values());
    assertThat(os).isEqualTo(CommonOS.Windows);
    assertThat(os.type()).isEqualTo(OSType.Windows);
  }

  @Test
  void detectOSXIfOsNameIsMacOSX() {
		CommonOS os = match(osNameIs("Mac OS X"), MatcherLookup.systemDefault(), CommonOS.values());
    assertThat(os).isEqualTo(CommonOS.OS_X);
    assertThat(os.type()).isEqualTo(OSType.OS_X);
  }

  @Test
  void detectFreeBSDIfOsNameIsFreeBSD() {
		CommonOS os = match(osNameIs("FreeBSD"), MatcherLookup.systemDefault(), CommonOS.values());
    assertThat(os).isEqualTo(CommonOS.FreeBSD);
    assertThat(os.type()).isEqualTo(OSType.FreeBSD);
  }

  @Test
  void detectSolarisIfOsNameContainsSunOS() {
		CommonOS os = match(osNameIs("doesNotMatterSunOSIgnoreThisTooo"), MatcherLookup.systemDefault(), CommonOS.values());
    assertThat(os).isEqualTo(CommonOS.Solaris);
    assertThat(os.type()).isEqualTo(OSType.Solaris);
  }

  @Test
  void detectionMustFailIfNothingMatches() {
    assertThatThrownBy(() -> match(osNameIs("what"), MatcherLookup.systemDefault(), CommonOS.values()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("no match out of [Linux, Windows, OS_X, Solaris, FreeBSD]");
  }

  @Test
  void detectionMustFailIfMoreThanOneMatch() {
    assertThatThrownBy(() -> match(osNameIs("WindowsSunOS"), MatcherLookup.systemDefault(), CommonOS.values()))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("more than one match: [Windows, Solaris]");
  }

  @Test
  void detectionMustUseFirstMatchIfMoreThanOneMatches() {
    AttributeExtractorLookup releaseFile = AttributeExtractorLookup.with(MappedTextFile.any(), r -> {
      switch (r.name()) {
        case "/etc/os-release": return Optional.of(ImmutableOsReleaseFile.builder()
          .putAttributes("NAME","Oracle")
          .putAttributes("VERSION_ID","9")
          .build());
      }
      return Optional.empty();
    });

    AttributeExtractorLookup extractorLookup = AttributeExtractorLookup.with(
        SystemProperty.any(), attribute -> {
					switch (attribute.name()) {
            case "os.name": return Optional.of("Linux");
            case "os.arch": return Optional.of("x86_64");
            case "os.version": return Optional.of("foo.amzn.bar");
            default: return Optional.empty();
          }
        })
      .join(releaseFile)
      .join(AttributeExtractorLookup.failing());
//    CommonOS os = match(extractorLookup, MatcherLookup.systemDefault(), CommonOS.values());

    Platform result = Platform.detect(CommonOS.list(), extractorLookup, MatcherLookup.systemDefault());

    assertThat(result.operatingSystem()).isEqualTo(CommonOS.Linux);
    assertThat(result.architecture()).isEqualTo(CommonArchitecture.X86_64);
    assertThat(result.distribution()).contains(LinuxDistribution.Oracle);
    assertThat(result.version()).contains(OracleVersion.Oracle_9);
  }

  @Test
  void detectionMustUseFirstMatchIfMoreThanOneMatchesEvenIfItsAmazonOnly() {
    AttributeExtractorLookup releaseFile = AttributeExtractorLookup.with(MappedTextFile.any(), r -> {
      switch (r.name()) {
        case "/etc/os-release": return Optional.of(ImmutableOsReleaseFile.builder()
          .putAttributes("NAME","Amazon Linux")
          .putAttributes("VERSION_ID","2")
          .build());
      }
      return Optional.empty();
    });

    AttributeExtractorLookup extractorLookup = AttributeExtractorLookup.with(
        SystemProperty.any(), attribute -> {
          switch (attribute.name()) {
            case "os.name": return Optional.of("Linux");
            case "os.arch": return Optional.of("x86_64");
            case "os.version": return Optional.of("foo.amzn2023.bar");
            default: return Optional.empty();
          }
        })
      .join(releaseFile)
      .join(AttributeExtractorLookup.failing());
//    CommonOS os = match(extractorLookup, MatcherLookup.systemDefault(), CommonOS.values());

    Platform result = Platform.detect(CommonOS.list(), extractorLookup, MatcherLookup.systemDefault());

    assertThat(result.operatingSystem()).isEqualTo(CommonOS.Linux);
    assertThat(result.architecture()).isEqualTo(CommonArchitecture.X86_64);
    assertThat(result.distribution()).contains(LinuxDistribution.Amazon);
    assertThat(result.version()).contains(AmazonVersion.AmazonLinux2023);
  }

  private static AttributeExtractorLookup osNameIs(String osName) {
    return AttributeExtractorLookup.with(
      SystemProperty.any(), attribute -> attribute.name().equals("os.name") ? Optional.of(osName) : Optional.empty())
      .join(AttributeExtractorLookup.failing());
  }
}
