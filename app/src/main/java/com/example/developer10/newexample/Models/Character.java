package com.example.developer10.newexample.Models;

public class Character {

    private String character, quote, image;

    public Character(String character, String quote, String image) {
        this.character = character;
        this.quote = quote;
        this.image = image;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
