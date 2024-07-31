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
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static de.flapdoodle.os.common.PeculiarityInspector.find;
import static org.assertj.core.api.Assertions.assertThat;

class LinuxMintVersionTest {

  @Test
  public void linuxMintVersionIdMustMatchUbuntuVersion() {
    assertVersion("19", LinuxMintVersion.LINUX_MINT_19_0, UbuntuVersion.Ubuntu_18_04);
    assertVersion("19.1", LinuxMintVersion.LINUX_MINT_19_1, UbuntuVersion.Ubuntu_18_04);
    assertVersion("19.2", LinuxMintVersion.LINUX_MINT_19_2, UbuntuVersion.Ubuntu_18_04);
    assertVersion("19.3", LinuxMintVersion.LINUX_MINT_19_3, UbuntuVersion.Ubuntu_18_04);
    assertVersion("20", LinuxMintVersion.LINUX_MINT_20_0, UbuntuVersion.Ubuntu_20_04);
    assertVersion("20.1", LinuxMintVersion.LINUX_MINT_20_1, UbuntuVersion.Ubuntu_20_04);
    assertVersion("20.2", LinuxMintVersion.LINUX_MINT_20_2, UbuntuVersion.Ubuntu_20_04);
    assertVersion("20.3", LinuxMintVersion.LINUX_MINT_20_3, UbuntuVersion.Ubuntu_20_04);
    assertVersion("21", LinuxMintVersion.LINUX_MINT_21_0, UbuntuVersion.Ubuntu_22_04);
    assertVersion("21.1", LinuxMintVersion.LINUX_MINT_21_1, UbuntuVersion.Ubuntu_22_04);
    assertVersion("21.2", LinuxMintVersion.LINUX_MINT_21_2, UbuntuVersion.Ubuntu_22_04);
    assertVersion("21.3", LinuxMintVersion.LINUX_MINT_21_3, UbuntuVersion.Ubuntu_22_04);
    assertVersion("22", LinuxMintVersion.LINUX_MINT_22_0, UbuntuVersion.Ubuntu_24_04);
    assertVersion("22.1", LinuxMintVersion.LINUX_MINT_22_1, UbuntuVersion.Ubuntu_24_04);
    assertVersion("22.2", LinuxMintVersion.LINUX_MINT_22_2, UbuntuVersion.Ubuntu_24_04);
    assertVersion("22.3", LinuxMintVersion.LINUX_MINT_22_3, UbuntuVersion.Ubuntu_24_04);
  }

  private static void assertVersion(String versionIdContent, LinuxMintVersion version, UbuntuVersion matchingUbuntuVersion) {
		Optional<Version> detectedVersion = detectVersion(AttributeExtractorLookups.osReleaseFileVersionIdIs(versionIdContent), LinuxMintVersion.values());
    assertThat(detectedVersion).contains(version);
    assertThat(((LinuxMintVersion) detectedVersion.get()).matchingUbuntuVersion()).isEqualTo(matchingUbuntuVersion);
  }

  private static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, Version... values) {
    return find(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.<Version>asList(values));
  }

}