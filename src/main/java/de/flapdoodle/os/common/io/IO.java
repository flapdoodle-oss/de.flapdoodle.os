package de.flapdoodle.os.common.io;

import de.flapdoodle.os.common.attributes.TextFileResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public abstract class IO {

  private static Logger logger= LoggerFactory.getLogger(IO.class);

  private IO() {

  }

  public static Optional<String> readString(Path path, Charset charset) {
    if (Files.exists(path)) {
      try {
        return Optional.of(new String(Files.readAllBytes(path), charset));
      } catch (IOException e) {
        logger.error("could not read "+path, e);
      }
    }
    return Optional.empty();
  }
}
