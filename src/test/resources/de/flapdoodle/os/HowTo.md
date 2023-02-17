### Run

Detect system platform with this call:
                
```java
${detectPlatform}
```

and inspect the result which may give '${detectPlatform.result.os}' for os, '${detectPlatform.result.architecture.cpuType}' for cpu type,
'${detectPlatform.result.architecture.bitSize}' for bit size, '${detectPlatform.result.distribution}' for 
distribution and '${detectPlatform.result.version}' for version.

You can set system property `de.flapdoodle.os.explain=true` and enable logging for
package `de.flapdoodle.os.common.attributes` to get some debugging output.

You can override platform detection with system property `de.flapdoodle.os.override=<platform>` where
`platform` contains the os, architecture, distribution and version (optional).
Sample value for macOs on x86 with 64bit: `OS_X|X86_64`, Centos7 on 32bit x86: `Linux|X86_32|CentOS|CentOS_7`.

