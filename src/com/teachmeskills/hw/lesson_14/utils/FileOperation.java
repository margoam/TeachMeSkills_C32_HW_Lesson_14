package com.teachmeskills.hw.lesson_14.utils;

import com.teachmeskills.hw.lesson_14.constant.Constants;
import com.teachmeskills.hw.lesson_14.exception.InvalidDocumentException;

import java.io.*;

public class FileOperation {

    public static void readTextFromFile(String path) throws InvalidDocumentException {
        String[] lines = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            int lineCount = 0;
            while (reader.readLine() != null) {
                lineCount++;
            }

            lines = new String[lineCount];

            try (BufferedReader newReader = new BufferedReader(new FileReader(path))) {
                String line;
                int index = 0;
                while ((line = newReader.readLine()) != null) {
                    lines[index++] = line.trim();
                }
            }
        } catch (IOException e) {
            System.err.println("Error during file reading: " + e.getMessage());
        }

        if (lines != null) {
            analyzeTextFromFile(lines);
        } else {
            System.err.println("File not found.");
        }
    }

    public static void analyzeTextFromFile(String[] parsedText) throws InvalidDocumentException {

        String[] validDocnums = new String[parsedText.length];
        String[] validContracts = new String[parsedText.length];
        String[] invalidNumbers = new String[parsedText.length];

        int invalidIndex = 0;
        int docnumIndex = 0;
        int contractIndex = 0;

        for (String line : parsedText) {
            if (line.trim().isEmpty()) {
                continue;
            }

            try {
                if (line.length() != 15) {
                    throw new InvalidDocumentException("Invalid length: Expected 15 characters)");
                }

                if (!line.matches(Constants.REGEX_EXP)) {
                    throw new InvalidDocumentException("Contains invalid characters");
                }

                if (line.startsWith("docnum")) {
                    validDocnums[docnumIndex++] = line;
                } else if (line.startsWith("contract")) {
                    validContracts[contractIndex++] = line;
                } else {
                    throw new InvalidDocumentException("Invalid prefix: Should start with 'docnum' or 'contract'");
                }

            } catch (InvalidDocumentException e) {
                invalidNumbers[invalidIndex++] = line + " - Reason: " + e.getMessage();
            }
        }

        createReport(validDocnums, "docnums_report.txt", docnumIndex);
        createReport(validContracts, "contracts_report.txt", contractIndex);
        createReport(invalidNumbers, "invalid_numbers_report.txt", invalidIndex);
    }

    public static void createReport(String[] data, String fileName, int count) {
        File reportDir = new File(Constants.PATH_TO_REPORTS);
        if (!reportDir.exists()) {
            reportDir.mkdir();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.PATH_TO_REPORTS + fileName))) {
            for (int i = 0; i < count; i++) {
                writer.write(data[i]);
                writer.newLine();
            }
            System.out.println("Report is created: " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file " + fileName + ": " + e.getMessage());
        }
    }
}
