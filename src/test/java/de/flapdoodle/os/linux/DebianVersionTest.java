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

import static de.flapdoodle.os.AttributeExtractorLookups.osReleaseFile;
import static de.flapdoodle.os.AttributeExtractorLookups.osReleaseFileVersionIdIs;
import static de.flapdoodle.os.common.PeculiarityInspector.find;
import static org.assertj.core.api.Assertions.assertThat;

class DebianVersionTest {

  @Test
  public void debianVersionIdMustMatchDebianVersion() {
    assertVersion("9", DebianVersion.DEBIAN_9);
    assertVersion("10", DebianVersion.DEBIAN_10);
    assertVersion("11", DebianVersion.DEBIAN_11);
    assertVersion("12", DebianVersion.DEBIAN_12);
    assertVersion("13", DebianVersion.DEBIAN_13);
  }

  @Test
  public void debian12_testingMatchCodeName() {
    Optional<Version> detectedVersion = detectVersion(osReleaseFile(ImmutableOsReleaseFile.builder()
      .putAttributes(OsReleaseFiles.VERSION_CODENAME, "bookworm")
      .build()), DebianVersion.values());
    assertThat(detectedVersion).contains(DebianVersion.DEBIAN_12);
  }

  @Test
  public void debian13_testingMatchCodeName() {
    Optional<Version> detectedVersion = detectVersion(osReleaseFile(ImmutableOsReleaseFile.builder()
      .putAttributes(OsReleaseFiles.VERSION_CODENAME, "trixie")
      .build()), DebianVersion.values());
    assertThat(detectedVersion).contains(DebianVersion.DEBIAN_13);
  }

  private static void assertVersion(String versionIdContent, DebianVersion version) {
    Optional<Version> detectedVersion = detectVersion(osReleaseFileVersionIdIs(versionIdContent), DebianVersion.values());
    assertThat(detectedVersion).contains(version);
  }

  private static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, Version... values) {
    return find(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.<Version>asList(values));
  }
}