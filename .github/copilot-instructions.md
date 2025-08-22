# Propernames Java Library

Propernames is a Java library to check whether a string is likely to be a proper name. This repository is currently empty (skeleton project) and needs to be set up with a proper Java project structure.

**Always reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.**

## Working Effectively

### Environment Setup
- Java 17 is available and working: `java -version` and `javac -version`
- Maven 3.9.11 is available: `mvn -version`
- Gradle 9.0.0 is available: `gradle -version`

### Project Setup (Required - Repository is Currently Empty)
The repository currently contains only README.md, LICENSE (GPL v3), and .gitignore. You MUST set up the project structure first:

**Choose ONE build system:**

#### Option A: Maven Setup (Recommended)
```bash
# Generate Maven project structure - takes ~15 seconds with internet
mvn archetype:generate \
  -DgroupId=com.elharo.propernames \
  -DartifactId=propernames \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false
  
# Move generated files to root
mv propernames/* .
mv propernames/.* . 2>/dev/null || true
rmdir propernames
```

#### Option B: Gradle Setup
```bash
# Generate Gradle project - NEVER CANCEL: takes 6+ minutes on first run
# Set timeout to 10+ minutes for this command
gradle init \
  --type java-library \
  --dsl groovy \
  --test-framework junit-jupiter \
  --project-name propernames \
  --package com.elharo.propernames

# When prompted:
# - Java version: Enter "17"
# - New APIs: Enter "no"
```

### Build and Test Commands

#### Maven Commands
- **Setup dependencies**: `mvn clean install` - takes ~10 seconds after first download
- **Compile**: `mvn clean compile` - takes ~6 seconds
- **Run tests**: `mvn test` - takes ~7 seconds  
- **Package**: `mvn package` - creates JAR file
- **Full build**: `mvn clean install` - NEVER CANCEL: allow 60 seconds timeout

#### Gradle Commands
- **First build**: `./gradlew build` - NEVER CANCEL: takes 20+ seconds on first run. Set timeout to 2+ minutes.
- **Subsequent builds**: `./gradlew build` - takes ~1 second (cached)
- **Run tests**: `./gradlew test` - takes ~1 second after initial build
- **Clean build**: `./gradlew clean build` - takes ~3 seconds

### Code Quality and Linting

#### Maven Linting
```bash
# Add checkstyle plugin to pom.xml <plugins> section if not present:
# <plugin>
#   <groupId>org.apache.maven.plugins</groupId>
#   <artifactId>maven-checkstyle-plugin</artifactId>
#   <version>3.6.0</version>
# </plugin>

# Run checkstyle analysis
mvn checkstyle:check
```

#### Gradle Linting
```bash
# Built-in with Gradle init - includes checkstyle
./gradlew check
```

### Testing Framework
- **Maven**: Uses JUnit 3.x by default (legacy)
- **Gradle**: Uses JUnit Jupiter 5.x (modern, recommended)

### Directory Structure
After setup, the standard Java structure will be:
```
src/
├── main/
│   └── java/
│       └── com/elharo/propernames/
└── test/
    └── java/
        └── com/elharo/propernames/
```

## Validation

**CRITICAL**: Always validate that your setup works:

### After Project Setup
1. **Verify structure**: `find src -name "*.java" | head -5`
2. **Test compilation**: Run build command for your chosen build system
3. **Test execution**: Run test command and verify tests pass
4. **Clean build**: Run clean + build to ensure reproducibility

### After Code Changes
1. **Always build first**: Use `mvn compile` or `./gradlew compileJava`
2. **Run tests**: Use `mvn test` or `./gradlew test`
3. **Run linting**: Use checkstyle commands above
4. **Full clean build**: Verify `mvn clean install` or `./gradlew clean build` succeeds

### Expected Build Times
- **Maven project generation**: ~15 seconds (first time with downloads)
- **Maven compile**: ~6 seconds 
- **Maven test**: ~7 seconds
- **Gradle init**: 6+ minutes (NEVER CANCEL - set 10+ minute timeout)
- **Gradle first build**: ~20 seconds (set 2+ minute timeout)
- **Gradle subsequent builds**: ~1 second (cached)

## Common Tasks

### Creating a New Class
```bash
# For Maven setup:
touch src/main/java/com/elharo/propernames/NewClass.java

# For Gradle setup:
touch lib/src/main/java/com/elharo/propernames/NewClass.java
```

### Running Specific Tests
```bash
# Maven
mvn test -Dtest=ClassNameTest

# Gradle  
./gradlew test --tests ClassNameTest
```

### Packaging for Distribution
```bash
# Maven
mvn clean package  # Creates target/propernames-*.jar

# Gradle
./gradlew jar      # Creates lib/build/libs/propernames.jar
```

## Common Issues and Solutions

### "No such file or directory" errors
- **Problem**: Repository is empty, no source files exist
- **Solution**: Follow "Project Setup" section above first

### Build failures with missing dependencies
- **Problem**: Internet connectivity or repository access
- **Solution**: Retry command - Maven/Gradle will resume downloads

### Long build times
- **Problem**: First-time dependency downloads
- **Solution**: Be patient, subsequent builds are much faster

### Test failures after changes
- **Problem**: Breaking changes to existing code
- **Solution**: Update tests or fix implementation to match expected behavior

## Key Project Information

### Repository Root Contents (Before Setup)
```
.
├── .git/           # Git repository
├── .gitignore      # Java-specific gitignore
├── LICENSE         # GNU GPL v3
└── README.md       # Minimal project description
```

### License
- **GNU General Public License v3.0**
- **Commercial use**: Allowed with GPL restrictions
- **Distribution**: Source code must be provided
- **Modification**: Allowed with GPL restrictions

### Project Purpose
Library to check whether a string is likely to be a proper name (e.g., "John Smith" vs "hello world").