package de.flapdoodle.os.linux;

import de.flapdoodle.os.AttributeExtractorLookups;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import de.flapdoodle.os.common.types.ImmutableOsReleaseFile;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static de.flapdoodle.os.common.PeculiarityInspector.find;
import static org.assertj.core.api.Assertions.assertThat;

class RockyVersionTest {

    @Test
    public void rockyReleaseFileNameMustNotChange() {
        assertThat(RockyVersion.RELEASE_FILE_NAME).isEqualTo("/etc/rocky-release");
    }

    @Test
    public void rockyVersionIdMustMatchVersion() {
        assertVersion("8", RockyVersion.Rocky_8);
        assertVersion("9", RockyVersion.Rocky_9);
    }

    private static void assertVersion(String versionIdContent, RockyVersion version) {
        Optional<Version> detectedRockyVersion = detectVersion(
                AttributeExtractorLookups.releaseFile(RockyVersion.RELEASE_FILE_NAME, ImmutableOsReleaseFile.builder()
                        .putAttributes(OsReleaseFiles.VERSION_ID, versionIdContent)
                        .build()), RockyVersion.values());

        Optional<Version> detectedOsReleaseVersion = detectVersion(
                AttributeExtractorLookups.releaseFile(OsReleaseFiles.RELEASE_FILE_NAME, ImmutableOsReleaseFile.builder()
                        .putAttributes(OsReleaseFiles.VERSION_ID, versionIdContent)
                        .build()), RockyVersion.values());
        assertThat(detectedRockyVersion).contains(version);
        assertThat(detectedOsReleaseVersion).contains(version);
    }

    private static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, Version... values) {
        return find(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.asList(values));
    }
}
