package FilesOperation;

import Entities.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class WriteToFile {

    public static void writePersonsToFile(String filepath, List<Person> persons) throws IOException {
        // Convert each Person object to its string representation and append to the file
        for (Person person : persons) {
            String personString = person.toString(); // or use JSON library to convert to JSON format
            appendToFile(filepath, personString);
        }
    }

    private static void appendToFile(String filepath, String content) throws IOException {
        try {
            // Append the content to the specified file
            Files.write(Paths.get(filepath), (content + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            // Handle specific exceptions and rethrow with a descriptive message
            throw new IOException("Error appending to file: " + filepath, e);
        }
    }
}
