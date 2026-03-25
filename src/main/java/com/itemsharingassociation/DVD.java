/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.itemsharingassociation;



/**
 *
 * @author br191
 */
public class DVD {
    private String director;
    private String[] audioLanguages;  
    
    public DVD(String title, String director, Member donatedBy, String language, String[] audioLanguages){
    this.director = director; 
    this.audioLanguages = audioLanguages;
    
    }
    
    public String getDirector() {
        return director;
        
    }
    
    public void setDirector(String director) {
        this.director = director;
        
    }
    
    public void setAudioLanguages(String[] languages) {
        this.audioLanguages = languages;
      
    }
    
    @Override 
    public String toString() {
        return "DVD{" +
                "director='" + director + '\'' +
                ", audioLanguages=" + java.util.Arrays.toString(audioLanguages) +
                '}';
    }
    
}
