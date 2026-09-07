#! /bin/bash
# ITS-Tools on a flat classpath: the plugins/ folder of the product as built by
# Tycho, no Equinox, no launcher, no configuration area, no extension registry.
# Same arguments as the its-tools launcher. ITSTOOLS names the product folder
# (default: the folder of this script). The jars a bundle nests (Bundle-ClassPath
# lib/*.jar) are unpacked into a private temporary folder for the run.
set -e
ITSTOOLS=${ITSTOOLS:-$(cd "$(dirname "$0")" && pwd)}
P=$ITSTOOLS/plugins
CP=""
for j in "$P"/*.jar ; do CP="$CP:$j" ; done
for d in "$P"/*/ ; do [ -f "$d/META-INF/MANIFEST.MF" ] && CP="$CP:${d%/}" ; done
LIB=$(mktemp -d "${TMPDIR:-/tmp}/its-tools-lib.XXXXXX")
trap 'rm -rf "$LIB"' EXIT
for j in "$P"/*.jar ; do
	if unzip -l "$j" 'lib/*.jar' > /dev/null 2>&1 ; then
		unzip -o -q -j "$j" 'lib/*.jar' -d "$LIB"
	fi
done
for j in "$LIB"/*.jar ; do [ -f "$j" ] && CP="$CP:$j" ; done
java -Xss128m -Xms40m -Xmx16384m -Djdk.lang.Process.launchMechanism=vfork \
	-cp "${CP#:}" fr.lip6.move.gal.itscl.application.Application "$@"
