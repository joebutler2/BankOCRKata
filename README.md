# Installation

Ensure you have Java 17 and Gradle 8.2 installed.

Then from the project root directory run `./gradlew` to have it setup the project.

You can run the test suite with `./gradlew test`.

## Usage

You can run this app as a CLI tool with:
```
./gradlew run --args="path/to/file.txt
```

Here is how you can run it with one of the test files:

```
./gradlew run --args="/Users/jbutler/code/interviews/BankOCR/src/test/resources/simple_account_numbers.txt"
```

Here is the command to run the test file for "Use Case 3".

```
./gradlew run --args="/Users/jbutler/code/interviews/BankOCR/src/test/resources/use-case-3.txt"
```
