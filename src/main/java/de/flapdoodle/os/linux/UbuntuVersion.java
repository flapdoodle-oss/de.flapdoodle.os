package de.flapdoodle.os.linux;

import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.matcher.Matchers;
import de.flapdoodle.os.common.types.OsReleaseFile;

import java.util.List;

public enum UbuntuVersion implements Version {
  UBUNTU_20_04(osReleaseFileVersionMatches("20.04"));

  private final List<Peculiarity<?>> peculiarities;

  UbuntuVersion(Peculiarity ... peculiarities) {
    this.peculiarities  = HasPecularities.asList(peculiarities);
  }

  @Override
  public List<Peculiarity<?>> pecularities() {
    return peculiarities;
  }

  private static Peculiarity<OsReleaseFile> osReleaseFileVersionMatches(String version) {
    return Peculiarity.of(LinuxDistribution.osReleaseFile(), Matchers.osReleaseFileEntry("VERSION_ID", ".*" + version + ".*"));
  }

}
