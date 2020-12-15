package de.flapdoodle.os.common;

import java.util.Optional;

import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.attributes.AttributeExtractor;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.matcher.Match;
import de.flapdoodle.os.common.matcher.Matcher;
import de.flapdoodle.os.common.matcher.MatcherLookup;

public abstract class PeculiarityInspector {

	public static boolean matches(
					AttributeExtractorLookup attributeExtractorLookup,
																MatcherLookup matcherLookup,
					Iterable<? extends Peculiarity<?>> peculiarities
	) {
		for (Peculiarity<?> it :peculiarities) {
			if (!matches(attributeExtractorLookup,matcherLookup,it)) {
				return false;
			}
		}
		return true;
	}

	public static <T> boolean matches(
					AttributeExtractorLookup attributeExtractorLookup,
					MatcherLookup matcherLookup,
					Peculiarity<T> peculiarity
	) {
		
		Attribute<T> attribute = peculiarity.attribute();
		Optional<AttributeExtractor<T, Attribute<T>>> extractor = attributeExtractorLookup.extractor(attribute);
		
		Optional<T> value = extractor.flatMap(it -> it.extract(attribute));
		
		Match<T> match = peculiarity.match();
		Optional<Matcher<T, Match<T>>> matcher = matcherLookup.matcher(match);
			
		return matcher
				.map(m -> m.match(value, match))
				.orElse(false);
	}
}
