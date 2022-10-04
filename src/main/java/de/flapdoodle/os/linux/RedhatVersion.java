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
import de.flapdoodle.os.common.OneOf;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.types.OsReleaseFile;

import java.util.List;

import static de.flapdoodle.os.linux.OsReleaseFiles.versionMatches;

public enum RedhatVersion implements Version {
  Redhat_6(versionMatches(OsReleaseFiles.osReleaseFile(),"6")),
  Redhat_7(versionMatches(OsReleaseFiles.osReleaseFile(),"7")),
  Redhat_8(versionMatches(OsReleaseFiles.osReleaseFile(),"8")),
  Redhat_9(versionMatches(OsReleaseFiles.osReleaseFile(),"9")),
  ;

  private final List<Peculiarity> peculiarities;

  RedhatVersion(Peculiarity... peculiarities) {
    this.peculiarities  = HasPecularities.asList(peculiarities);
  }

  @Override
  public List<Peculiarity> pecularities() {
    return peculiarities;
  }
}
