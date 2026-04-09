#!/bin/bash
# Verify that the build is reproducible by building twice and comparing checksums.
# Usage: ./verify-reproducible-build.sh
# Exit code 0 means the build is reproducible; non-zero means it is not.

set -e

TMPDIR_1=$(mktemp -d)
TMPDIR_2=$(mktemp -d)

cleanup() {
  rm -rf "$TMPDIR_1" "$TMPDIR_2"
}
trap cleanup EXIT

echo "Building first time..."
./mvnw clean package
cp target/*.jar "$TMPDIR_1/"

echo "Building second time..."
./mvnw clean package
cp target/*.jar "$TMPDIR_2/"

echo "Comparing checksums..."
sha256sum "$TMPDIR_1"/*.jar | sed "s|$TMPDIR_1/||g" | sort > "$TMPDIR_1/checksums.txt"
sha256sum "$TMPDIR_2"/*.jar | sed "s|$TMPDIR_2/||g" | sort > "$TMPDIR_2/checksums.txt"

if diff "$TMPDIR_1/checksums.txt" "$TMPDIR_2/checksums.txt"; then
  echo "Build is reproducible."
  exit 0
else
  echo "Build is NOT reproducible." >&2
  exit 1
fi
