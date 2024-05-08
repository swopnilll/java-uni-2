import Entities.Person;
import FilesOperation.WriteToFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PhoneBookOperation {

//    public static String getCommandVerb(String instructions){
//
//
//        return instructions.split(" ")[0].trim();
//    }

//    public static String getCommandInstruction(String instructions){
//        return instructions.split(" ")[1].trim();
//    }

//    public static void operateCommands(String instructions, List<Person> phoneBookDatabase, List<Person> queriesList){
//        String commandVerb;
//        String command;
//
//        if(getCommandVerb(instructions).equalsIgnoreCase("Add")
//                || getCommandVerb(instructions).equalsIgnoreCase("Delete")){
//
//            int indexOfSpaceAfterCommandVerb = instructions.equalsIgnoreCase("save")
//                    ? 0
//                    :  instructions.trim().indexOf(' ');
//
//            commandVerb = instructions.equalsIgnoreCase("save")
//                    ? instructions
//                    : instructions.substring(0, indexOfSpaceAfterCommandVerb).trim();
//
//            command =  commandVerb.equalsIgnoreCase("save")
//                    ? ""
//                    : instructions.substring(indexOfSpaceAfterCommandVerb).trim();
//        }else{
//            commandVerb = getCommandVerb(instructions);
//
//            command = getCommandInstruction(instructions);
//        }
//
//        switch (commandVerb) {
//            case "Add" :
//                System.out.println("Add Command Called");
//                addPhoneBook(command, phoneBookDatabase);
//                break;
//            case "delete" :
//                System.out.println("Delete Command Called");
//                deletePhoneBookEntry(phoneBookDatabase, command);
//                break;
//            case "query" :
//                System.out.println("Query Command Called");
//                queryEntries(queriesList, command);
//                break;
//            case "save" :
//                System.out.println("Save Command Called");
//                break;
//            default :
//                System.out.println("Invalid Command");
//        }
//
//    }

    public static String getCommandVerb(String instructions) {
        return instructions.split(" ")[0].trim().toLowerCase();
    }

    public static String getCommandInstruction(String instructions) {
        String[] parts = instructions.split(" ", 2);

        return parts.length > 1 ? parts[1].trim() : "";
    }

    public static void operateCommands(String instructions, List<Person> phoneBookDatabase, List<Person> queriesList) throws IOException {
        String commandVerb = getCommandVerb(instructions);
        String command = getCommandInstruction(instructions);

        switch (commandVerb) {
            case "add":
                addPhoneBook(command, phoneBookDatabase);
                break;
            case "delete":
                deletePhoneBookEntry(phoneBookDatabase, command);
                break;
            case "query":
                System.out.println("Query Command Called");
                queryEntries(phoneBookDatabase, command);
                break;
            case "save":
                System.out.println("Save Command Called");
                break;
            default:
                System.out.println("Invalid Command");
        }
    }


    public static Boolean validateIfEntryExists(Person person, List<Person> phoneBookDatabase){
       return phoneBookDatabase.stream().anyMatch(item ->
               item.getName().equals(person.getName()) && item.getBirthday().equals(person.getBirthday())
                       );
    }

    public static void updateExistingEntry(Person person, List<Person> phoneBookDatabase){
       Person existingPerson = phoneBookDatabase.stream().filter(entry ->
            entry.getName().equals(person.getName()) && entry.getBirthday().equals(person.getBirthday())
        ).toList().get(0);

       existingPerson.setAddress(person.getAddress());
       existingPerson.setPhone(person.getPhone());
    }

    public static void addPhoneBook(String newEntryDetails, List<Person> phoneBookDatabase){
        String[] keyValue = newEntryDetails.split(";");

        Person person = new Person();

        for(String item: keyValue){
            String trimmedItem = item.trim();

            String fieldName = trimmedItem.substring(0, trimmedItem.indexOf(' '));

            String value = trimmedItem.substring(trimmedItem.indexOf(' ')).trim();

            switch (fieldName) {
                case "name":
                    person.setName(value);
                    break;
                case "birthday":
                    person.setBirthday(value);
                    break;
                case "phone":
                    person.setPhone(value);
                    break;
                case "address":
                    person.setAddress(value);
                    break;
            }

        }

        if(validateIfEntryExists(person, phoneBookDatabase)){
            System.out.println("Entry Already exists for: ");
            updateExistingEntry(person, phoneBookDatabase);
        }else{
            phoneBookDatabase.add(person);
        }

    }

    public static void deletePhoneBookEntry(List<Person> phoneBookDatabase, String detail){
        String[] keyValue = detail.split(";");

        if(keyValue.length == 2){
            String name = keyValue[0].trim();
            String birthday = keyValue[1].trim();

            phoneBookDatabase
                    .removeIf(item -> item.getName().equalsIgnoreCase(name.trim()) && item.getBirthday().equalsIgnoreCase(birthday.trim()));
        }else{
            phoneBookDatabase.removeIf(item -> item.getName().equalsIgnoreCase(detail.trim()));
        }

    }

    public static void queryEntries(List<Person> phoneBookDatabase, String command) throws IOException {
        System.out.println("Command =" +command);

        String field = getCommandVerb(command);
        String value = getCommandInstruction(command);

        List<Person> queryOutcome = new ArrayList<>();

        String queryResult;

        switch (field){
            case "name":
                queryOutcome = phoneBookDatabase
                        .stream().
                        filter(
                                result -> result.getName().
                                        equalsIgnoreCase(value)).collect(Collectors.toList());
                break;

            case "birthday":
                queryOutcome = phoneBookDatabase
                        .stream().
                        filter(
                                result -> result.getBirthday().
                                        equalsIgnoreCase(value)).collect(Collectors.toList());
                break;

            case "phone":
                queryOutcome = phoneBookDatabase
                        .stream().
                        filter(
                                result -> result.getPhone().
                                        equalsIgnoreCase(value)).collect(Collectors.toList());
                break;

            default:
                queryOutcome = null;
        }

        System.out.println("query outcome");

        if(queryOutcome != null){
            queryOutcome.stream().forEach(item -> System.out.println(item));
        }

        if(queryOutcome != null){
            WriteToFile.writePersonsToFile("result.txt", queryOutcome);
        }

    }

    public static void saveEntries(List<Person> phoneBookDatabase, List<Person> queriesList){

    }

}
