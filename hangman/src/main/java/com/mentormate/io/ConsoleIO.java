package com.mentormate.io;

import java.util.Scanner;

public class ConsoleIO implements IO {
    private final Scanner scanner;

    public ConsoleIO() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }

    @Override
    public void write(String str) {
        System.out.println(str);
    }


    @Override
    public void close() throws Exception {
        scanner.close();
    }
}
