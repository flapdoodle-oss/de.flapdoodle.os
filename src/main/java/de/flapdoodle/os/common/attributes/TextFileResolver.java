package de.flapdoodle.os.common.attributes;

import de.flapdoodle.os.common.io.IO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class TextFileResolver implements AttributeExtractor<String, TextFile> {

  @Override
  public Optional<String> extract(TextFile attribute) {
    return IO.readString(Paths.get(attribute.name()), attribute.charset());
  }
}
