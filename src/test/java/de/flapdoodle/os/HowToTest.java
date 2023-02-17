package de.flapdoodle.os;

import de.flapdoodle.testdoc.Recorder;
import de.flapdoodle.testdoc.Recording;
import de.flapdoodle.testdoc.TabSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.assertj.core.api.Assertions.assertThat;

public class HowToTest {

	@RegisterExtension
	public static Recording recording= Recorder.with("HowTo.md", TabSize.spaces(2));
	
	@Test
	public void detectPlatform() {
		recording.begin();
		Platform result = Platform.detect(CommonOS.list());
		recording.end();
		assertThat(result).isNotNull();
		recording.output("result.os", result.operatingSystem().name());
		recording.output("result.architecture.cpuType", result.architecture().cpuType().name());
		recording.output("result.architecture.bitSize", result.architecture().bitSize().name());
		recording.output("result.distribution", result.distribution().map(Distribution::name).orElse("---"));
		recording.output("result.version", result.version().map(Version::name).orElse("---"));
	}
}
