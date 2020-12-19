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
import de.flapdoodle.os.common.matcher.MatcherLookup;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class PlatformTest {
  @Test
  void systemDefaults() {
    Platform result = Platform.detect(AttributeExtractorLookup.systemDefault(), MatcherLookup.systemDefault());
    System.out.println("OS: " + result.operatingSystem());
    Optional<Distribution> dist = result.distribution();
    dist.ifPresent(distribution -> System.out.println("Distribution: " + distribution));
    result.version().ifPresent(version -> System.out.println("Version: " + version));
  }
}