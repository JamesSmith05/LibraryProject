import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

// line deleter from online https://www.quora.com/How-can-I-delete-a-line-from-a-file-using-Java

public class Main {

    //login hash doesnt work

    private static File myObj = new File("Books_We_Are_In_Possession_Of.txt");
    private static File BorrowedObject = new File("Books_That_Are_Being_Borrowed.txt");
    private static File UserObject = new File("LoginSystem.txt");

    public static void main(String[] args) {
        Menu();
    }

    public static int Hashing(String password){
        int passwordHash = 7;
        try{
            for (int i = 0; i < password.length(); i++) {
                passwordHash = passwordHash * 37 + password.charAt(i);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return passwordHash;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static void Register() {
        try {
            String valid = "true";
            FileWriter myWriter = new FileWriter(UserObject.getName(), true);
            String usernameAttempt = " ";
            while (usernameAttempt.contains(" ")) {
                usernameAttempt = getInput("What would you like your username to be, bare in mind no spaces are allowed ");
            }
            Scanner myReader = new Scanner(UserObject);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] bookDetails = data.split("ʶ");
                if (bookDetails[0].equals(usernameAttempt)) {
                    valid = "false";
                }
            }
            if (valid.equals("true")) {
                String passwordAttempt = (" ");
                while (passwordAttempt.contains(" ")) {
                    passwordAttempt = getInput("What would you like your password to be, bare in mind no spaces are allowed ");
                }
                int hashedPassword = Hashing(passwordAttempt);
                myWriter.write(usernameAttempt + "ʶ" + hashedPassword +"\n");
                System.out.println("Successfully registered");
                Menu();
            }
            else {
                System.out.println("Registering failed, user already exists");
                Menu();
            }
            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void Menu() {
        try {
            String choice = getInput("Would you like to login or register? ");
            choice = choice.toLowerCase();
            if (choice.equals("login")){
                login();
            }
            else if (choice.equals("register")){
                Register();
            }
            else{
                Menu();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void login(){
        try{
            String usernameFound = "";
            String passwordFound = "";
            String username = getInput("What is your username? ");
            String password = getInput("What is your password? ");
            int hashedPassword =  Hashing(password);

            Scanner myReader = new Scanner(UserObject);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] bookDetails = data.split("ʶ");
                if (bookDetails[0].equals(username)) {
                   usernameFound = "found";
                }
            }
            while (myReader.hasNextLine()){
                String data = myReader.nextLine();
                String[] bookDetails = data.split(",,");

                if (bookDetails[1].equals(Integer.toString(hashedPassword))) {
                    passwordFound = "found";
                }
            }
            if (usernameFound.equals("found") && passwordFound.equals("found")){
                HomePage();
            }
            else{
                System.out.println("username or password incorrect, returning to home screen... ");
                Menu();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void HomePage() {
        boolean continU = true;
        while (continU) {
            try {
                String temp = getInput("What would you like to do? \n1.Add a book/books \n2.List all books \n3.Search for a book title \n4.Search for an ISBN \n5.Search for an Author \n6.Quit");
                temp = temp.toLowerCase();
                if (temp.contains("1")  || temp.contains("add")) {
                    AddBooks();
                } else if (temp.contains("2") ||  temp.contains("list")) {
                    ListBooks();
                } else if (temp.contains("3") ||  temp.contains("title")) {
                    SearchTitle();
                } else if (temp.contains("4") ||  temp.contains("isbn")) {
                    SearchISBN();
                } else if (temp.contains("5") || temp.contains("author")) {
                    SearchAuthor();
                } else if (temp.contains("6") || temp.contains("quit")) {
                    continU = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void AddBooks() {
        try {
            FileWriter myWriter = new FileWriter(myObj.getName(), true); //True means append to file contents, False means overwrite
            int count = Integer.parseInt(getInput("How many books are you adding? "));
            for (int i = 0; i < count; i++) {
                myWriter.write(getBookDetails() + "\n");// Overwrites everything in the file
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String getInput(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.println(prompt);
        return input.nextLine();
    }

    public static String getBookDetails() {
        String bookTitle = getInput("Enter book title");
        String ISBN = getInput("Enter ISBN");
        String authorName = getInput("Enter Author");
        String genre = getInput("Enter genre");
        return (bookTitle + ",," + ISBN + ",," + authorName + ",," + genre);

    }

    public static void ListBooks() {
        try {
            Scanner myReader = new Scanner(myObj);
            System.out.println("These are all the books stored: ");
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] bookDetails = data.split(",,");
                for (int j = 0; j <= 3; j++) {
                    System.out.print(bookDetails[j] + " - ");
                }
                System.out.println("");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void SearchAuthor() {
        ArrayList<String> books = new ArrayList<>();
        String author = getInput("What Author are you searching for? ");
        int count = 0;
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] bookDetails = data.split(",,");
                if (bookDetails[2].contains(author)) {
                    books.add(data);
                    count++;
                }
            }
            myReader.close();
            if (count == 1) {
                System.out.println("There is 1 book written by " + author + " :");
                BookPrint(count, books);
                System.out.println("");
            } else if (count > 1) {
                System.out.println("There are " + count + " books written by " + author + ", they are as follows: ");
                BookPrint(count, books);
            } else {
                System.out.println("There are no Books written by " + author);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void SearchISBN() {
        ArrayList<String> books = new ArrayList<>();
        String isbnSearch = getInput("What ISBN are you searching for? ");
        int count = 0;
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] bookDetails = data.split(",,");
                if (bookDetails[1].contains(isbnSearch)) {
                    books.add(data);
                    count++;
                }
            }
            myReader.close();
            if (count == 1) {
                System.out.println("There is 1 book with + " + isbnSearch + " in its ISBN: ");
                BookPrint(count, books);
                System.out.println("");
            } else if (count > 0) {
                System.out.println("There are " + count + " books containing " + isbnSearch + " in their ISBN, they are as follows: ");
                BookPrint(count, books);
            } else {
                System.out.println("There are no Books with the ISBN " + isbnSearch);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void SearchTitle() {
        ArrayList<String> books = new ArrayList<>();
        String bookTitle = getInput("What book are you searching for? ");
        int count = 0;
        try {
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] bookDetails = data.split(",,");
                if (bookDetails[0].contains(bookTitle)) {
                    books.add(data);
                    count++;
                }
            }
            myReader.close();
            if (count == 1) {
                System.out.println("There is 1 containing " + bookTitle + " :");
                BookPrint(count, books);
                System.out.println("");
            } else if (count > 1) {
                System.out.println("There are " + count + " books containing " + bookTitle + ", they are as follows: ");
                BookPrint(count, books);
            } else {
                System.out.println("There are no books containing " + bookTitle);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void BookPrint(int count, ArrayList books) {
        for (int i = 0; i < count; i++) {
            String temp = (String) books.get(i);
            String[] bookDetails = temp.split(",,");
            for (int j = 0; j <= 3; j++) {
                System.out.print(bookDetails[j] + " - ");
            }
            System.out.println("");
        }
    }

    public static void BorrowABook() throws IOException {
        Scanner input = new Scanner(System.in);
        Scanner myReader = new Scanner(myObj);
        System.out.println("What is the ISBN of the book like to borrow?");
        String isbnSearch = input.nextLine();
        FileWriter myWriter = new FileWriter(BorrowedObject.getName(), true);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] bookDetails = data.split(",,");
            if (bookDetails[1].contains(isbnSearch)) {
                myWriter.write(data + "\n");
                LineDeleter(data);
            }
        }
    }

    public static void LineDeleter(String lineToRemove) throws IOException {
        File tempFile = new File("myTempFile.txt");
        BufferedReader reader = new BufferedReader(new FileReader(myObj));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if (trimmedLine.equals(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        boolean successful = tempFile.renameTo(myObj);
    }
}


