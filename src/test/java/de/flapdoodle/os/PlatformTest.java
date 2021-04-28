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

import de.flapdoodle.os.common.attributes.*;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import de.flapdoodle.os.common.types.ImmutableOsReleaseFile;
import de.flapdoodle.os.linux.LinuxDistribution;
import de.flapdoodle.os.linux.UbuntuVersion;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class PlatformTest {

  @Test
  void detectAll() {
    AttributeExtractorLookup attributeExtractorLookup = AttributeExtractorLookup
            .forType(SystemProperty.class, prop -> {
              switch (prop.name()) {
                case "os.name":
                  return Optional.of("Linux");
                case "os.arch":
                  return Optional.of("x86");
              }
              return Optional.empty();
            })
            .join(AttributeExtractorLookup.forType(MappedTextFile.class, new MockMappedTextFile() {
              @Override
              public Optional extract(Attribute attribute) {
                return Optional.of(ImmutableOsReleaseFile.builder()
                        .putAttributes("NAME","Ubuntu")
                        .putAttributes("VERSION_ID","18.10")
                        .build());
              }
            }))
            .join(AttributeExtractorLookup.failing());
    
    MatcherLookup matcherLookup = MatcherLookup.systemDefault();

    Platform result = Platform.detect(attributeExtractorLookup, matcherLookup);

    Assertions.assertThat(result)
            .isEqualTo(ImmutablePlatform.builder()
                    .operatingSystem(OS.Linux)
                    .distribution(LinuxDistribution.Ubuntu)
                    .version(UbuntuVersion.UBUNTU_18_10)
                    .architecture(CommonArchitecture.X86_32)
                    .build());
  }

  interface MockMappedTextFile<T> extends AttributeExtractor<T, MappedTextFile<T>> {

  }

  @Test
  void systemDefaults() {
    Platform result = Platform.detect();
    System.out.println("OS: " + result.operatingSystem());
    System.out.println("Architecture: " + result.architecture());

    result.distribution().ifPresent(distribution -> System.out.println("Distribution: " + distribution));
    result.version().ifPresent(version -> System.out.println("Version: " + version));
  }
}