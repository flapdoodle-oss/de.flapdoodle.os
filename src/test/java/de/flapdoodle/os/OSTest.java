package de.flapdoodle.os;

import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.attributes.SystemProperty;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static de.flapdoodle.os.Platform.detectOS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OSTest {

  @Test
  void detectLinuxIfOsNameIsLinux() {
    OS os = detectOS(osNameIs("Linux"), MatcherLookup.systemDefault());
    assertThat(os).isEqualTo(OS.Linux);
  }

  @Test
  void detectWindowsIfOsNameStartsWithWindows() {
    OS os = detectOS(osNameIs("WindowsFooBarBlub"), MatcherLookup.systemDefault());
    assertThat(os).isEqualTo(OS.Windows);
  }

  @Test
  void detectOSXIfOsNameIsMacOSX() {
    OS os = detectOS(osNameIs("Mac OS X"), MatcherLookup.systemDefault());
    assertThat(os).isEqualTo(OS.OS_X);
  }

  @Test
  void detectFreeBSDIfOsNameIsFreeBSD() {
    OS os = detectOS(osNameIs("FreeBSD"), MatcherLookup.systemDefault());
    assertThat(os).isEqualTo(OS.FreeBSD);
  }

  @Test
  void detectSolarisIfOsNameContainsSunOS() {
    OS os = detectOS(osNameIs("doesNotMatterSunOSIgnoreThisTooo"), MatcherLookup.systemDefault());
    assertThat(os).isEqualTo(OS.Solaris);
  }

  @Test
  void detectionMustFailIfNothingMatches() {
    assertThatThrownBy(() -> detectOS(osNameIs("what"), MatcherLookup.systemDefault()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("no match out of [Linux, Windows, OS_X, Solaris, FreeBSD]");
  }

  @Test
  void detectionMustFailIfMoreThanOneMatch() {
    assertThatThrownBy(() -> detectOS(osNameIs("WindowsSunOS"), MatcherLookup.systemDefault()))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("more than one match: [Windows, Solaris]");
  }

  private static AttributeExtractorLookup osNameIs(String osName) {
    return AttributeExtractorLookup.forType(SystemProperty.class, attribute -> {
      if (attribute.name().equals("os.name")) {
        return Optional.of(osName);
      }
      return Optional.empty();
    }).join(AttributeExtractorLookup.failing());
  }
}
