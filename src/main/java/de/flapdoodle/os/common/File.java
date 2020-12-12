package de.flapdoodle.os.common;

import java.util.regex.Pattern;

public abstract class File {

	public static FileExist exists(String fileName) {
		return ImmutableFileExist.of(fileName);
	}
	
	public static FileContentMatches contains(String fileName, Pattern regex) {
		return ImmutableFileContentMatches.of(fileName, regex);
	}
}
