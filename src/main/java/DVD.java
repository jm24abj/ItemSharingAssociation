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
    
    public String[] getAudioLanguages() {
        return this.audioLanguages;
    }
    
    @Override 
    public String toString() {
        String returnString = "Audio Languages: ";
        for (int i = 0; i < audioLanguages.length; i++) {
            returnString += this.audioLanguages[i] + " | ";
        }
        return returnString;
    }
    
}
