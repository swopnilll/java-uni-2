import Entities.Person;
import FilesOperation.ReadFromFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
       var phoneBookPath = "phonebookfile.txt";
       var instructionsPath = "instructionfile.txt";

        List<Person> phoneBookDatabase = new ArrayList<>();

        List<Person> queriesList = new ArrayList<>();

        try {
            var phoneBookFileList = ReadFromFile.getFilesContentInList(phoneBookPath);

            for(String item : phoneBookFileList){
                String[] parts = item.split(";");

                // Validate that the line has the expected number of parts
                if (parts.length < 4) {
                    System.err.println("Invalid data format in phone book file: " + item);
                    System.err.println("Please check the phone book file and try again.");
                    System.exit(1); // Terminate the application with an error status
                }

                // Trim leading and trailing whitespace from each part
                String name = parts[0].trim();
                String birthday = parts[1].trim();
                String phone = parts[2].trim();
                String address = parts[3].trim();

                // Create a Person object and add it to the phoneBookDatabase
                Person person = new Person(name, birthday, phone, address);
                phoneBookDatabase.add(person);
            }

            // Process phone book and instructions lists here...
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(); // Optionally, print the stack trace for debugging
        }

        try{
            var instructions = ReadFromFile.getFilesContentInList(instructionsPath);

            for(String item : instructions){
                if(!item.isEmpty()) {

                    PhoneBookOperation.operateCommands(item, phoneBookDatabase, queriesList);
                }
            }
        } catch(IOException e){
            System.err.println("Error reading instructions file: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }



        System.out.println("---------------");

        for(Person item: phoneBookDatabase){
            System.out.println("final Entry");
            System.out.println(item.toString());
        }

    }
}