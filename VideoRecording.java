package com.company;
// class VideoRecording derived from Recording class - similar to AudioRecording, includes instance framerate
public class VideoRecording extends Recording{
    protected final double framerate;
    public VideoRecording(){
        super();
        framerate = 0;
    }
    public VideoRecording(String artist, String recordingName, int durationInSeconds, double rate){
        super(artist, recordingName, durationInSeconds);
        framerate = rate;
    }
    public double getFramerate() {
        return framerate;
    }

    @Override
    public String toString(){
        return super.toString() + " [VIDEO | framerate: " + getFramerate() + " fps]";
    }

}
