package de.flapdoodle.os.common;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.attributes.AttributeExtractor;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.matcher.Match;
import de.flapdoodle.os.common.matcher.Matcher;
import de.flapdoodle.os.common.matcher.MatcherLookup;

// TODO cache extracted attributes
public abstract class PeculiarityInspector {

	public static <T extends HasPecularities> T match(
					AttributeExtractorLookup attributeExtractorLookup,
					MatcherLookup matcherLookup,
					T ... items
	) {
		return match(attributeExtractorLookup, matcherLookup, Arrays.asList(items));
	}

	public static <T extends HasPecularities> T match(
					AttributeExtractorLookup attributeExtractorLookup,
					MatcherLookup matcherLookup,
					Iterable<T> items
	) {
		List<T> matching = matching(attributeExtractorLookup, matcherLookup, items);
		if (matching.isEmpty()) {
			throw new IllegalArgumentException("no match out of "+ items);
		}
		if (matching.size()>1) {
			throw new IllegalArgumentException("more than one match: "+ matching);
		}
		return matching.get(0);
	}

	public static <T extends HasPecularities> List<T> matching(
					AttributeExtractorLookup attributeExtractorLookup,
					MatcherLookup matcherLookup,
					T ... items
	) {
		return matching(attributeExtractorLookup, matcherLookup, Arrays.asList(items));
	}

	public static <T extends HasPecularities> List<T> matching(
					AttributeExtractorLookup attributeExtractorLookup,
					MatcherLookup matcherLookup,
					Iterable<T> items
	) {
		return StreamSupport.stream(items.spliterator(), false)
						.filter(it -> matches(attributeExtractorLookup, matcherLookup, it.pecularities()))
						.collect(Collectors.toList());
	}


	// TODO cache extracted attributes
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

	// TODO cache extracted attributes
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
