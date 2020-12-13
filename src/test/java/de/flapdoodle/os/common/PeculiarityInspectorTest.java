package de.flapdoodle.os.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.attributes.AttributeExtractor;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.attributes.ImmutableTextFile;
import de.flapdoodle.os.common.attributes.TextFile;
import de.flapdoodle.os.common.attributes.TextFileResolver;
import de.flapdoodle.os.common.matcher.ImmutableMatchPattern;
import de.flapdoodle.os.common.matcher.Match;
import de.flapdoodle.os.common.matcher.MatchPattern;
import de.flapdoodle.os.common.matcher.Matcher;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import de.flapdoodle.os.common.matcher.PatternMatcher;

class PeculiarityInspectorTest {

	@Test
	void playground() {
		ImmutablePeculiarity<String> peculiarity = ImmutablePeculiarity.<String>builder()
		.attribute(ImmutableTextFile.of("foo"))
		.match(ImmutableMatchPattern.of(Pattern.compile("^[a-z]+$")))
		.build();
		
		AttributeExtractorLookup attributeExtractorLookup=new AttributeExtractorLookup() {
			
			@Override
			public <T, A extends Attribute<T>> Optional<AttributeExtractor<T, A>> extractor(A attribute) {
				if (attribute instanceof TextFile) {
					return Optional.of(new AttributeExtractor<T, A>() {
						@Override
						public Optional<T> extract(A attribute) {
							return (Optional<T>) Optional.of("fooo");
						}
					});
				}
				return Optional.empty();
			}
		};
		MatcherLookup matcherLookup=new MatcherLookup() {
			
			@Override
			public <T, M extends Match<T>> Optional<Matcher<T, M>> extractor(M match) {
				if (match instanceof MatchPattern) {
					return Optional.of((Matcher<T,M>) new PatternMatcher());
				}
				return Optional.empty();
			}
		};
		
		boolean matches = PeculiarityInspector.matches(peculiarity, attributeExtractorLookup, matcherLookup);
		
		assertThat(matches).isTrue();
	}

}
