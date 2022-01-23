package com.github.anilople.javalua.chunk;

/**
 * @author wxq
 */
class Version {
  static final Version INSTANCE = new Version(5, 4, 0);
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
   * @see <a href="https://github.com/lua/lua/blob/5d708c3f9cae12820e415d4f89c9eacbe2ab964b/lundump.h#L25">LUAC_VERSION</a>
   */
  byte encode() {
    return (byte) (majorVersion * 16 + minorVersion);
  }
}
