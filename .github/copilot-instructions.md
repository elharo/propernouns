# Propernames Java Library

Propernames is a Java library to check whether a string is likely to be a proper name. This repository is currently empty (skeleton project) and needs to be set up with a proper Java project structure.

**Always reference these instructions first and fallback to search or bash commands only when you encounter unexpected information that does not match the info here.**

## Working Effectively

### Environment Setup
- Java 8 is available and working: `java -version` and `javac -version`
- Maven 3.9.9 is available: `mvn -version`

### Build and Test Commands
- **Setup dependencies**: `mvn clean install` - takes ~10 seconds after first download
- **Compile**: `mvn clean compile` - takes ~6 seconds
- **Run tests**: `mvn test` - takes ~7 seconds  
- **Package**: `mvn package` - creates JAR file
- **Full build**: `mvn clean install` - NEVER CANCEL: allow 60 seconds timeout

### Code Quality and Linting
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

### Testing Framework
- **Maven**: Uses JUnit 4.x 

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

### After Initial Setup
1. **Verify structure**: `find src -name "*.java" | head -5`
2. **Test compilation**: Run build command
3. **Test execution**: Run test command and verify tests pass
4. **Clean build**: Run clean + build to ensure reproducibility

### After Code Changes
1. **Always build first**: Use `mvn compile`
2. **Run tests**: Use `mvn test`
3. **Run linting**: Use checkstyle commands above
4. **Full clean build**: Verify `mvn clean install` succeeds

### Expected Build Times
- **Maven project generation**: ~15 seconds (first time with downloads)
- **Maven compile**: ~6 seconds 
- **Maven test**: ~7 seconds

## Common Tasks

### Creating a New Class
```bash
touch src/main/java/com/elharo/propernames/NewClass.java
```

### Running Specific Tests
```bash
mvn test -Dtest=ClassNameTest
```

### Packaging for Distribution
```bash
mvn clean package  # Creates target/propernames-*.jar
```

## Common Issues and Solutions

### "No such file or directory" errors
- **Problem**: Repository is empty, no source files exist
- **Solution**: Set up the project structure using Maven commands

### Build failures with missing dependencies
- **Problem**: Internet connectivity or repository access
- **Solution**: Retry command - Maven will resume downloads

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

## Project Standards
- Use Java 8+ language features
- Follow Google Java code style
- Include a linefeed as the final character of each source code file
- Package structure: com.elharo.propernames
- Write JUnit 4 tests for new functionality
- Maintain immutability where possible
- Handle edge cases in Javadoc parsing gracefully
- Do not use reflection to test. Unit test through public and package private APIs.
- Do not catch raw java.lang.Exception or java.lang.RuntimeException unless absolutely required by a third party method that throws an undifferentiated exception. Catch only more specific subclasses. Assume most runtime exceptions indicate bugs that should be fixed by preventing the exception from being thrown rather than catching it.
- When writing a PR description include a link to the issue that is being fixed such as "fixes #146" assuming the PR completely resolves the issue.
