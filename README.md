# AdventOfCode-2019

A repository of solutions for the [AdventOfCode](https://adventofcode.com/) challenges, implemented in different languages.

## Use

In your terminal, run `solve <language> <day> <path-to-input-file>`. The following solutions are available so far:

| Day/Lang | clojure | go  | haskell | scala | java | kotlin | js  |
| -------- | ------- | --- | ------- | ----- | ---- | ------ | --- |
| 01       | ✅       | ✅   | ✅       | ✅     | ✅    | ✅      | ✅   |
| 02       | ✅       | ✅   | ✅       | ✅     | ✅    | ✅      | ✅   |
| 03       | ✅       | ✅   | ✅       | ❌     | (✅)  | ❌      | ✅   |
| 04       | ✅       | ✅   | ✅       | ❌     | ❌    | ✅      | ❌   |
| 05       | ✅       | ✅   | ✅       | ❌     | ❌    | ❌      | ❌   |
| 06       | ✅       | ❌   | ❌       | ❌     | ❌    | ✅      | ❌   |

For `day`, input `Day01`, `Day02` etc.

The input file is the one you receive from the challenge website. If no value is given, it is assumed to be located in `./input/<day>.txt`, where `<day>` is the second parameter.

## How to contribute

- Click "Fork" in this repository and fork it into your account

- In your terminal, run `git clone https://github.com/<yourname>/AdventOfCode-2019.git`

- Choose your language and make a directory with its name - if there already is a directory for the language, name it differently (for example "java2" if "java" already exists)

- Place a "run.bat" and "run.sh" file in the newly created directory and write a script that takes the day that ought to be solved and the input file as a command parameter input and executes whatever action is required to run your solutions. For example, this is what this script for java might look like (batch):

```java
:: %1 is the first argument, e.g. "Day01"
set file_name=%1.java
:: compile the code
javac %file_name%
:: run the code - %2 is the input file
java %1 %2
```

- Add a readme to your directory depicting the requirements to run your script.

- Write your script in a way that is compatible with the naming convention ("Day01", "Day02", ...)

- To keep it structured, use a different file for each day.

- Open a Pull Request whenever you want; make sure [your fork is up to date](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/syncing-a-fork)
