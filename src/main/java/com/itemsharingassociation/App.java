package com.itemsharingassociation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.*;
import java.util.Scanner;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Collection system = new Collection();

        try{
        Scanner fileReader = new Scanner(new File("..\\ItemSharingAssociation\\src\\main\\resources\\input-1.dat"));
        
        Member currentMember = null;
        
        while(fileReader.hasNextLine()){
            String[] lineOfData = fileReader.nextLine().split("\\|");
            if (lineOfData[0].equals("Member")){
                String name = lineOfData[1];
                String address = lineOfData[2];
                String email = lineOfData[3];
                int donatedQty = Integer.parseInt(lineOfData[4]);
                currentMember = new Member(name,address,email,donatedQty);
                // need something that stores the members - arraylist?
                
            }
            
            else if(lineOfData[0].equals("Book")){
                String title = lineOfData[1];
                String author = lineOfData[2];
                String isbn = lineOfData[3];
                String language = lineOfData[4];
                String borrowerEmail = (lineOfData.length > 5 && !lineOfData[5].isEmpty()) ? lineOfData[5] : null;
              
                if (currentMember != null){
                    system.addBook(title, author, currentMember, language, isbn);
                    
                    if (borrowerEmail != null){
                       Item book = system.getItem(title);
                    // TODO - loan
                    // this part will set a loan
                    // need to find the member via email - will need a new method
                    // and a collection of members
                    }
                }
                
                else{
                    // item belongs to a departed member (won't be in the file)
                    system.addBook(title, author, null, language, isbn);
                    if (borrowerEmail != null){
                        Item book = system.getItem(title);
                        // TODO - loan
                    }
                    
                }
            }
            else if (lineOfData[0].equals("DVD")){
                String title = lineOfData[1];
                String language = lineOfData[2];
                String director = lineOfData[3];
                String[] audioLanguages = lineOfData[4].split(",");
                String borrowerEmail = (lineOfData.length > 5 && !lineOfData[5].isEmpty()) ? lineOfData[5] : null;
                
                // to fix white spaces in file
                for (int i = 0; i < audioLanguages.length; i++)
                    audioLanguages[i] = audioLanguages[i].trim();
                
                if (currentMember != null){
                system.addDVD(title, director, currentMember, language, audioLanguages);
                
                    if (borrowerEmail != null){
                        Item dvd = system.getItem(title);
                        // TODO - loan
                    }
                }
                
                else{
                    system.addDVD(title, director, null, language, audioLanguages);
                    if (borrowerEmail != null){
                        Item dvd = system.getItem(title);
                        // TODO - loan
                    }
                
                }
                
            }
        }
        
        fileReader.close();
        } catch (FileNotFoundException e){
           System.out.println(e.toString());
        }
        
        
        // placeholder code below
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}