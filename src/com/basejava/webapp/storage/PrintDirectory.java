package com.basejava.webapp.storage;

import java.io.File;
import java.util.Objects;

public class PrintDirectory {
    public static void printDir(File dir) {
        String[] list = dir.list();
        int i;
        for(i = 0; i < Objects.requireNonNull(list).length; i++) {
            File f1 = new File(dir + File.separator + list[i]);
            if(f1.isFile()) {
                //System.out.println(dir + File.separator + list[i]);
                System.out.println(list[i]);
            } else {
                printDir(new File(dir + File.separator + list[i]));
            }
        }
    }

    public static void main(String[] args) {
        File dir = new File("./src/com/basejava/webapp");
        printDir(dir);
    }
}
