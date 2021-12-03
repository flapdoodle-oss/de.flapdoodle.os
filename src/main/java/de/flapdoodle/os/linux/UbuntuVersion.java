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
package de.flapdoodle.os.linux;

import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;

import java.util.List;

public enum UbuntuVersion implements Version {
  Ubuntu_18_04(OsReleaseFiles.osReleaseFileVersionMatches("18.04")),
  Ubuntu_18_10(OsReleaseFiles.osReleaseFileVersionMatches("18.10")),
  Ubuntu_19_04(OsReleaseFiles.osReleaseFileVersionMatches("19.04")),
  Ubuntu_19_10(OsReleaseFiles.osReleaseFileVersionMatches("19.10")),
  Ubuntu_20_04(OsReleaseFiles.osReleaseFileVersionMatches("20.04")),
  Ubuntu_20_10(OsReleaseFiles.osReleaseFileVersionMatches("20.10"))
  ;

  private final List<Peculiarity<?>> peculiarities;

  UbuntuVersion(Peculiarity... peculiarities) {
    this.peculiarities  = HasPecularities.asList(peculiarities);
  }

  @Override
  public List<Peculiarity<?>> pecularities() {
    return peculiarities;
  }

}
