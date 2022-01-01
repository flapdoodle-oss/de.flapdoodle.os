# Organisation Flapdoodle OSS

We are now a github organisation. You are invited to participate.


# OS Detection Library

## Howto

... still in progress ...

### Maven

Stable (Maven Central Repository, Released: 28.12.2021 - wait 24hrs for [maven central](http://repo1.maven.org/maven2/de/flapdoodle/de.flapdoodle.os/maven-metadata.xml))

	<dependency>
		<groupId>de.flapdoodle</groupId>
		<artifactId>de.flapdoodle.os</artifactId>
		<version>1.1.1</version>
	</dependency>

Snapshots (Repository http://oss.sonatype.org/content/repositories/snapshots)

	<dependency>
		<groupId>de.flapdoodle</groupId>
		<artifactId>de.flapdoodle.os</artifactId>
		<version>1.1.2-SNAPSHOT</version>
	</dependency>

### Run

You can set system property `de.flapdoodle.os.explain=true` and enable logging for
package `de.flapdoodle.os.common.attributes` to get some debugging output.

