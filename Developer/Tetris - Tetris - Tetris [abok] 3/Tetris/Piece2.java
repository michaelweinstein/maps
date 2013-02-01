package Tetris;

/**
* This is the Piece2 Class, a subclass of piece
 * abok
 * 
 * 
 */

public class Piece2 extends Piece {

  private TetrisPanel _tetrisPanel;

  public Piece2(TetrisPanel tp) {
    super(tp);
    this.setLocation(6*Constants.SQR_SIZE, 3*Constants.SQR_SIZE, Constants.SQR_SIZE, -Constants.SQR_SIZE, Constants.SQR_SIZE, 0, 2*Constants.SQR_SIZE, -Constants.SQR_SIZE);
    this.setColor(java.awt.Color.GREEN);
  } //end constructor

  

}//end class