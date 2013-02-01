package Tetris;

/**
* This is the Piece6 Class, a subclass of piece
 * abok
 * 
 * 
 */

public class Piece6 extends Piece {

  private TetrisPanel _tetrisPanel;

  public Piece6(TetrisPanel tp) {
    super(tp);
    this.setLocation(6*Constants.SQR_SIZE, 3*Constants.SQR_SIZE, 0, -Constants.SQR_SIZE, 0, -2*Constants.SQR_SIZE, Constants.SQR_SIZE, 0);
    this.setColor(java.awt.Color.PINK);
  } //end constructor

  

}//end class 