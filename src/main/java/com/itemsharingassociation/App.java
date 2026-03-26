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
        while(fileReader.hasNextLine()){
            String rowOfData [] = fileReader.nextLine().split("|");
            if (rowOfData[0].equals("Member")){
                String name = rowOfData[1];
                String address = rowOfData[2];
                String email = rowOfData[3];
                int donatedQty = Integer.parseInt(rowOfData[4]);
                // need something that stores the members - arraylist?
            }
            else if(rowOfData[0].equals("Book")){
        
            }
            else if (rowOfData[0].equals("DVD")){
            
            }
        }
        
        fileReader.close();
        } catch (FileNotFoundException e){
           System.out.println(e.toString());
        }
        
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