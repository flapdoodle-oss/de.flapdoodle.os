package de.flapdoodle.os.linux;

import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.matcher.Matchers;
import de.flapdoodle.os.common.types.OsReleaseFile;

import java.util.List;

public enum CentosVersion implements Version {
  ;

  private final List<Peculiarity<?>> peculiarities;

  CentosVersion(Peculiarity ... peculiarities) {
    this.peculiarities  = HasPecularities.asList(peculiarities);
  }

  @Override
  public List<Peculiarity<?>> pecularities() {
    return peculiarities;
  }
}
