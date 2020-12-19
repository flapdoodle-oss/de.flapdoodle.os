package de.flapdoodle.os;

import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.matcher.MatcherLookup;

import java.util.List;
import java.util.Optional;

public interface Distribution extends HasPecularities {
  List<Version> versions();
}
