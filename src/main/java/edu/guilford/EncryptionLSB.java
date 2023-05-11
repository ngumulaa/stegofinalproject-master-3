package edu.guilford;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


//This class is used to hide the message the user entered into the image the user chose
//and then save the new image with the message hidden in it
//We will you LSB - Least Significant Bit

/**
 * Provides methods for encrypting a message into an image using the LSB (Least Significant Bit) technique.
*/
public class EncryptionLSB {

/**
 * Encrypts the given message into the selected image file using the LSB technique.
 * @param selectedFile the selected image file to embed the message into
 * @param message the message to be encrypted and embedded
 */

    public static void Encrypt(File selectedFile, String message){
        //Create a path to save the new image to
        String path = "C:/stegofinalproject/src/main/EncodedImages/Used";
        String newImageFileString = path + "Finalimage.png";
        File newImageFile = new File(newImageFileString);
        
        BufferedImage image;
        try{
            image = ImageIO.read(selectedFile);
            BufferedImage imageToEmbed = GetImageToEmbed(image);
            Pixel[] pixels = GetPixelArray(imageToEmbed);
            String[] messageBinary = ConvertMessageToBinary(message);
            EncodeMessageBinaryInPixels(pixels, messageBinary);
            ReplacePixelsInNewBufferedImage(pixels, imageToEmbed);
            SaveNewFile(imageToEmbed, newImageFile);
        } catch(IOException e) {

        }
    }

    /**
     * Retrieves a new BufferedImage object from the given image for embedding the message.
     * @param image the original image to be embedded
     * @return the new BufferedImage object for embedding
    */

    private static BufferedImage GetImageToEmbed(BufferedImage image) {
        //Determine how the colors are represented within AWT (0,0,0) - (255,255,255)
        ColorModel colorModel = image.getColorModel();
        boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
        //Provides pixel writing capabilities
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
    }

    /**
     * Retrieves an array of Pixels representing the image to be embedded.
     * @param imageToEmbed the BufferedImage object for embedding
     * @return an array of Pixels representing the image to be embedded
    */

    private static Pixel[] GetPixelArray(BufferedImage imageToEmbed) {
        int width = imageToEmbed.getWidth();
        int height = imageToEmbed.getHeight();
        Pixel[] pixels = new Pixel[width * height];
        int count = 0;
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++) {
                Color colorToAdd = new Color(imageToEmbed.getRGB(x, y));
                pixels[count] = new Pixel(x, y, colorToAdd);
                count++;
            }
        }
        return pixels;
    }

/**
 * Converts the message into an array of binary strings.
 * @param message the message to be converted
 * @return an array of binary strings representing the message
 */

    private static String[] ConvertMessageToBinary(String message) {
        int[] messageAscii = ConvertMessageToAscii(message);
        String[] messageBinary = ConvertAsciiToBinary(messageAscii);
        return messageBinary;
    }
/**
 * Converts the message into an array of ASCII values.
* @param message the message to be converted
* @return an array of ASCII values representing the message
*/

    private static int[] ConvertMessageToAscii(String message) {
        int[] messageAscii = new int[message.length()];
        for(int i = 0; i < message.length(); i++) {
            //Cast the character to an int
            //Which then turns it into an Ascii value
            messageAscii[i] = (int)message.charAt(i);
        }
        return messageAscii;
    }

/**
 * Converts an array of ASCII values into an array of binary strings.
 * @param asciiValues the array of ASCII values to be converted
 * @return an array of binary strings representing the ASCII values
 */

    private static String[] ConvertAsciiToBinary(int[] asciiValues) {
        String[] messageBinary = new String[asciiValues.length];
        for(int i = 0; i < asciiValues.length; i++) {
            String binary = leftPadZeros(Integer.toBinaryString(asciiValues[i]));
            messageBinary[i] = binary;
        }
        return messageBinary;
    }
/**
 * Left-pads the given binary string with zeros to ensure it has a length of 8 characters.
 * @param binary the binary string to be left-padded
 * @return the left-padded binary string
 */

    private static String leftPadZeros(String binary) {
        StringBuilder sb = new StringBuilder("00000000");
        int offSet = 8 - binary.length();
        for(int i = 0; i < binary.length(); i++) {
            sb.setCharAt(offSet + i, binary.charAt(i));
        }
        return sb.toString();
    }
/**
 * Encodes the binary message into the pixels of the image.
 * @param pixels the array of Pixels representing the image
 * @param messageBinary the array of binary strings representing the message
 */

    private static void EncodeMessageBinaryInPixels(Pixel[] pixels, String[] messageBinary) {
        int pixelIndex = 0;
        boolean isLastCharacter = false;
        for(int i = 0; i < messageBinary.length; i++) {
            Pixel[] currentPixels = new Pixel[] {pixels[pixelIndex], pixels[pixelIndex + 1], pixels[pixelIndex + 2]};
            if (i + 1 == messageBinary.length) {
                isLastCharacter = true;
            }
            ChangePixelsColor(messageBinary[i], currentPixels, isLastCharacter);
            pixelIndex = pixelIndex + 3;
        }
    }
/**
 * Changes the color of the pixels based on the binary message and the specified conditions.
 * @param messageBinary the binary string representing the message
 * @param pixels the array of Pixels to be modified
 * @param isLastCharacter a flag indicating if the current character is the last character of the message
 */

    private static void ChangePixelsColor(String messageBinary, Pixel[] pixels, boolean isLastCharacter) {
        int messageIndex = 0;
        for(int i = 0; i < pixels.length - 1; i++) {
            char[] messageBinaryCharacter = new char[] {messageBinary.charAt(messageIndex), messageBinary.charAt(messageIndex + 1), messageBinary.charAt(messageIndex + 2)};
            String[] pixelRGBBinary = GetPixelsRGBBinary(pixels[i], messageBinaryCharacter);
            pixels[i].setColor(GetNewPixelColor(pixelRGBBinary));
            messageIndex = messageIndex + 3;
        }
        if (isLastCharacter == false) {
            char[] messageBinaryCharacter = new char[] {messageBinary.charAt(messageIndex), messageBinary.charAt(messageIndex + 1), '1'};
            String[] pixelRGBBinary = GetPixelsRGBBinary(pixels[pixels.length - 1], messageBinaryCharacter);
            pixels[pixels.length - 1].setColor(GetNewPixelColor(pixelRGBBinary));
        } else {
            char[] messageBinaryCharacter = new char[] {messageBinary.charAt(messageIndex), messageBinary.charAt(messageIndex + 1), '0'};
            String[] pixelRGBBinary = GetPixelsRGBBinary(pixels[pixels.length - 1], messageBinaryCharacter);
            pixels[pixels.length - 1].setColor(GetNewPixelColor(pixelRGBBinary));
        }
    }

    private static String[] GetPixelsRGBBinary(Pixel pixel, char[] messageBinaryChars) {
        String[] pixelRGBBinary = new String[3];
        pixelRGBBinary[0] = ChangePixelBinary(Integer.toBinaryString(pixel.getColor().getRed()), messageBinaryChars[0]);
        pixelRGBBinary[1] = ChangePixelBinary(Integer.toBinaryString(pixel.getColor().getGreen()), messageBinaryChars[1]);
        pixelRGBBinary[2] = ChangePixelBinary(Integer.toBinaryString(pixel.getColor().getBlue()), messageBinaryChars[2]);
        
        return pixelRGBBinary;
    }

    private static String ChangePixelBinary(String pixelBinary, char messageBinaryChar) {
        StringBuilder sb = new StringBuilder(pixelBinary);
        sb.setCharAt(pixelBinary.length() - 1, messageBinaryChar);
        
        return sb.toString();
    }

    private static Color GetNewPixelColor(String[] colorBinary) {
        return new Color(Integer.parseInt(colorBinary[0], 2), Integer.parseInt(colorBinary[1], 2), Integer.parseInt(colorBinary[2], 2));
    }

    private static void ReplacePixelsInNewBufferedImage(Pixel[] pixels, BufferedImage imageToEmbed) {
        for(int i = 0; i < pixels.length; i++) {
            imageToEmbed.setRGB(pixels[i].getX(), pixels[i].getY(), pixels[i].getColor().getRGB());
        }
    }

    private static void SaveNewFile(BufferedImage newImage, File newImageFile) {
        try {
            ImageIO.write(newImage, "png", newImageFile);
        } catch (IOException e) {
            System.out.println("Error saving file");
        }
    }
}

               

           


