
package com.example.excelutility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class ExcelUtility {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        String comment = """
                /*******************************************************
                 *                                                     *
                 *                   Read Excel File                   *
                 *                                                     *
                 * Description:                                        *
                 * This script reads an Excel file and extracts data   *
                 * from specified sheets. It then processes the sheet  *
                 * data, removes specified content, and writes the     *
                 * cleaned data to an output text file.                *
                 *                                                     *
                 * Author: Yelati Sachin                               *
                 * Copilit GenAI Team                                  *
                 *                                                     *
                 *******************************************************/
                """;

        System.out.println(comment);

        // Automatically detect the base path
        String basePath = System.getProperty("user.home");

        // Ask the user for the Excel file name
        System.out.print("Please Enter The Requirement Excel File Path: ");
        String fileName = scanner.nextLine().replaceAll("^\"|\"$", "");

        // Get and print all sheet names
        List<String> sheetNames = getSheetNames(fileName);
        System.out.println("The Excel file contains the following sheets: " + String.join(", ", sheetNames));

        // Ask the user to enter sheet names separated by commas
        System.out.print("Please enter the sheet names separated by commas: ");
        String sheetInput = scanner.nextLine();
        List<String> sheetNameArray = Arrays.stream(sheetInput.split(",")).map(String::trim)
                .collect(Collectors.toList());

        // Ask the user to enter headers for each sheet separated by commas
        System.out.print(
                "Enter 'Response', 'Request', 'BusinessLogic', or any other value for default header (press Enter to skip): ");
        String headerInput = scanner.nextLine();
        List<String> headerArray = Arrays.stream(headerInput.split(",")).map(String::trim).collect(Collectors.toList());

        // Apply default header if user presses Enter to skip
        if (headerInput.trim().isEmpty()) {
            headerArray = Collections.nCopies(sheetNameArray.size(), "");
        }

        if (sheetNameArray.size() != headerArray.size()) {
            System.err.println("The number of sheet names and headers must be the same.");
            return;
        }

        for (int i = 0; i < sheetNameArray.size(); i++) {
            String sheetName = sheetNameArray.get(i).split(" ")[0];
            String headerType = headerArray.get(i);

            if (!sheetNames.contains(sheetName)) {
                System.err.println("Sheet \"" + sheetName + "\" not found in the Excel file.");
                return;
            }

            Workbook workbook;
            try {
                workbook = new XSSFWorkbook(new FileInputStream(fileName));
            } catch (IOException e) {
                System.err.println("Error reading the Excel file: " + e.getMessage());
                return;
            }

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                System.err.println("Sheet \"" + sheetName + "\" not found in the Excel file.");
                return;
            }

            // Convert the sheet to text format
            List<List<String>> sheetData = new ArrayList<>();
            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    rowData.add(cell.toString());
                }
                sheetData.add(rowData);
            }
            String textData = sheetData.stream().map(row -> String.join("\t", row)).collect(Collectors.joining("\n"));

            // Default content to remove
            List<String> defaultContentToRemove = Arrays.asList(
                    "Set value", "Application/json", "UTF-8", "GUID形式", "X\t{社員コード}",
                    "{MetLifeのオペレーター ID}", "{お客様の顧客番号}", "{利用システム}", "https:", "Accept", "URL Sample");

            // Ask the user if they want to remove all Japanese text
            System.out.print("Do you want to remove all Japanese text from sheet \"" + sheetName + "\"? (yes/no): ");
            String removeJapaneseTextAnswer = scanner.nextLine();
            if (removeJapaneseTextAnswer.equalsIgnoreCase("yes")) {
                textData = textData.replaceAll(
                        "[\\u3000-\\u303F\\u3040-\\u309F\\u30A0-\\u30FF\\uFF00-\\uFFEF\\u4E00-\\u9FAF\\u3400-\\u4DBF]",
                        "");
            }

            // Remove URLs starting with "https://"
            textData = textData.replaceAll("https://[^\\s]+", "");

            // Automatically set the answer to "no" for removing specified content
            String removeContentAnswer = "no";

            List<String> contentToRemove = new ArrayList<>();
            if (removeContentAnswer.equalsIgnoreCase("yes")) {
                System.out.println("Enter the content to remove (type 'done' when finished):");
                while (true) {
                    String content = scanner.nextLine();
                    if (content.equalsIgnoreCase("done")) {
                        break;
                    }
                    contentToRemove.add(content);
                }
            }

            // Combine default content to remove with user-specified content
            contentToRemove.addAll(defaultContentToRemove);

            // Remove specified content
            String cleanedData = textData;
            for (String content : contentToRemove) {
                cleanedData = cleanedData.replaceAll("\\t*" + Pattern.quote(content) + "\\t*\\n", "");
            }

            // Remove duplicate string values
            Set<String> uniqueLines = new LinkedHashSet<>(Arrays.asList(cleanedData.split("\n")));
            cleanedData = String.join("\n", uniqueLines);

            // Remove additional spaces
            cleanedData = cleanedData.replaceAll("\\s+", " ").trim();

            // Function to split long lines into multiple lines
            cleanedData = splitLongLines(cleanedData, 80);

            // Define the header based on user input
            String header;
            switch (headerType.toLowerCase()) {
                case "response":
                    header = """
                            Use the below template in a excel table format
                            Design document version\tSheet name\tResponse Items\tAPI Response (Expected Result)\tTest Case ID\tPass/Fail\tExecuted by\tDefect ID
                            """;
                    break;
                case "request":
                    header = """
                            Use the below template in a excel table format
                            Design document version\tSheet name\tRequest Items\tData Variations\tExpected Result\tTest Case ID\tPass/Fail\tRequest body\tTested By\tComments\tDefect ID
                             """;
                    break;
                case "businesslogic":
                    header = """
                            Analysis the below code business logic and provide the key inputs for designing the test cases
                            """;
                    break;
                default:
                    header = """
                            Use the below template in a excel table format
                            TestCase ID\tTest Description\tActual Result \tExpected Result\tPass/Fail\tTested By\tComments\tDefect ID
                            """;
                    break;
            }

            // Define the footer to be added to the output file
            String footer = "\nDesign document version: " + Paths.get(fileName).getFileName() + "\nSheet Name: "
                    + sheetName;

            // Combine all chunks into a single output
            String combinedOutput = header + cleanedData + footer + "\n\n";

            // Generate the output file name using the sheet name
            String outputFileName = "RequirementOutput_" + sheetName + ".txt";
            Path outputDir = Paths.get(basePath, "TestOutput");

            // Ensure the output directory exists, or create a fallback directory
            try {
                if (!Files.exists(outputDir)) {
                    Files.createDirectories(outputDir);
                }
            } catch (IOException e) {
                System.err.println("Error creating the initial output directory: " + e.getMessage());
                outputDir = Paths.get(basePath, "TestOutput");
                try {
                    if (!Files.exists(outputDir)) {
                        Files.createDirectories(outputDir);
                    }
                } catch (IOException ex) {
                    System.err.println("Error creating the fallback output directory: " + ex.getMessage());
                    outputDir = Paths.get(System.getProperty("java.io.tmpdir"), "TestOutput_Fallback");
                    try {
                        if (!Files.exists(outputDir)) {
                            Files.createDirectories(outputDir);
                        }
                    } catch (IOException exc) {
                        System.err.println("Error creating the temporary output directory: " + exc.getMessage());
                        return;
                    }
                }
            }

            Path outputFilePath = outputDir.resolve(outputFileName);

            // Clean up output files if count exceeds 10
            cleanUpOutputFiles(outputDir, 10);

            // Write the combined output to the file
            try {
                Files.writeString(outputFilePath, combinedOutput);
                System.out.println("Data has been written to " + outputFilePath);
            } catch (IOException e) {
                System.err.println("Error writing to " + outputFilePath + ": " + e.getMessage());
            }
        }

        // Ask the user if they want to convert the Excel file to Markdown format
        System.out.print("Do you want to convert the TestCases Existing in Excel file to Markdown format? (yes/no): ");
        String convertToMarkdownAnswer = scanner.nextLine();
        if (convertToMarkdownAnswer.equalsIgnoreCase("yes")) {
            // Ask the user for the new Excel file name
            System.out.print("Please enter the Excel file path to convert to Markdown format: ");
            String newFileName = scanner.nextLine().replaceAll("^\"|\"$", "");

            List<String> newSheetNames = getSheetNames(newFileName);
            if (newSheetNames.size() > 1) {
                System.out.println("The Excel file contains the following sheets: " + String.join(", ", newSheetNames));
                System.out.print("Please provide the sheet name to convert to Markdown format: ");
                String sheetNameForMarkdown = scanner.nextLine();
                if (!newSheetNames.contains(sheetNameForMarkdown)) {
                    System.err.println("Sheet \"" + sheetNameForMarkdown + "\" not found in the Excel file.");
                    return;
                }
                Sheet sheet = new XSSFWorkbook(new FileInputStream(newFileName)).getSheet(sheetNameForMarkdown);
                List<List<String>> sheetData = new ArrayList<>();
                for (Row row : sheet) {
                    List<String> rowData = new ArrayList<>();
                    for (Cell cell : row) {
                        rowData.add(cell.toString());
                    }
                    sheetData.add(rowData);
                }
                String markdownData = convertSheetToMarkdown(sheetData);
                String markdownFileName = "Standard_ReferenceTemplate_" + sheetNameForMarkdown + ".md";
                Path markdownOutputDir = Paths.get(basePath, "Manual_TestCase_Reference");
                if (!Files.exists(markdownOutputDir)) {
                    Files.createDirectories(markdownOutputDir);
                }
                Path markdownFilePath = markdownOutputDir.resolve(markdownFileName);
                Files.writeString(markdownFilePath, markdownData);
                System.out.println("Markdown file has been written to " + markdownFilePath);
            } else {
                Sheet sheet = new XSSFWorkbook(new FileInputStream(newFileName)).getSheet(newSheetNames.get(0));
                List<List<String>> sheetData = new ArrayList<>();
                for (Row row : sheet) {
                    List<String> rowData = new ArrayList<>();
                    for (Cell cell : row) {
                        rowData.add(cell.toString());
                    }
                    sheetData.add(rowData);
                }
                String markdownData = convertSheetToMarkdown(sheetData);
                String markdownFileName = "output_" + newSheetNames.get(0) + ".md";
                Path markdownOutputDir = Paths.get(basePath, "Manual_TestCase_Reference");
                if (!Files.exists(markdownOutputDir)) {
                    Files.createDirectories(markdownOutputDir);
                }
                Path markdownFilePath = markdownOutputDir.resolve(markdownFileName);
                Files.writeString(markdownFilePath, markdownData);
                System.out.println("Markdown file has been written to " + markdownFilePath);
            }
        }
    }

    private static List<String> getSheetNames(String fileName) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(fileName))) {
            List<String> sheetNames = new ArrayList<>();
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                sheetNames.add(workbook.getSheetName(i));
            }
            return sheetNames;
        } catch (IOException e) {
            throw new RuntimeException("Error reading the Excel file: " + e.getMessage(), e);
        }
    }

    private static void cleanUpOutputFiles(Path outputDir, int maxFiles) {
        try (Stream<Path> files = Files.list(outputDir)) {
            List<Path> sortedFiles = files
                    .map(path -> new AbstractMap.SimpleEntry<>(path, path.toFile().lastModified()))
                    .sorted(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            if (sortedFiles.size() > maxFiles) {
                List<Path> filesToDelete = sortedFiles.subList(0, sortedFiles.size() - maxFiles);
                for (Path file : filesToDelete) {
                    Files.delete(file);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error cleaning up output files: " + e.getMessage(), e);
        }
    }

    private static String splitLongLines(String text, int maxLength) {
        return Arrays.stream(text.split("\n"))
                .map(line -> {
                    if (line.length() <= maxLength)
                        return line;
                    String[] words = line.split(" ");
                    StringBuilder newLine = new StringBuilder();
                    StringBuilder result = new StringBuilder();
                    for (String word : words) {
                        if (newLine.length() + word.length() > maxLength) {
                            result.append(newLine.toString().trim()).append("\n");
                            newLine.setLength(0);
                        }
                        newLine.append(word).append(" ");
                    }
                    result.append(newLine.toString().trim());
                    return result.toString();
                })
                .collect(Collectors.joining("\n"));
    }

    private static String convertSheetToMarkdown(List<List<String>> sheetData) {
        StringBuilder markdown = new StringBuilder();
        List<String> headers = sheetData.get(0);
        List<List<String>> rows = sheetData.subList(1, sheetData.size());

        markdown.append("| ").append(String.join(" | ", headers)).append(" |\n");
        markdown.append("| ").append(headers.stream().map(h -> "---").collect(Collectors.joining(" | ")))
                .append(" |\n");

        for (List<String> row : rows) {
            markdown.append("| ").append(String.join(" | ", row)).append(" |\n");
        }

        return markdown.toString();
    }
}