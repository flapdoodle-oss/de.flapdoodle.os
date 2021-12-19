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

import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.attributes.Attributes;
import de.flapdoodle.os.common.attributes.SystemProperty;
import de.flapdoodle.os.common.attributes.TextFile;
import de.flapdoodle.os.common.matcher.MatchPattern;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import de.flapdoodle.os.common.matcher.Matchers;
import de.flapdoodle.os.common.matcher.PatternMatcher;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PeculiarityInspectorTest {

	@Test
	void matchSinglePecularity() {
		AttributeExtractorLookup attributeExtractorLookup = AttributeExtractorLookup.with(
			TextFile.any(), attribute -> attribute.name().equals("foo") ? Optional.of("fooo") : Optional.empty());
		MatcherLookup matcherLookup = MatcherLookup.forType(MatchPattern.class, new PatternMatcher());

		DistinctPeculiarity<String> peculiarity = DistinctPeculiarity.of(Attributes.textFile("foo"), Matchers.matchPattern("^[a-z]+$"));

		boolean matches = PeculiarityInspector.matches(attributeExtractorLookup, matcherLookup, peculiarity);

		assertThat(matches).isTrue();
	}

	@Test
	void matchAnyPecularity() {
		AttributeExtractorLookup attributeExtractorLookup = AttributeExtractorLookup.with(
			TextFile.any(), attribute -> attribute.name().equals("bar") ? Optional.of("bar") : Optional.empty());
		MatcherLookup matcherLookup = MatcherLookup.forType(MatchPattern.class, new PatternMatcher());

		DistinctPeculiarity<String> foo = DistinctPeculiarity.of(Attributes.textFile("foo"), Matchers.matchPattern("^[0-9]+$"));
		DistinctPeculiarity<String> bar = DistinctPeculiarity.of(Attributes.textFile("bar"), Matchers.matchPattern("^[a-z]+$"));
		OneOf any = OneOf.of(foo, bar);

		boolean matches = PeculiarityInspector.matches(attributeExtractorLookup, matcherLookup, any);

		assertThat(matches).isTrue();
	}

	@Test
	void matchListOfPecularities() {
		AttributeExtractorLookup attributeExtractorLookup = AttributeExtractorLookup.with(
				TextFile.any(), attribute -> attribute.name().equals("foo") ? Optional.of("fooo") : Optional.empty())
			.join(AttributeExtractorLookup.with(
				SystemProperty.any(), attribute -> attribute.name().equals("os.name") ? Optional.of("Any Linux OS") : Optional.empty()))
			.join(AttributeExtractorLookup.failing());

		MatcherLookup matcherLookup = MatcherLookup.forType(MatchPattern.class, new PatternMatcher());

		DistinctPeculiarity<String> textfile = DistinctPeculiarity.of(Attributes.textFile("foo"), Matchers.matchPattern("^[a-z]+$"));
		DistinctPeculiarity<String> osName = DistinctPeculiarity.of(Attributes.systemProperty("os.name"), Matchers.matchPattern("(inux)"));

		boolean matches = PeculiarityInspector.matches(attributeExtractorLookup, matcherLookup, HasPecularities.asList(textfile, osName));

		assertThat(matches).isTrue();
	}
}
