package com.testdouble;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class OCR {
    private final Map<String, Integer> digitToInteger = Map.ofEntries(
        entry(" _ " +
              "| |" +
              "|_|", 0),
        entry("   " +
              "  |" +
              "  |", 1),
        entry(" _ " +
              " _|" +
              "|_ ", 2),
        entry(" _ " +
              " _|" +
              " _|", 3),
        entry("   " +
              "|_|" +
              "  |", 4),
        entry(" _ " +
              "|_ " +
              " _|", 5),
        entry(" _ " +
              "|_ " +
              "|_|", 6),
        entry(" _ " +
              "  |" +
              "  |", 7),
        entry(" _ " +
              "|_|" +
              "|_|", 8),
        entry(" _ " +
              "|_|" +
              " _|", 9)
    );

    private List<List<String>> rawAccountNumbers;

    public List<List<String>> parseFile(String filePath) throws IOException {
        var reader = new BufferedReader(new FileReader(filePath));
        rawAccountNumbers = chunkStream(reader.lines(), 3);
        reader.close();
        return rawAccountNumbers;
    }

    public static <T> List<List<T>> chunkStream(Stream<T> stream, int groupSize) {
        var counter = new AtomicInteger();
        var collection = stream
            .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / groupSize))
            .values();
        return new ArrayList<>(collection);
    }

    public AccountNumber parseRawAccountNumber(List<String> rawAccountNumber) {
        var lines = convertToLinesInChunksOfThree(rawAccountNumber);
        // Now we have a 3D list that contains 3 lines, and each line contains 9 entries. Each entry has 3 characters
        // for that digit. Now we need to concatenate each digit fragment to complete the digit. Then we can translate
        // it into a number.
        // E.g.
        // [" _ ", ...]
        // ["| |", ...]
        // ["|_|", ...]
        // We want to convert it into a single string per digit:
        // " _ " +
        // "| |" +
        // "|_|"
        // Then once we have that full digit we can use a map to convert it into an Integer.
        var top = lines.get(0);
        var middle = lines.get(1);
        var bottom = lines.get(2);
        var digits = IntStream
            .range(0, 9)
            .mapToObj(i -> {
                var digit = top.get(i) + middle.get(i) + bottom.get(i);
                return digitToInteger.getOrDefault(digit, -1);
            })
            .toList();
        return new AccountNumber(digits);
    }

    public List<AccountNumber> getAccountNumbers() {
        return rawAccountNumbers.stream().map(this::parseRawAccountNumber).toList();
    }

    private static List<List<String>> convertToLinesInChunksOfThree(List<String> rawAccountNumber) {
        return rawAccountNumber.stream()
            .map(line -> {
                // We are iterating over each row: the top, middle, and bottom.
                // The goal is to get them into chunks of three, which represents
                // each digit.
                List<List<String>> groupsOfThree = chunkStream(Arrays.stream(line.split("")), 3);
                return groupsOfThree.stream().map(group -> String.join("", group)).toList();
            }).toList();
    }
}
