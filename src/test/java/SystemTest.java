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
        DataLoader.resetFieldsForTest();
        DataLoader.loadData();
    }   
    @Test
    public void searchItem(){

    // tests item searching method in collection
    Item item = DataLoader.system.getItem("The Shining");
    assertEquals("The Shining",item.getTitle());
    }
    
    @Test
    public void searchItemDonator(){
    Item item1 = DataLoader.system.getItem("The Shining");
    assertNull(item1.getDonator());
    
    Item item2 = DataLoader.system.getItem("Война и миръ");
    assertEquals("Andrey Bolotov",item2.getDonator());
    }
    
    @Test
    public void removeItem(){
    int numOfEntries = DataLoader.system.getItems().size();
    DataLoader.system.removeItem(DataLoader.system.getItem("The Shining"));
    int newNumOfEntries = DataLoader.system.getItems().size();
    assertNotEquals(numOfEntries, newNumOfEntries);
    assertEquals(newNumOfEntries,newNumOfEntries -1);
    }
    
    @Test
    public void addDvd(){
    String[] audioLanguages = {"English","Italian"};
    DataLoader.system.addDVD("The Godfather", "Francis Ford Coppola", null,"English",audioLanguages);
    assertNotNull(DataLoader.system.getItem("The Godfather"));
    }
    
    @Test
    public void addBook(){
    DataLoader.system.addBook("The Great Gatsby","F. Scott Fitzgerald", null, "English", "9781853260414");
    assertNotNull(DataLoader.system.getItem("The Great Gatsby"));
    
    Book newBook = (Book) DataLoader.system.getItem("The Great Gatsby");
    assertEquals("F. Scott Fitzgerald",newBook.getAuthor());
    }
    
    @Test
    public void searchMember(){
    assertEquals("John Wong",DataLoader.searchForMember("wong.j@aol.com"));
    }
    
    @Test
    public void changeBookAuthor(){
    Book targetBook = (Book) DataLoader.system.getItem("The Shining");
    
    String oldAuthor = targetBook.getAuthor();
    targetBook.setAuthor("John Doe");
    
    String newAuthor = targetBook.getAuthor();
    assertNotEquals(oldAuthor,newAuthor);
    }
    
    @Test
    public void changeDVDDirector(){
    DVD targetDVD = (DVD) DataLoader.system.getItem("精武門");
    
    String oldDirector = targetDVD.getDirector();
    targetDVD.setDirector("Jane Doe");
    
    String newDirector = targetDVD.getDirector();
    assertNotEquals(oldDirector,newDirector);
    }
    
    // TODO - maybe a few more related to file handling
}
