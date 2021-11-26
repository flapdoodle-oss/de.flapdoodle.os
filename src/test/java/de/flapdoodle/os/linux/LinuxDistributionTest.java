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
import de.flapdoodle.os.common.attributes.AttributeExtractor;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.attributes.MappedTextFile;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import de.flapdoodle.os.common.types.ImmutableOsReleaseFile;
import de.flapdoodle.os.common.types.OsReleaseFile;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class LinuxDistributionTest {

  @Test
  public void selectUbuntuIfReleaseFileContainsNameWithUbuntu() {
    Optional<Distribution> dist = detectDistribution(osReleaseFileNameIs("ignoreThis_Ubuntu_andThat"), LinuxDistribution.values());
    assertThat(dist).contains(LinuxDistribution.Ubuntu);
  }

  @Test
  public void selectCentosIfReleaseFileContainsNameWithUbuntu() {
    Optional<Distribution> dist = detectDistribution(osReleaseFileNameIs("ignoreThis_Centos_andThat"), LinuxDistribution.values());
    assertThat(dist).contains(LinuxDistribution.CentOS);
  }

  private static Optional<Distribution> detectDistribution(AttributeExtractorLookup attributeExtractorLookup, Distribution... values) {
    return Platform.detectDistribution(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.asList(values));
  }

  private static AttributeExtractorLookup osReleaseFileNameIs(String content) {

    return AttributeExtractorLookup.with((Predicate<? super MappedTextFile<?>>) attr -> true, (AttributeExtractor<OsReleaseFile, MappedTextFile<OsReleaseFile>>) attribute -> {
      if (attribute.name().equals("/etc/os-release")) {
        return Optional.of(ImmutableOsReleaseFile.builder()
                .putAttributes("NAME",content)
                .build());
      }
      return Optional.empty();
    }).join(AttributeExtractorLookup.failing());
  }
}