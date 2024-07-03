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

import de.flapdoodle.os.Distribution;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.OneOf;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.collections.Enums;

import java.util.List;

public enum LinuxDistribution implements Distribution {
  Ubuntu(UbuntuVersion.class, OsReleaseFiles.osReleaseFileNameMatches("Ubuntu")),
  CentOS(CentosVersion.class, CentosVersion.centosReleaseFileNameMatches("CentOS")),
  Redhat(RedhatVersion.class, OsReleaseFiles.osReleaseFileNameMatches("Red Hat")),
  Fedora(FedoraVersion.class, OsReleaseFiles.osReleaseFileNameMatches("Fedora")),
  Oracle(OracleVersion.class, OsReleaseFiles.osReleaseFileNameMatches("Oracle")),
  OpenSUSE(OpenSUSEVersion.class, OsReleaseFiles.osReleaseFileNameMatches("openSUSE")),
  LinuxMint(LinuxMintVersion.class, OsReleaseFiles.osReleaseFileNameMatches("Linux Mint")),
  PopOS(PopOSVersion.class, OsReleaseFiles.osReleaseFileNameMatches("Pop!_OS")),
  Debian(DebianVersion.class, OsReleaseFiles.osReleaseFileNameMatches("Debian")),
  Alpine(AlpineVersion.class, OsReleaseFiles.osReleaseFileNameMatches("Alpine")),
  Amazon(AmazonVersion.class, OneOf.of(
    OsReleaseFiles.osReleaseFileNameMatches("Amazon Linux"),
    AmazonVersion.osVersionMatches(".*amzn.*"))
  ),
  Alma(AlmaVersion.class, AlmaVersion.almaReleaseFileNameMatches("AlmaLinux")),
  Rocky(RockyVersion.class, RockyVersion.rockyReleaseFileNameMatches("Rocky Linux"))
  ;

  private final List<Peculiarity> peculiarities;
  private final List<? extends Version> versions;

  <T extends Enum<T> & Version> LinuxDistribution(Class<T> versionClazz, Peculiarity... peculiarities) {
    this.peculiarities = HasPecularities.asList(peculiarities);
    this.versions = Enums.valuesAsList(versionClazz);
  }
  @Override
  public List<Peculiarity> pecularities() {
    return peculiarities;
  }

  @Override
  public List<? extends Version> versions() {
    return this.versions;
  }

}
