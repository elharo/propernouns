#!/usr/bin/env bash

set -euo pipefail

DRY_RUN=false
for ARG in "$@"; do
  case "$ARG" in
    --dry-run)
      DRY_RUN=true
      ;;
    *)
      echo "Unknown argument: $ARG" >&2
      echo "Usage: ./prepare-release.sh [--dry-run]" >&2
      exit 1
      ;;
  esac
done

SCRIPT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
POM_FILE="$SCRIPT_DIR/pom.xml"
MVNW="$SCRIPT_DIR/mvnw"

if ! PROJECT_VERSION=$("$MVNW" -f "$POM_FILE" help:evaluate -Dexpression=project.version -q -DforceStdout); then
  echo "Failed to read project version from $POM_FILE using $MVNW. Ensure the Maven wrapper is available and pom.xml is valid." >&2
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

run_cmd git -C "$SCRIPT_DIR" checkout main >&2
run_cmd git -C "$SCRIPT_DIR" pull origin main >&2

BRANCH_NAME="update-timestamp-$RELEASE_VERSION"
if [ "$DRY_RUN" = "true" ]; then
  echo "[dry-run] git -C $SCRIPT_DIR checkout -b $BRANCH_NAME" >&2
elif git -C "$SCRIPT_DIR" rev-parse --verify "$BRANCH_NAME" >/dev/null 2>&1; then
  run_cmd git -C "$SCRIPT_DIR" checkout "$BRANCH_NAME" >&2
else
  run_cmd git -C "$SCRIPT_DIR" checkout -b "$BRANCH_NAME" >&2
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

printf 'export RELEASE_VERSION=%q\n' "$RELEASE_VERSION"
run_cmd git -C "$SCRIPT_DIR" add pom.xml
if [ "$DRY_RUN" = "true" ]; then
  echo "[dry-run] git -C $SCRIPT_DIR commit -m \"Update reproducible build timestamp for release $RELEASE_VERSION\"" >&2
elif git -C "$SCRIPT_DIR" diff --cached --quiet; then
  echo "No pom.xml changes to commit." >&2
else
  run_cmd git -C "$SCRIPT_DIR" commit -m "Update reproducible build timestamp for release $RELEASE_VERSION" >&2
fi
run_cmd git -C "$SCRIPT_DIR" push -u origin "$BRANCH_NAME" >&2

if [ "$DRY_RUN" = "true" ]; then
  echo "[dry-run] gh pr create --base main --head $BRANCH_NAME --title \"Update reproducible build timestamp for release $RELEASE_VERSION\" --body \"Updates the build timestamp for reproducible builds\"" >&2
else
  if ! command -v gh >/dev/null 2>&1; then
    echo "GitHub CLI (gh) is required to create the pull request. Install from https://cli.github.com/ or run with --dry-run to preview steps without creating a PR." >&2
    exit 1
  fi
  EXISTING_PR_NUMBER=$(cd "$SCRIPT_DIR" && gh pr list --head "$BRANCH_NAME" --base main --state open --limit 1 --json number --template '{{range .}}{{.number}}{{end}}')
  if [ -n "$EXISTING_PR_NUMBER" ]; then
    echo "Pull request #$EXISTING_PR_NUMBER already exists for $BRANCH_NAME." >&2
  else
    (cd "$SCRIPT_DIR" && gh pr create \
      --base main \
      --head "$BRANCH_NAME" \
      --title "Update reproducible build timestamp for release $RELEASE_VERSION" \
      --body "Updates the build timestamp for reproducible builds") >&2
  fi
fi

if [ "$DRY_RUN" = "true" ]; then
  echo "Dry run complete." >&2
else
  echo "Updated pom.xml project.build.outputTimestamp to $TIMESTAMP" >&2
fi
