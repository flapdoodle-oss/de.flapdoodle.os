package de.flapdoodle.os.linux;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OsReleaseFilesTest {
	@Test
	void osReleaseFileConstMustNotChange() {
		assertThat(OsReleaseFiles.RELEASE_FILE_NAME)
			.isEqualTo("/etc/os-release");
	}

	@Test
	void osReleaseFileKeysMustNotChange() {
		assertThat(OsReleaseFiles.NAME).isEqualTo("NAME");
		assertThat(OsReleaseFiles.VERSION_ID).isEqualTo("VERSION_ID");
		assertThat(OsReleaseFiles.VERSION_CODENAME).isEqualTo("VERSION_CODENAME");
	}
}