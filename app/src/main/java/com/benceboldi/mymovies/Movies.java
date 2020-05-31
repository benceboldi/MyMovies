package com.benceboldi.mymovies;

public class Movies {

    private int id;
    private String title;
    private String language;
    private int duration;
    private String imdb;
    private String director;
    private String genre;
    private String description;
    private String img;

    public Movies() {
    }

    public Movies(int id, String title, String language, int duration, String imdb, String director, String genre, String description, String img) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.duration = duration;
        this.imdb = imdb;
        this.director = director;
        this.genre = genre;
        this.description = description;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public int getDuration() {
        return duration;
    }

    public String getImdb() {
        return imdb;
    }

    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public String getImg() {
        return img;
    }
}
