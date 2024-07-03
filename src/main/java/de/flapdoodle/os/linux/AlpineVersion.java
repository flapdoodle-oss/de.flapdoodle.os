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

import java.util.List;

public enum AlpineVersion implements Version {
    ALPINE_3_15(OsReleaseFiles.osReleaseFileVersionMatches("3.15")),
    ALPINE_3_16(OsReleaseFiles.osReleaseFileVersionMatches("3.16")),
    ALPINE_3_17(OsReleaseFiles.osReleaseFileVersionMatches("3.17")),
    ALPINE_3_18(OsReleaseFiles.osReleaseFileVersionMatches("3.18")),
    ALPINE_3_19(OsReleaseFiles.osReleaseFileVersionMatches("3.19")),
    ALPINE_3_20(OsReleaseFiles.osReleaseFileVersionMatches("3.20")),
    ALPINE_3_21(OsReleaseFiles.osReleaseFileVersionMatches("3.21"))
    ;

    private final List<Peculiarity> peculiarities;

    AlpineVersion(final Peculiarity... peculiarities) {
        this.peculiarities = HasPecularities.asList(peculiarities);
    }

    @Override
    public List<Peculiarity> pecularities() {
        return peculiarities;
    }
}
