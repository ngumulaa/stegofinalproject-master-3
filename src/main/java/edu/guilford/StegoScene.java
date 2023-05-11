package edu.guilford;

import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The StegoScene class represents the main application scene for the Steganography App
 * It extends the Application class and provides the start method to initialize the scene 
 */

public class StegoScene extends Application {
/** 
 * The main scene of the application 
 */

    private static Scene scene;

/** 
 * The start method is called when the application is launched
 * Itinitializes the main stage and sets up the scene 
 * @param stage the main stage of the application
 * @throws IOException if an I/O error occurs
 */
    @Override
    public void start(Stage stage) throws IOException {
        //Instantiate a BorderPane object
        BorderPane root = new BorderPane();

        root.getChildren().add(new StegoPane());

        //Get the width and height of the screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        System.out.println("Width: " + screenBounds.getWidth() + " Height: " + screenBounds.getHeight());

        scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();
    }
    /** 
     * The main method is the entry point of the application 
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        launch();
    }

}