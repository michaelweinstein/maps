package Tetris;

/**
* This is the Piece7 Class, a subclass of piece
 * abok
 * 
 * 
 */

public class Piece7 extends Piece {

  private TetrisPanel _tetrisPanel;

  public Piece7(TetrisPanel tp) {
    super(tp);
    this.setLocation(6*Constants.SQR_SIZE, 3*Constants.SQR_SIZE, 0, -2*Constants.SQR_SIZE, 0, -3*Constants.SQR_SIZE, 0, -Constants.SQR_SIZE);
    this.setColor(java.awt.Color.MAGENTA);
  } //end constructor

  

}//end class 