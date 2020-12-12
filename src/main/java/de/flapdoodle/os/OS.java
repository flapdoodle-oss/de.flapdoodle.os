package de.flapdoodle.os;

import java.util.Objects;
import java.util.Optional;

import de.flapdoodle.os.linux.*;

public enum OS {
	Linux(new LinuxDistributionDetector()),
	Windows(Distribution.Detector.noop()),
	OS_X(Distribution.Detector.noop()),
	Solaris(Distribution.Detector.noop()),
	FreeBSD(Distribution.Detector.noop());
	
	private Distribution.Detector<?> distributionDetector;

	OS(Distribution.Detector<?> detectDistribution) {
		this.distributionDetector  = Objects.requireNonNull(detectDistribution,"detectDistribution is null");
	}
	
	public Optional<? extends Distribution> distribution() {
		return distributionDetector.distribution();
	}
	
	public static OS detect() {
		String osName = System.getProperty("os.name");
		if (osName.equals("Linux"))
			return Linux;
		if (osName.startsWith("Windows", 0))
			return Windows;
		if (osName.equals("Mac OS X"))
			return OS_X;
		if (osName.contains("SunOS"))
			return Solaris;
		if (osName.equals("FreeBSD"))
			return FreeBSD;
		throw new IllegalArgumentException("Could not detect Platform: os.name=" + osName);
	}

}
