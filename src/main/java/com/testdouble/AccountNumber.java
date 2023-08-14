package com.testdouble;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AccountNumber {
    private final List<Integer> numbers;

    public AccountNumber(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public boolean hasValidChecksum() {
        if (hasIllegibleNumber()) {
            return false;
        }
        var multiples = IntStream.range(1, 10).boxed().sorted(Collections.reverseOrder()).iterator();
        var total = numbers.stream()
            .reduce(0, (acc, number) -> acc + (number * multiples.next()));
        return total % 11 == 0;
    }

    public String getStatus() {
        if (hasIllegibleNumber()) {
            return "ILL";
        }
        return this.hasValidChecksum() ? "" : "ERR";
    }

    private boolean hasIllegibleNumber() {
        return this.numbers.stream().anyMatch(i -> i < 0);
    }

    public String getFullMessage() {
        return this + " " + getStatus();
    }

    @Override
    public String toString() {
        return numbers.stream()
            .map(integer -> integer >= 0 ? String.valueOf(integer) : "?")
            .collect(Collectors.joining());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountNumber that = (AccountNumber) o;

        return Objects.equals(numbers, that.numbers);
    }

    @Override
    public int hashCode() {
        return numbers != null ? numbers.hashCode() : 0;
    }

}
