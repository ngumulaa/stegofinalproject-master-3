package edu.guilford;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;

import javax.imageio.ImageIO;

 
/**
 * A class that provides methods for decrypting a message embedded in LSB (Least Significant Bit) of an image.
 */

public class DecryptionLSB {
    /**
     * Decrypts the message embedded in the LSB of an image.
     */
    public static void Decrypt() {
        //Get the path of the image to be decrypted
        String path = "C:/stegofinalproject/src/main/EncodedImages/Used";
        //Get the image file
        String newImageFileString = path + "Finalimage.png";
        File newImageFile = new File(newImageFileString);
        try {
            BufferedImage image = ImageIO.read(newImageFile);
            Pixel[] pixels = GetPixelArray(image);
            System.out.println("Message: " + DecodeMessageBinaryFromPixels(pixels));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/**
 * Retrieves an array of Pixels from the given image.
 * @param imageToEmbed the image from which to extract the pixel array
 * @return an array of Pixels representing the image
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
 * Decodes the binary message from the pixels.
 * @param pixels the array of Pixels containing the encoded message
 * @return the decoded message as a String
 */

    private static String DecodeMessageBinaryFromPixels(Pixel[] pixels) {
        boolean completed = false;
        int pixelIndex = 0;
        StringBuilder messageBuilder = new StringBuilder("");
        while(completed == false) {
            Pixel[] pixelsToRead = new Pixel[3];
            for(int i = 0; i < 3; i++) {
                pixelsToRead[i] = pixels[pixelIndex];
                pixelIndex++;
            }
            messageBuilder.append(ConvertPixelsToCharacter(pixelsToRead));
            if(IsEndOfMessage(pixelsToRead[2]) == true) {
                completed = true;
            }
        }
        return messageBuilder.toString();
    }
/**
 * Converts an array of Pixels to a character based on the LSB of their RGB values.
 * @param pixelsToRead the array of Pixels to be converted
 * @return the converted character
 */

    private static char ConvertPixelsToCharacter(Pixel[] pixelsToRead) {
        ArrayList<String> binaryValues = new ArrayList<String>();
        for(int i = 0; i < pixelsToRead.length; i++) {
            String[] currentBinary = TurnPixelIntergersToBinary(pixelsToRead[i]);
            binaryValues.add(currentBinary[0]);
            binaryValues.add(currentBinary[1]);
            binaryValues.add(currentBinary[2]);
        }
        return ConvertBinaryValuesToCharacter(binaryValues);
    }
/**
 * Converts the RGB values of a Pixel to binary strings.
 * @param pixel the Pixel containing the RGB values
 * @return an array of binary strings representing the RGB values
 */

    private static String[] TurnPixelIntergersToBinary(Pixel pixel) {
        String[] values = new String[3];
        values[0] = Integer.toBinaryString(pixel.getColor().getRed());
        values[1] = Integer.toBinaryString(pixel.getColor().getGreen());
        values[2] = Integer.toBinaryString(pixel.getColor().getBlue());
        return values;
    }
/**
 * Converts a list of binary values into a character by extracting the least significant bit of each binary value and converting it into ASCII code.
 * @param binaryValues The list of binary values to be converted.
 * @return The character representation of the binary values.
 */

    private static char ConvertBinaryValuesToCharacter(ArrayList<String> binaryValues) {
        StringBuilder endbinary = new StringBuilder("");
        for(int i = 0; i < binaryValues.size(); i++) {
            endbinary.append(binaryValues.get(i).charAt(binaryValues.get(i).length() - 1));
        }
        String endBinaryString = endbinary.toString();
        String noZeros = RemovePaddedZeros(endBinaryString);
        int ascii = Integer.parseInt(noZeros, 2);
        return (char)ascii;
    }
/**
 * Removes any leading padded zeros from a binary string.
 * @param endBinaryString The binary string to remove padded zeros from.
 * @return The modified binary string without leading zeros.
 */

    private static String RemovePaddedZeros(String endBinaryString) {
        StringBuilder builder = new StringBuilder(endBinaryString);
        int paddedZeros = 0;
        for(int i = 0; i < builder.length(); i++) {
            if(builder.charAt(i) == '0') {
                paddedZeros++;
            } else {
                break;
            }
        }
        for(int i = 0; i < paddedZeros; i++) {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

/**
 * Checks if the least significant bit of the blue color component of a Pixel represents the end of a message.
 * @param pixel The Pixel object to check.
 * @return True if the least significant bit of the blue component ends with '1', indicating the end of a message. False otherwise.
 */
    private static boolean IsEndOfMessage(Pixel pixel) {
        if(TurnPixelIntergersToBinary(pixel)[2].endsWith("1")) {
            return false;
        } else {
            return true;
        }
    }
}
