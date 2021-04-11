package com.basejava.webapp;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PrintDirectory {
    static Path parent;

    public static void printDirectoryDeeply(File dir) {
        File[] files = dir.listFiles();
        List<File> javaFiles = new ArrayList<>();
        List<File> directories = new ArrayList<>();

        for (File value : Objects.requireNonNull(files)) {
            if (value.isFile()) {
                javaFiles.add(value);
            } else if (value.isDirectory()) {
                directories.add(value);
            }
        }
        int count = dir.toPath().getNameCount() - parent.getNameCount();
        System.out.println("\t".repeat(count) + "\uF030 " + dir.getName());
        javaFiles.forEach(file -> System.out.println("\t".repeat(count) + "\t\uF032 " + file.getName()));
        directories.forEach(PrintDirectory::printDirectoryDeeply); //directories.forEach(file -> printDirectoryDeeply(file));
    }

    public static void main(String[] args) throws IOException {
        File dir = new File("./src/com"); //File dir = new File("./src/com/basejava/webapp");
        parent = Paths.get(dir.toString());
        printDirectoryDeeply(dir);
        printDir(dir);
    }

    //for fun
    public static void printDir(File dir) throws IOException {
        Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs) {
                int count = path.getNameCount() - parent.getNameCount();
                System.out.println("\t".repeat(count) + "\uF030 " + path.getFileName());
                File[] files = path.toFile().listFiles();
                for (File value : Objects.requireNonNull(files)) {
                    if (value.isFile()) {
                        System.out.println("\t".repeat(count) + "\t\uF032 " + value.getName());
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}

