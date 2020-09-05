package com.example.hiplayer.Model;

public class MusicFiles {

    private String path;
    private String title;
    private String album;
    private String artist;

    public MusicFiles() {
    }

    public MusicFiles(String path, String title, String album, String artist) {
        this.path = path;
        this.title = title;
        this.album = album;
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

}
