# propernouns

[![CI](https://github.com/elharo/propernouns/actions/workflows/ci.yml/badge.svg)](https://github.com/elharo/propernouns/actions/workflows/ci.yml)

Library to check whether a string is likely to be a name or other proper noun.
The methods are deliberately case insensitive since the motivating purpose is to
support correction of possibly incorrect casing.

This library was needed to fix a particular problem, and is the simplest thing
that could possibly work. It is not and cannot be exhaustive.
It identifies some names, not all of them.

## Installation

Add the library to your project using one of these dependency management tools:

### Maven

```xml
<dependency>
  <groupId>com.elharo</groupId>
  <artifactId>propernouns</artifactId>
  <version>1.0</version>
</dependency>
```

### Gradle

```gradle
implementation 'com.elharo:propernouns:1.0'
```

### Ivy

```xml
<dependency org="com.elharo" name="propernouns" rev="1.0"/>
```

## Usage

The library provides a simple static method to check if a string is likely to be a proper name:

```java
import com.elharo.propernouns.Names;

public class Example {
    public static void main(String[] args) {
        // Check single names
        boolean isName = Names.isName("John");
        System.out.println("John: " + isName); // true
        
        // Check full names
        isName = Names.isName("John Smith");
        System.out.println("John Smith: " + isName); // true
        
        // Case insensitive
        isName = Names.isName("john smith");
        System.out.println("john smith: " + isName); // true
        
        // Non-names return false
        isName = Names.isName("hello world");
        System.out.println("hello world: " + isName); // false
    }
}
```

## Reporting Bugs

If you know a heuristic for identifying certain groups of names, please file an issue.
There are already heuristics that identify many Irish, Scottish, and Icelandic surnames.
If you need one or more names added to the checks, send a PR that adds them to
src/main/resources/names.txt.

Nouns that commonly serve as both names and common nouns will not be reported as names.

Since this library was built for the purpose of determining word case, it does not include names
from scripts that do not distinguish upper and lower case
such as Chinese, Arabic, and Hebrew. Case is primarily a feature of Latin, Greek, and Cyrillic scripts.

Some other lists of names:

* https://gist.github.com/timler/5c96cffbacb8affd375a
* https://en.wiktionary.org/w/index.php?title=Category:English_proper_nouns
* https://github.com/cltk/greek_proper_names_cltk

Unlike this library, these lists include many names that are also common nouns.


