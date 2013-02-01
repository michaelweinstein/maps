package Tetris;

/**
* This is the Piece1 Class, a subclass of piece
 * abok
 * 
 * 
 */

public class Piece1 extends Piece {

  private TetrisPanel _tetrisPanel;

  public Piece1(TetrisPanel tp) {
    super(tp);
    this.setLocation(6*Constants.SQR_SIZE, 3*Constants.SQR_SIZE, Constants.SQR_SIZE, 0, Constants.SQR_SIZE, Constants.SQR_SIZE, 2*Constants.SQR_SIZE, Constants.SQR_SIZE);
    this.setColor(java.awt.Color.YELLOW);
  } //end constructor

  

}//end class
