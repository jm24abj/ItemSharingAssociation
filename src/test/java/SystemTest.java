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
    }   
    @Test
    public void searchForItem(){

    // tests item searching method in collection
    DataLoader.loadData();
    Item item = DataLoader.system.getItem("The Shining");
    assertEquals("The Shining",item.getTitle());
    }
    
    @Test
    public void searchItemDonator(){
    DataLoader.loadData();
    Item item1 = DataLoader.system.getItem("The Shining");
    assertNull(item1.getDonator());
    
    Item item2 = DataLoader.system.getItem("Война и миръ");
    assertEquals("Andrey Bolotov",item2.getDonator());
    }
    // TODO - other tests
}
