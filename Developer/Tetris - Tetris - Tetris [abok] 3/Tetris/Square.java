package Tetris;

/**
 * This is the Square class - it extends a gfx.Rectangle
 * Instances of this class will be added to an array to create our playing board
 * as well as make up our pieces
 *
 * abok
 * 
 */

public class Square extends gfx.Rectangle {

  public Square (TetrisPanel tp) {
    super(tp);
    this.setSize(Constants.SQR_SIZE-2, Constants.SQR_SIZE-2); //sets side length to constant defined in constants class
    this.setFillColor(java.awt.Color.BLACK); //sets default color to black - will be changed as needed
    this.setBorderColor(java.awt.Color.BLACK);
    this.setVisible(true);
    this.setBorderWidth(2);
  }

  public void moveDown() { //moves the square down by one square
    this.setLocation(this.getX(), this.getY() + Constants.SQR_SIZE);
  }

  public void moveRight() { //moves the square right by one square
    this.setLocation(this.getX() + Constants.SQR_SIZE, this.getY());
  }

  public void moveLeft() { //moves the square left by one square
    this.setLocation(this.getX() - Constants.SQR_SIZE, this.getY());
  }

} //end class