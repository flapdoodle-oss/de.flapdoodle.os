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

class PopOSVersionTest {

  @Test
  public void popOsVersionIdMustMatchUbuntuVersion() {
    assertVersion("22.04", PopOSVersion.POP_OS_22_4, UbuntuVersion.Ubuntu_22_04);
  }

  private static void assertVersion(String versionIdContent, PopOSVersion version, UbuntuVersion matchingUbuntuVersion) {
		Optional<Version> detectedVersion = detectVersion(AttributeExtractorLookups.osReleaseFileVersionIdIs(versionIdContent), PopOSVersion.values());
    assertThat(detectedVersion).contains(version);
    assertThat(((PopOSVersion) detectedVersion.get()).matchingUbuntuVersion()).isEqualTo(matchingUbuntuVersion);
  }

  private static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, Version... values) {
    return find(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.<Version>asList(values));
  }

}