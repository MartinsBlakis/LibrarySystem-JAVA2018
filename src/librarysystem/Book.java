/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysystem;

/**
 *
 * @author Lietotajs
 */
public class Book {
    public String isbn;
    private int year;
    private String author;
    private String title;
    private double rating;
    private String condition;
    private String rarity;

    public Book() {}
    public Book(String isbn, int year, String author, String title, double rating, String condition, String rarity) {
        this.isbn = isbn;
        this.year = year;
        this.author = author;
        this.title = title;
        this.rating = rating;
        this.condition = condition;
        this.rarity = rarity;
    }
    public String getIsbn() {
        return isbn;
    }

    public  void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

}
