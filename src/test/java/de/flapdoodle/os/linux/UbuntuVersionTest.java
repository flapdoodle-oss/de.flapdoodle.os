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

import de.flapdoodle.os.Platform;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UbuntuVersionTest {

  @Test
  public void ubuntuVersionIdMustMatchUbuntuVersion() {
    assertVersion("18.04", UbuntuVersion.Ubuntu_18_04);
    assertVersion("18.10", UbuntuVersion.Ubuntu_18_10);
    assertVersion("19.04", UbuntuVersion.Ubuntu_19_04);
    assertVersion("19.10", UbuntuVersion.Ubuntu_19_10);
    assertVersion("20.04", UbuntuVersion.Ubuntu_20_04);
    assertVersion("20.10", UbuntuVersion.Ubuntu_20_10);
  }

  private static void assertVersion(String versionIdContent, UbuntuVersion version) {
    Optional<Version> detectedVersion = detectVersion(LinuxDistributionTest.osReleaseFile_VersionIdIs(versionIdContent), UbuntuVersion.values());
    assertThat(detectedVersion).contains(version);

  }

  private static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, Version... values) {
    return Platform.detectVersion(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.<Version>asList(values));
  }

}