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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OsReleaseFilesTest {
	@Test
	void osReleaseFileConstMustNotChange() {
		assertThat(OsReleaseFiles.RELEASE_FILE_NAME)
			.isEqualTo("/etc/os-release");
	}

	@Test
	void osReleaseFileKeysMustNotChange() {
		assertThat(OsReleaseFiles.NAME).isEqualTo("NAME");
		assertThat(OsReleaseFiles.VERSION_ID).isEqualTo("VERSION_ID");
		assertThat(OsReleaseFiles.VERSION_CODENAME).isEqualTo("VERSION_CODENAME");
	}
}