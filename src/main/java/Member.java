
/**
 *
 * @author j
 */
import java.util.ArrayList;

public class Member {

    private String name;
    private String address;
    private String email;
    private int donatedQty;
    private ArrayList<Item> borrowing;
    private ArrayList<Item> donatedItems;

    public Member(String name, String address, String email, int donatedQty) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.donatedQty = donatedQty;
        this.borrowing = new ArrayList<>();
        this.donatedItems = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public int getDonatedQty() {
        return donatedQty;
    }

    public ArrayList<Item> getDonatedItems() {
        return donatedItems;
    }

    public ArrayList<Item> getLoanItems() {
        return borrowing;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int borrowingQty() {
        return borrowing.size();
    }
    
    public void decreaseBorrowingQty(int amount) { // used duing file loading to make sure qty is correct
        donatedQty = Math.max(0,donatedQty - amount);
    }
    
    public void addDonation(Item item) {
        donatedItems.add(item);
        donatedQty++;
    }
    
    public boolean canBorrow() {
        return borrowing.size() < Math.min(5, donatedQty);
    }

    public boolean lend(Item item) {
        if (!canBorrow()) {
            System.out.println("cant borrow");
            return false;
        }
        borrowing.add(item);
        return true;
    }

    public void returnItem(Item item) {
        borrowing.remove(item);
    }
    
    public String getBorrowingItems() {
        String borrowingItemsString = "";
        for (Item item : getLoanItems()) {
            borrowingItemsString += item.getTitle() + " | ";
        } 
        return borrowingItemsString;
    }

    @Override
    public String toString() { // returns a list of all the items theyre borrowing for the UI 
        return getName();
    }
}