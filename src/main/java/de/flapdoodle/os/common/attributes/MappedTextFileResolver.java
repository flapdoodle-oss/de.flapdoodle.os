package de.flapdoodle.os.common.attributes;

import de.flapdoodle.os.common.io.IO;

import java.nio.file.Paths;
import java.util.Optional;

public class MappedTextFileResolver<T> implements AttributeExtractor<T, MappedTextFile<T>> {

  @Override
  public Optional<T> extract(MappedTextFile<T> attribute) {
    return IO.readString(Paths.get(attribute.name()), attribute.charset())
            .map(attribute.converter());
  }
}
