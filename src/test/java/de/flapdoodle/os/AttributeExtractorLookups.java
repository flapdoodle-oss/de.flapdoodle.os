package de.flapdoodle.os;

import de.flapdoodle.os.common.attributes.AttributeExtractor;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.attributes.MappedTextFile;
import de.flapdoodle.os.common.attributes.SystemProperty;
import de.flapdoodle.os.common.types.ImmutableOsReleaseFile;
import de.flapdoodle.os.common.types.OsReleaseFile;
import de.flapdoodle.os.linux.OsReleaseFiles;

import java.util.Optional;

public class AttributeExtractorLookups {

	public static AttributeExtractorLookup osReleaseFileVersionIdIs(String content) {
		return osReleaseFile(ImmutableOsReleaseFile.builder()
			.putAttributes(OsReleaseFiles.VERSION_ID,content)
			.build());
	}

	public static AttributeExtractorLookup osReleaseFile(OsReleaseFile osReleaseFile) {
		return releaseFile(OsReleaseFiles.RELEASE_FILE_NAME, osReleaseFile);
	}

	public static AttributeExtractorLookup releaseFile(String releaseFileName, OsReleaseFile osReleaseFile) {
		return AttributeExtractorLookup.with(MappedTextFile.any(), (AttributeExtractor<OsReleaseFile, MappedTextFile<OsReleaseFile>>) attribute ->
				attribute.name().equals(releaseFileName)
					? Optional.of(osReleaseFile)
					: Optional.empty())
			.join(AttributeExtractorLookup.with(SystemProperty.any(), it -> Optional.empty()))
			.join(AttributeExtractorLookup.failing());

	}
}
