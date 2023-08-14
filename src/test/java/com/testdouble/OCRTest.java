package com.testdouble;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OCRTest {
    String simpleTestFile;
    public OCRTest() {
        var classLoader = getClass().getClassLoader();
        simpleTestFile = classLoader.getResource("simple_account_numbers.txt").getFile();
    }

    @Test
    void shouldLoadInFileAndPrint3LineChunks() throws IOException {
        var output = (new OCR()).parseFile(simpleTestFile);
        List<String> expected = List.of(
            " _  _  _  _  _  _  _  _  _ ",
            "| || || || || || || || || |",
            "|_||_||_||_||_||_||_||_||_|");
        assertEquals(expected, output.get(0));
    }

    @Test
    void shouldReturnAListOfAccountNumbers() throws IOException {
        OCR ocr = new OCR();
        ocr.parseFile(simpleTestFile);
        List<AccountNumber> accounts = ocr.getAccountNumbers();
        var expected = new AccountNumber(List.of(0,0,0,0,0,0,0,0,0));
        assertEquals(expected, accounts.get(0));
    }

    @Test
    void shouldConvertA3LineChunkIntoAnAccountNumber() {
        var rawAccountNumber = List.of(
            " _  _  _  _  _  _  _  _  _ ",
            "| || || || || || || || || |",
            "|_||_||_||_||_||_||_||_||_|");
        var ocr = new OCR();
        assertEquals(
            "000000000",
            ocr.parseRawAccountNumber(rawAccountNumber).toString()
        );
    }

    @Test
    void shouldHandleAllNumbersInAnAccountNumber() {
        var rawAccountNumber = List.of(
            "    _  _     _  _  _  _  _ ",
            "  | _| _||_||_ |_   ||_||_|",
            "  ||_  _|  | _||_|  ||_| _|");
        var ocr = new OCR();
        assertEquals(
            "123456789",
            ocr.parseRawAccountNumber(rawAccountNumber).toString()
        );
    }

    @Test
    void shouldUseQuestionMarkIfADigitIsWrong() {
        var rawAccountNumber = List.of(
            "    _  _     _  _  _  _    ",
            "  | _| _||_||_ |_   ||_| _|",
            "  ||_  _|  | _||_|  ||_| _|");
        var ocr = new OCR();
        assertEquals(
            "12345678?",
            ocr.parseRawAccountNumber(rawAccountNumber).toString()
        );
    }

    @Test
    void shouldChunkAListIntoGroupsOfXSize() {
        var list = List.of(1, 2, 3, 4, 5, 6);
        assertEquals(
            List.of(List.of(1, 2), List.of(3, 4), List.of(5, 6)),
            OCR.chunkStream(list.stream(), 2));
    }
}