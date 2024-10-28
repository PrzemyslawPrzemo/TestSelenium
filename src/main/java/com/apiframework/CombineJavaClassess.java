package com.apiframework;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CombineJavaClassess {

    public static void main(String[] args) {
        String projectPath = "C:\\myProjects\\SelProject";
        String outputFile = "combined_java_classes3.txt";

        try {
            combineJavaClasses(projectPath, outputFile);
            System.out.println("Kod ze wszystkich klas Java został połączony w pliku " + outputFile);
        } catch (IOException e) {
            System.err.println("Wystąpił błąd: " + e.getMessage());
        }
    }

    private static void combineJavaClasses(String projectPath, String outputFile) throws IOException {
        List<Path> javaFiles = findJavaClasses(projectPath);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile))) {
            for (Path javaFile : javaFiles) {
                List<String> lines = Files.readAllLines(javaFile);
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
                writer.write("\n" + "=".repeat(50) + "\n\n");
            }
        }
    }

    private static List<Path> findJavaClasses(String projectPath) throws IOException {
        List<Path> javaFiles = new ArrayList<>();
        Files.walk(Paths.get(projectPath))
                .filter(path -> path.toString().endsWith(".java") || path.toString().endsWith(".feature"))
                .forEach(javaFiles::add);
        return javaFiles;
    }
}

