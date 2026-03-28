import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UIEngine extends Application {
    
    // Contains the different menus so they can be displayed from within other functions
    private Stage UIStage;
    private Scene itemSearchScene; 
    private Scene itemDetailsScene;
    private Scene mainMenuScene;
    
    private Button setupAddItemButton() {
        Button addItemButton = new Button();
        addItemButton.setText("Add Item");
        return addItemButton;
    }
    
    private Button setupUpdateItemButton() {
        Button updateItemButton = new Button();
        updateItemButton.setText("Update Item");
        return updateItemButton;
    }
    
    private Button setupRemoveItemButton() {
        Button removeItemButton = new Button();
        removeItemButton.setText("Remove Item");
        return removeItemButton;
    }
    
    private Button setupAddMemberButton() {
        Button addMemberButton = new Button();
        addMemberButton.setText("Add Member");
        return addMemberButton;
    }
    
    private Button setupUpdateMemberButton() {
        Button updateMemberButton = new Button();
        updateMemberButton.setText("Add Item");
        return updateMemberButton;
    }
    
    private Button setupRemoveMemberButton() {
        Button removeMemberButton = new Button();
        removeMemberButton.setText("Update Member");
        return removeMemberButton;
    }
    
    private Button setupLendItemButton() {
        Button lendItemButton = new Button();
        lendItemButton.setText("Lend Item");
        return lendItemButton;
    }
    
    private Button setupReturnItemButton() {
        Button returnItemButton = new Button();
        returnItemButton.setText("Return Item");
        return returnItemButton;
    }

    private VBox getItemOptionsMenu(Item item) {
        String loanAvailable = (item.isAvailable()) ? "Loan: Available" : ("Owned by" + item.getDonator().getName());
        
        Button backItemButton = new Button();
        backItemButton.setText("Return to menu");
        backItemButton.setOnAction((e)->{
            UIStage.setScene(itemSearchScene); // sets the current scene back to the search menu
        });
        
        VBox buttonHolder = new VBox(
            new Label(item.getTitle()),
            new Label("Language : " + item.getLanguage()),
            new Label(loanAvailable)
        );
        
        if (item instanceof Book) {
            Book book = (Book) item;
            buttonHolder.getChildren().addAll(new Label("Author : " + book.getAuthor()),new Label("ISBN : " + book.getIsbn()));
        } else {
            DVD dvd = (DVD) item;
            buttonHolder.getChildren().addAll(new Label("Director : " + dvd.getDirector()), new Label(dvd.toString()));
        }
        
        buttonHolder.getChildren().addAll(
            backItemButton,
            setupRemoveItemButton(),
            setupUpdateItemButton()
        );
        
        if (!item.isAvailable()) { // if the item is on loan 
            // if this is true then it needs more options
            buttonHolder.getChildren().add(setupLendItemButton());
            buttonHolder.getChildren().add(setupReturnItemButton());
        }
        
        return buttonHolder;
    }
    
    private void fillSearchResults(VBox searchResultsArea,String searchText) {
        ArrayList<Item> searchResults = DataLoader.system.searchItems(searchText); // system contains every item in our project (books, dvds etc..)
        searchResultsArea.getChildren().clear();
        for (Item item : searchResults) {
            Button resultButton = new Button(item.getTitle());
            resultButton.setMaxHeight(Double.MAX_VALUE);
            resultButton.setPrefWidth(Double.MAX_VALUE);
            
            resultButton.setOnAction((e) -> {
                itemDetailsScene = new Scene(new StackPane(getItemOptionsMenu(item)), 640, 480);
                UIStage.setScene(itemDetailsScene);
            });
            
            searchResultsArea.getChildren().add(resultButton);
        }
    }
    
    private VBox setupItemSearchMenu() {
        
        Label labelForSearch = new Label("Search for item");
        TextField searchField = new TextField();
        searchField.setPromptText("Enter item name");
        
        VBox searchArea = new VBox(labelForSearch,searchField);
        VBox searchResultsArea = new VBox();
        
        fillSearchResults(searchResultsArea,"");
        
        searchField.setOnKeyPressed((e) -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                fillSearchResults(searchResultsArea,searchField.getText());
            }
        });
        
        return new VBox(searchArea,searchResultsArea);
    }
    
    @Override
    public void start(Stage stage) {
        
        // SETUP MISC
        UIStage = stage;
        ArrayList<Member> members = DataLoader.loadData(); // gets a list of the members from the DataLoader.java file
        
        // SETUP OF ALL MENUS THAT CAN BE SWITCHED BETWEEN
        itemSearchScene = new Scene(new StackPane(setupItemSearchMenu()), 640, 480);
        itemDetailsScene = new Scene(new StackPane(), 640, 480);
        // SETTING THE SCENE TO THE MAIN MENU
        UIStage.setScene(itemSearchScene);
        UIStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}