#!/usr/bin/env bash

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

if ! TIMESTAMP=$(git -C "$SCRIPT_DIR" log -1 --format=%cI); then
  echo "Failed to read the latest commit timestamp from git." >&2
  exit 1
fi

if ! grep -q "<project.build.outputTimestamp>" "$POM_FILE"; then
  echo "Could not find <project.build.outputTimestamp> in $POM_FILE" >&2
  exit 1
fi

TEMP_POM=$(mktemp)
cleanup() {
  rm -f "$TEMP_POM"
}
trap cleanup EXIT

sed "s|<project.build.outputTimestamp>.*</project.build.outputTimestamp>|<project.build.outputTimestamp>$TIMESTAMP</project.build.outputTimestamp>|" "$POM_FILE" > "$TEMP_POM"
mv "$TEMP_POM" "$POM_FILE"

echo "export RELEASE_VERSION=$RELEASE_VERSION"
echo "Updated pom.xml project.build.outputTimestamp to $TIMESTAMP" >&2
