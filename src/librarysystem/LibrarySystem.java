/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysystem;

import java.util.ArrayList;

/**
 *
 * @author Lietotajs
 */
public class LibrarySystem {
    private static ArrayList<Reader> readerList = new ArrayList<Reader>();
    private static ArrayList<Book> bookList = new ArrayList<Book>();
    private static ArrayList<Employee> employeeList = new ArrayList<Employee>();
    private static ArrayList<Book> bookQueForFutureCheckout;
    private static ArrayList<String> workingTimeList = new ArrayList<String>();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Book gramata1 = new Book("978-3-16-148410-0", "2005", "George R.R. Martin", "Game of Thrones", 6.7, "mint", "common");
        Book gramata2 = new Book("675-2-16-158550-0", "1990", "Hunter S. Thompson", "Fear and Loathing In Las Vegas", 10.0, "medium", "rare");
        Book gramata3 = new Book("123-2-11-166650-0", "unknown", "Various", "Holy Bible", 5.0, "low", "common");
        bookList.add(gramata1);
        bookList.add(gramata2);
        bookList.add(gramata3);
        Reader lasitajs1 = new Reader("Gatis", "Kandis", 201);
        Reader lasitajs2 = new Reader("Karina", "Rudzupuke", 202);
        Reader lasitajs3 = new Reader("Davis", "Bistrovs", 203);
        readerList.add(lasitajs1);
        readerList.add(lasitajs2);
        readerList.add(lasitajs3);
        Employee darbinieks1 = new Employee("Inese", "Upeniece", 301);
        Employee darbinieks2 = new Employee("Solvita", "Ozola", 302);
        employeeList.add(darbinieks1);
        employeeList.add(darbinieks2);
        String timeMonday = "Monday: 8:00 am - 6:00 pm";
        String timeTuesday = "Tuesday: 8:00 am - 6:00 pm";
        String timeWednesday = "Wednesday: 8:00 am - 6:00 pm";
        String timeThursday= "Thursday: 8:00 am - 6:00 pm";
        String timeFriday = "Friday: 8:00 am - 6:00 pm";
        String timeSaturtday = "Saturtday: Closed";
        String timeSunday = "Sunday: Closed";
        workingTimeList.add(timeMonday);
        workingTimeList.add(timeTuesday);
        workingTimeList.add(timeWednesday);
        workingTimeList.add(timeThursday);
        workingTimeList.add(timeFriday);
        workingTimeList.add(timeSaturtday);
        workingTimeList.add(timeSunday);
        
        giveBook(gramata1, lasitajs1);
        giveBook(gramata2, lasitajs1);
        
        takeBook(gramata1, lasitajs1);
        
        removeBook("123-2-11-166650-0");
 
        printAllBooks(bookList);
        //printAllReaders(readerList);
        //printAllEmployees(employeeList);
        //printWorkingTime(workingTimeList);
    }
    
    public static void printAllReaders(ArrayList<Reader> itais){
        System.out.println("READERS:");
        for(Reader i: readerList){
            System.out.println("NAME: "+i.getName()+", SURNAME: "+i.getSurname()+", LIBRARY USER NR.: "+i.getLibraryUserNumber()+"CURRENTLY TAKEN BOOKS: ");
            i.printCurrentTakenBookList();
        } 
    }
    
    public static void printAllBooks(ArrayList<Book> itais){
        System.out.println("BOOKS:");
        for(Book i: bookList){
            System.out.println("ISBN: "+i.getIsbn()+", YEAR: "+i.getYear()+", AUTHOR: "+i.getAuthor()+", TITLE: "+i.getTitle()+", RATING: "+i.getRating()+", CONDITION: "+i.getCondition()+" RARITY: "+i.getRarity());
        } 
    }
    
    public static void printAllEmployees(ArrayList<Employee> itais){
        System.out.println("EMPLOYEES:");
        for(Employee i: employeeList){
            System.out.println("NAME: "+i.getName()+", SURNAME: "+i.getSurname()+", ID: "+i.getEmployeeId());
        } 
    }
    
    public static void printWorkingTime(ArrayList<String> itais){
        System.out.println("LIBRARY WORKING TIME: "+workingTimeList);
    }
    
    public static void giveBook(Book bookId, Reader readerId){
        bookList.remove(bookId);
        readerId.currentTakenBookList.add(bookId);
    }
    
    public static void takeBook(Book bookId, Reader readerId){
        bookList.add(bookId);
        readerId.currentTakenBookList.remove(bookId);
    }
    
    public static void addBook(Book newName){
        if (bookList.contains(newName))
            System.out.println("The name is already taken");
        else {
            
        }
    }
    
    public static boolean removeBook(String bookISBN){
        if (bookList.isEmpty()){
            return false;
        }
        else{
            for(Book itais: bookList){
                if(bookISBN==itais.getIsbn()){
                    bookList.remove(itais);
            }
        }
            return true;
        } 
    }
        
    
}
