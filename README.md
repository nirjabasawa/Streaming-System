# Streaming-System
Basic streaming system source code, interactive user interface and options, written in Java.

Uses object-oriented programming -- abstraction, modularization, inheritance.

Files - 
- StreamingSystem.java -- displays user menu and user options
- StreamingSystemApp.java -- calls StreamingSystem to be started by the user (main)
- Playable.java -- interface for abstract method play() to be called by User object
- Unplayable.java -- exception class for invalid Recording object
- Recording.java -- abstract class implementing Playable, creates object to hold recordings stored in Playlist object
- Playlist.java -- creates Playlist object to hold multiple Recording objects, allows user options to interact with Playlist
- AudioRecording.java -- child class of Recording.java, imitates features of audio only recording (bitrate instance)
- VideoRecording.java -- child class of Recording.java, imitates features of video only recording (framerate instance)
- User.java -- creates User object which can interact with the Playlist object and Recording object, used to interact with interface/menu of StreamingSystem.java
