package com.company;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;


public class User implements Playable{
    private final String username;
    private int userID;
    private Playlist myPlaylist;
    private static int count = 0;

    public User(){
        username = addName();
        userID = count;
        count++;
        myPlaylist = new Playlist();
    }

    public String addName(){
        Scanner read = new Scanner(System.in);
        System.out.print("Please enter a username: ");
        return read.nextLine();
    }

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }

    public Playlist getMyPlaylist() {
        return myPlaylist;
    }

    public void remove(String recordingName) {
        myPlaylist.remove(recordingName);
    }
    public void remove(int index) {
        myPlaylist.remove(index);
    }

    public void add(){
        Scanner userInput = new Scanner(System.in);
        System.out.print("Please enter the artist name: ");
        String artistName = userInput.next();
        System.out.print("Please enter the recording name: ");
        String recordingName = userInput.next();
        for(int i = 0; i < myPlaylist.recordingList.size(); i++) {
            if (recordingName.equals(myPlaylist.recordingList.get(i).getName())
                    && artistName.equals(myPlaylist.recordingList.get(i).getArtist())) {
                System.out.println("The recording " + myPlaylist.recordingList.get(i).getName() + " already exists in the playlist.");
                return;
            }
        }
        System.out.print("Please enter the duration of the recording (in seconds): ");
        int durationInSeconds = userInput.nextInt();
        System.out.print("Audio or Video Recording? Please enter either 'audio' or 'video' for recording type: ");
        String recordingType = userInput.next().toUpperCase();
        if(recordingType.equals("VIDEO")){
            System.out.print("Please enter the framerate (decimal): ");
            int frameRate = userInput.nextInt();
            myPlaylist.add(new VideoRecording(artistName, recordingName, durationInSeconds, frameRate));
        } else if(recordingType.equals("AUDIO")){
            System.out.print("Please enter the bitrate (int value): ");
            int bitrate = userInput.nextInt();
            myPlaylist.add(new AudioRecording(artistName, recordingName, durationInSeconds, bitrate));
        } else{
            System.out.println("Please enter either 'audio' or 'video' for recording type");
        }
    }


    public void add(File fileName){
        myPlaylist.add(fileName);
    }
    public void add(User anotherUser){
        int playlistSize = anotherUser.myPlaylist.numberOfRecordings;
        for(int i = 0; i < playlistSize; i++) {
            if(anotherUser.myPlaylist.recordingList.get(i).getName().equals(myPlaylist.recordingList.get(i).getName())
                    && anotherUser.myPlaylist.recordingList.get(i).getArtist().equals(myPlaylist.recordingList.get(i).getArtist())){
                System.out.println("The recording + " + anotherUser.myPlaylist.recordingList.get(i).getName() +
                        "already exists in the playlist.");
            }
            else
                myPlaylist.recordingList.add(anotherUser.myPlaylist.recordingList.get(i));
        }
    }

    public String toString(){
        return "Username: " + username + "\nUser ID: " + userID;
    }


    public void play(int index) throws Unplayable{
        try{
            if(index >= 0){
                System.out.println("Now playing: " + myPlaylist.recordingList.get(index).toString());
                myPlaylist.recordingList.get(index).numOfPlays++;
            }
            else{
                throw new Unplayable("ERROR: Cannot play this recording.");
            }
        }catch(InputMismatchException ime){
            System.out.println("Please enter an numerical index location");
        }
    }
    public void play(String name) throws Unplayable{
        try{
            if(name != null){
                for(int i = 0; i < myPlaylist.recordingList.size(); i++){
                    if(myPlaylist.recordingList.get(i).recordingName.equals(name)){
                        System.out.println("Now playing: " + myPlaylist.recordingList.get(i).toString());
                        myPlaylist.recordingList.get(i).numOfPlays++;
                    }
                }
            }
            else{
                throw new Unplayable("ERROR: Cannot play this recording.");
            }
        }catch(InputMismatchException ime){
            System.out.println("Please enter an numerical index location");
        }
    }
    public void play() throws Unplayable{
            if (myPlaylist.durationInSeconds > 0){
                System.out.println("Now playing: " + myPlaylist);
                for(int i = 0; i < myPlaylist.numberOfRecordings; i++){
                    myPlaylist.recordingList.get(i).numOfPlays++;
                }
            } else {
                throw new Unplayable("ERROR: Cannot play this playlist.");
            }
    }

    public void save(String fileName) {
        try {
            File myObj = new File("filename.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter("filename.txt");
            for (int i=0; i<myPlaylist.getNumberOfRecordings(); i++) {
                myWriter.write(myPlaylist.toString() + "\n");
            }
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }


}

