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

public enum ManjaroVersion implements Version {
	MANJARO_24_0(LsbReleaseFiles.lsbReleaseFileVersionMatches("24.0")),
	MANJARO_24_1(LsbReleaseFiles.lsbReleaseFileVersionMatches("24.1")),
	MANJARO_24_2(LsbReleaseFiles.lsbReleaseFileVersionMatches("24.2")),
	MANJARO_24_3(LsbReleaseFiles.lsbReleaseFileVersionMatches("24.3")),
	;

	private final List<Peculiarity> peculiarities;

	ManjaroVersion(final Peculiarity... peculiarities) {
		this.peculiarities = HasPecularities.asList(peculiarities);
	}

	@Override
	public List<Peculiarity> pecularities() {
		return peculiarities;
	}

}
