package com.arindam.das.imc.common;

import java.util.Scanner;

public class CommandLineInput {
    private Scanner scanner;

    public CommandLineInput() {
        scanner = new Scanner(System.in);
    }
    public String getUserInput() {
        return scanner.next();
    }
}
