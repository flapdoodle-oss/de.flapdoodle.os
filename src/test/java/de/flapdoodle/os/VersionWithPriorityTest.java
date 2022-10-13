package de.flapdoodle.os;

import de.flapdoodle.os.common.collections.Immutables;
import de.flapdoodle.os.linux.AmazonVersion;
import de.flapdoodle.os.linux.CentosVersion;
import de.flapdoodle.os.linux.LinuxDistribution;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VersionWithPriorityTest {

	@Test
	void sortByPriority() {
		ImmutablePlatform amazon2 = ImmutablePlatform.builder()
			.operatingSystem(OS.Linux)
			.architecture(CommonArchitecture.X86_64)
			.distribution(LinuxDistribution.Amazon)
			.version(AmazonVersion.AmazonLinux2)
			.build();

		ImmutablePlatform centos8 = ImmutablePlatform.builder()
			.operatingSystem(OS.Linux)
			.architecture(CommonArchitecture.X86_64)
			.distribution(LinuxDistribution.CentOS)
			.version(CentosVersion.CentOS_8)
			.build();

		List<ImmutablePlatform> platforms = Immutables.asList(amazon2,centos8);

		List<Platform> sorted = VersionWithPriority.sorteByPriority(platforms);

		assertThat(sorted)
			.containsExactly(centos8,amazon2);
	}
}