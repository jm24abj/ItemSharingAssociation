import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UIEngine extends Application {
    
    private Button setupSearchButton() {
        Button searchItemsButton = new Button();
        searchItemsButton.setText("Search Items");
        return searchItemsButton;
    }
    
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

    private VBox getMainMenuItems() {
       
        VBox buttonHolder = new VBox(
            setupSearchButton(),
            setupAddItemButton(),
            setupUpdateItemButton(),
            setupRemoveItemButton(),
            setupAddMemberButton(),
            setupUpdateMemberButton(),
            setupRemoveMemberButton(),
            setupLendItemButton(),
            setupReturnItemButton()
        );
        
        return buttonHolder;
    }
    
    @Override
    public void start(Stage stage) {
        
        ArrayList<Member> members = DataLoader.loadData(); // gets a list of the members from the DataLoader.java file
        
        VBox mainMenuButtons = getMainMenuItems();
        
        var scene = new Scene(new StackPane(mainMenuButtons), 640, 480);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}