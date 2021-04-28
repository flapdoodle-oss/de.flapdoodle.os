package de.flapdoodle.os.linux;

import de.flapdoodle.os.Platform;
import de.flapdoodle.os.Version;
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

class UbuntuVersionTest {

  @Test
  public void ubuntuVersionIdMustMatchUbuntuVersion() {
    assertVersion("18.04", UbuntuVersion.UBUNTU_18_04);
    assertVersion("18.10", UbuntuVersion.UBUNTU_18_10);
    assertVersion("19.04", UbuntuVersion.UBUNTU_19_04);
    assertVersion("19.10", UbuntuVersion.UBUNTU_19_10);
    assertVersion("20.04", UbuntuVersion.UBUNTU_20_04);
    assertVersion("20.10", UbuntuVersion.UBUNTU_20_10);
  }

  private static void assertVersion(String versionIdContent, UbuntuVersion version) {
    Optional<Version> detectedVersion = detectVersion(osReleaseFileVersionIdIs(versionIdContent), UbuntuVersion.values());
    assertThat(detectedVersion).contains(version);

  }

  private static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, Version... values) {
    return Platform.detectVersion(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.<Version>asList(values));
  }

  private static AttributeExtractorLookup osReleaseFileVersionIdIs(String content) {

    return AttributeExtractorLookup.with((Predicate<? super MappedTextFile<?>>) attr -> true, (AttributeExtractor<OsReleaseFile, MappedTextFile<OsReleaseFile>>) attribute -> {
      if (attribute.name().equals("/etc/os-release")) {
        return Optional.of(ImmutableOsReleaseFile.builder()
                .putAttributes("VERSION_ID",content)
                .build());
      }
      return Optional.empty();
    }).join(AttributeExtractorLookup.failing());
  }

}