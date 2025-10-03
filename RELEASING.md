# Releasing propernames to Maven Central

This document explains how to upload the propernames library to Maven Central.

propernames is a single-artifact Maven project that provides a library to check whether a string is likely to be a name or other proper noun.

## Prerequisites

Before releasing, ensure you have completed the one-time setup requirements:

- Sonatype Central account with access to `com.elharo.propernouns` groupId
- GPG key for artifact signing
- Maven settings.xml configured with credentials

For detailed setup instructions, see the [Central Portal Documentation](https://central.sonatype.com/publishing).

## Release Process

### 0. Create a release branch

```bash
# Ensure you're on main and have the latest changes
git checkout main
git pull origin main

# Create the release branch
git checkout -b release/<VERSION>
```

### 1. Prepare the Release

Before releasing, ensure the project is ready:

```bash
# Verify everything compiles and tests pass
mvn clean package
```

### 2. Update Version Numbers

Update the version in the POM from SNAPSHOT to the release version:

```bash
# Use Maven versions plugin to update the version
mvn versions:set -DnewVersion=<VERSION>

# Commit the version change
git add .
git commit -m "Release version <VERSION>"
```

### 3. Create Pull Request for Release

Create a pull request for the release version changes:

```bash
# Push the release branch
git push origin release/<VERSION>
```

Then create a pull request from `release/<VERSION>` to `main` with:
- Title: "Release version <VERSION>"
- Description: Include changelog and release notes

After creating the pull request, merge it to main.

### 4. Tag the Release

After the release PR is merged to main, create the release tag:

```bash
# Switch to main and pull the merged changes
git checkout main
git pull origin main

# Create and push the release tag
git tag v<VERSION>
git push origin v<VERSION>
```

### 5. Deploy to Maven Central

Deploy the artifacts to Maven Central from the tagged release:

```bash
# Deploy to Maven Central
mvn deploy -Prelease -DskipRemoteStaging -DaltStagingDirectory=/tmp/propernames-deploy -Dmaven.install.skip
```

### 6. Monitor and Publish Deployment

Monitor and publish the deployment through the Central Portal:

1. Go to [Central Portal](https://central.sonatype.com/publishing/deployments).
2. Log in with your Sonatype credentials.
3. Click the Publish link at the top right of the page.
4. If necessary, wait for artifacts to be validated.
5. Once validation is complete, click the "Publish" button to release artifacts to Maven Central.
6. Publication typically takes 10-30 minutes after clicking publish.

### 7. Prepare for Next Development Iteration

Create another pull request for the next development version:

```bash
# Create a new branch for the next development iteration
git checkout -b prepare-next-development-<NEXT-VERSION>

# Update to next development version
mvn versions:set -DnewVersion=<NEXT-VERSION>-SNAPSHOT

# Commit the version change
git add .
git commit -m "Prepare for next development iteration: <NEXT-VERSION>-SNAPSHOT"

# Push the branch and create a pull request
git push origin prepare-next-development-<NEXT-VERSION>
```

Then create a pull request from `prepare-next-development-<NEXT-VERSION>` to `main` with:
- Title: "Prepare for next development iteration: <NEXT-VERSION>-SNAPSHOT"
- Description: Updates version numbers for continued development

After creating the pull request, merge it to main.

## Verification

After release, verify the artifacts are available for download:

1. **Direct repository check** (available immediately):
   ```bash
   # Test downloading the library
   mvn dependency:get -Dartifact=com.elharo.propernouns:propernames:<VERSION>
   ```

2. **Direct URL check** (available immediately):
   - Library: `https://repo1.maven.org/maven2/com/elharo/propernouns/propernames/<VERSION>/`

3. **Maven Central Search** (may take several hours to update):
   - [Search results](https://search.maven.org/search?q=g:com.elharo.propernouns)
   - Note: Search indexing can lag behind artifact availability by many hours

## Usage After Release

Once released, users can use the library:

### Library Dependency

```xml
<dependency>
  <groupId>com.elharo.propernouns</groupId>
  <artifactId>propernames</artifactId>
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
