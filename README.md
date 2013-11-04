grouping-exercise
=========
This is a solution for a simple data matching problem.

Given a collection of entries, this algorithm finds matches based on a specified "match type".

Usage:
--------------

```sh
git clone https://github.com/matthewoates/grouping-exercise.git
cd grouping-exercise
make
make test
make run [input_file (path relative to bin/)] [matching_type]
```
*NOTE: Due to the build and execution environment, paths to the input file must be relative to the bin directory that contains the build.*

# The Algorithm
**Complexity: O(MN)**
*(where M is the number of columns for the matching type, and N is the number of rows. In practice, M is very low)*

Firstly, all fields are sanitized. This removes things like phone number formatting and country codes.

In `Solver.findMatches` we construct a many-to-many, symmetrical graph between entries. The simple construction of this  graph provides us with all the relational information we need.

To output the data, we iterate through all entries in the order that they were in originally. Each node that has not been identified will be given an ID. That id will be shared with all matching entries.

# Output format
We output a new CSV file, with a column added. This column contains an ID for each entry. If an entry's ID is 0, then it has not been matched with any other entries. Otherwise, that entry matches all other entries that have the same ID value.

# Tests
For a test to pass, it must execute the specified input file without an exception, and if a file exists which specifies the desired output, the output must match the desired output as well.

The output validation is fairly simple. Each expected output file is a list of numbers. This list of numbers is compared with the first column that is added to the output data.


# Notes
### The good

#####New matching types can be added trivially.
*(as long as they do not span multiple column types, such as Phone and LastName)*

When a new matching type is added through `MatchType`, tests will automatically run for this new match type. Also, the usage output *(shown when input to the program is malformed)* will automatically show the new matching type.
### The bad

##### Emails are very complicated
The following emails will all resolve to the same gmail address:
* foo.bar@gmail.com
* foobar@gmail.com
* foobar+wtf@gmail.com

Omitting the period may be a non-neglegibale use-case. However, other email providers handle these situations differently. It may be sensible to support features like this for large email providers.

##### Input files must be relative to the class files
This is due to the build and execution environment. Since I am not familiar with best practices for java build environments, I used GNU Make in a way that made sense to me.


# Dependencies
* java 1.6
* GNU make
