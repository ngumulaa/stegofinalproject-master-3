package edu.guilford;

import java.awt.Color;

/**
 * The Pixel class represents a pixel in an image
 * It contains attributes for the x and y coordinates and the color of the pixel
 * It contains getters and setters for the attributes
 */
public class Pixel {
    //Attributes
    private int x;
    private int y;
    private Color color;

/**
* Constructs a new Pixel object with the specified coordinates and color.
* @param x the x-coordinate of the pixel
* @param y the y-coordinate of the pixel
* @param color the color of the pixel
*/
    //Constructor
    public Pixel(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

/**
 * Returns the x-coordinate of the pixel.
 * @return the x-coordinate of the pixel
*/

    //Getters and Setters
    public int getX() {
        return x;
    }
/**
 * Returns the y-coordinate of the pixel.
 * @return the y-coordinate of the pixel
 */
     public int getY() {
        return y;
    }

/**
* Returns the color of the pixel.
* @return the color of the pixel
*/
    public Color getColor() {
        return this.color;
    }
/**
 * Sets the color of the pixel.
 * @param color the new color for the pixel
 */

    public void setColor(Color color) {
        this.color = color;
    }
/**
 * Sets the x-coordinate of the pixel.
 * @param x the new x-coordinate for the pixel
 */

    public void setX(int x) {
        this.x = x;
    }
/**
 * Sets the y-coordinate of the pixel.
 * @param y the new y-coordinate for the pixel
 */

    public void setY(int y){
        this.y = y;
    }    
}