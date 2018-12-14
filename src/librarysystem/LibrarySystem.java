/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.sql.*;
import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Lietotajs
 */
public class LibrarySystem extends Application{
    private static ArrayList<Reader> readerList = new ArrayList<Reader>();
    private static ArrayList<Book> bookList = new ArrayList<Book>();
    private static ArrayList<Employee> employeeList = new ArrayList<Employee>();
    private static ArrayList<LibraryDepartment> checkedOutBookList = new ArrayList<LibraryDepartment>();
    private static ArrayList<WorkingTime> workingTimeList = new ArrayList<WorkingTime>();
    Button button;
    Stage window;
    Scene scene1, scene2, scene3, scene4, scene5;
    TextField isbnInput, yearInput, authorInput, titleInput, ratingInput, conditionInput,
            rarityInput, readerNameInput, readerSurnameInput, libraryUserNumberInput,
            employeeNameInput, employeeSurnameInput, employeeIdInput, weekdayInput, opensInput,
            closesInput, book_isbnInput, employee_employeeIdInput, reader_libraryUserNumberInput,
            checkOutDateInput, returnDateInput, checkOutIdInput;
    TableView<Book> observableBookListTable;
    TableView<Reader> observableReaderListTable;
    TableView<Employee> observableEmployeeListTable;
    TableView<WorkingTime> observableWorkingTimeListTable;
    TableView<LibraryDepartment> observableCheckedOutBookTable;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        launch(args);
    }
    public static Connection getConnection() throws Exception{
        try{
            //String driver = "com.mysql.jdbc.Driver"; //OUTDATED (DRIVERS ARE LOADED AUTOMATICALY NOWADAYS)
            String url = "jdbc:mysql://localhost:3306/library";
            String username = "root";
            String password = "password";
            //Class.forName(driver); //OUTDATED
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return conn;
        } catch (Exception e){System.out.println(e);}
        return null;
        
    }

    public static ArrayList<String> getBooks() throws Exception {
        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM book");
            
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Book book = new Book();
                book.setIsbn(result.getString("isbn"));
                book.setYear(result.getInt("year"));
                book.setAuthor(result.getString("author"));
                book.setTitle(result.getString("title"));
                book.setRating(result.getDouble("rating"));
                book.setCondition(result.getString("condition"));
                book.setRarity(result.getString("rarity"));
                bookList.add(book);
            }
        } catch(Exception e){System.out.println("Booklist retrieved!");}
        return null;
    }
   
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        
        window = primaryStage;
        window.setTitle("Library System");
        
        //BOOK TAB CONTENT
        //isbn column
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setMinWidth(150);
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        //year column
        TableColumn<Book, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setMinWidth(50);
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        //author column
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setMinWidth(150);
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        //title column
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(200);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        //rating column
        TableColumn<Book, Double> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setMinWidth(50);
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        //rating condition
        TableColumn<Book, String> conditionColumn = new TableColumn<>("Condition");
        conditionColumn.setMinWidth(75);
        conditionColumn.setCellValueFactory(new PropertyValueFactory<>("condition"));
        //rarity condition
        TableColumn<Book, String> rarityColumn = new TableColumn<>("Rarity");
        rarityColumn.setMinWidth(75);
        rarityColumn.setCellValueFactory(new PropertyValueFactory<>("rarity"));
        
        //isbn input
        isbnInput = new TextField();
        isbnInput.setPromptText("ISBN");
        isbnInput.setMinWidth(150);
        //year input
        yearInput = new TextField();
        yearInput.setPromptText("Year");
        yearInput.setMinWidth(50);
        //author input
        authorInput = new TextField();
        authorInput.setPromptText("Author");
        authorInput.setMinWidth(150);
        //title input
        titleInput = new TextField();
        titleInput.setPromptText("Title");
        titleInput.setMinWidth(200);
        //rating input
        ratingInput = new TextField();
        ratingInput.setPromptText("Rating");
        ratingInput.setMinWidth(50);
        //condition input
        conditionInput = new TextField();
        conditionInput.setPromptText("Condition");
        conditionInput.setMinWidth(75);
        //isbn input
        rarityInput = new TextField();
        rarityInput.setPromptText("Rarity");
        rarityInput.setMinWidth(75);
        
        //Button
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked());
        Button deleteButton = new Button("Delete");  
        deleteButton.setOnAction(e -> deleteButtonClicked());
        //HBox
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(isbnInput, yearInput, authorInput, titleInput, ratingInput, conditionInput, rarityInput, addButton, deleteButton);
        
        observableBookListTable = new TableView<>();
        observableBookListTable.setItems(getObservableBookList());
        observableBookListTable.getColumns().addAll(isbnColumn, yearColumn, authorColumn, titleColumn, ratingColumn, conditionColumn, rarityColumn);
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(observableBookListTable, hBox);
        
        //READER TAB CONTENT   
        TableColumn<Reader, String> readerNameColumn = new TableColumn<>("Name");
        readerNameColumn.setMinWidth(150);
        readerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Reader, String> readerSurnameColumn = new TableColumn<>("Surname");
        readerSurnameColumn.setMinWidth(150);
        readerSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        TableColumn<Reader, Integer> libraryUserNumberColumn = new TableColumn<>("Library user ID");
        libraryUserNumberColumn.setMinWidth(50);
        libraryUserNumberColumn.setCellValueFactory(new PropertyValueFactory<>("libraryUserNumber"));
        readerNameInput = new TextField();
        readerNameInput.setPromptText("Name");
        readerNameInput.setMinWidth(150);
        readerSurnameInput = new TextField();
        readerSurnameInput.setPromptText("Surname");
        readerSurnameInput.setMinWidth(150);
        libraryUserNumberInput = new TextField();
        libraryUserNumberInput.setPromptText("Library User ID");
        libraryUserNumberInput.setMinWidth(50);
        Button addReaderButton = new Button("Add");
        addReaderButton.setOnAction(e -> addReaderButtonClicked());
        Button deleteReaderButton = new Button("Delete");  
        deleteReaderButton.setOnAction(e -> deleteReaderButtonClicked());
        HBox hBoxReader = new HBox();
        hBoxReader.setPadding(new Insets(10,10,10,10));
        hBoxReader.setSpacing(10);
        hBoxReader.getChildren().addAll(readerNameInput, readerSurnameInput, libraryUserNumberInput, addReaderButton, deleteReaderButton);
        observableReaderListTable = new TableView<>();
        observableReaderListTable.setItems(getObservableReaderList());
        observableReaderListTable.getColumns().addAll(readerNameColumn, readerSurnameColumn, libraryUserNumberColumn);
        VBox vBoxReader = new VBox();
        vBoxReader.getChildren().addAll(observableReaderListTable, hBoxReader);
        
        //Employee TAB CONTENT   
        TableColumn<Employee, String> employeeNameColumn = new TableColumn<>("Name");
        employeeNameColumn.setMinWidth(150);
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Employee, String> employeeSurnameColumn = new TableColumn<>("Surname");
        employeeSurnameColumn.setMinWidth(150);
        employeeSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        TableColumn<Employee, Integer> employeeIdColumn = new TableColumn<>("Employee ID");
        employeeIdColumn.setMinWidth(50);
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        employeeNameInput = new TextField();
        employeeNameInput.setPromptText("Name");
        employeeNameInput.setMinWidth(150);
        employeeSurnameInput = new TextField();
        employeeSurnameInput.setPromptText("Surname");
        employeeSurnameInput.setMinWidth(150);
        employeeIdInput = new TextField();
        employeeIdInput.setPromptText("Employee ID");
        employeeIdInput.setMinWidth(50);
        Button addEmployeeButton = new Button("Add");
        addEmployeeButton.setOnAction(e -> addEmployeeButtonClicked());
        Button deleteEmployeeButton = new Button("Delete");  
        deleteEmployeeButton.setOnAction(e -> deleteEmployeeButtonClicked());
        HBox hBoxEmployee = new HBox();
        hBoxEmployee.setPadding(new Insets(10,10,10,10));
        hBoxEmployee.setSpacing(10);
        hBoxEmployee.getChildren().addAll(employeeNameInput, employeeSurnameInput, employeeIdInput, addEmployeeButton, deleteEmployeeButton);
        observableEmployeeListTable = new TableView<>();
        observableEmployeeListTable.setItems(getObservableEmployeeList());
        observableEmployeeListTable.getColumns().addAll(employeeNameColumn, employeeSurnameColumn, employeeIdColumn);
        VBox vBoxEmployee = new VBox();
        vBoxEmployee.getChildren().addAll(observableEmployeeListTable, hBoxEmployee);
        
        //working time TAB CONTENT   
        TableColumn<WorkingTime, String> weekdayColumn = new TableColumn<>("Weekday");
        weekdayColumn.setMinWidth(150);
        weekdayColumn.setCellValueFactory(new PropertyValueFactory<>("weekday"));
        TableColumn<WorkingTime, String> opensColumn = new TableColumn<>("Opens at");
        opensColumn.setMinWidth(150);
        opensColumn.setCellValueFactory(new PropertyValueFactory<>("opens"));
        TableColumn<WorkingTime, Integer> closesColumn = new TableColumn<>("Closes at");
        closesColumn.setMinWidth(50);
        closesColumn.setCellValueFactory(new PropertyValueFactory<>("closes"));
        weekdayInput = new TextField();
        weekdayInput.setPromptText("Weekday");
        weekdayInput.setMinWidth(150);
        opensInput = new TextField();
        opensInput.setPromptText("Opens at");
        opensInput.setMinWidth(150);
        closesInput = new TextField();
        closesInput.setPromptText("Closes at");
        closesInput.setMinWidth(50);
        Button addWorkingTimeButton = new Button("Add");
        addWorkingTimeButton.setOnAction(e -> addWorkingTimeButtonClicked());
        Button deleteWorkingTimeButton = new Button("Delete");  
        deleteWorkingTimeButton.setOnAction(e -> deleteWorkingTimeButtonClicked());
        HBox hBoxWorkingTime = new HBox();
        hBoxWorkingTime.setPadding(new Insets(10,10,10,10));
        hBoxWorkingTime.setSpacing(10);
        hBoxWorkingTime.getChildren().addAll(weekdayInput, opensInput, closesInput, addWorkingTimeButton, deleteWorkingTimeButton);
        observableWorkingTimeListTable = new TableView<>();
        observableWorkingTimeListTable.setItems(getObservableWorkingTimeList());
        observableWorkingTimeListTable.getColumns().addAll(weekdayColumn, opensColumn, closesColumn);
        VBox vBoxWorkingTime = new VBox();
        vBoxWorkingTime.getChildren().addAll(observableWorkingTimeListTable, hBoxWorkingTime);
        
        //checked out list TAB CONTENT   
        TableColumn<LibraryDepartment, String> book_isbnColumn = new TableColumn<>("Book ISBN");
        book_isbnColumn.setMinWidth(150);
        book_isbnColumn.setCellValueFactory(new PropertyValueFactory<>("book_isbn"));
        TableColumn<LibraryDepartment, Integer> employee_employeeIdColumn = new TableColumn<>("Employee ID");
        employee_employeeIdColumn.setMinWidth(150);
        employee_employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employee_employeeId"));
        TableColumn<LibraryDepartment, Integer> reader_libraryUserNumberColumn = new TableColumn<>("Library User ID");
        reader_libraryUserNumberColumn.setMinWidth(50);
        reader_libraryUserNumberColumn.setCellValueFactory(new PropertyValueFactory<>("reader_libraryUserNumber"));
        TableColumn<LibraryDepartment, String> checkOutDateColumn = new TableColumn<>("Check out date");
        checkOutDateColumn.setMinWidth(150);
        checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));        
        TableColumn<LibraryDepartment, String> returnDateColumn = new TableColumn<>("Return date");
        returnDateColumn.setMinWidth(150);
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        TableColumn<LibraryDepartment, Integer> checkOutIdColumn = new TableColumn<>("Check out ID");
        checkOutIdColumn.setMinWidth(50);
        checkOutIdColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutId"));
        book_isbnInput = new TextField();
        book_isbnInput.setPromptText("Book ISBN");
        book_isbnInput.setMinWidth(130);
        employee_employeeIdInput = new TextField();
        employee_employeeIdInput.setPromptText("Employee ID");
        employee_employeeIdInput.setMinWidth(130);
        reader_libraryUserNumberInput = new TextField();
        reader_libraryUserNumberInput.setPromptText("Library User Number");
        reader_libraryUserNumberInput.setMinWidth(50);
        checkOutDateInput = new TextField();
        checkOutDateInput.setPromptText("Check out date");
        checkOutDateInput.setMinWidth(150);
        returnDateInput = new TextField();
        returnDateInput.setPromptText("Return date");
        returnDateInput.setMinWidth(150);
        checkOutIdInput = new TextField();
        checkOutIdInput.setPromptText("Check out ID");
        checkOutIdInput.setMinWidth(50);
        Button addCheckOutBookButton = new Button("Add");
        addCheckOutBookButton.setOnAction(e -> addCheckOutBookButtonClicked());
        Button deleteCheckOutBookButton = new Button("Delete");  
        deleteCheckOutBookButton.setOnAction(e -> deleteCheckOutBookButtonClicked());
        HBox hBoxCheckOutBook = new HBox();
        hBoxCheckOutBook.setPadding(new Insets(10,10,10,10));
        hBoxCheckOutBook.setSpacing(10);
        hBoxCheckOutBook.getChildren().addAll(book_isbnInput, employee_employeeIdInput, reader_libraryUserNumberInput, checkOutDateInput, returnDateInput, checkOutIdInput, addCheckOutBookButton, deleteCheckOutBookButton);
        observableCheckedOutBookTable = new TableView<>();
        observableCheckedOutBookTable.setItems(getObservableCheckedOutBookList());
        observableCheckedOutBookTable.getColumns().addAll(book_isbnColumn, employee_employeeIdColumn,  reader_libraryUserNumberColumn, checkOutDateColumn, returnDateColumn, checkOutIdColumn);
        VBox vBoxLibraryDepartment = new VBox();
        vBoxLibraryDepartment.getChildren().addAll(observableCheckedOutBookTable, hBoxCheckOutBook);
        
        
        TabPane layout = new TabPane();
        layout.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        Tab tab1 = new Tab("Books");
        tab1.setContent(vBox);
        layout.getTabs().add(tab1);
        Tab tab2 = new Tab("Readers");
        tab2.setContent(vBoxReader);
        layout.getTabs().add(tab2);
        Tab tab3 = new Tab("Employees");
        tab3.setContent(vBoxEmployee);
        layout.getTabs().add(tab3);
        Tab tab4 = new Tab("Working Schedule");
        tab4.setContent(vBoxWorkingTime);
        layout.getTabs().add(tab4);
        Tab tab5 = new Tab("Books checked out");
        tab5.setContent(vBoxLibraryDepartment);
        layout.getTabs().add(tab5);
        
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
        
    }

    private boolean isInt(TextField input, String message){
        try{
            int age = Integer.parseInt(input.getText());
            System.out.println("User is: "+ age);
            return true;
        }catch(NumberFormatException e){
            System.out.println("Error: "+message+" is not a number!");
            return false;
        }
    }
    
    public ObservableList<Book> getObservableBookList(){
        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM book");
            
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Book book = new Book();
                book.setIsbn(result.getString("isbn"));
                book.setYear(result.getInt("year"));
                book.setAuthor(result.getString("author"));
                book.setTitle(result.getString("title"));
                book.setRating(result.getDouble("rating"));
                book.setCondition(result.getString("condition"));
                book.setRarity(result.getString("rarity"));
                bookList.add(book);
            }
        } catch(Exception e){System.out.println("Booklist retrieved!");}
        ObservableList<Book> observableBookList = FXCollections.observableArrayList(bookList);
        return observableBookList;
    }

    public void addButtonClicked () { //BOOK
        String isbn = isbnInput.getText(); //reads in our input values
        String author = authorInput.getText();
        String title = titleInput.getText();
        String condition = conditionInput.getText();
        String rarity = rarityInput.getText();
        int year = Integer.parseInt(yearInput.getText());
        double rating = Double.parseDouble(ratingInput.getText());
        
        Book book = new Book(); //Creates a new book
        book.setIsbn(isbn); //Sets book values
        book.setYear(year);
        book.setAuthor(author);
        book.setTitle(title);
        book.setRating(rating);
        book.setCondition(condition);
        book.setRarity(rarity);
        observableBookListTable.getItems().add(book); //Adds book to observableBookListTable
        bookList.add(book); //Adds book to bookList
        try { //Adds book to MySQL database
            Connection con = getConnection();
            PreparedStatement added = con.prepareStatement("INSERT INTO `library`.`book` (`isbn`, `year`, `author`, `title`, `rating`, `condition`, `rarity`)"
                    + " VALUES('"+isbn+"','"+year+"','"+author+"','"+title+"','"+rating+"','"+condition+"','"+rarity+"')");
            added.executeUpdate();
        } catch (Exception e){System.out.println(e);}
        finally {
            System.out.println("New book added!");
        }
        isbnInput.clear(); //Clears input values
        authorInput.clear();
        titleInput.clear();
        conditionInput.clear();
        rarityInput.clear();
        yearInput.clear();
        ratingInput.clear();
    }

    public void deleteButtonClicked() { //BOOK
        Book selectedItem = observableBookListTable.getSelectionModel().getSelectedItem(); //Dletes from MySQL database by ISBN
        String isbnForDeletion = selectedItem.getIsbn();
        try {
            Connection con = getConnection();
            PreparedStatement added = con.prepareStatement("DELETE FROM book WHERE isbn = '"+isbnForDeletion+"' LIMIT 1");
            added.executeUpdate();
            bookList.removeIf(book -> book.isbn == isbnForDeletion);
        } catch (Exception e){System.out.println(e);}
        finally {
            System.out.println(selectedItem.getTitle()+" was deleted from the LibrarySystem!");
        }
        ObservableList<Book> bookSelected, allBooks;
        allBooks = observableBookListTable.getItems();
     
        bookSelected = observableBookListTable.getSelectionModel().getSelectedItems();
        bookSelected.forEach(allBooks::remove);
        
    }
    
    public ObservableList<Reader> getObservableReaderList(){
        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM reader");
            
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Reader reader = new Reader();
                reader.setName(result.getString("name"));
                reader.setSurname(result.getString("surname"));
                reader.setLibraryUserNumber(result.getInt("libraryUserNumber"));
                readerList.add(reader);
            }
        } catch(Exception e){System.out.println("Reader list retrieved!");}
        ObservableList<Reader> observableReaderList = FXCollections.observableArrayList(readerList);
        return observableReaderList;
    }
        
    public void addReaderButtonClicked () {
        String name = readerNameInput.getText(); //reads in our input values
        String surname = readerSurnameInput.getText();
        int libraryUserNumber = Integer.parseInt(libraryUserNumberInput.getText());
        
        Reader reader = new Reader(); //Creates a new book
        reader.setName(name); //Sets book values
        reader.setSurname(surname);
        reader.setLibraryUserNumber(libraryUserNumber);


        observableReaderListTable.getItems().add(reader); //Adds book to observableBookListTable
        readerList.add(reader); //Adds book to bookList
        try { //Adds book to MySQL database
            Connection con = getConnection();
            PreparedStatement added = con.prepareStatement("INSERT INTO `library`.`reader` (`name`, `surname`, `libraryUserNumber`) VALUES ('"+name+"', '"+surname+"', '"+libraryUserNumber+"');");
            added.executeUpdate();
        } catch (Exception e){System.out.println(e);}
        finally {
            System.out.println("New reader added!");
        }
        readerNameInput.clear(); //Clears input values
        readerSurnameInput.clear();
        libraryUserNumberInput.clear();
    }

    public void deleteReaderButtonClicked() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Reader selectedItem = observableReaderListTable.getSelectionModel().getSelectedItem(); //Dletes from MySQL database by ISBN
        int libraryUserNumberForDeletion = selectedItem.getLibraryUserNumber();
        try {
            Connection con = getConnection();
            PreparedStatement added = con.prepareStatement("DELETE FROM reader WHERE libraryUserNumber = '"+libraryUserNumberForDeletion+"' LIMIT 1");
            added.executeUpdate();
            readerList.removeIf(reader -> reader.libraryUserNumber == libraryUserNumberForDeletion);
        } catch (Exception e){System.out.println(e);}
        finally {
            System.out.println(selectedItem.getName()+" "+selectedItem.getSurname()+ " was deleted from the LibrarySystem!");
        }
        ObservableList<Reader> readerSelected, allReaders;
        allReaders = observableReaderListTable.getItems();
     
        readerSelected = observableReaderListTable.getSelectionModel().getSelectedItems();
        readerSelected.forEach(allReaders::remove);
        
    }
    
    public ObservableList<Employee> getObservableEmployeeList(){
        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM employee");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                Employee employee = new Employee();
                employee.setName(result.getString("name"));
                employee.setSurname(result.getString("surname"));
                employee.setEmployeeId(result.getInt("employeeId"));
                employeeList.add(employee);
            }
        } catch(Exception e){System.out.println("Employee list retrieved!");}
        ObservableList<Employee> observableEmployeeList = FXCollections.observableArrayList(employeeList);
        return observableEmployeeList;
    }
        
    public void addEmployeeButtonClicked () {
        String name = employeeNameInput.getText(); //reads in our input values
        String surname = employeeSurnameInput.getText();
        int employeeId = Integer.parseInt(employeeIdInput.getText());
        Employee employee = new Employee(); //Creates a new book
        employee.setName(name); //Sets book values
        employee.setSurname(surname);
        employee.setEmployeeId(employeeId);
        observableEmployeeListTable.getItems().add(employee); //Adds employee to observableEmployeeTable
        employeeList.add(employee); //Adds book to bookList
        try { //Adds book to MySQL database
            Connection con = getConnection();
            PreparedStatement added = con.prepareStatement("INSERT INTO `library`.`employee` (`name`, `surname`, `employeeId`) VALUES ('"+name+"', '"+surname+"', '"+employeeId+"');");
            added.executeUpdate();
        } catch (Exception e){System.out.println(e);}
        finally {
            System.out.println("New reader added!");
        }
        employeeNameInput.clear(); //Clears input values
        employeeSurnameInput.clear();
        employeeIdInput.clear();
    }

    public void deleteEmployeeButtonClicked() {
        Employee selectedItem = observableEmployeeListTable.getSelectionModel().getSelectedItem(); //Deletes from MySQL database by id
        int employeeIdForDeletion = selectedItem.getEmployeeId();
        try {
            Connection con = getConnection();
            PreparedStatement added = con.prepareStatement("DELETE FROM employee WHERE employeeId = '"+employeeIdForDeletion+"' LIMIT 1");
            added.executeUpdate();
            employeeList.removeIf(employee -> employee.employeeId == employeeIdForDeletion);
        } catch (Exception e){System.out.println(e);}
        finally {
            System.out.println(selectedItem.getName()+" "+selectedItem.getSurname()+ " was deleted from the LibrarySystem!");
        }
        ObservableList<Employee> employeeSelected, allEmployees;
        allEmployees = observableEmployeeListTable.getItems();
        employeeSelected = observableEmployeeListTable.getSelectionModel().getSelectedItems();
        employeeSelected.forEach(allEmployees::remove);  
    }
        
    public ObservableList<WorkingTime> getObservableWorkingTimeList(){
        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM workingtime");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                WorkingTime workingtime = new WorkingTime();
                workingtime.setWeekday(result.getString("weekday"));
                workingtime.setOpens(result.getString("opens"));
                workingtime.setCloses(result.getString("closes"));
                workingTimeList.add(workingtime);
            }
        } catch(Exception e){System.out.println("Working schedule retrieved!");}
        ObservableList<WorkingTime> observableWorkingTimeList = FXCollections.observableArrayList(workingTimeList);
        return observableWorkingTimeList;
    }
        
    public void addWorkingTimeButtonClicked () {
        String weekday = weekdayInput.getText(); //reads in our input values
        String opens = opensInput.getText();
        String closes = closesInput.getText();
        WorkingTime workingtime = new WorkingTime(); //Creates a new working time
        workingtime.setWeekday(weekday); //Sets book values
        workingtime.setOpens(opens);
        workingtime.setCloses(closes);
        observableWorkingTimeListTable.getItems().add(workingtime); //Adds working time to observableallWorkingTimeTable
        workingTimeList.add(workingtime); //Adds book to WorkingTimeList
        try { //Adds book to MySQL database
            Connection con = getConnection();
            PreparedStatement added = con.prepareStatement("INSERT INTO `library`.`workingtime` (`weekday`, `opens`, `closes`) VALUES ('"+weekday+"', '"+opens+"', '"+closes+"');");
            added.executeUpdate();
        } catch (Exception e){System.out.println(e);}
        finally {
            System.out.println("New working time added!");
        }
        weekdayInput.clear(); //Clears input values
        opensInput.clear();
        closesInput.clear();
    }

    public void deleteWorkingTimeButtonClicked() {
        WorkingTime selectedItem = observableWorkingTimeListTable.getSelectionModel().getSelectedItem(); //Deletes from MySQL database by weekday
        String weekdayForDeletion = selectedItem.getWeekday();
        try {
            Connection con = getConnection();
            PreparedStatement added = con.prepareStatement("DELETE FROM workingtime WHERE weekday = '"+weekdayForDeletion+"' LIMIT 1");
            added.executeUpdate();
            workingTimeList.removeIf(workingtime -> workingtime.weekday == weekdayForDeletion);
        } catch (Exception e){System.out.println(e);}
        finally {
            System.out.println(selectedItem.getWeekday()+ " was deleted from the LibrarySystem!");
        }
        ObservableList<WorkingTime> workingTimeSelected, allWorkingTimes;
        allWorkingTimes = observableWorkingTimeListTable.getItems();
        workingTimeSelected = observableWorkingTimeListTable.getSelectionModel().getSelectedItems();
        workingTimeSelected.forEach(allWorkingTimes::remove);  
    }
    
    public ObservableList<LibraryDepartment> getObservableCheckedOutBookList(){
        try{
            Connection conn = getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM checkedout");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                LibraryDepartment checkedout = new LibraryDepartment();
                checkedout.setBook_isbn(result.getString("book_isbn"));
                checkedout.setEmployee_employeeId(result.getInt("employee_employeeId"));
                checkedout.setReader_libraryUserNumber(result.getInt("reader_libraryUserNumber"));
                checkedout.setCheckOutDate(result.getString("checkOutDate"));
                checkedout.setReturnDate(result.getString("returnDate"));
                checkedout.setCheckOutId(result.getInt("checkOutId"));
                checkedOutBookList.add(checkedout);
            }
        } catch(Exception e){System.out.println("Working schedule retrieved!");}
        ObservableList<LibraryDepartment> observableCheckedOutBookList = FXCollections.observableArrayList(checkedOutBookList);
        return observableCheckedOutBookList;
    }
        
    public void addCheckOutBookButtonClicked () {
        String book_isbn = book_isbnInput.getText(); //reads in our input values
        int employee_employeeId = Integer.parseInt(employee_employeeIdInput.getText());
        int reader_libraryUserNumber = Integer.parseInt(reader_libraryUserNumberInput.getText());
        String checkOutDate = checkOutDateInput.getText();
        String returnDate = returnDateInput.getText();
        int checkOutId = Integer.parseInt(checkOutIdInput.getText());
        
        LibraryDepartment checkedout = new LibraryDepartment(); //Creates a new working time
        checkedout.setBook_isbn(book_isbn); //Sets book values
        checkedout.setEmployee_employeeId(employee_employeeId);
        checkedout.setReader_libraryUserNumber(reader_libraryUserNumber);
        checkedout.setCheckOutDate(checkOutDate);
        checkedout.setReturnDate(returnDate);
        checkedout.setCheckOutId(checkOutId);

        observableCheckedOutBookTable.getItems().add(checkedout); //Adds working time to observableallWorkingTimeTable
        checkedOutBookList.add(checkedout); //Adds book to WorkingTimeList
        try { //Adds book to MySQL database
            Connection con = getConnection();
            PreparedStatement added = con.prepareStatement("INSERT INTO `library`.`checkedout` (`book_isbn`, `employee_employeeId`, `reader_libraryUserNumber`, `checkoutdate`, `returndate`, `checkoutId`) VALUES ('"+book_isbn+"', '"+employee_employeeId+"', '"+reader_libraryUserNumber+"', '"+checkOutDate+"', '"+returnDate+"', '"+checkOutId+"');");
            added.executeUpdate();
        } catch (Exception e){System.out.println(e);}
        finally {
            System.out.println("New check out registered added!");
        }
        book_isbnInput.clear(); //Clears input values
        employee_employeeIdInput.clear();
        reader_libraryUserNumberInput.clear();
        checkOutDateInput.clear();
        returnDateInput.clear();
        checkOutIdInput.clear();
    }

    public void deleteCheckOutBookButtonClicked() {
        LibraryDepartment selectedItem = observableCheckedOutBookTable.getSelectionModel().getSelectedItem(); //Deletes from MySQL database by weekday
        int checkOutIdForDeletion = selectedItem.getCheckOutId();
        try {
            Connection con = getConnection();
            PreparedStatement added = con.prepareStatement("DELETE FROM checkedout WHERE checkOutId = '"+checkOutIdForDeletion+"' LIMIT 1");
            added.executeUpdate();
            checkedOutBookList.removeIf(checkedout -> checkedout.checkOutId == checkOutIdForDeletion);
        } catch (Exception e){System.out.println(e);}
        finally {
            System.out.println(selectedItem.getCheckOutId()+ " was deleted from the LibrarySystem!");
        }
        ObservableList<LibraryDepartment> checkOutSelected, allCheckOuts;
        allCheckOuts = observableCheckedOutBookTable.getItems();
        checkOutSelected = observableCheckedOutBookTable.getSelectionModel().getSelectedItems();
        checkOutSelected.forEach(allCheckOuts::remove);  
    }

}
