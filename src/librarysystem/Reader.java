/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysystem;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Lietotajs
 */
public class Reader {
    private String name;
    private String surname;
    public int libraryUserNumber;
    public ArrayList<Book> currentTakenBookList;

    public Reader() {
    }
    
    public Reader(String newName, String newSurname, int newLibraryUserNumber) {
        this.name = newName;
        this.surname = newSurname;
        this.libraryUserNumber = newLibraryUserNumber;
        this.currentTakenBookList = new ArrayList<Book>();
    }
            
    public Reader(String newName, String newSurname, int newLibraryUserNumber, ArrayList<Book> newCurrentTakenBookList) {
        this.name = newName;
        this.surname = newSurname;
        this.libraryUserNumber = newLibraryUserNumber;
        this.currentTakenBookList = newCurrentTakenBookList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getLibraryUserNumber() {
        return libraryUserNumber;
    }

    public void setLibraryUserNumber(int libraryUserNumber) {
        this.libraryUserNumber = libraryUserNumber;
    }

    public void printCurrentTakenBookList() { 
        for (Book itais : currentTakenBookList) {
            System.out.println(itais.getTitle()+" by "+itais.getAuthor());
        } 
    }
    
    private void addToCurrentTakenBookList(Book bookId){
        currentTakenBookList.add(bookId);
    }
    
    private void deleteFromCurrentTakenBookList(Book bookId){
        currentTakenBookList.remove(bookId);
    }
}
