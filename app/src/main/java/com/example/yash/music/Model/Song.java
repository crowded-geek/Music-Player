package com.example.yash.music.Model;

import java.io.Serializable;

public class Song implements Serializable {

    private String songName,songArtist,songUrl;

    public Song(String songName, String songArtist, String songUrl) {
        this.songName = songName;
        this.songArtist = songArtist;
        this.songUrl = songUrl;
    }

    public Song() {
    }

    public String getSongName() {
        return songName;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongUrl() {
        return songUrl;
    }
}
