package com.teachmeskills.hw.lesson_14.utils;

import com.teachmeskills.hw.lesson_14.constant.Constants;
import com.teachmeskills.hw.lesson_14.exception.InvalidDocumentException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileOperation {

    public static void readTextFromFile(String path) throws InvalidDocumentException {
        List<String> lines;

        try {
            lines = Files.readAllLines(Paths.get(path));
            lines.removeIf(String::isBlank);
            lines.replaceAll(String::trim);
        } catch (IOException e) {
            System.err.println("Error during file reading: " + e.getMessage());
            return;
        }

        analyzeTextFromFile(lines);
    }

    public static void analyzeTextFromFile(List<String> parsedText) {

        List<String> validDocnums = new ArrayList<>();
        List<String> validContracts = new ArrayList<>();
        List<String> invalidNumbers = new ArrayList<>();

        for (String line : parsedText) {
            try {
                if (line.length() != 15) {
                    throw new InvalidDocumentException("Invalid length: Expected 15 characters");
                }

                if (!line.matches(Constants.REGEX_EXP)) {
                    throw new InvalidDocumentException("Contains invalid characters");
                }

                if (line.startsWith("docnum")) {
                    validDocnums.add(line);
                } else if (line.startsWith("contract")) {
                    validContracts.add(line);
                } else {
                    throw new InvalidDocumentException("Invalid prefix: Should start with 'docnum' or 'contract'");
                }
            } catch (InvalidDocumentException e) {
                invalidNumbers.add(line + " - Reason: " + e.getMessage());
            }
        }

        createReport(validDocnums, "docnums_report.txt");
        createReport(validContracts, "contracts_report.txt");
        createReport(invalidNumbers, "invalid_numbers_report.txt");
    }

    public static void createReport(List<String> data, String fileName) {
        Path path = Paths.get(Constants.PATH_TO_REPORTS, fileName);
        try {
            Files.write(path, data);
            System.out.println("Report is created: " + path);
        } catch (IOException e) {
            System.err.println("Error writing report: " + e.getMessage());
        }
    }
}
