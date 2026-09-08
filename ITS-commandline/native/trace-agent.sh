#! /bin/bash
# Records the reflection, resources and proxies ITS-Tools uses, merging into config/, by running
# the flat class path under GraalVM's JVM with the native-image agent on given (model folder,
# examination) pairs:
#   GRAALVM=... [TRACE_TIMEOUT=30] ./trace-agent.sh /path/to/product INPUTS/AirplaneLD-PT-0010 LTLCardinality [more pairs...]
# The tool gets a 30 s budget, not a campaign's 1800: what is recorded is reached in the
# first seconds (parsing registers at 11 ms), and the tool overruns its budget rather than
# stopping itself, so a generous one costs minutes per pair and records nothing more.
set -e
PROD=${1:?product folder}; shift
HERE=$(cd "$(dirname "$0")" && pwd)
GRAALVM=${GRAALVM:?path to a GraalVM with native-image}
CP=""
for j in "$PROD"/plugins/*.jar ; do CP="$CP:$j" ; done
for d in "$PROD"/plugins/*/ ; do [ -f "$d/META-INF/MANIFEST.MF" ] && CP="$CP:${d%/}" ; done
for j in "$PROD"/plugins-lib/*.jar ; do [ -f "$j" ] && CP="$CP:$j" ; done
while [ $# -ge 2 ] ; do
	"$GRAALVM/bin/java" -agentlib:native-image-agent=config-merge-dir="$HERE/config" \
	  -Xss128m -Xmx16384m -Djdk.lang.Process.launchMechanism=vfork -cp "${CP#:}" \
	  fr.lip6.move.gal.itscl.application.Application -pnfolder "$1" -examination "$2" \
	  -its -ltsmin -greatspnpath "${GREATSPN:-$PROD/../greatspn/}" -order META -manyOrder -smt -timeout ${TRACE_TIMEOUT:-30}
	shift 2
done
