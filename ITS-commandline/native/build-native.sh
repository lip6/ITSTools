#! /bin/bash
# ITS-Tools as a native executable (GraalVM native-image) from a Linux product folder.
#   GRAALVM=/path/to/graalvm-jdk-25 ./build-native.sh /path/to/product [its-tools-native]
# The product is the Tycho output (its-tools, its-tools-flat.sh, plugins/); the class path is
# the one its-tools-flat.sh builds. config/ holds the reachability metadata the tracing agent
# recorded (trace-agent.sh); a run that hits an unregistered class or resource is traced and
# added there, then the image is rebuilt (about 40 s on 16 cores).
# The ini's settings are the image's run-time defaults: 16 GB heap, 40 MB initial, 128 MB thread
# stacks, G1; -Xmx and -XX: on the executable's command line override them.
# At run time the executable needs -Dfr.lip6.binaries.root=<product>/plugins to find the tools.
set -e
PROD=${1:?product folder}
OUT=${2:-its-tools-native}
HERE=$(cd "$(dirname "$0")" && pwd)
GRAALVM=${GRAALVM:?path to a GraalVM with native-image}
cd "$PROD" && ITSTOOLS="$PROD" ./its-tools-flat.sh > /dev/null 2>&1 || true   # populates plugins-lib/
CP=""
for j in "$PROD"/plugins/*.jar ; do CP="$CP:$j" ; done
for d in "$PROD"/plugins/*/ ; do [ -f "$d/META-INF/MANIFEST.MF" ] && CP="$CP:${d%/}" ; done
for j in "$PROD"/plugins-lib/*.jar ; do [ -f "$j" ] && CP="$CP:$j" ; done
cd "$HERE"
"$GRAALVM/bin/native-image" -cp "${CP#:}" \
  -H:ConfigurationFileDirectories="$HERE/config" \
  --no-fallback -J-Xmx24g \
  --gc=G1 -R:MaxHeapSize=16g -R:MinHeapSize=40m -R:StackSize=128m \
  -o "$OUT" fr.lip6.move.gal.itscl.application.Application
ls -la "$OUT"
