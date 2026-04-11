import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UIEngine extends Application {
    
    // Contains the different menus so they can be displayed from within other functions
    private Stage UIStage;
    
    private Scene itemSearchScene; 
    private Scene memberSearchScene; 
    private Scene itemDetailsScene;
    private Scene memberDetailsScene;
    private Scene mainMenuScene;
    private Scene addItemScene;
    private Scene addMemberScene;
    private Scene updateMemberScene;
    private Scene updateItemScene;
    private Scene lendItemScene;
    
    public boolean isInteger( String input ) {
    try {
        Integer.parseInt( input );
        return true;
    }
    catch( Exception e ) {
        return false;
    }
}
    
    private Button setupRemoveMemberButton(Member member) {
        Button removeMemberButton = new Button();
        removeMemberButton.setText("Remove Member");
        
        removeMemberButton.setOnAction((e) -> {
            member.clearUserData();
            DataHandler.allMembers.remove(member);
            memberSearchScene = new Scene(new StackPane(setupMemberSearchMenu(DataHandler.allMembers)), 640, 480);
            UIStage.setScene(memberSearchScene);
        });
        
        return removeMemberButton;
    }
    
    private void setupUpdateMemberMenu(Member member) {
        VBox memberMenu = new VBox();
        Label errorDisplay = new Label("");
        Button createButton = new Button("Submit");
        TextField nameField = new TextField(member.getName());
        nameField.setPromptText("Name");
        TextField addressField = new TextField(member.getAddress());
        addressField.setPromptText("Address");
        TextField emailField = new TextField(member.getEmail());
        emailField.setPromptText("Email");
        
        createButton.setOnAction((e) -> {
            if (nameField.getText() == "" || addressField.getText() == "" || emailField.getText() == "") {
                errorDisplay.setText("Must Fill All Fields!");
            } else {
                String name = nameField.getText();
                String address = addressField.getText();
                String email = emailField.getText();
                member.setName(name);
                member.setAddress(address);
                member.setEmail(email);
                memberDetailsScene = new Scene(new StackPane(getMemberDetailsMenu(member)), 640, 480);
                UIStage.setScene(memberDetailsScene);
            }
        });
        
        memberMenu.getChildren().addAll(
            errorDisplay,
            new Label("Name"), nameField,
            new Label("Address"), addressField,
            new Label("Email"),emailField,
            createButton
        );
        
        updateMemberScene = new Scene(new StackPane(memberMenu), 640, 480);
        UIStage.setScene(updateMemberScene);
    }
    
    private Button setupUpdateMemberButton(Member member) {
        Button updateMemberButton = new Button();
        updateMemberButton.setText("Update Member");
        
        updateMemberButton.setOnAction((e) -> {
            setupUpdateMemberMenu(member);
        });
        
        return updateMemberButton;
    }
    
    private void setupUpdateItemMenu(Item item) {
        VBox itemMenu = new VBox();
        
        Label errorDisplay = new Label("");
        errorDisplay.setTextFill(Color.RED);
        Button createButton = new Button("Submit");
        
        TextField titleField = new TextField(item.getTitle());
        titleField.setPromptText("Name");
        TextField languageField = new TextField(item.getLanguage());
        languageField.setPromptText("Address");
        
        itemMenu.getChildren().addAll(
            errorDisplay,
            new Label("Title"), titleField,
            new Label("Language"), languageField
        );
        
        if (item instanceof Book) {
            Book bookItem = (Book) item;
            TextField authorField = new TextField(bookItem.getAuthor());
            authorField.setPromptText("Author");
            TextField isbnField = new TextField(bookItem.getIsbn());
            isbnField.setPromptText("ISBN");
            itemMenu.getChildren().addAll(new Label("Author"),authorField,new Label("ISBN"),isbnField);
            
            createButton.setOnAction((e) -> {
                if (titleField.getText() == "" || languageField.getText() == "" || authorField.getText() == "" || isbnField.getText() == "") {
                    errorDisplay.setText("Must Fill All Fields!");
                } else {
                    bookItem.setIsbn(isbnField.getText());
                    bookItem.setTitle(titleField.getText());
                    bookItem.setAuthor(authorField.getText());
                    bookItem.setLanguage(languageField.getText());
                    itemDetailsScene = new Scene(new StackPane(getItemDetailsMenu(item)), 640, 480);
                    UIStage.setScene(itemDetailsScene);
                }
            });
        } else if (item instanceof DVD) {
            DVD DVDItem = (DVD) item;
            TextField directorField = new TextField(DVDItem.getDirector());
            directorField.setPromptText("Director");
            String defaultAudioString = "";
            for (String audioLang : DVDItem.getAudioLanguages()) {
                defaultAudioString+= audioLang + ",";
            }
            
            TextField audioLanguagesField = new TextField(defaultAudioString);
            audioLanguagesField.setPromptText("Audio Languages (Split by commas)");
            itemMenu.getChildren().addAll(new Label("Author"),directorField,new Label("Audio Languages (Seperate via commas)"), audioLanguagesField);
            
            createButton.setOnAction((e) -> {
                if (titleField.getText() == "" || languageField.getText() == "" || directorField.getText() == "" ) {
                    errorDisplay.setText("Must Fill All Fields!");
                } else {
                    String[] newLanguageList = audioLanguagesField.getText().split(",");
                    DVDItem.setAudioLanguages(newLanguageList);
                    DVDItem.setTitle(titleField.getText());
                    DVDItem.setDirector(directorField.getText());
                    DVDItem.setLanguage(languageField.getText());
                    itemDetailsScene = new Scene(new StackPane(getItemDetailsMenu(item)), 640, 480);
                    UIStage.setScene(itemDetailsScene);
                }
            });
        }
        
        itemMenu.getChildren().add(createButton);      
        updateItemScene = new Scene(new StackPane(itemMenu), 640, 480);
        UIStage.setScene(updateItemScene);
    }
    
    
    private Button setupUpdateItemButton(Item item) {
        Button updateItemButton = new Button();
        updateItemButton.setText("Update Item");
        
        updateItemButton.setOnAction((e) -> {
            setupUpdateItemMenu(item);
        });
        
        
        return updateItemButton;
    }
    
    private Button setupRemoveItemButton(Item item) {
        Button removeItemButton = new Button();
        removeItemButton.setText("Remove Item");
        
        removeItemButton.setOnAction((e) -> {
            if (item.getLoanMember() != null) {
                item.getLoanMember().returnItem(item);
            }
            DataHandler.system.removeItem(item);
            
            itemSearchScene = new Scene(new StackPane(setupItemSearchMenu()), 640, 480);
            UIStage.setScene(itemSearchScene);
        });
        
        return removeItemButton;
    }
    
    private void setupLendItemMenu(Item item) {
        VBox lendItemsMenu = new VBox();
        Label errorDisplay = new Label();
        errorDisplay.setTextFill(Color.RED);
        Button cancelButton = new Button("Cancel");
        Button confirmButton = new Button("Confirm");
        
        ComboBox candidateLenders = new ComboBox();
        for (Member member : DataHandler.allMembers) { // adds all members to the list of candidate donators
            candidateLenders.getItems().add(member);
        }
        
        cancelButton.setOnAction((e) -> {
            itemDetailsScene = new Scene(new StackPane(getItemDetailsMenu(item)), 640, 480);
            UIStage.setScene(itemDetailsScene);
        });
        
        confirmButton.setOnAction((e) -> {
            if (candidateLenders.getValue() == null) {
                errorDisplay.setText("Must Select A Member!");
            } else {
                errorDisplay.setText("");
                Member memberToLend = (Member) candidateLenders.getValue();
                if (memberToLend.canBorrow()) {
                    item.loanTo(memberToLend);
                    itemDetailsScene = new Scene(new StackPane(getItemDetailsMenu(item)), 640, 480);
                    UIStage.setScene(itemDetailsScene);
                } else {
                    errorDisplay.setText("Member cannot borrow more than then the amount donated up to 5 maximum books");
                }
            }
        });
        
        lendItemsMenu.getChildren().addAll(errorDisplay,new Label("Member To Lend"),candidateLenders,cancelButton,confirmButton);
        lendItemScene = new Scene(new StackPane(lendItemsMenu), 640, 480);
        UIStage.setScene(lendItemScene);
    }
    
    private Button setupLendItemButton(Item item) {
        Button lendItemButton = new Button();
        lendItemButton.setText("Lend Item");
        
        lendItemButton.setOnAction((e) -> {
            setupLendItemMenu(item);
        });
        
        return lendItemButton;
    }
    
    private Button setupReturnItemButton(Item item) {
        Button returnItemButton = new Button();
        returnItemButton.setText("Return Item");
        
        returnItemButton.setOnAction((e) -> {
            item.getLoanMember().returnItem(item);
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
            new Label("Borrowing : " + member.getBorrowingItems()),
            backItemButton,
            setupRemoveMemberButton(member),
            setupUpdateMemberButton(member)
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
            buttonHolder.getChildren().add(new Label("Donated by: " + item.getDonator().getName()));
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
        ArrayList<Item> searchResults = DataHandler.system.searchItems(searchText); // system contains every item in our project (books, dvds etc..)
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
    
    private String[] convertFieldsToAudioLanguages(VBox audioLanguagesHolder) {
        ArrayList<String> audioLanguages = new  ArrayList<String>();
        for (Node node : audioLanguagesHolder.getChildren()) {
            if (node instanceof TextField) {
                audioLanguages.add(((TextField) node).getText());
            }
        }
        return audioLanguages.toArray(new String[audioLanguages.size()]);
    }
    
    private void createAddMemberMenu() {
        VBox memberMenu = new VBox();
        Button createButton = new Button("Submit");
        
        Label errorDisplay = new Label ("");
        errorDisplay.setTextFill(Color.RED);
        
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField addressField = new TextField();
        addressField.setPromptText("Address");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        Button backButton = new Button("Return to Main Menu");
        backButton.setOnAction((e) -> {
            UIStage.setScene(mainMenuScene);
        });
        
        createButton.setOnAction((e) -> {
            if (nameField.getText() == "" || addressField.getText() == "" || emailField.getText() == "") {
                errorDisplay.setText("Must Fill All Fields!");
            } else {
                String name = nameField.getText();
                String address = addressField.getText();
                String email = emailField.getText();
                DataHandler.allMembers.add(new Member(name,address,email,0));
                UIStage.setScene(mainMenuScene);
            }
        });
        
        memberMenu.getChildren().addAll(
                backButton,
                errorDisplay,
                new Label("Name"), nameField,
                new Label("Address"), addressField,
                new Label("Email"),emailField,
                createButton
        );
        
        addMemberScene = new Scene(new StackPane(memberMenu), 640, 480);
        UIStage.setScene(addMemberScene);
    }
    
    private void createAddDVDMenu(ComboBox itemType,ComboBox donatedBy,Button createButton) {
        VBox dvdMenu = new VBox();
        
        VBox audioLanguages = new VBox();
        TextField audioLanguage = new TextField();
        audioLanguage.setPromptText("Audio Language");
     
        Button addAudioLanguage = new Button("Add Language");
        
        audioLanguages.getChildren().add(audioLanguage);
        addAudioLanguage.setOnAction((e) -> {
            TextField newAudioLanguage = new TextField();
            audioLanguage.setPromptText("Audio Language");
            audioLanguages.getChildren().add(newAudioLanguage);
        });
        
        Label errorDisplay = new Label ("");
        errorDisplay.setTextFill(Color.RED);
        
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField directorField = new TextField();
        directorField.setPromptText("Director");
        TextField languageField = new TextField();
        languageField.setPromptText("Language");
        
        Button backButton = new Button("Return to Main Menu");
        backButton.setOnAction((e) -> {
            UIStage.setScene(mainMenuScene);
        });
        
        createButton.setOnAction((e) -> {
            if (itemType.getValue() == null) {
                errorDisplay.setText("No Item Type Selected!");
            } else if (donatedBy.getValue() == null){
                errorDisplay.setText("No Donator Selected!");
            } else if (titleField.getText() == "" || directorField.getText() == "" || languageField.getText() == "") {
                errorDisplay.setText("Must Fill All Fields!");
            } else {
                String[] allAudioLanguages = convertFieldsToAudioLanguages(audioLanguages);
                String title = titleField.getText();
                String author = directorField.getText();
                String language = languageField.getText();
                DataHandler.system.addDVD(title, author, (Member) donatedBy.getValue(),language,allAudioLanguages);
                UIStage.setScene(mainMenuScene);
            }
        });
        
        dvdMenu.getChildren().addAll(
                backButton,
                errorDisplay,
                new Label("Item Type"), itemType,
                new Label("Title"), titleField,
                new Label("Director"),directorField,
                new Label("Language"), languageField,
                new Label("Audio Languages"),audioLanguages,addAudioLanguage,
                new Label ("Donated By"), donatedBy,
                createButton
        );
        
        addItemScene = new Scene(new StackPane(dvdMenu), 640, 480);
        UIStage.setScene(addItemScene);
        
    }
    
    private void createAddBookMenu(ComboBox itemType,ComboBox donatedBy,Button createButton) {
        VBox bookMenu = new VBox();
        
        Label errorDisplay = new Label ("");
        errorDisplay.setTextFill(Color.RED);
        
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        TextField languageField = new TextField();
        languageField.setPromptText("Language");
        
        Button backButton = new Button("Return to Main Menu");
        backButton.setOnAction((e) -> {
            UIStage.setScene(mainMenuScene);
        });
        
        createButton.setOnAction((e) -> {
            if (itemType.getValue() == null) {
                errorDisplay.setText("No Item Type Selected!");
            } else if (donatedBy.getValue() == null){
                errorDisplay.setText("No Donator Selected!");
            } else if (!isInteger(isbnField.getText())){
                errorDisplay.setText("ISBN Must Be A Number!");
            } else if (titleField.getText() == "" || authorField.getText() == "" || languageField.getText() == "") {
                errorDisplay.setText("Must Fill All Fields!");
            } else {
                String title = titleField.getText();
                String author = authorField.getText();
                String isbn = isbnField.getText();
                String language = languageField.getText();
                DataHandler.system.addBook(title, author, (Member) donatedBy.getValue(), language, isbn);
                UIStage.setScene(mainMenuScene);
            }
        });
        
        bookMenu.getChildren().addAll(
                backButton,
                errorDisplay,
                new Label("Item Type"), itemType,
                new Label("Title"), titleField,
                new Label("Author"),authorField,
                new Label("ISBN"), isbnField,
                new Label("Language"), languageField,
                new Label ("Donated By"), donatedBy,
                createButton
        );
        
        addItemScene = new Scene(new StackPane(bookMenu), 640, 480);
        UIStage.setScene(addItemScene);
        
    }
    
    private void setupAddItemMenu() {
        
        VBox itemMenu = new VBox();
        Button submit = new Button("Submit");
        
        ComboBox itemType = new ComboBox();
        itemType.getItems().addAll( // contains all of the item types 
            "Book",
            "DVD"
        );
        
        ComboBox donatedBy = new ComboBox();
        for (Member member : DataHandler.allMembers) { // adds all members to the list of candidate donators
            donatedBy.getItems().add(member);
        }

        itemType.valueProperty().addListener(new ChangeListener<String>() {
            // triggers on a new item type is selected in the drop down menu
            // needs to do this as the different item types have different attributes ISBN etc...
            @Override
            public void changed(ObservableValue ov,String t,String newSelection) {
                switch (newSelection) { // new selection contains string value of item type
                    // calls the function that contains the way the actual UI is created depending on item type
                    // If you wanted to be able to create new types of items you need to add a new 
                    // value to the switch case and a new function to create UI with all of the fields 
                    // like in the createAddBookMenu() and createAddDVDMenu() methods
                    case "Book":
                        createAddBookMenu(itemType,donatedBy,submit);
                        break;
                    case "DVD":
                        createAddDVDMenu(itemType,donatedBy,submit);
                        break;
                }
            }
        });
        
        itemType.setValue("Book");
        createAddBookMenu(itemType,donatedBy,submit); // sets up the ui initially with book selected
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
    
    public StackPane setupMainMenu() {
        Button searchItemsButton = new Button("Search Items");
        Button searchMembersButton = new Button("Search Members");
        Button addMemberButton = new Button("Add Member");
        Button addItemButton = new Button("Add Item");
        
        Button saveDataButton = new Button("Save Data");
        TextField filenameField = new TextField();
        filenameField.setPromptText("File Name");
        HBox saveholder = new HBox(filenameField,saveDataButton);
        
        searchItemsButton.setOnAction((e) -> {
            itemSearchScene = new Scene(new StackPane(setupItemSearchMenu()), 640, 480);
            UIStage.setScene(itemSearchScene);
        });
        
        searchMembersButton.setOnAction((e) -> {
            memberSearchScene = new Scene(new StackPane(setupMemberSearchMenu(DataHandler.allMembers)), 640, 480);
            UIStage.setScene(memberSearchScene);
        });
        
        addMemberButton.setOnAction((e) -> {
            createAddMemberMenu();
        });
        
        addItemButton.setOnAction((e) -> {
            setupAddItemMenu();
        });
        
        saveDataButton.setOnAction((e) -> {
            DataHandler.saveData(filenameField.getText());
        });
        
        VBox vbox = new VBox(searchItemsButton,searchMembersButton,addMemberButton,addItemButton,saveholder);
        StackPane scene = new StackPane(vbox);
        return scene;
    }
    
    @Override
    public void start(Stage stage) {
        
        // SETUP MISC
        UIStage = stage;
        DataHandler.loadData(); // gets a list of the members from the DataLoader.java file
        
        //SETUP AND LODING MAIN MENU
        mainMenuScene = new Scene(setupMainMenu(), 640, 480);
        UIStage.setScene(mainMenuScene);
        UIStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}