package FilesOperation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadFromFile {
   public static List<String> getFilesContentInList(String filepath) throws IOException {
       List<String> fileContent = new ArrayList<>();

       try{
           // Check if the file exists
           if (!Files.exists(Paths.get(filepath))) {
               throw new IOException("File not found: " + filepath);
           }

           // Read all lines from the file
           fileContent = Files.readAllLines(Paths.get(filepath));

           // Check if the file is empty
           if (fileContent.isEmpty()) {
               throw new IOException("File is empty: " + filepath);
           }

       } catch (IOException e){
           // Handle specific exceptions and rethrow with a descriptive message
           throw new IOException("Error reading file: " + filepath, e);
       }

       return fileContent;
   }
}
