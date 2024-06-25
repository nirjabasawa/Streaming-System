package com.company;

// 
// Nirja Basawa
// Playlist.java
//

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

// playlist implements the abstract method from Playable
public class Playlist implements Playable{
    private String name;
    protected int numberOfRecordings = 0;
    protected int durationInSeconds = 0;
    protected ArrayList<Recording> recordingList;

    // Non-parametrized constructor
    Playlist(){
        this.name = "Unknown";
        this.recordingList = new ArrayList<Recording>();
    }

    // Parametrized constructor
    Playlist(String name){
        // Check if arguments are valid...
        if(name != null){
            // ...if yes, use them to set attribute values
            this.name = name;
            this.recordingList = new ArrayList<Recording>();
        } else {
            // ...if not, fall back on non-parametrized constructor behavior
            this.name = "Unknown";
            this.recordingList = new ArrayList<Recording>();
        }
    }

    // Setters
    public void setName(String name){
        if (name != null){
            this.name = name;
        }
    }

    // Getters
    public int getNumberOfRecordings(){
        return this.numberOfRecordings;
    }

    public String getName(){
        return this.name;
    }

    public int getDuration(){
        return this.durationInSeconds;
    }

    // methods that add new recordings

    // add recording using newRecording
    public boolean add(Recording newRecording){
        // Make sure we can add this recording first (not null)
        if (newRecording != null){
            for (Recording recording : recordingList) {
                if (newRecording.getName().equals(recording.getName())
                        && newRecording.getArtist().equals(recording.getArtist())) {
                    System.out.println("The recording " + newRecording + " already exists in the playlist.");
                    return false;
                }
            }
            // ...if we can, add it...
            this.recordingList.add(newRecording);
            // ...increment the number of recordings...
            this.numberOfRecordings++;
            // ...and add its duration to total playlist duration
            this.durationInSeconds = this.durationInSeconds + newRecording.getDuration();
            // everything worked - return false
            return true;
        } else {
            // ...if we cannot - return false
            return false;
        }
    }
    // add recording based on File fileName
    public boolean add(File fileName){
        if (fileName != null) {
            try {
                Scanner fileScanner = new Scanner(fileName);
                System.out.println("Processing playlist file " + fileName + ":");
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();

                    if (line != null) {
                        String[] lineAsArray = line.split(",");

                        if (lineAsArray != null && lineAsArray.length == 5) {
                            if (lineAsArray[0].equals("A") || lineAsArray[0].equals("V")) {
                                String name = lineAsArray[1];
                                String artist = lineAsArray[2];
                                for(int i = 0; i < recordingList.size(); i++){
                                    if(name.equals(recordingList.get(i).getName())
                                            && artist.equals(recordingList.get(i).getArtist())){
                                        System.out.println("The recording " + recordingList
                                                .get(i) + " already exists in the playlist.");
                                        return false;
                                    }
                                }
                                try {
                                    int durationInSeconds = Integer.parseInt(lineAsArray[3]);
                                    double rate = Double.parseDouble(lineAsArray[4]);

                                    if (lineAsArray[0].equals("A")) {
                                        AudioRecording newRecording = new AudioRecording(artist, name, durationInSeconds, rate);
                                        this.add(newRecording);
                                    }
                                    if (lineAsArray[0].equals("V")) {
                                        VideoRecording newRecording = new VideoRecording(artist, name, durationInSeconds, rate);
                                        this.add(newRecording);
                                    }
                                    return true;

                                } catch (NumberFormatException nfe) {
                                    System.out.println("ERROR: Number format exception. Recording rejected (" + line + ").");
                                }
                            } else {
                                System.out.println("ERROR: Unknown recording type data (" + line + ").");
                            }

                        } else {
                            System.out.println("ERROR: Inconsistent or no data read (" + line + ").");
                        }
                    } else {
                        System.out.println("ERROR: Empty line read from a file");
                    }
                }
                fileScanner.close();
            } catch (FileNotFoundException error) {
                System.out.println("ERROR: File " + fileName + " not found");
            }
        } else {
            System.out.println("ERROR: Invalid file provided.");
        }
        return false;
    }
    public boolean add(ArrayList<Recording> otherPlaylist){
        if(otherPlaylist != null){
            for(int i = 0; i < otherPlaylist.size(); i++){
                if(otherPlaylist.get(i).getName().equals(recordingList.get(i).getName())
                        && otherPlaylist.get(i).getArtist().equals(recordingList.get(i).getArtist())){
                    System.out.println("The recording " + otherPlaylist.get(i).getName() + " already exists in the playlist.");
                }
                else{
                    recordingList.add(otherPlaylist.get(i));
                    return true;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    // remove individual recordings
    public void remove(int index) {
        if (index <= getNumberOfRecordings()) {
            recordingList.remove(index);
        } else {
            System.out.println("Index out of range.");
        }
    }
    public void remove(String name) {
        for (int index = 0; index < this.numberOfRecordings; index++){
            if(recordingList.get(index).getName().equals(name)) {
                recordingList.remove(index);
            }
        }
    }

    // play entire playlist
    public void play() throws Unplayable{
        // Check if the playlist is empty...
        if (this.numberOfRecordings > 0) {
            // ...if not, iterate over all array objects ...
            for (int index = 0; index < this.numberOfRecordings; index++){
                // ... and invoke their play method
                recordingList.get(index).play();
                recordingList.get(index).numOfPlays++;
            }
            System.out.println();
        } else {
            // ...if empty, display this error message
            throw new Unplayable("Unplayable playlist");
        }
    }

    // shuffle entire playlist once
    public void shuffle(int numberOfRecordingsToPlay) throws Unplayable{
        // Check if the playlist is empty...
        if (this.numberOfRecordings > 0 && numberOfRecordingsToPlay > 0) {
            // Set up "already played" counter
            int recordingsPlayedCounter = 0;

            // Set up the random number generator object
            Random randomNumberGenerator = new Random();

            // ...if not, randomly choose numberOfRecordings...
            while (recordingsPlayedCounter < numberOfRecordingsToPlay){
                // ... and invoke their play method
                recordingList.get(randomNumberGenerator.nextInt(this.numberOfRecordings)).play();
                recordingsPlayedCounter++;
            }
            System.out.println();
        } else {
            // ...if empty, display this error message
            System.out.println("ERROR: Empty playlist.");
        }
    }

    // loads playlist from passed file
    public void load(String fileName){
        if (fileName != null){
            try {
                File playlistFile = new File(fileName);

                Scanner fileScanner = new Scanner(playlistFile);

                System.out.println("Processing playlist file " + playlistFile + ":");

                while (fileScanner.hasNextLine()){
                    String line = fileScanner.nextLine();

                    if (line != null){
                        String [] lineAsArray = line.split(",");

                        if (lineAsArray != null && lineAsArray.length == 5) {
                            if (lineAsArray[0].equals("A") || lineAsArray[0].equals("V")) {
                                String name = lineAsArray[1];
                                String artist = lineAsArray[2];
                                try {
                                    int durationInSeconds = Integer.parseInt(lineAsArray[3]);
                                    double rate = Double.parseDouble(lineAsArray[4]);

                                    if (lineAsArray[0].equals("A")){
                                        AudioRecording newRecording = new AudioRecording(artist, name, durationInSeconds, rate);
                                        this.add(newRecording);
                                    }
                                    if (lineAsArray[0].equals("V")){
                                        VideoRecording newRecording = new VideoRecording(artist, name, durationInSeconds, rate);
                                        this.add(newRecording);
                                    }

                                } catch (NumberFormatException nfe){
                                    System.out.println("ERROR: Number format exception. Recording rejected (" + line + ").");
                                }
                            } else {
                                System.out.println("ERROR: Unknown recording type data (" + line + ").");
                            }

                        } else {
                            System.out.println("ERROR: Inconsistent or no data read (" + line + ").");
                        }
                    } else {
                        System.out.println("ERROR: Empty line read from a file");
                    }
                }
                fileScanner.close();
            } catch (FileNotFoundException fnfe) {
                System.out.println("ERROR: File " + fileName + " not found!");
            }
        } else {
            System.out.println("ERROR: No file name provided.");
        }
    }

    // toString method
    public String toString(){
        String returnString = "Playlist: " + this.name + " [" + ((int) Math.floor(this.durationInSeconds/60)) + "m" + (this.durationInSeconds % 60) + "s]:\n";
        if (this.numberOfRecordings > 0) {
            for (int index = 0; index < this.numberOfRecordings; index++){
                returnString = returnString + recordingList.get(index).toString() + "\n";
            }
        }
        return returnString;
    }
}

