/**
 * Copyright (C) 2020
 *   Michael Mosmann <michael@mosmann.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.os;

import de.flapdoodle.os.Architecture;
import de.flapdoodle.os.BitSize;
import de.flapdoodle.os.CPUType;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.attributes.Attributes;
import de.flapdoodle.os.common.matcher.Matchers;

import java.util.List;

public enum CommonArchitecture implements Architecture {
  X86_64(CPUType.X86, BitSize.B64, osArchMatches("^(x8664|amd64|ia32e|em64t|x64)$")),
  X86_32(CPUType.X86, BitSize.B32, osArchMatches("^(x8632|x86|i[3-6]86|ia32|x32)$")),
  ARM_64(CPUType.ARM, BitSize.B64, osArchMatches("^aarch64$")),
  ARM_32(CPUType.ARM, BitSize.B32, osArchMatches("^(arm|arm32)$"))
  ;

  private final CPUType cpuType;
  private final BitSize bitSize;
  private final List<Peculiarity<?>> peculiarities;

  CommonArchitecture(CPUType cpuType, BitSize bitSize, Peculiarity<?>... peculiarities) {
    this.cpuType = cpuType;
    this.bitSize = bitSize;
    this.peculiarities = HasPecularities.asList(peculiarities);
  }

  @Override
  public CPUType cpuType() {
    return cpuType;
  }

  @Override
  public BitSize bitSize() {
    return bitSize;
  }

  @Override
  public List<Peculiarity<?>> pecularities() {
    return peculiarities;
  }

  private static Peculiarity<String> osArchMatches(String name) {
    return Peculiarity.of(osArchProperty(), Matchers.matchPattern(name));
  }

  static Attribute<String> osArchProperty() {
    return Attributes.systemProperty("os.arch");
  }

//  String osArch = System.getProperty("os.arch");
//		if (osArch.equals("i686_64") || osArch.equals("x86_64") || osArch.equals("amd64") || osArch.equals("ppc64le") || osArch.equals("aarch64"))
//  bitSize = BitSize.B64;

  /*
   private static String normalizeArch(String value) {
        value = normalize(value);
        if (value.matches("^(x8664|amd64|ia32e|em64t|x64)$")) {
            return "x86_64";
        }
        if (value.matches("^(x8632|x86|i[3-6]86|ia32|x32)$")) {
            return "x86_32";
        }
        if (value.matches("^(ia64w?|itanium64)$")) {
            return "itanium_64";
        }
        if ("ia64n".equals(value)) {
            return "itanium_32";
        }
        if (value.matches("^(sparc|sparc32)$")) {
            return "sparc_32";
        }
        if (value.matches("^(sparcv9|sparc64)$")) {
            return "sparc_64";
        }
        if (value.matches("^(arm|arm32)$")) {
            return "arm_32";
        }
        if ("aarch64".equals(value)) {
            return "aarch_64";
        }
        if (value.matches("^(mips|mips32)$")) {
            return "mips_32";
        }
        if (value.matches("^(mipsel|mips32el)$")) {
            return "mipsel_32";
        }
        if ("mips64".equals(value)) {
            return "mips_64";
        }
        if ("mips64el".equals(value)) {
            return "mipsel_64";
        }
        if (value.matches("^(ppc|ppc32)$")) {
            return "ppc_32";
        }
        if (value.matches("^(ppcle|ppc32le)$")) {
            return "ppcle_32";
        }
        if ("ppc64".equals(value)) {
            return "ppc_64";
        }
        if ("ppc64le".equals(value)) {
            return "ppcle_64";
        }
        if ("s390".equals(value)) {
            return "s390_32";
        }
        if ("s390x".equals(value)) {
            return "s390_64";
        }

        return UNKNOWN;
    }
   */
}
