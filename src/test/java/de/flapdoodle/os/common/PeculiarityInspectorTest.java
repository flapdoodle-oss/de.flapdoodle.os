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
