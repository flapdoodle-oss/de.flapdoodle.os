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
package de.flapdoodle.os.linux;

import de.flapdoodle.os.Architecture;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.matcher.Matchers;
import de.flapdoodle.os.common.types.OsReleaseFile;

import java.util.Arrays;
import java.util.List;

public enum UbuntuVersion implements Version {
  UBUNTU_20_04(CommonArchitecture.class, osReleaseFileVersionMatches("20.04"));

  private final List<Peculiarity<?>> peculiarities;
  private final List<Architecture> architectures;

  <T extends Enum<T> & Architecture> UbuntuVersion(Class<T> architecureClazz, Peculiarity ... peculiarities) {
    this.peculiarities  = HasPecularities.asList(peculiarities);
    this.architectures = Arrays.asList(architecureClazz.getEnumConstants());
  }

  @Override
  public List<Peculiarity<?>> pecularities() {
    return peculiarities;
  }

  @Override
  public List<Architecture> architectures() {
    return architectures;
  }

  private static Peculiarity<OsReleaseFile> osReleaseFileVersionMatches(String version) {
    return Peculiarity.of(LinuxDistribution.osReleaseFile(), Matchers.osReleaseFileEntry("VERSION_ID", ".*" + version + ".*"));
  }

}
