package com.company;

// 
// Nirja Basawa
// AudioRecording.java
// 

// create AudioRecording class from Recording class
public class AudioRecording extends Recording{
    protected final double bitrate;
    public AudioRecording(){
        super();
        bitrate = 0;
    }
    public AudioRecording(String artist, String recordingName, int durationInSeconds, double bitrate){
        super(artist, recordingName, durationInSeconds);
        this.bitrate = bitrate;
    }

    public double getBitrate() {
        return bitrate;
    }

    @Override
    public String toString(){
        return super.toString() + " [AUDIO | bitrate: " + getBitrate() + " kbps]";
    }
}
