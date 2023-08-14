package com.testdouble;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountNumberTest {
    List<Integer> validNumbers = List.of(3, 4, 5, 8, 8, 2, 8, 6, 5);
    List<Integer> invalidNumbers = List.of(6, 6, 4, 3, 7, 1, 4, 9, 5);
    List<Integer> illegibleNumbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, -1);

    @Test
    void shouldAcceptAListOfNumbersAndPrintThemAsAString() {
        var account = new AccountNumber(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        assertEquals("123456789", account.toString());
    }

    @Test
    void shouldPrintNegativeOneAsQuestionMark() {
        var account = new AccountNumber(illegibleNumbers);
        assertEquals("12345678?", account.toString());
    }

    @Test
    void shouldValidateTheChecksum() {
        var account = new AccountNumber(validNumbers);
        assertTrue(account.hasValidChecksum());
    }

    @Test
    void hasValidChecksum_shouldReturnFalseIfTheCheckSumFails() {
        var account = new AccountNumber(invalidNumbers);
        assertFalse(account.hasValidChecksum());
    }

    @Test
    void hasValidChecksum_shouldReturnFalseIfOneOfTheNumbersIsIllegible() {
        var account = new AccountNumber(illegibleNumbers);
        assertFalse(account.hasValidChecksum());
    }

    @Test
    void getStatus_shouldReturnEmptyStringIfChecksumIsValid() {
        var account = new AccountNumber(validNumbers);
        assertEquals("", account.getStatus());
    }

    @Test
    void getStatus_shouldReturnErrIfChecksumIsInvalid() {
        var account = new AccountNumber(invalidNumbers);
        assertEquals("ERR", account.getStatus());
    }

    @Test
    void getStatus_shouldReturnIllIfAnyOfTheNumbersAreIllegible() {
        var account = new AccountNumber(illegibleNumbers);
        assertEquals("ILL", account.getStatus());
    }

    @Test
    void fullMessage_shouldReturnTheNumbersAsAStringAlongWithTheStatus() {
        var account = new AccountNumber(illegibleNumbers);
        assertEquals("12345678? ILL", account.getFullMessage());

        var invalidAccount = new AccountNumber(invalidNumbers);
        assertEquals("664371495 ERR", invalidAccount.getFullMessage());
    }
}