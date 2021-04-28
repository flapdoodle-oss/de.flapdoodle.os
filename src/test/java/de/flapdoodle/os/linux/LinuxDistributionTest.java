package de.flapdoodle.os.linux;

import de.flapdoodle.os.Architecture;
import de.flapdoodle.os.CommonArchitecture;
import de.flapdoodle.os.Distribution;
import de.flapdoodle.os.Platform;
import de.flapdoodle.os.common.attributes.AttributeExtractor;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.attributes.MappedTextFile;
import de.flapdoodle.os.common.attributes.SystemProperty;
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