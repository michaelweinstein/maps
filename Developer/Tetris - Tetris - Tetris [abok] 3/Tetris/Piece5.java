package Tetris;

/**
* This is the Piece5 Class, a subclass of piece
 * abok
 * 
 * 
 */

public class Piece5 extends Piece {

  private TetrisPanel _tetrisPanel;

  public Piece5(TetrisPanel tp) {
    super(tp);
    this.setLocation(6*Constants.SQR_SIZE, 3*Constants.SQR_SIZE, Constants.SQR_SIZE, -Constants.SQR_SIZE, Constants.SQR_SIZE, -2*Constants.SQR_SIZE, Constants.SQR_SIZE, 0);
    this.setColor(java.awt.Color.BLUE);
  } //end constructor

  

}//end class