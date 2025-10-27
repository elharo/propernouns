# Releasing propernouns to Maven Central

This document explains how to upload the propernouns library to Maven Central.

propernouns is a single-artifact Maven project that provides a library to check whether a string is likely to be a name or other proper noun.

## Prerequisites

Before releasing, ensure you have completed the one-time setup requirements:

- Sonatype Central account with access to `com.elharo` groupId
- GPG key for artifact signing
- Maven settings.xml configured with credentials

For detailed setup instructions, see the [Central Portal Documentation](https://central.sonatype.com/publishing).

## Release Process

### 1. Set Release Version Environment Variable

Before starting the release process, set the release version as an environment variable. This allows you to copy and paste the commands below without editing version numbers.

```bash
# Set the release version (e.g., 1.0.3)
export RELEASE_VERSION=1.0.3
```

You can verify it's set correctly:

```bash
echo $RELEASE_VERSION
```

### 2. Create a release branch for the new version

Create a release branch from main. Main always has a SNAPSHOT version. The release branch will be updated to the release version and then tagged.

```bash
# Ensure you're on main and have the latest changes
git checkout main
git pull origin main

# Create the release branch for the new version
git checkout -b release/$RELEASE_VERSION
```

### 3. Update Version Numbers

Update the version in the POM from SNAPSHOT to the release version:

```bash
# Use Maven versions plugin to update the version
mvn versions:set -DnewVersion=$RELEASE_VERSION

# Commit the version change
git add .
git commit -m "Release version $RELEASE_VERSION"
```

### 4. Update Reproducible Build Timestamp

This project implements [reproducible builds](https://reproducible-builds.org/), ensuring that builds are byte-for-byte identical regardless of when or where they are executed. Before creating a release, update the `project.build.outputTimestamp` property in pom.xml to the current date or the date of the last commit:

```bash
# Option 1: Use current date
echo "    <project.build.outputTimestamp>$(date -u +%Y-%m-%dT%H:%M:%SZ)</project.build.outputTimestamp>"

# Option 2: Use the timestamp of the last commit
echo "    <project.build.outputTimestamp>$(git log -1 --format=%cI)</project.build.outputTimestamp>"
```

Update the property manually in pom.xml with the generated timestamp.

To verify reproducibility:

```bash
# Build twice and compare checksums
mvn clean package
sha256sum target/*.jar > checksums1.txt

mvn clean package
sha256sum target/*.jar > checksums2.txt

diff checksums1.txt checksums2.txt
```

If the builds are reproducible, the checksums will be identical.

### 5. Prepare the Release

Before releasing, ensure the project is ready:

```bash
# Verify everything compiles and tests pass
mvn clean package
```

### 6. Push the Release Branch

Push the release branch to GitHub:

```bash
# Push the release branch
git push origin release/$RELEASE_VERSION
```

**Important**: Do not create a pull request to merge the release branch to main. Release branches are independent and are not merged back to main.

### 7. Tag the Release

Create the release tag on the release branch:

```bash
# Ensure you're on the release branch
git checkout release/$RELEASE_VERSION

# Create and push the release tag
git tag v$RELEASE_VERSION
git push origin v$RELEASE_VERSION
```

### 8. Check Out the Release Tag

Before deploying, check out the release tag:

```bash
# Check out the release tag
git checkout v$RELEASE_VERSION
```

### 9. Deploy to Maven Central

Deploy the artifacts to Maven Central:

```bash
# Deploy to Maven Central
mvn deploy -Prelease -DskipRemoteStaging -DaltStagingDirectory=/tmp/propernouns-deploy -Dmaven.install.skip
```

### 10. Monitor and Publish Deployment

Monitor and publish the deployment through the Central Portal:

1. Go to [Central Portal](https://central.sonatype.com/publishing/deployments).
2. Log in with your Sonatype credentials.
3. Click the Publish link at the top right of the page.
4. If necessary, wait for artifacts to be validated.
5. Once validation is complete, click the "Publish" button to release artifacts to Maven Central.
6. Publication typically takes 10-30 minutes after clicking publish.

### 11. Update Main to Next Development Version

Update the SNAPSHOT version on main to the next development version. First, set the next development version as an environment variable:

```bash
# Set the next development version (e.g., 1.0.4)
export NEXT_VERSION=1.0.4
```

Then update the version on main:

```bash
# Switch to main
git checkout main
git pull origin main

# Create a new branch for the version update
git checkout -b prepare-next-development-$NEXT_VERSION

# Update to next development version
mvn versions:set -DnewVersion=$NEXT_VERSION-SNAPSHOT

# Commit the version change
git add .
git commit -m "Prepare for next development iteration: $NEXT_VERSION-SNAPSHOT"

# Push the branch and create a pull request
git push origin prepare-next-development-$NEXT_VERSION
```

Then create a pull request from `prepare-next-development-$NEXT_VERSION` to `main` with:
- Title: "Prepare for next development iteration: $NEXT_VERSION-SNAPSHOT"
- Description: Updates version numbers for continued development

After creating the pull request, merge it to main.

## Verification

After release, verify the artifacts are available for download:

1. **Direct repository check** (available immediately):
   ```bash
   # Test downloading the library
   mvn dependency:get -Dartifact=com.elharo:propernouns:$RELEASE_VERSION
   ```

2. **Direct URL check** (available immediately):
   - Library: `https://repo1.maven.org/maven2/com/elharo/propernouns/{version}/`
   - Replace `{version}` with the actual version number (e.g., `1.0.1`)

3. **Maven Central Search** (may take several hours to update):
   - [Search results](https://search.maven.org/search?q=g:com.elharo)
   - Note: Search indexing can lag behind artifact availability by many hours

## Usage After Release

Once released, users can use the library:

### Library Dependency

```xml
<dependency>
  <groupId>com.elharo</groupId>
  <artifactId>propernouns</artifactId>
  <version>VERSION</version>
</dependency>
```

## Troubleshooting

### Common Issues

1. **GPG signing fails**: Ensure your GPG key is properly configured and the keyname matches your settings.xml
2. **401 Unauthorized**: Check your Sonatype credentials in settings.xml
3. **Validation errors**: Ensure all required metadata is present (name, description, url, licenses, developers, SCM)
4. **Key server timeout**: Try uploading to multiple key servers

### Debugging

```bash
# Debug Maven deployment
mvn clean deploy -X

# Test GPG signing
mvn clean verify -Dgpg.skip=false

# Validate POM completeness
mvn help:effective-pom
```

### Support

- [Central Portal Documentation](https://central.sonatype.com/publishing)
- [Maven Central Documentation](https://maven.apache.org/repository/guide-central-repository-upload.html)
- [Sonatype Support](https://issues.sonatype.org/)
