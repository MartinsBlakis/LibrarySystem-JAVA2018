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


public class LibraryDepartment {

    //private String specialization;

    public String book_isbn; //Checked-out-isbn
    public int employee_employeeId;
    public int reader_libraryUserNumber;
    private String checkOutDate; //checkoutdate
    private String returnDate; //returndate 
    public int checkOutId;
    
    public LibraryDepartment() {}
    public LibraryDepartment(String book_isbn, int employee_employeeId, int reader_libraryUserNumber, String checkOutDate, String returnDate, int checkOutId) {
        this.book_isbn = book_isbn;
        this.employee_employeeId = employee_employeeId;
        this.reader_libraryUserNumber = reader_libraryUserNumber;
        this.checkOutDate = checkOutDate;
        this.returnDate = returnDate;
        this.checkOutId = checkOutId;
    }
    public String getBook_isbn() {
        return book_isbn;
    }
    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }
    public int getEmployee_employeeId() {
        return employee_employeeId;
    }
    public void setEmployee_employeeId(int employee_employeeId) {
        this.employee_employeeId = employee_employeeId;
    }
    public int getReader_libraryUserNumber() {
        return reader_libraryUserNumber;
    }
    public void setReader_libraryUserNumber(int reader_libraryUserNumber) {
        this.reader_libraryUserNumber = reader_libraryUserNumber;
    }
    public String getCheckOutDate() {
        return checkOutDate;
    }
    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    public String getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    public int getCheckOutId() {
        return checkOutId;
    }
    public void setCheckOutId(int checkOutId) {
        this.checkOutId = checkOutId;
    }
    
}
