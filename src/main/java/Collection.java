
import java.util.ArrayList;

public class Collection {
    private ArrayList<Item> items;

    public Collection() {
        this.items = new ArrayList<>();
    }

    public void addBook(String title, String author, Member donator, String language, String isbn) {
        Book book = new Book(title, language, donator, author, isbn);
        items.add(book);
    }

    public void addDVD(String title, String director, Member donator, String language, String[] audioLanguages) {
        DVD dvd = new DVD(title, director, donator, language, audioLanguages);
        items.add(dvd);
    }

    public ArrayList<Item> searchItems(String searchTerm) {
        ArrayList<Item> results = new ArrayList<>();
        for (Item item : items) {
            if (item.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                results.add(item);
            }
        }
        return results;
    }

    public Item getItem(String title) {
        for (Item item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                return item;
            }
        }
        return null;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }
}