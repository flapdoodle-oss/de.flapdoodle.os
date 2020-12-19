package de.flapdoodle.os;

import de.flapdoodle.os.common.PeculiarityInspector;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import org.immutables.value.Value;

import java.util.Optional;

import static de.flapdoodle.os.common.PeculiarityInspector.find;
import static de.flapdoodle.os.common.PeculiarityInspector.match;

@Value.Immutable
public interface Platform {
  OS operatingSystem();

  Optional<Distribution> distribution();

  Optional<Version> version();

  static Platform detect(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup) {
    ImmutablePlatform.Builder builder = ImmutablePlatform.builder();
    OS os = detectOS(attributeExtractorLookup, matcherLookup);
    Optional<Distribution> dist = find(attributeExtractorLookup, matcherLookup, os.distributions());

    return builder.operatingSystem(os)
            .distribution(dist)
            .version(dist.flatMap(d -> find(attributeExtractorLookup,matcherLookup, d.versions())))
            .build();
  }

  static OS detectOS(AttributeExtractorLookup attributeExtractorLookup, MatcherLookup matcherLookup) {
    return match(attributeExtractorLookup, matcherLookup, OS.values());
  }
}
