package com.coderscampus.assignment3;

// Java imports
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class UserService {

    // instantiation of the array used to store information
    User[] database = new User[4];

    public User createUser(String email, String password, String name) {

        User user = new User();

        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        return user;
    }

    public void retrieveUser() {


        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader("src/com/coderscampus/Assignment2/data.txt"));

            String line;
            int i = 0;

            while ((line = fileReader.readLine()) != null) {

                String[] dataArray = line.split(",");
                String email = dataArray[0];
                String password = dataArray[1];
                String name = dataArray[2];

                database[i] = createUser(email, password, name);
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("There was an I/O exception");
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Closing file reader");
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void validateLogin() {

        String inputEmail;
        String inputPassword;
        int loginAttempt = 0;
        final int MAX_LOGIN_ATTEMPT = 5;
        boolean successfulLogin = false;

        Scanner userInput = new Scanner(System.in);

        while (loginAttempt < MAX_LOGIN_ATTEMPT && !successfulLogin) {

            System.out.println("Enter your email:");
            inputEmail = userInput.next();
            System.out.println("Enter your password:");
            inputPassword = userInput.next();

            for (int i = 0; i < database.length; i++) {
                if (inputEmail.equalsIgnoreCase(database[i].getEmail()) &&
                        inputPassword.equals(database[i].getPassword())) {
                    System.out.println("Welcome " + database[i].getName());
                    successfulLogin = true;
                }
            }
            if (!successfulLogin) {
                loginAttempt++;
                if (loginAttempt != MAX_LOGIN_ATTEMPT) {
                    System.out.println("Invalid login, please try again.");
                }
            }
        }
        if (loginAttempt >= MAX_LOGIN_ATTEMPT) {
            System.out.println("Too many failed login attempts, you are now locked out.");
        } userInput.close();
    }
}