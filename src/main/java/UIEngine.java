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
    private Scene memberSearchScene; 
    private Scene itemDetailsScene;
    private Scene memberDetailsScene;
    private Scene mainMenuScene;
    
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
    
    private Button setupUpdateItemButton(Item item) {
        Button updateItemButton = new Button();
        updateItemButton.setText("Update Item");
        return updateItemButton;
    }
    
    private Button setupRemoveItemButton(Item item) {
        Button removeItemButton = new Button();
        removeItemButton.setText("Remove Item");
        
        removeItemButton.setOnAction((e) -> {
            DataLoader.system.removeItem(item);
            itemSearchScene = new Scene(new StackPane(setupItemSearchMenu()), 640, 480);
            UIStage.setScene(itemSearchScene);
        });
        
        return removeItemButton;
    }
    
    private Button setupLendItemButton(Item item) {
        Button lendItemButton = new Button();
        lendItemButton.setText("Lend Item");
        return lendItemButton;
    }
    
    private Button setupReturnItemButton(Item item) {
        Button returnItemButton = new Button();
        returnItemButton.setText("Return Item");
        
        returnItemButton.setOnAction((e) -> {
            item.returnLoan();
            itemDetailsScene = new Scene(new StackPane(getItemDetailsMenu(item)), 640, 480);
            UIStage.setScene(itemDetailsScene);
        });
        
        return returnItemButton;
    }
    
    private VBox getMemberDetailsMenu(Member member) {
        Button backItemButton = new Button();
        backItemButton.setText("Return to menu");
        backItemButton.setOnAction((e)->{
            UIStage.setScene(memberSearchScene); // sets the current scene back to the search menu
        });
        
        VBox buttonHolder = new VBox(
            new Label(member.getName()),
            new Label("Address : " + member.getAddress()),
            new Label("Email : " + member.getEmail()),
            new Label("Number of Items Donated : " + Integer.toString(member.getDonatedQty())),
            new Label("Numer of Items Borrowing : " + Integer.toString(member.borrowingQty())),
            new Label("Borrowing : " + member.toString()),
            backItemButton,
            setupRemoveMemberButton(),
            setupUpdateMemberButton()
        );
        return buttonHolder;
    }

    private VBox getItemDetailsMenu(Item item) {
        String loanAvailable = "Loan: Available";
        if (!item.isAvailable() && item.getLoanMember() != null) {
            loanAvailable = "Loaned to " + item.getLoanMember().getName();
        }
        
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
        
        if (item.getDonator() != null) {
            buttonHolder.getChildren().add(new Label(item.getDonator().getName()));
        }
        
        if (item instanceof Book) {
            Book book = (Book) item;
            buttonHolder.getChildren().addAll(new Label("Author : " + book.getAuthor()),new Label("ISBN : " + book.getIsbn()));
        } else {
            DVD dvd = (DVD) item;
            buttonHolder.getChildren().addAll(new Label("Director : " + dvd.getDirector()), new Label(dvd.toString()));
        }
        
        buttonHolder.getChildren().addAll(
            backItemButton,
            setupRemoveItemButton(item),
            setupUpdateItemButton(item)
        );
        
        if (!item.isAvailable()) { // if the item is on loan 
            // if this is true then it needs more options
            buttonHolder.getChildren().add(setupReturnItemButton(item));
        } else {
            buttonHolder.getChildren().add(setupLendItemButton(item));
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
                itemDetailsScene = new Scene(new StackPane(getItemDetailsMenu(item)), 640, 480);
                UIStage.setScene(itemDetailsScene);
            });
            
            searchResultsArea.getChildren().add(resultButton);
        }
    }
    
    private ArrayList<Member> searchMembers(ArrayList<Member> members, String searchText) {
        ArrayList<Member> searchResults = new ArrayList<Member>();
        for (Member member : members) {
            if (member.getName().toLowerCase().contains(searchText.toLowerCase())) {
                searchResults.add(member);
            }
        }
        return searchResults;
    } 
    
    private void fillSearchResults(VBox searchResultsArea,String searchText, ArrayList<Member> members) {
        ArrayList<Member> searchResults = searchMembers(members,searchText);
        searchResultsArea.getChildren().clear();
        for (Member member : searchResults) {
            Button resultButton = new Button(member.getName());
            resultButton.setMaxHeight(Double.MAX_VALUE);
            resultButton.setPrefWidth(Double.MAX_VALUE);
            
            resultButton.setOnAction((e) -> {
                memberDetailsScene = new Scene(new StackPane(getMemberDetailsMenu(member)), 640, 480);
                UIStage.setScene(memberDetailsScene);
            });
            
            searchResultsArea.getChildren().add(resultButton);
        }
    }
    
    private VBox setupItemSearchMenu() {

        
        Button returnButton = new Button("Return To Main Menu");
        
        returnButton.setOnAction((e) -> {
            UIStage.setScene(mainMenuScene);
        });
        
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
        
        return new VBox(returnButton,searchArea,searchResultsArea);
    }
    
    private VBox setupMemberSearchMenu(ArrayList<Member> members) {

        Button returnButton = new Button("Return To Main Menu");
        
        returnButton.setOnAction((e) -> {
            UIStage.setScene(mainMenuScene);
        });
        
        Label labelForSearch = new Label("Search for item");
        TextField searchField = new TextField();
        searchField.setPromptText("Enter item name");
        
        VBox searchArea = new VBox(labelForSearch,searchField);
        VBox searchResultsArea = new VBox();
        

        fillSearchResults(searchResultsArea,"", members);

        
        searchField.setOnKeyPressed((e) -> {
            if (e.getCode().equals(KeyCode.ENTER)) {

                fillSearchResults(searchResultsArea,searchField.getText(), members);

            }
        });
        
        return new VBox(returnButton,searchArea,searchResultsArea);
    }
    
    public StackPane setupMainMenu(ArrayList<Member> members) {
        Button searchItemsButton = new Button("Search Items");
        Button searchMembersButton = new Button("Search Members");
        Button addMemberButton = new Button("Add Item");
        Button addItemButton = new Button("Add Member");
        
        searchItemsButton.setOnAction((e) -> {
            itemSearchScene = new Scene(new StackPane(setupItemSearchMenu()), 640, 480);
            UIStage.setScene(itemSearchScene);
        });
        
        searchMembersButton.setOnAction((e) -> {
            memberSearchScene = new Scene(new StackPane(setupMemberSearchMenu(members)), 640, 480);
            UIStage.setScene(memberSearchScene);
        });
        
        addMemberButton.setOnAction((e) -> {
            
        });
        
        addItemButton.setOnAction((e) -> {
            
        });
        
        VBox vbox = new VBox(searchItemsButton,searchMembersButton,addMemberButton,addItemButton);
        StackPane scene = new StackPane(vbox);
        return scene;
    }
    
    @Override
    public void start(Stage stage) {
        
        // SETUP MISC
        UIStage = stage;
        ArrayList<Member> members = DataLoader.loadData(); // gets a list of the members from the DataLoader.java file
        
        //SETUP AND LODING MAIN MENU
        mainMenuScene = new Scene(setupMainMenu(members), 640, 480);
        UIStage.setScene(mainMenuScene);
        UIStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}