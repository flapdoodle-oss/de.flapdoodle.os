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
import de.flapdoodle.os.common.DistinctPeculiarity;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;

import java.util.List;

public enum LinuxMintVersion implements Version {
    LINUX_MINT_19_0(UbuntuVersion.Ubuntu_18_04, OsReleaseFiles.osReleaseFileVersionIs("19")),
    LINUX_MINT_19_1(UbuntuVersion.Ubuntu_18_04, OsReleaseFiles.osReleaseFileVersionIs("19.1")),
    LINUX_MINT_19_2(UbuntuVersion.Ubuntu_18_04, OsReleaseFiles.osReleaseFileVersionIs("19.2")),
    LINUX_MINT_19_3(UbuntuVersion.Ubuntu_18_04, OsReleaseFiles.osReleaseFileVersionIs("19.3")),
    LINUX_MINT_20_0(UbuntuVersion.Ubuntu_20_04, OsReleaseFiles.osReleaseFileVersionIs("20")),
    LINUX_MINT_20_1(UbuntuVersion.Ubuntu_20_04, OsReleaseFiles.osReleaseFileVersionIs("20.1")),
    LINUX_MINT_20_2(UbuntuVersion.Ubuntu_20_04, OsReleaseFiles.osReleaseFileVersionIs("20.2")),
    LINUX_MINT_20_3(UbuntuVersion.Ubuntu_20_04, OsReleaseFiles.osReleaseFileVersionIs("20.3")),
    LINUX_MINT_21_0(UbuntuVersion.Ubuntu_22_04, OsReleaseFiles.osReleaseFileVersionIs("21")),
    LINUX_MINT_21_1(UbuntuVersion.Ubuntu_22_04, OsReleaseFiles.osReleaseFileVersionIs("21.1")),
    LINUX_MINT_21_2(UbuntuVersion.Ubuntu_22_04, OsReleaseFiles.osReleaseFileVersionIs("21.2")),
    LINUX_MINT_21_3(UbuntuVersion.Ubuntu_22_04, OsReleaseFiles.osReleaseFileVersionIs("21.3")),
    LINUX_MINT_22_0(UbuntuVersion.Ubuntu_24_04, OsReleaseFiles.osReleaseFileVersionIs("22"));

    private final UbuntuVersion matchingUbuntuVersion;
    private final List<Peculiarity> peculiarities;

    LinuxMintVersion(final UbuntuVersion matchingUbuntuVersion, final DistinctPeculiarity<?>... peculiarities) {
        this.matchingUbuntuVersion = matchingUbuntuVersion;
        this.peculiarities = HasPecularities.asList(peculiarities);
    }

    public UbuntuVersion matchingUbuntuVersion() {
        return matchingUbuntuVersion;
    }

    @Override
    public List<Peculiarity> pecularities() {
        return peculiarities;
    }
}
