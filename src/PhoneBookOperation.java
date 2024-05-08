import java.util.ArrayList;
import java.util.stream.Collectors;

public class PhoneBookOperation {
    ArrayList<Person> newEntries = new ArrayList<>();

    public static void operateCommands(String instructions, ArrayList<Person> phoneBookDatabase){
        int indexOfSpaceAfterCommandVerb = instructions.equalsIgnoreCase("save")
                ? 0
                :  instructions.trim().indexOf(' ');

        String commandVerb = instructions.equalsIgnoreCase("save")
                ? instructions
                : instructions.substring(0, indexOfSpaceAfterCommandVerb).trim();

        String command =  commandVerb.equalsIgnoreCase("save")
                ? ""
                : instructions.substring(indexOfSpaceAfterCommandVerb).trim();

        System.out.println(command);

        switch (commandVerb) {
            case "Add" :
                System.out.println("Add Command Called");
                addPhoneBook(command, phoneBookDatabase);
                break;
            case "delete" :
                System.out.println("Delete Command Called");
                deletePhoneBookEntry(phoneBookDatabase, command);
                break;
            case "query" :
                System.out.println("Query Command Called");
                break;
            case "save" :
                System.out.println("Save Command Called");
                break;
            default :
                System.out.println("Invalid Command");
        }

    }

    public static Boolean validateIfEntryExists(Person person, ArrayList<Person> phoneBookDatabase){
       return phoneBookDatabase.stream().anyMatch(item ->
               item.getName().equals(person.getName()) && item.getBirthday().equals(person.getBirthday())
                       );
    }

    public static void updateExistingEntry(Person person, ArrayList<Person> phoneBookDatabase){
       Person existingPerson = phoneBookDatabase.stream().filter(entry ->
            entry.getName().equals(person.getName()) && entry.getBirthday().equals(person.getBirthday())
        ).toList().get(0);

       existingPerson.setAddress(person.getAddress());
       existingPerson.setPhone(person.getPhone());
    }

    public static void addPhoneBook(String newEntryDetails, ArrayList<Person> phoneBookDatabase){
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

    public static void deletePhoneBookEntry(ArrayList<Person> phoneBookDatabase, String detail){
        phoneBookDatabase.removeIf(item -> item.getName().equalsIgnoreCase(detail.trim()));
    }
}
