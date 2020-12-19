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