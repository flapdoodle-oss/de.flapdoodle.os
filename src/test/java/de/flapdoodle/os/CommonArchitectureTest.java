/**
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
import de.flapdoodle.os.common.attributes.SystemProperty;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CommonArchitectureTest {

  @ParameterizedTest
  @ValueSource(strings = {"amd64","ia32e","x64"})
  void detectX86_64IfOsArchMatches(String osArch) {
    Optional<Architecture> arch = detectArchitecture(osArchIs(osArch), CommonArchitecture.values());
    assertThat(arch).contains(CommonArchitecture.X86_64);
  }

  @ParameterizedTest
  @ValueSource(strings = {"aarch64"})
  void detectArm64IfOsArchMatches(String osArch) {
    Optional<Architecture> arch = detectArchitecture(osArchIs(osArch), CommonArchitecture.values());
    assertThat(arch).contains(CommonArchitecture.ARM_64);
  }

  private static Optional<Architecture> detectArchitecture(AttributeExtractorLookup attributeExtractorLookup, Architecture ... values) {
    return Platform.detectArchitecture(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.<Architecture>asList(values));
  }
  
  private static AttributeExtractorLookup osArchIs(String content) {
    return AttributeExtractorLookup.forType(SystemProperty.class, attribute -> {
      if (attribute.name().equals("os.arch")) {
        return Optional.of(content);
      }
      return Optional.empty();
    }).join(AttributeExtractorLookup.failing());
  }

}