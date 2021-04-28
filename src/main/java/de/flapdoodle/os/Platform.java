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
import de.flapdoodle.os.common.matcher.MatcherLookup;
import org.immutables.value.Value;

import java.util.List;
import java.util.Optional;

import static de.flapdoodle.os.common.PeculiarityInspector.find;
import static de.flapdoodle.os.common.PeculiarityInspector.match;

@Value.Immutable
public interface Platform {
  OS operatingSystem();

  Architecture architecture();

  Optional<Distribution> distribution();

  Optional<Version> version();

  static Platform detect() {
    return detect(AttributeExtractorLookup.systemDefault(), MatcherLookup.systemDefault());
  }

  static Platform detect(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup) {
    ImmutablePlatform.Builder builder = ImmutablePlatform.builder();
    OS os = detectOS(attributeExtractorLookup, matcherLookup);
    Architecture architecture = detectArchitecture(attributeExtractorLookup, matcherLookup, os.architectures());

    Optional<Distribution> dist = detectDistribution(attributeExtractorLookup, matcherLookup, os.distributions());
    Optional<Version> version = dist.flatMap(d -> detectVersion(attributeExtractorLookup, matcherLookup, d));

    return builder.operatingSystem(os)
            .distribution(dist)
            .version(version)
            .architecture(architecture)
            .build();
  }

  static OS detectOS(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup) {
    return match(attributeExtractorLookup, matcherLookup, OS.values());
  }

  static Optional<Distribution> detectDistribution(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup, Iterable<Distribution> distributions) {
    return find(attributeExtractorLookup, matcherLookup, distributions);
  }

  static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup, Distribution distribution) {
    return detectVersion(attributeExtractorLookup, matcherLookup, distribution.versions());
  }

  static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup, List<Version> versions) {
    return find(attributeExtractorLookup, matcherLookup, versions);
  }

  static Architecture detectArchitecture(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup, List<Architecture> architectures) {
    return match(attributeExtractorLookup, matcherLookup, architectures);
  }
}
