# Organisation Flapdoodle OSS

We are now a github organisation. You are invited to participate.


# OS Detection Library

## Howto

... still in progress ...

### Maven

Stable (Maven Central Repository, Released: 31.03.2022 - wait 24hrs for [maven central](http://repo1.maven.org/maven2/de/flapdoodle/de.flapdoodle.os/maven-metadata.xml))

	<dependency>
		<groupId>de.flapdoodle</groupId>
		<artifactId>de.flapdoodle.os</artifactId>
		<version>1.1.8</version>
	</dependency>

Snapshots (Repository http://oss.sonatype.org/content/repositories/snapshots)

	<dependency>
		<groupId>de.flapdoodle</groupId>
		<artifactId>de.flapdoodle.os</artifactId>
		<version>1.1.9-SNAPSHOT</version>
	</dependency>

### Run

You can set system property `de.flapdoodle.os.explain=true` and enable logging for
package `de.flapdoodle.os.common.attributes` to get some debugging output.

You can override platform detection with system property `de.flapdoodle.os.override=<platform>` where
`platform` contains the os, architecture, distribution and version (optional).
Sample value for macOs on x86 with 64bit: `OS_X|X86_64`, Centos7 on 32bit x86: `Linux|X86_32|CentOS|CentOS_7`.

### Changelog

#### Version 1.1.8

- print detection result on explain

#### Version 1.1.6

- detect ubuntu 22.04

#### Version 1.1.5

- detect debian 11

#### Version 1.1.4

- detect ubuntu 21.10

#### Version 1.1.3

- detect redhat 6,7,8
- detect oracle 6,7,8

