package com.company;

// 
// Nirja Basawa
// StreamingSystem.java
//

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

// create class to be called by StreamingSystemApp
public class StreamingSystem {
    private ArrayList<User> userDatabase;

    public StreamingSystem(){
        userDatabase = new ArrayList<User>();
    }

    // start()
    // display user interface and provide selectable options for user
    public void start() throws Unplayable {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Main menu:\n"
                + "[1] Add new user\n"
                + "[2] Remove user\n"
                + "[3] List all users\n"
                + "[4] User menu\n"
                + "[5] Exit\n"
                + "What is your choice? ");
        
        int choice = userInput.nextInt();
        switch(choice) {
            case 1: //create user to add to database
                User newUser = new User();
                userDatabase.add(newUser);
                break;
            //NEXT
            case 2: //remove user
                removeUser();
                break;
            //NEXT
            case 3://list all users
                System.out.println(userStats());
                break;
            //NEXT
            case 4://user menu
                userMenu();
                break;
            //NEXT
            case 5://exit so basically nothing
                return;
        }
        start();
    }

    // removeUser()
    // delete user by user choice
    public void removeUser() throws Unplayable {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Remove user menu:\n"
                + "[1] Remove user by ID\n"
                + "[2] Remove user by name\n"
                + "[3] Go back to previous menu\n"
                + "What is your choice? ");
        int choice2 = userInput.nextInt();
        switch(choice2) {
            case 1:
                System.out.print("Please enter the ID: ");
                int ID = userInput.nextInt();
                for(int i=0; i<userDatabase.size(); i++) {
                    if(userDatabase.get(i).getUserID()==ID) {
                        userDatabase.remove(i);
                    }
                }
                break;
            case 2:
                System.out.print("Please enter the username: ");
                String name = userInput.next();
                for (int i = 0; i < userDatabase.size(); i++){
                    if(userDatabase.get(i).getUsername().equals(name)) {
                        userDatabase.remove(i);
                    }
                }
                break;
            case 3:
                start();
                break;
        }
    }

    // userStats()
    // display user statistics
    public String userStats() {
        String stats = "";
        for (int index = 0; index < userDatabase.size(); index++){
            stats += userDatabase.get(index).toString() + "\n";
        }
        return stats;
    }

    // userMenu()
    // display interactable menu for user to edit playlist
    public void userMenu() throws Unplayable {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Which user would you like to edit? Enter the user ID.");
        int currentUserIndex = userInput.nextInt();
        System.out.println("[1] Add recording\r\n"
                + "[2] Add playlist from file\r\n"
                + "[3] Add playlist from another user\r\n"
                + "[4] Remove recording from playlist\r\n"
                + "[5] Play individual recording\r\n"
                + "[6] Play entire playlist in order ONCE\r\n"
                + "[7] Play entire playlist shuffled ONCE\r\n"
                + "[8] Save playlist to a file\r\n"
                + "[9] Display playlist stats\r\n"
                + "[10] Go back to previous menu\r\n"
                + "What is your choice? ");
        int choice = userInput.nextInt();
        switch(choice) {
            case 1: //add recording
                userDatabase.get(currentUserIndex).add();
                break;
            case 2: //add playlist from file
                System.out.print("Please enter the file name: ");
                String fileName = userInput.next();
                File playlistFile = new File(fileName);
                userDatabase.get(currentUserIndex).add(playlistFile);
                break;
            case 3://add playlist from another user
                System.out.println("Which user would you like to add from? Enter the user index.");
                int addFromIndex = userInput.nextInt();
                userDatabase.get(currentUserIndex).add(userDatabase.get(addFromIndex));
                break;
            case 4://remove recording from playlist
                removeRecording(userDatabase.get(currentUserIndex));
                break;
            case 5://play individual recording
                playRecording(userDatabase.get(currentUserIndex));
                break;
            case 6://play entire playlist in order
                userDatabase.get(currentUserIndex).play();
                break;
            case 7://play entire playlist shuffled
                userDatabase.get(currentUserIndex).getMyPlaylist().shuffle(userDatabase.get(currentUserIndex).getMyPlaylist().recordingList.size());
                break;
            case 8://save playlist to file
                System.out.println("Enter the name for the file:");
                String newFile = userInput.next();
                userDatabase.get(currentUserIndex).save(newFile);
                break;
            case 9://display playlist stats
                System.out.println(userDatabase.get(currentUserIndex).getMyPlaylist().toString());
                break;
            case 10://go back
                start();
                break;
        }

    }

    // removeRecording()
    // removes recording from playlist based on index/name
    public void removeRecording(User currentUser) throws Unplayable {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Remove recording from playlist menu:\r\n"
                + "[1] Remove recording by id\r\n"
                + "[2] Remove recording by name\r\n"
                + "[3] Go back to previous menu\r\n"
                + "What is your choice?");
        int choice4 = userInput.nextInt();
        switch(choice4) {
            case 1:
                System.out.println("Enter the index of the recording.");
                int index = userInput.nextInt();
                currentUser.remove(index);
                break;
            case 2:
                System.out.println("Enter the name of the recording.");
                String name = userInput.next();
                currentUser.remove(name);
                break;
            case 3:
                userMenu();
                break;
        }
    }

    // playRecording()
    // plays recording for currentUser based on index/name
    public void playRecording(User currentUser) throws Unplayable {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Play individual recording menu:\r\n"
                + "[1] Play recording by id\r\n"
                + "[2] Play recording by name\r\n"
                + "[3] Go back to previous menu\r\n"
                + "What is your choice?");
        int choice5 = userInput.nextInt();
        switch(choice5) {
            case 1:
                System.out.println("Enter the index of the recording.");
                int index = userInput.nextInt();
                currentUser.play(index);
                break;
            case 2:
                System.out.println("Enter the name of the recording.");
                String name = userInput.next();
                currentUser.play(name);
                break;
            case 3:
                userMenu();
                break;
        }
    }
}
