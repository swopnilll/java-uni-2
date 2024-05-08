package ReadFromFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReadFromFile {
    private final String filepath;

   public ReadFromFile(String path){
       this.filepath = path;
   }

   public List<String> getFilesContentInList() throws IOException {
       return Files.readAllLines(Paths.get(this.filepath));
   }
}
