#!/bin/bash

set -euo pipefail

SCRIPT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
POM_FILE="$SCRIPT_DIR/pom.xml"

if ! PROJECT_VERSION=$(mvn -f "$POM_FILE" help:evaluate -Dexpression=project.version -q -DforceStdout); then
  echo "Failed to read project version from $POM_FILE. Is Maven installed and is pom.xml valid?" >&2
  exit 1
fi
if [[ "$PROJECT_VERSION" == *-SNAPSHOT ]]; then
  RELEASE_VERSION="${PROJECT_VERSION%-SNAPSHOT}"
else
  RELEASE_VERSION="$PROJECT_VERSION"
fi

TIMESTAMP=$(git -C "$SCRIPT_DIR" log -1 --format=%cI)
TEMP_POM=$(mktemp)
sed "s|<project.build.outputTimestamp>.*</project.build.outputTimestamp>|<project.build.outputTimestamp>$TIMESTAMP</project.build.outputTimestamp>|" "$POM_FILE" > "$TEMP_POM"
mv "$TEMP_POM" "$POM_FILE"

echo "export RELEASE_VERSION=$RELEASE_VERSION"
echo "Updated pom.xml project.build.outputTimestamp to $TIMESTAMP" >&2
