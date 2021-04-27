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
