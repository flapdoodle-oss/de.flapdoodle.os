package de.flapdoodle.os.linux;

import java.util.Optional;

import de.flapdoodle.os.Distribution;

public class LinuxDistributionDetector implements Distribution.Detector<LinuxDistribution> {

	@Override
	public Optional<LinuxDistribution> distribution() {
		return Optional.empty();
	}

}
