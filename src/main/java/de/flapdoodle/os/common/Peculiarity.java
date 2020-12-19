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
package de.flapdoodle.os.common;

import org.immutables.value.Value.Immutable;

import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.matcher.Match;

@Immutable
public interface Peculiarity<T> {
	Attribute<T> attribute();
	Match<T> match();

	static <T> Peculiarity<T> of(Attribute<T> attribute, Match<T> match) {
		return Peculiarity.<T>builder()
						.attribute(attribute)
						.match(match)
						.build();
	}

	static <T> ImmutablePeculiarity.Builder<T> builder() {
		return ImmutablePeculiarity.builder();
	}
}
