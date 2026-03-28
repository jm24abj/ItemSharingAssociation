/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */




/**
 *
 * @author br191
 */
public class DVD extends Item{
    private String director;
    private String[] audioLanguages;  
    
    public DVD(String title, String director, Member donatedBy, String language, String[] audioLanguages){
        super(title, language, donatedBy);
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
        return "";
    }
    
}
