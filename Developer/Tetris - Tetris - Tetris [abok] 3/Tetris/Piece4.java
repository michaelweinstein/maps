package Tetris;

/**
* This is the Piece4 Class, a subclass of piece
 * abok
 * 
 * 
 */

public class Piece4 extends Piece {

  private TetrisPanel _tetrisPanel;

  public Piece4(TetrisPanel tp) {
    super(tp);
    this.setLocation(6*Constants.SQR_SIZE, 3*Constants.SQR_SIZE, Constants.SQR_SIZE, Constants.SQR_SIZE, 0, Constants.SQR_SIZE, Constants.SQR_SIZE, 0);
    this.setColor(java.awt.Color.CYAN);
  } //end constructor

  public void rotatePiece() {
   //overrides rotatePiece method so this shape doesn't rotate
   //because this shape is a square, a rotation around its center won't change its appearance
   //i.e. it does not need to rotate
  }


}//end class