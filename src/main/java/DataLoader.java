
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;

// To add new data into the data file go to Other Sources/src/main/resources/input-1.dat

public class DataLoader {
    
    public static Collection system  = new Collection(); // contains every item in our project (books, dvds etc..)
    public static ArrayList<Member> allMembers = new ArrayList<Member> ();
    
    public static void loadData() {
        
        File dataFile = new File("..\\ItemSharingAssociation\\src\\main\\resources\\input-1.dat");
        
        try{
        Scanner fileReader = new Scanner(dataFile);
        
            // need to have all members loaded before you can load items so loaning works
        while (fileReader.hasNextLine()){
            String[] lineOfData = fileReader.nextLine().split("\\|");
            if (lineOfData[0].equals("Member")){
                String name = lineOfData[1];
                String address = lineOfData[2];
                String email = lineOfData[3];
                int donatedQty = Integer.parseInt(lineOfData[4]);
                Member newMember = new Member(name,address,email,donatedQty);
                allMembers.add(newMember);
            }
        }
        fileReader.close();
        } catch (FileNotFoundException e){
           System.out.println(e.toString());
        }
 
        
        // loading items in
        Member currentMember = null;
        try{
            
        Scanner fileReader = new Scanner(dataFile);
        
        while(fileReader.hasNextLine()){
            String[] lineOfData = fileReader.nextLine().split("\\|");
            if (lineOfData[0].equals("Member")){
                
                String email = lineOfData[3];
                
                currentMember = searchForMember(email,allMembers);
            }
            else if(lineOfData[0].equals("Book")){
                String title = lineOfData[1];
                String author = lineOfData[2];
                String isbn = lineOfData[3];
                String language = lineOfData[4];
                String borrowerEmail = (lineOfData.length > 5 && !lineOfData[5].isEmpty()) ? lineOfData[5] : null;
              
                if (currentMember != null){
                    system.addBook(title, author, currentMember, language, isbn);
                }
                else{
                    // item was donated by a departed member (won't be in the file)
                    system.addBook(title, author, null, language, isbn);
                }
                
                if (borrowerEmail != null){
                   Item book = system.getItem(title);
                   Member borrower = searchForMember(borrowerEmail,allMembers);
                   if (borrower != null){
                       book.loanTo(borrower);
                       borrower.decreaseBorrowingQty(1);
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
                }
                else{
                    system.addDVD(title, director, null, language, audioLanguages);
                }
                    if (borrowerEmail != null){
                        Item dvd = system.getItem(title);
                        Member borrower = searchForMember(borrowerEmail,allMembers);
                        if (borrower != null){
                           dvd.loanTo(borrower);
                           borrower.decreaseBorrowingQty(1);
                       }
                    }
            }
        }
        fileReader.close();
        } catch (FileNotFoundException e){
           System.out.println(e.toString());
        }
    }
    public static Member searchForMember(String email, ArrayList<Member> allMembers){
        for(Member member: allMembers){
            if (member.getEmail().equals(email))
                return member;
        }
        return null;
    }
    public static void saveData(String fileName){
        File saveLocation = new File("..\\ItemSharingAssociation\\src\\main\\resources\\"+fileName+".dat");
        try{
            PrintWriter writer = new PrintWriter(saveLocation);
            // need to have items that don't have a donor in the system at top
            
            for (Item item : system.getItems()){
                if (item.getDonator() == null){
                    writeItemData(writer, item);
                }
            } 
            // adds a member and all of their items donated underneath them
            for (Member member : allMembers){
                writer.println("Member" + "|" + member.getName() + "|"+ member.getAddress()
                + "|"+ member.getEmail()+"|"+member.getDonatedQty());
                
                for (Item donatorItem : member.getDonatedItems()){
                    writeItemData(writer,donatorItem);
                }
            
            }
            writer.close();
        }catch (FileNotFoundException e) {
            System.out.println(e.toString());
        }
    }
    
    // helper for writing items to file
    private static void writeItemData(PrintWriter writer, Item item){
        if(item instanceof DVD){
            DVD dvd = (DVD) item;
            String dvdData = "DVD"+"|"+dvd.getTitle()+"|"+dvd.getLanguage()
                +"|"+dvd.getDirector()+"|"+ String.join(",", dvd.getAudioLanguages());
            writer.println(dvdData);
            }
        else if (item instanceof Book){
            Book book = (Book) item;
            String bookData = "Book"+"|"+book.getTitle()+"|"+book.getAuthor()
                +"|"+book.getIsbn()+"|"+book.getLanguage();
            if (book.getLoanMember() != null){
                bookData +="|"+book.getLoanMember().getEmail();
            }
            writer.println(bookData);
            }   
    }
}
