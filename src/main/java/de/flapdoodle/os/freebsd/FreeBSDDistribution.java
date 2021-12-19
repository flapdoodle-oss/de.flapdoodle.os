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
package de.flapdoodle.os.freebsd;

import de.flapdoodle.os.Distribution;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.Peculiarity;

import java.util.Collections;
import java.util.List;

public enum FreeBSDDistribution implements Distribution {
	;

	@Override
	public List<Peculiarity> pecularities() {
		return Collections.emptyList();
	}

	@Override
	public List<Version> versions() {
		return Collections.emptyList();
	}
}
