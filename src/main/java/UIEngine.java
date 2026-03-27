import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UIEngine extends Application {
    
    private Button setupAddItemButton() {
        Button addItemButton = new Button();
        addItemButton.setText("Add Items");
        return addItemButton;
    }
    
    private Button setupUpdateItemButton() {
        Button updateItemButton = new Button();
        updateItemButton.setText("Update Items");
        return updateItemButton;
    }
    
    private Button setupRemoveItemButton() {
        Button removeItemButton = new Button();
        removeItemButton.setText("Remove Items");
        return removeItemButton;
    }
    
    private Button setupAddMemberButton() {
        Button addMemberButton = new Button();
        addMemberButton.setText("Add Member");
        return addMemberButton;
    }
    
    private Button setupUpdateMemberButton() {
        Button updateMemberButton = new Button();
        updateMemberButton.setText("Add Items");
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
        VBox buttonHolder = new VBox(
            setupRemoveItemButton(),
            setupUpdateItemButton()
        );
        
        if (item.isAvailable()) { // if the item is on loan 
            
            // if this is true then it needs more options
            // should say full name of member who has the item
            
            buttonHolder.getChildren().add(setupLendItemButton());
            buttonHolder.getChildren().add(setupReturnItemButton());
        }
        
        return buttonHolder;
    }
    
    private HBox createTopbar() {
        return new HBox();
    }
    
    private void fillSearchResults(VBox searchResultsArea,String searchText) {
        ArrayList<Item> searchResults = DataLoader.system.searchItems(searchText); // system contains every item in our project (books, dvds etc..)
        searchResultsArea.getChildren().clear();
        for (Item item : searchResults) {
            searchResultsArea.getChildren().add(new Button(item.getTitle()));
        }
    }
    
    private VBox setupItemSearch() {
        
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
        
        // SETUP
        ArrayList<Member> members = DataLoader.loadData(); // gets a list of the members from the DataLoader.java file
        VBox mainMenuButtons = setupItemSearch();
        
        
        
        var scene = new Scene(new StackPane(mainMenuButtons), 640, 480);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}