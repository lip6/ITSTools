#! /bin/bash
# ITS-Tools on a flat classpath: the plugins/ folder of the product as built by
# Tycho, no Equinox, no launcher, no configuration area, no extension registry.
# Same arguments as the its-tools launcher. ITSTOOLS names the product folder
# (default: the folder of this script).
set -e
ITSTOOLS=${ITSTOOLS:-$(cd "$(dirname "$0")" && pwd)}
P=$ITSTOOLS/plugins
CP=""
for j in "$P"/*.jar ; do CP="$CP:$j" ; done
for d in "$P"/*/ ; do [ -f "$d/META-INF/MANIFEST.MF" ] && CP="$CP:${d%/}" ; done
# The jars a bundle nests (Bundle-ClassPath lib/*.jar) are unpacked once into
# plugins-lib/ beside plugins/: built under a temporary name and renamed, so
# concurrent first runs cannot see a half-filled folder (the loser's copy is
# dropped). Remove the folder after a product update.
LIB=$ITSTOOLS/plugins-lib
if [ ! -d "$LIB" ] ; then
	T=$(mktemp -d "$ITSTOOLS/plugins-lib.XXXXXX")
	for j in "$P"/*.jar ; do
		if unzip -l "$j" 'lib/*.jar' > /dev/null 2>&1 ; then
			unzip -o -q -j "$j" 'lib/*.jar' -d "$T"
		fi
	done
	mv -T "$T" "$LIB" 2> /dev/null || rm -rf "$T"
fi
for j in "$LIB"/*.jar ; do [ -f "$j" ] && CP="$CP:$j" ; done
# ITS_JAVA_OPTS: extra JVM flags (a class-loading trace, a CDS archive, ...)
java -Xss128m -Xms40m -Xmx16384m -Djdk.lang.Process.launchMechanism=vfork $ITS_JAVA_OPTS \
	-cp "${CP#:}" fr.lip6.move.gal.itscl.application.Application "$@"
