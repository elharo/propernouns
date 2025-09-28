# propernames

[![CI](https://github.com/elharo/propernames/actions/workflows/ci.yml/badge.svg)](https://github.com/elharo/propernames/actions/workflows/ci.yml)

Library to check whether a string is likely to be a name or other proper noun.
The methods are deliberately case insensitive since the motivating purpose is to
support correction of possibly incorrect casing.

This library was needed to fix a particular problem, and is the simplest thing
that could possibly work. It is not and cannot be exhaustive.
It identifies some names, not all of them. 

If you know a heuristic for identifying certain groups of names, please file an issue.
There are already heuristics that identify many Irish, Scottish, and Icelandic surnames.
If you happen to need one or more names added to the checks, send a PR that adds them to
src/main/resources/names.txt.

Nouns that commonly serve as both names and common nouns will not be reported as names. 
