package de.flapdoodle.os;

import de.flapdoodle.os.common.HasPecularities;

import java.util.Optional;

public interface Distribution extends HasPecularities {
  Optional<Version> version();
}
