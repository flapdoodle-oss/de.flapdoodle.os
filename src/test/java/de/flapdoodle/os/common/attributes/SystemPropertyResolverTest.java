package de.flapdoodle.os.common.attributes;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SystemPropertyResolverTest {
  private SystemPropertyResolver testee = new SystemPropertyResolver();

  @Test
  public void findPropertyByName() {
    String key = UUID.randomUUID().toString();
    String value = UUID.randomUUID().toString();

    assertThat(testee.extract(Attributes.systemProperty(key))).isNotPresent();

    System.setProperty(key, value);

    assertThat(testee.extract(Attributes.systemProperty(key)))
            .isPresent()
            .contains(value);
  }
}