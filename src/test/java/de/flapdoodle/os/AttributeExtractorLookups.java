/*
 * Copyright (C) 2020
 *   Michael Mosmann <michael@mosmann.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
