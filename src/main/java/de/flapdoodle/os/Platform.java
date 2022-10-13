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
import de.flapdoodle.os.common.attributes.LoggingWrapper;
import de.flapdoodle.os.common.collections.Immutables;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import org.immutables.value.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.flapdoodle.os.common.PeculiarityInspector.*;

@Value.Immutable
public interface Platform {
  OS operatingSystem();

  Architecture architecture();

  Optional<Distribution> distribution();

  Optional<Version> version();

  Logger logger = LoggerFactory.getLogger(Platform.class);

  static Platform detect() {
    boolean explain = "true".equals(System.getProperty("de.flapdoodle.os.explain"));

    String override = System.getProperty("de.flapdoodle.os.override");
    if (override!=null && !override.trim().isEmpty()) {
      logger.info("try to override Platform.detect() with "+override);
      return parseOverride(override);
    }

    AttributeExtractorLookup attributeExtractorLookup = AttributeExtractorLookup.systemDefault();
    MatcherLookup matcherLookup = MatcherLookup.systemDefault();
    if (explain) {
      attributeExtractorLookup= LoggingWrapper.wrap(attributeExtractorLookup);
      matcherLookup = LoggingWrapper.wrap(matcherLookup);
    }
    Platform result = detect(attributeExtractorLookup, matcherLookup);
    if (explain) {
      logger.info("Platform.detect() -> "+result);
    }
    return result;
  }

  static Platform parseOverride(String override) {
    ImmutablePlatform.Builder builder = ImmutablePlatform.builder();
    List<String> parts = Arrays.asList(override.split("\\|"));
    if (parts.size()==0) throw new IllegalArgumentException("could not parse: "+override);

    try {
      OS os = OS.valueOf(parts.get(0));
      builder.operatingSystem(os);
      if (parts.size() > 1) {
        builder.architecture(CommonArchitecture.valueOf(parts.get(1)));
      }
      if (parts.size() > 3) {
        String dist = parts.get(2);
        String version = parts.get(3);

        List<? extends Distribution> matchingDists = os.distributions().stream()
          .filter(d -> d.name().equals(dist))
          .collect(Collectors.toList());

        if (matchingDists.size() != 1) {
          throw new IllegalArgumentException("could not match " + dist + " to " + os.distributions());
        }

        Distribution distribution = matchingDists.get(0);
        builder.distribution(distribution);

        List<? extends Version> matchingVersions = distribution.versions().stream()
          .filter(v -> v.name().equals(version))
          .collect(Collectors.toList());

        if (matchingVersions.size() != 1) {
          throw new IllegalArgumentException("could not match " + version + " to " + distribution.versions());
        }

        builder.version(matchingVersions.get(0));
      }
      return builder.build();
    } catch (RuntimeException rx) {
      StringBuilder sb=new StringBuilder();
      String nl = System.lineSeparator();

      sb.append("---").append(nl);
      sb.append("something went wrong, some minor hints to guide you a little bit:").append(nl);
      sb.append("  format:       '<OS>|<Architecture>' or '<OS>|<Architecture>|<Dist>|<Version>'").append(nl);
      sb.append("  you provided: '").append(override).append("'").append(nl);
      sb.append("you should choose from:").append(nl);
      sb.append("  OS: ").append(nl)
        .append("    ").append(Stream.of(OS.values()).map(OS::name).collect(Collectors.joining(", "))).append(nl);
      sb.append("  Architecture: ").append(nl)
        .append("    ").append(Stream.of(CommonArchitecture.values()).map(CommonArchitecture::name).collect(Collectors.joining(", "))).append(nl);

      for (OS os : OS.values()) {
        os.distributions().forEach(dist -> {
          sb.append("  Dist and Version: ").append(nl)
            .append("    ").append(dist.name()).append(" and ").append(dist.versions().stream().map(Version::name).collect(Collectors.joining(", "))).append(nl);
        });
      }

      throw new RuntimeException(sb.toString(), rx);
    }
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

  static List<Platform> guess(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup) {
    OS os = detectOS(attributeExtractorLookup, matcherLookup);
    Architecture architecture = detectArchitecture(attributeExtractorLookup, matcherLookup, os.architectures());

    List<? extends Distribution> dists = matching(attributeExtractorLookup, matcherLookup, os.distributions());

    List<Platform> platforms = dists.stream()
      .flatMap(dist -> {
        List<? extends Version> versions = matching(attributeExtractorLookup, matcherLookup, dist.versions());

        return !versions.isEmpty()
          ? versions
            .stream()
            .map(version -> ImmutablePlatform.builder()
              .operatingSystem(os)
              .distribution(dist)
              .architecture(architecture)
              .version(version)
              .build())
          : Stream.of(ImmutablePlatform.builder()
            .operatingSystem(os)
            .distribution(dist)
            .architecture(architecture)
            .build());
        }
      )
      .collect(Collectors.toList());

    List<? extends Platform> matches = !platforms.isEmpty()
      ? platforms
      : Immutables.asNonEmptyList(ImmutablePlatform.builder()
      .operatingSystem(os)
      .architecture(architecture)
      .build());

    return VersionWithPriority.sorteByPriority(matches);
  }

  static OS detectOS(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup) {
    return match(attributeExtractorLookup, matcherLookup, OS.values());
  }

  static Optional<Distribution> detectDistribution(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup, Iterable<? extends Distribution> distributions) {
    return find(attributeExtractorLookup, matcherLookup, distributions);
  }

  static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup, Distribution distribution) {
    return detectVersion(attributeExtractorLookup, matcherLookup, distribution.versions());
  }

  static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup, List<? extends Version> versions) {
    return find(attributeExtractorLookup, matcherLookup, versions);
  }

  static Architecture detectArchitecture(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup, List<? extends Architecture> architectures) {
    return match(attributeExtractorLookup, matcherLookup, architectures);
  }
}
