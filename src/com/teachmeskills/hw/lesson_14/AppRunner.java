package com.teachmeskills.hw.lesson_14;

import com.teachmeskills.hw.lesson_14.exception.InvalidDocumentException;
import com.teachmeskills.hw.lesson_14.utils.*;

import java.util.Scanner;

/**
 * Допустим есть файл с номерами документов.
 * Номером документа является строка, состоящая из букв и цифр(без служебных символов).
 * Пусть этот файл содержит каждый номер документа с новой строки и в строке никакой другой информации, только номер документа.
 * Валидный номер документа должен иметь длину 15 символов и начинаться с последовательности docnum(далее любая последовательность букв/цифр) или сontract(далее любая последовательность букв/цифр).
 * Написать программу для чтения информации из входного файла - путь к входному файлу должне задаваться через консоль.
 * Программа должна проверять номера документов на валидность.
 * Валидные номера документов docnum следует записать в один файл-отчет.
 * Валидные номера контрактов сontract следует записать в другой файл-отчет.
 * Невалидные номера документов следует записать в другой файл-отчет, но после номеров документов следует добавить ифнформацию о том, почему этот документ невалиден (неправильная последовательность символов в начале/есть служебные символы в имени документа и другое).
 */
public class AppRunner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please specify the file path: ");

        try {
            String filePath = scanner.nextLine();
            FileOperation.readTextFromFile(filePath);

        } catch (Exception e) {
            System.err.println("General error: " + e.getMessage());
        }
    }
}
