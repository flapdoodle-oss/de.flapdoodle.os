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
package de.flapdoodle.os.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Optional;

import de.flapdoodle.os.common.attributes.*;
import de.flapdoodle.os.common.matcher.*;
import org.junit.jupiter.api.Test;

class PeculiarityInspectorTest {

	@Test
	void matchSinglePecularity() {
		AttributeExtractorLookup attributeExtractorLookup = AttributeExtractorLookup.forType(TextFile.class, attribute -> Optional.of("fooo"));
		MatcherLookup matcherLookup=MatcherLookup.forType(MatchPattern.class, new PatternMatcher());

		Peculiarity<String> peculiarity = Peculiarity.of(Attributes.textFile("foo"), Matchers.matchPattern("^[a-z]+$"));

		boolean matches = PeculiarityInspector.matches(attributeExtractorLookup, matcherLookup, peculiarity);
		
		assertThat(matches).isTrue();
	}

	@Test
	void matchListOfPecularities() {
		AttributeExtractorLookup attributeExtractorLookup = AttributeExtractorLookup.forType(TextFile.class, attribute -> Optional.of("fooo"))
						.join(AttributeExtractorLookup.forType(SystemProperty.class, attribute -> Optional.of("Any Linux OS")))
						.join(AttributeExtractorLookup.failing());
		
		MatcherLookup matcherLookup=MatcherLookup.forType(MatchPattern.class, new PatternMatcher());

		Peculiarity<String> textfile = Peculiarity.of(Attributes.textFile("foo"), Matchers.matchPattern("^[a-z]+$"));
		Peculiarity<String> osName = Peculiarity.of(Attributes.systemProperty("os.name"), Matchers.matchPattern("(inux)"));

		boolean matches = PeculiarityInspector.matches(attributeExtractorLookup, matcherLookup, Arrays.asList(textfile, osName));

		assertThat(matches).isTrue();
	}
}
