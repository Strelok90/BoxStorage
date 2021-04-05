import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("BlackBox");
        primaryStage.setScene(new Scene(root, 250, 120));
        primaryStage.show();
    }

    public void wait(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    public static void main(String[] args) {

        launch(args);
    }
}