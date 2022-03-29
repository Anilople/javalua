package com.github.anilople.javalua.chunk;

/**
 * @author wxq
 */
class Version {

  static final Version INSTANCE = new Version(5, 3, 4);
  final int majorVersion;
  final int minorVersion;
  final int releaseVersion;

  Version(int majorVersion, int minorVersion, int releaseVersion) {
    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
    this.releaseVersion = releaseVersion;
  }

  /**
   * Encode major-minor version in one byte, one nibble for each
   *
   * @see <a href="https://github.com/lua/lua/blob/e354c6355e7f48e087678ec49e340ca0696725b1/lundump.h">lundump.h LUAC_VERSION</a>
   */
  byte encode() {
    return (byte) (majorVersion * 16 + minorVersion);
  }
}
