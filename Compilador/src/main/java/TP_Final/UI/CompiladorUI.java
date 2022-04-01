package TP_Final.UI;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class CompiladorUI extends Application{

	public static void main(String[] args) {	
		launch(args);
	}

	@Override
	public void start(Stage stage) {		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CompiladorInterfaz.fxml"));
		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			
			scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
			
			stage.setTitle("Compilador");
			stage.setResizable(true);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

}