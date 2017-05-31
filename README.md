# xdg-basedir

This library is an implementation of the [XDG Base Directory Specification](https://specifications.freedesktop.org/basedir-spec/basedir-spec-latest.html).
It provides information about directories for specific purposes, e.g. configuration and caching, according to the
 specification.
It is intended to be used by command-line applications running on the Java Virtual Machine (JVM).

# Compatibility

The table below lists the compatibility between `xdg-basedir` versions and the versions of the specification:

| `xdg-basedir` version | XDG BDS version |
| --- | --- |
| &ge; 1.0 | 0.7 (latest) |

# Motivation

My main motivation for writing this library was trying out some of the Java 8 and IntelliJ IDEA features, as well as
 becoming familiar with the deployment process on Maven Central / Sonatype.

Although the library is very tiny and has close to no complexity (meaning you could easily implement the logic
 yourself), I wrote it in the hope that it may be useful to others. 

# Notes

This library is versioned using Semantic Versioning as defined in the
 [Semantic Versioning Specification](http://semver.org/), version 2.0.0.

# License

This library is licensed under the [MIT License](https://mit-license.org/).
