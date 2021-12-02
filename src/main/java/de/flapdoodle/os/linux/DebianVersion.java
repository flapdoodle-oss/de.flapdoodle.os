package de.flapdoodle.os.linux;

import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.matcher.Matchers;
import de.flapdoodle.os.common.types.OsReleaseFile;

import java.util.List;

public enum DebianVersion implements Version {
    DEBIAN_10(osReleaseFileVersionMatches("10")),
    DEBIAN_9(osReleaseFileVersionMatches("9"));

    private final List<Peculiarity<?>> peculiarities;

    DebianVersion(final Peculiarity<?>... peculiarities) {
        this.peculiarities = HasPecularities.asList(peculiarities);
    }

    @Override
    public List<Peculiarity<?>> pecularities() {
        return peculiarities;
    }

    private static Peculiarity<OsReleaseFile> osReleaseFileVersionMatches(final String version) {
        return Peculiarity.of(Attributes.osReleaseFile(), Matchers.osReleaseFileEntry("VERSION_ID", version));
    }

}
