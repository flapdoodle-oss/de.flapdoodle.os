package de.flapdoodle.os.linux;

import de.flapdoodle.os.Platform;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.common.attributes.AttributeExtractorLookup;
import de.flapdoodle.os.common.attributes.SystemProperty;
import de.flapdoodle.os.common.matcher.MatcherLookup;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AmazonVersionTest {

	@Test
	public void osVersionMustMatchAmazonVersion() {
		assertVersion("4.9.76-3.78.amzn1.x86_64", AmazonVersion.AmazonLinux);
		assertVersion("4.14.186-146.268.amzn2.x86_64", AmazonVersion.AmazonLinux2);
	}

	private static void assertVersion(String versionIdContent, AmazonVersion version) {
		Optional<Version> detectedVersion = detectVersion(osVersion(versionIdContent), AmazonVersion.values());
		assertThat(detectedVersion).contains(version);
	}

	private static Optional<Version> detectVersion(AttributeExtractorLookup attributeExtractorLookup, Version... values) {
		return Platform.detectVersion(attributeExtractorLookup, MatcherLookup.systemDefault(), Arrays.<Version>asList(values));
	}

	static AttributeExtractorLookup osVersion(String content) {
		return AttributeExtractorLookup.with(
				SystemProperty.any(), attribute -> attribute.name().equals("os.version") ? Optional.of(content) : Optional.empty())
			.join(AttributeExtractorLookup.failing());
	}


}