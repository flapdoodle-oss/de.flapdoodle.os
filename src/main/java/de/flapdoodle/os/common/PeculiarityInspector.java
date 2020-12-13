package de.flapdoodle.os.common;

import java.util.Optional;

import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.attributes.AttributeExtractor;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.matcher.Match;
import de.flapdoodle.os.common.matcher.Matcher;
import de.flapdoodle.os.common.matcher.MatcherLookup;

public abstract class PeculiarityInspector {
	
	public static <T> boolean matches(
			Peculiarity<T> peculiarity, 
			AttributeExtractorLookup attributeExtractorLookup,
			MatcherLookup matcherLookup) {
		
		Attribute<T> attribute = peculiarity.attribute();
		Optional<AttributeExtractor<T, Attribute<T>>> extractor = attributeExtractorLookup.extractor(attribute);
		
		Optional<T> value = extractor.flatMap(it -> it.extract(attribute));
		
		Match<T> match = peculiarity.match();
		Optional<Matcher<T, Match<T>>> matcher = matcherLookup.extractor(match);
			
		return matcher
				.map(m -> m.match(value, match))
				.orElse(false);
	}
}
