#!/usr/bin/env bash

set -euo pipefail

AUTOMATE_STEP_2=false
DRY_RUN=false
for ARG in "$@"; do
  case "$ARG" in
    --automate-step-2)
      AUTOMATE_STEP_2=true
      ;;
    --dry-run)
      DRY_RUN=true
      ;;
    *)
      echo "Unknown argument: $ARG" >&2
      echo "Usage: ./prepare-release.sh [--automate-step-2] [--dry-run]" >&2
      exit 1
      ;;
  esac
done

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
export RELEASE_VERSION

run_cmd() {
  if [ "$DRY_RUN" = "true" ]; then
    echo "[dry-run] $*" >&2
  else
    "$@"
  fi
}

if [ "$AUTOMATE_STEP_2" = "true" ]; then
  run_cmd git -C "$SCRIPT_DIR" checkout main
  run_cmd git -C "$SCRIPT_DIR" pull origin main
  BRANCH_NAME="update-timestamp-$RELEASE_VERSION"
  if [ "$DRY_RUN" = "true" ]; then
    echo "[dry-run] git -C $SCRIPT_DIR checkout -b $BRANCH_NAME" >&2
  elif git -C "$SCRIPT_DIR" rev-parse --verify "$BRANCH_NAME" >/dev/null 2>&1; then
    git -C "$SCRIPT_DIR" checkout "$BRANCH_NAME"
  else
    git -C "$SCRIPT_DIR" checkout -b "$BRANCH_NAME"
  fi
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

if [ "$DRY_RUN" = "true" ]; then
  echo "[dry-run] update project.build.outputTimestamp in $POM_FILE to $TIMESTAMP" >&2
else
  sed "s|<project.build.outputTimestamp>.*</project.build.outputTimestamp>|<project.build.outputTimestamp>$TIMESTAMP</project.build.outputTimestamp>|" "$POM_FILE" > "$TEMP_POM"
  mv "$TEMP_POM" "$POM_FILE"
fi

if [ "$AUTOMATE_STEP_2" = "true" ]; then
  run_cmd git -C "$SCRIPT_DIR" add pom.xml
  if [ "$DRY_RUN" = "true" ]; then
    echo "[dry-run] git -C $SCRIPT_DIR commit -m \"Update reproducible build timestamp for release $RELEASE_VERSION\"" >&2
  elif git -C "$SCRIPT_DIR" diff --cached --quiet; then
    echo "No pom.xml changes to commit." >&2
  else
    git -C "$SCRIPT_DIR" commit -m "Update reproducible build timestamp for release $RELEASE_VERSION"
  fi
fi
if [ "$DRY_RUN" = "true" ]; then
  echo "Dry run complete." >&2
else
  echo "Updated pom.xml project.build.outputTimestamp to $TIMESTAMP" >&2
fi
