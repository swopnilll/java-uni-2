import ReadFromFile.ReadFromFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
       var phoneBookPath = "phonebookfile.txt";
       var instructionsPath = "instructionfile.txt";

       var phoneBookFileList = new ReadFromFile(phoneBookPath).getFilesContentInList();

        ArrayList<Person> phoneBookDatabase = new ArrayList<>();

       for(String item : phoneBookFileList){
           Person person = new Person(item.split(";")[0], item.split(";")[1], item.split(";")[2], item.split(";")[3]);
           phoneBookDatabase.add(person);
       }

       var instructions = new ReadFromFile(instructionsPath).getFilesContentInList();

       for(String item : instructions){
           PhoneBookOperation.operateCommands(item, phoneBookDatabase);
       }

        for(Person item: phoneBookDatabase){
            System.out.println("final Entry");
            System.out.println(item.toString());
        }

    }
}