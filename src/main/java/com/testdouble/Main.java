package com.testdouble;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the OCR parser.");
        try {
            var ocr = new OCR();
            ocr.parseFile(args[0]);
            ocr.getAccountNumbers().forEach(account -> System.out.println(account.getFullMessage()));
        } catch (IOException exception) {
            System.out.println("Unable to load that file. Please check that the file is present and named correctly.");
        }
    }
}
