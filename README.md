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
src/main/resources/names.txt.

Nouns that commonly serve as both names and common nouns will not be reported as names.

Since this library was built for the purpose of determining word case, it does not include names from scripts that do not distinguish upper and lower case
such as Chinese, Arabic, and Hebrew. Case is primarily a feature of Latin. Greek, and Cyrillic scripts.

Some other lists of names:

* https://gist.github.com/timler/5c96cffbacb8affd375a
* https://en.wiktionary.org/w/index.php?title=Category:English_proper_nouns
* https://github.com/cltk/greek_proper_names_cltk

Unlike this library, these lists include many names that are also common nouns.


