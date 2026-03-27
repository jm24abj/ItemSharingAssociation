import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UIEngine extends Application {

    @Override
    public void start(Stage stage) {
        
        ArrayList<Member> members = DataLoader.loadData(); // gets a list of the members from the DataLoader.java file
        
        var label = new Label("Hello, world!");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}