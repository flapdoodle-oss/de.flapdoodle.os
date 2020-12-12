package de.flapdoodle.os;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class OSTest {

	@Test
	void detectIt() {
		OS os = OS.detect();
		System.out.println("OS: "+os);
		Optional<? extends Distribution> distribution = os.distribution();
		System.out.println("Distribution: "+distribution);
		distribution.ifPresent(dist -> {
			
		});
	}

}
