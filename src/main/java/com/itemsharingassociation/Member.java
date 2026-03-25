package com.itemsharingassociation;
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

    /**
     * Returns the maximum number of items this member can borrow.
     * Min of 5 or number of items donated.
     */
    public int borrowingQty() {
        return Math.min(5, donatedQty);
    }

    /**
     * Adds a donated item to this member's donated list and increments donated count.
     */
    public void addDonation(Item item) {
        donatedItems.add(item);
        donatedQty++;
    }

    /**
     * Records that this member has borrowed an item.
     * Returns false if the member has already reached their borrowing limit.
     */
    public boolean lend(Item item) {
        if (borrowing.size() >= borrowingQty()) {
            return false;
        }
        borrowing.add(item);
        return true;
    }

    /**
     * Returns an item — removes it from the borrowing list.
     */
    public void returnItem(Item item) {
        borrowing.remove(item);
    }

    @Override
    public String toString() {
        return "";
    }
}