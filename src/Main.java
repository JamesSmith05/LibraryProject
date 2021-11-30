import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//Very good job although myObj isn't an amazing name

// need to add a split system
// not sure where to write
// have set up an input and a book info grabber
// i believe the reader works but might need to tweak it

public class Main {

    private static File myObj = new File("Books_We_Are_In_Possession_Of.txt");
    private static ArrayList<String> books = new ArrayList<>();

    public static void main(String[] args) {
        WriteToFile();
        ReadFile();
    }

    public static void WriteToFile() {
        try {
            FileWriter myWriter = new FileWriter(myObj.getName(), false); //True means append to file contents, False means overwrite
            int count = Integer.parseInt(getInput("How many books are you adding? "));
            for (int i = 0; i < count; i++) {
                //books.add(getBookDetails());
                myWriter.write(getBookDetails()); // Overwrites everything in the file
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static String getInput(String prompt){
        Scanner input = new Scanner(System.in);
        System.out.println(prompt);
        return input.nextLine();
    }
    public static String getBookDetails(){
        String bookTitle = getInput("Enter book title");
        String ISBN = getInput("Enter ISBN");
        String authorName = getInput("Enter Author");
        String genre = getInput("Enter genre");
        return (bookTitle + ",," + ISBN + ",," + authorName + ",," + genre);
    }
    public static void ReadFile() {
        try {
            Scanner myReader = new Scanner(myObj);
            System.out.println("This is the contents of the file:");
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
                }
            myReader.close();
            }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
