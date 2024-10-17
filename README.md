# Organisation Flapdoodle OSS

We are now a github organisation. You are invited to participate.


# OS Detection Library

## Howto

... still in progress ...

### Maven

Stable (Maven Central Repository, Released: 07.10.2024 - wait 24hrs for [maven central](http://repo1.maven.org/maven2/de/flapdoodle/de.flapdoodle.os/maven-metadata.xml))

	<dependency>
		<groupId>de.flapdoodle</groupId>
		<artifactId>de.flapdoodle.os</artifactId>
		<version>1.8.0</version>
	</dependency>

### Run

Detect system platform with this call:
                
```java
Platform result = Platform.detect(CommonOS.list());
```

and inspect the result which may give 'Linux' for os, 'X86' for cpu type,
'B64' for bit size, 'Ubuntu' for 
distribution and 'Ubuntu_22_04' for version.

You can set system property `de.flapdoodle.os.explain=true` and enable logging for
package `de.flapdoodle.os.common.attributes` to get some debugging output.

You can override platform detection with system property `de.flapdoodle.os.override=<platform>` where
`platform` contains the os, architecture, distribution and version (optional).
Sample value for macOs on x86 with 64bit: `OS_X|X86_64`, Centos7 on 32bit x86: `Linux|X86_32|CentOS|CentOS_7`.
You should **NOT** use quotes, so `de.flapdoodle.os.override=OS_X|X86__64`.

### Changelog

#### Unreleased

- ubuntu up to 25.10

#### 1.8.0

- manjaro linux support

#### 1.7.3

- kde neon 6.x support

#### 1.7.2

- linux mint 22.x support
- check for amazon version priority

#### 1.7.0

- amazon detection fix

#### 1.6.0

- alpine linux detection

#### 1.5.6

- rocky linux support

#### 1.5.5

- ubuntu 24.04

#### 1.5.4

- alma linux support

#### 1.5.3

- plugin updates

#### 1.5.2

- fedora 39, 40, 41

#### 1.5.1

- debian 12, 13

#### 1.5.0

- pop os support, enum ordering

#### 1.4.4

- linux mint 21.2 and fedora 38

#### 1.4.1

- better amazon2 detection

#### 1.4.0

- amazon 2023 support

#### 1.3.4

- ubuntu 23.x support

#### 1.3.0

- api extracted to de.flapdoodle.os-api

#### 1.2.7

- mint 21.1

#### 1.2.6

- detect debian 12

#### Version 1.2.4

- detect oracle9, centos9

#### Version 1.2.3

- detect ubuntu 22.10, bugfix 

#### Version 1.2.1

- api change

#### Version 1.2.0

- return best version if more than one match

#### Version 1.1.12

- detect linux mint 21

#### Version 1.1.10

- detect ubuntu 16.04/16.10

#### Version 1.1.9

- override with system property

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

