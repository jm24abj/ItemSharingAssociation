/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author j
 */

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SystemTest {
    
    @BeforeEach
    public void setup(){
        DataHandler.resetFieldsForTest();
        DataHandler.loadData();
    }   
    @Test
    public void searchItem(){
        
    // tests item searching method in collection
    Item item = DataHandler.system.getItem("The Shining");
    assertEquals("The Shining",item.getTitle());
    }
    
    @Test
    public void searchItemDonator(){
    Item item1 = DataHandler.system.getItem("The Shining");
    assertNull(item1.getDonator());
    
    Item item2 = DataHandler.system.getItem("Война и миръ");
    assertEquals("Andrey Bolotov",item2.getDonator());
    }
    
    @Test
    public void removeItem(){
    int numOfEntries = DataHandler.system.getItems().size();
    DataHandler.system.removeItem(DataHandler.system.getItem("The Shining"));
    int newNumOfEntries = DataHandler.system.getItems().size();
    assertNotEquals(numOfEntries, newNumOfEntries);
    assertEquals(newNumOfEntries,newNumOfEntries -1);
    }
    
    @Test
    public void addDvd(){
    String[] audioLanguages = {"English","Italian"};
    DataHandler.system.addDVD("The Godfather", "Francis Ford Coppola", null,"English",audioLanguages);
    assertNotNull(DataHandler.system.getItem("The Godfather"));
    }
    
    @Test
    public void addBook(){
    DataHandler.system.addBook("The Great Gatsby","F. Scott Fitzgerald", null, "English", "9781853260414");
    assertNotNull(DataHandler.system.getItem("The Great Gatsby"));
    
    Book newBook = (Book) DataHandler.system.getItem("The Great Gatsby");
    assertEquals("F. Scott Fitzgerald",newBook.getAuthor());
    }
    
    @Test
    public void searchMember(){
    assertEquals("John Wong",DataHandler.searchForMember("wong.j@aol.com"));
    }
    
    @Test
    public void changeBookAuthor(){
    Book targetBook = (Book) DataHandler.system.getItem("The Shining");
    
    String oldAuthor = targetBook.getAuthor();
    targetBook.setAuthor("John Doe");
    
    String newAuthor = targetBook.getAuthor();
    assertNotEquals(oldAuthor,newAuthor);
    }
    
    @Test
    public void changeDVDDirector(){
    DVD targetDVD = (DVD) DataHandler.system.getItem("精武門");
    
    String oldDirector = targetDVD.getDirector();
    targetDVD.setDirector("Jane Doe");
    
    String newDirector = targetDVD.getDirector();
    assertNotEquals(oldDirector,newDirector);
    }
    
    @Test
    public void checkIsbn(){
        Book targetBook = (Book) DataHandler.system.getItem("The Shining");
        assertEquals("9780345806789",targetBook.getIsbn());
    }
    
    @Test
    public void changeIsbn(){
       Book targetBook = (Book) DataHandler.system.getItem("The Shining");
       String originalIsbn = targetBook.getIsbn();
       targetBook.setIsbn("235435345345");
       assertNotEquals(originalIsbn,targetBook.getIsbn());
    }
    @Test
    public void setLoan(){
        Book targetBook = (Book) DataHandler.system.getItem("Le Symbole Perdu");
        Member borrower = DataHandler.searchForMember("a.smith@yahoo.com");
        int oldNumberOfLoans = borrower.getLoanItems().size();
        targetBook.loanTo(borrower);
        assertNotEquals(oldNumberOfLoans, borrower.getLoanItems().size());
        assertEquals(borrower, targetBook.getLoanMember());
    }
    
    @Test
    public void clearDonator(){
        Book targetBook = (Book) DataHandler.system.getItem("Le Symbole Perdu");
        targetBook.clearDonator();
        assertNull(targetBook.getDonator());
    }
}
