package de.flapdoodle.os.linux;

import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.OneOf;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.types.OsReleaseFile;

import java.util.List;

import static de.flapdoodle.os.linux.OsReleaseFiles.versionMatches;

public enum RockyVersion implements Version {
    Rocky_8(OneOf.of(versionMatches(rockyReleaseFile(),"8"), versionMatches(OsReleaseFiles.osReleaseFile(),"8"))),
    Rocky_9(OneOf.of(versionMatches(rockyReleaseFile(),"9"), versionMatches(OsReleaseFiles.osReleaseFile(),"9")))
    ;

    public static final String RELEASE_FILE_NAME="/etc/rocky-release";

    private final List<Peculiarity> peculiarities;

    RockyVersion(Peculiarity... peculiarities) { this.peculiarities = HasPecularities.asList(peculiarities); }

    @Override
    public List<Peculiarity> pecularities() { return peculiarities; }

    static Peculiarity rockyReleaseFileNameMatches(String name) {
        return OneOf.of(OsReleaseFiles.nameMatches(rockyReleaseFile(), name), OsReleaseFiles.nameMatches(OsReleaseFiles.osReleaseFile(), name));
    }

    private static Attribute<OsReleaseFile> rockyReleaseFile() {
        return OsReleaseFiles.releaseFile(RELEASE_FILE_NAME);
    }
}
