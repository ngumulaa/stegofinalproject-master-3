package edu.guilford;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;


//This class will be the pane that holds the method for the user
//to input the message they want to encrypt and the image they want
//to encrypt it in

/** 
 * the StegoPane class represents a custom JavaFX pane that provides a user interface for the Steganography App
 * It allows the user to encrypt a message and embed it in an image of their choice
 */
public class StegoPane extends Pane {
    
    //add an beginning label to the pane
    private Label beginningLabel;

    //add a directionsLabel to the pane
    private TextArea directionsArea;

    //add a text field for the user to input the message they want to encrypt
    private TextArea messageText;
    private Label messageLabel;
    private String message;
    //add a button for the user to click to send the message to the encrypt class
    private Button encrypt;
    private Label encryptLabel;
    
    //add a button for the user to click to select the image they want to encrypt
    private Button newImage;
    //add a button for the user to click to select a previously used image
    private Button prevImage;
    private Label imageText;
    private Label imageText2;
    //add an imageview to show the image chosen
    private ImageView imageView;
    
    //add a button for the user to click to save the encrypted image
    private Button send;
    private Rectangle2D screenBounds;

    //Add two buttons and a label to the pane
    private Label questionLabel;
    private Button stegoImage;
    private Button decrypt;

    //Add a hidden label to show the hidden message
    private Label hiddenLabel;

     /** 
     * constructs a StegoPane object
     * Initializes and sets up all the UI components 
     */
    
    //Constructor for the StegoPane class
    public StegoPane() {

        screenBounds = Screen.getPrimary().getVisualBounds();

        //Instantiate and change the aspects of the textArea
        messageText = new TextArea();
        messageText.setPrefSize(500, 250);
        messageText.setStyle("-fx-font-size: 18px; -fx-font-type: Arial");
        messageText.setWrapText(true);
        messageText.setScrollLeft(100);

        //Instantiate and change the aspects of the imageText
        imageText = new Label("Choose between either a new image");
        imageText2 = new Label("or a previously used image");
        imageText.setStyle("-fx-font-size: 18px; -fx-font-type: Arial");
        imageText2.setStyle("-fx-font-size: 18px; -fx-font-type: Arial");

        //Instantiate the imageView
        imageView = new ImageView();

        //Instantiate the labels
        beginningLabel = new Label("Welcome to the Steganography App");
        beginningLabel.setStyle("-fx-font-size: 22px; -fx-font-type: Arial");
        messageLabel = new Label("Enter the message you want to encrypt");
        messageLabel.setStyle("-fx-font-size: 18px; -fx-font-type: Arial");
        encryptLabel = new Label("Click to encrypt the message. Click to embed the message in the image");
        encryptLabel.setStyle("-fx-font-size: 18px; -fx-font-type: Arial");
        hiddenLabel = new Label();
        hiddenLabel.setStyle("-fx-font-size: 18px; -fx-font-type: Arial");

        //Instantiate the buttons
        encrypt = new Button("Encrypt");
        encrypt.setStyle("-fx-font-size: 15px; -fx-font-type: Arial");
        newImage = new Button("New Image");
        newImage.setStyle("-fx-font-size: 15px; -fx-font-type: Arial");
        prevImage = new Button("Previous Image");
        prevImage.setStyle("-fx-font-size: 15px; -fx-font-type: Arial");
        send = new Button("Send");
        send.setStyle("-fx-font-size: 15px; -fx-font-type: Arial");
        stegoImage = new Button("Stego Image");
        stegoImage.setStyle("-fx-font-size: 15px; -fx-font-type: Arial");
        decrypt = new Button("Decrypt");
        decrypt.setStyle("-fx-font-size: 15px; -fx-font-type: Arial");

        //Instantiate the directionsArea
        directionsArea = new TextArea();
        directionsArea.setText("Welcome to the Steganography App. \n\nThis app will allow you to encrypt a message and embed it in an image of your choice. \nYou can choose between a new image or a previously used image. If you choose a new image, you will be prompted to select an image from your computer. If you choose a previously used image, you will be prompted to select an image from a preset folder. \n\nAfter you have selected an image, you will be prompted to enter the message you want to encrypt. After you have entered the message, you will be prompted to click the encrypt button. After you have clicked the encrypt button, you will be prompted to click the send button. \n\nAfter you have clicked the send button, you will be prompted to select a location on your computer to save the encrypted image. After you have selected a location, the encrypted image will be saved to your computer.");
        directionsArea.setPrefSize(300, 500);
        directionsArea.setStyle("-fx-font-size: 18px; -fx-font-type: Arial");
        directionsArea.setWrapText(true);
        directionsArea.setScrollLeft(100);
        directionsArea.setEditable(false);

        //Instantiate a questionsLabel
        questionLabel = new Label("Would you like to decrypt an embedded image?");
        questionLabel.setStyle("-fx-font-size: 18px; -fx-font-type: Arial");

        //Set the location of the questionsLabel
        questionLabel.relocate((screenBounds.getWidth() / 2.8), (screenBounds.getHeight() / 1.3));

        //Set the location of the directionsArea to the middle of the pane
        directionsArea.relocate((screenBounds.getWidth() / 2.4), (screenBounds.getHeight() / 8.5));

        //Set the location of the beginningLabel to the top center of the pane
        beginningLabel.relocate((screenBounds.getWidth() / 2.8), 0);

        //Set the location of the messageLabel and message
        messageLabel.relocate(screenBounds.getWidth() / 20, 50);
        messageText.relocate(screenBounds.getWidth() / 20, 100);

        //Set the location of the newImage and prevImage buttons and the imageText
        imageText.relocate((screenBounds.getWidth() / 1.5), 50);
        imageText2.relocate((screenBounds.getWidth() / 1.5), 80);
        newImage.relocate((screenBounds.getWidth() / 1.5), 150);
        prevImage.relocate((screenBounds.getWidth() / 1.2), 150);

        //Set the location of the encryptLabel and encrypt button
        encryptLabel.relocate((screenBounds.getWidth() / 27), (screenBounds.getHeight() / 1.78));
        encrypt.relocate((screenBounds.getWidth() / 15), (screenBounds.getHeight() / 1.58));
        send.relocate((screenBounds.getWidth() / 3.9), (screenBounds.getHeight() / 1.58));

        //Set the location of the stegoImage and decrypt buttons
        stegoImage.relocate((screenBounds.getWidth() / 2.8), (screenBounds.getHeight() / 1.2));
        decrypt.relocate((screenBounds.getWidth() / 1.8), (screenBounds.getHeight() / 1.2));

        //Set the location of the hiddenLabel between the directionsLabel and the questionsLabel
        hiddenLabel.relocate((screenBounds.getWidth() / 2.8), (screenBounds.getHeight() / 1.5));

        //Add an event listener for the newImage button to open a file chooser from the user's computer
        newImage.setOnAction(e -> {
            // Instantiate a FileChooser object
            FileChooser fileChooser = new FileChooser();
            //Set the title of the fileChooser
            fileChooser.setTitle("Open New Image");
            //Find the path to the users home
            String path2 = "user.home";
            //Set the initial directory of the fileChooser
            fileChooser.setInitialDirectory(new File(System.getProperty(path2)));
            //Set the extension filter of the fileChooser
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg", "*.bmp"));
            //Instantiate a File object
            File selectedFile = fileChooser.showOpenDialog(null);
            //Instantiate an Image object
            Image image = new Image(selectedFile.toURI().toString());
            //Copy the image using the Commons IO library
            try {
                FileUtils.copyFile(selectedFile, new File("C:/stegofinalproject/src/main/PrevImages/Final" + selectedFile.getName()), true);
            } catch (Exception e1) {
                //Print the stack trace if there is an error
                e1.printStackTrace();
            } 
            //Add the image to the imageView
            imageView.setImage(image);
            //Set the size of the ImageView
            imageView.setFitWidth(300);
            //Preserve the aspect ratio of the image
            imageView.setPreserveRatio(true);
            //Set the location of the ImageView to the top left of the pane
            imageView.relocate(screenBounds.getWidth() / 1.48, 220);
        });

        //Add an event listener for the prevImage button to choose a previously used image 
        //from the PrevImages folder in the project
        prevImage.setOnAction(e -> {
            // Instantiate a FileChooser object to get the home directory
            FileChooser fileChooser = new FileChooser();
            //Set the title of the fileChooser
            fileChooser.setTitle("Open Previously Used Image");
            //Get the path to the PrevImage Folder
            String path = "C:/stegofinalproject/src/main/PrevImages";
            //Set the initial directory of the fileChooser
            fileChooser.setInitialDirectory(new File(path));
            //Set the extension filter of the fileChooser
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg", "*.bmp"));
            //Instantiate a File object
            File selectedFile = fileChooser.showOpenDialog(null);
            //Instantiate an Image object
            Image image = new Image(selectedFile.toURI().toString());
            //Add the image to the imageView
            imageView.setImage(image);
            //Set the size of the ImageView
            imageView.setFitWidth(300);
            //Preserve the aspect ratio of the image
            imageView.setPreserveRatio(true);
            //Set the location of the ImageView to the top left of the pane
            imageView.relocate(screenBounds.getWidth() / 1.48, 220);
        });

        //Add an event listener for the encrypt button to encrypt the message the user entered using the crypto class
        encrypt.setOnAction(e -> {
            //Get the message the user entered
            message = messageText.getText();
            //Instantiate a string object
            String crypto = "";
            //Try to encrypt the message the user entered
            try {
                //Encrypt the message the user entered
                crypto = Crypto.encrypt(message);
            } catch (Exception e1) {
                //Print the stack trace if there is an error
                e1.printStackTrace();
            }
        });

        //Add an event listener to send the encrypted message into the image the user chose
        send.setOnAction(e -> {
            //Write an if statement to check if the user has chosen an image and entered a message then run the encrypt method in stego class
            if (imageView.getImage() != null && messageText.getText() != null) {
                //write an if else statement for if the user chose a new image or a previously used image to run the encrypt method in the stego class
                if (newImage.isPressed()) {
                    //Get the file
                    File selectedFile = new File("C:/stegofinalproject/src/main/PrevImages/Final" + imageView.getImage().getUrl().substring(5));
                    //Run the encrypt method in the stego class
                    EncryptionLSB.Encrypt(selectedFile, message);
                } else if (prevImage.isPressed()) {
                    //Instantiate a File object
                    File selectedFile = new File("C:/stegofinalproject/src/main/EncodedImages/Final" + imageView.getImage().getUrl().substring(5));
                    //Run the encrypt method in the stego class
                    EncryptionLSB.Encrypt(selectedFile, message);
                }
            }
            //save the file to the users desktop
            FileChooser fileChooser = new FileChooser();
            //Set the title of the fileChooser
            fileChooser.setTitle("Save Encrypted Image");
            //Save the file as image files
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg", "*.bmp"));
            //Set the initial directory of the fileChooser
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            //Set a default file name
            fileChooser.setInitialFileName("image.png");
            //Show the dialog and get the selected file
            File selectedFile = fileChooser.showSaveDialog(getScene().getWindow());
            if (selectedFile != null) {
                //Save the file
                try (FileOutputStream outputStream = new FileOutputStream(selectedFile)) {
                    Image image = imageView.getImage();
                    BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
                    ImageIO.write(bImage, "png", outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e1) {
                    System.err.println("Error saving file: " + e1.getMessage());
                }
            }
            //Add a copy of the image to the EncodedImages folder
            try {
                FileUtils.copyFile(selectedFile, new File("C:/stegofinalproject/src/main/EncodedImages/Final" + selectedFile.getName()), true);
            } catch (Exception e1) {
                //Print the stack trace if there is an error
                e1.printStackTrace();
            }
        });

        //Add an event listener for the stegoImage button to open a file chooser from the EncodedImages folder
        stegoImage.setOnAction(e -> {
            // Instantiate a FileChooser object to get the home directory
            FileChooser fileChooser = new FileChooser();
            //Set the title of the fileChooser
            fileChooser.setTitle("Open Encoded Image");
            //Get the path to the EncodedImages Folder
            String path = "C:/stegofinalproject/src/main/EncodedImages";
            //Set the initial directory of the fileChooser
            fileChooser.setInitialDirectory(new File(path));
            //Set the extension filter of the fileChooser
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg", "*.bmp"));
            //Instantiate a File object
            File selectedFile = fileChooser.showOpenDialog(null);
            //Instantiate an Image object
            Image image = new Image(selectedFile.toURI().toString());
            //Add the image to the imageView
            imageView.setImage(image);
            //Set the size of the ImageView
            imageView.setFitWidth(300);
            //Preserve the aspect ratio of the image
            imageView.setPreserveRatio(true);
            //Set the location of the ImageView to the top left of the pane
            imageView.relocate(screenBounds.getWidth() / 1.48, 220);
        });

        //Add an event listener for the decrypt button to decrypt the message the user entered using the crypto class
        decrypt.setOnAction(e -> {
            //Get the message from the image
            DecryptionLSB.Decrypt();
            //Grab the string from the DecryptionLSB class
            
            //Get the message
            message = messageText.getText();
            //Get the encrypt and decrypted message
            try{
            String encrypt = "";
            encrypt = Crypto.encrypt(message);
            String decrypt = "";
            decrypt = Crypto.decrypt(encrypt);
            hiddenLabel.setText("The coded message: " + encrypt + "\n" + "The decoded message: " + decrypt);
            } catch (Exception e1) {
                //Print the stack trace if there is an error
                e1.printStackTrace();
            }
        });

        this.getChildren().addAll(beginningLabel, messageLabel, messageText, imageText, imageText2, newImage, prevImage, imageView, encrypt, encryptLabel, send, directionsArea, questionLabel, stegoImage, decrypt, hiddenLabel);
    }
}
