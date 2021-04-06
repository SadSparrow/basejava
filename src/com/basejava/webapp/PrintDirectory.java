package com.basejava.webapp;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class PrintDirectory {
    public static void printDir(File dir) {
        String[] list = dir.list();
        int i;
        for(i = 0; i < Objects.requireNonNull(list).length; i++) {
            File f1 = new File(dir + File.separator + list[i]);
            if(f1.isFile()) {
                System.out.println("каталог: " + dir.getAbsolutePath());
                System.out.println("файл: " + list[i]);
            } else {
                printDir(new File(dir + File.separator + list[i]));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File dir = new File("./src/com/basejava/webapp");
        printDir(dir);
    }
}
