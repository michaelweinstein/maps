package Tetris;

/**
* This is the PieceProxy Class
 * It will contain a reference to our current piece and delegate methods
 * This way, only one reference (this one) will have to be updated when
 * a new piece is created
 * 
 * abok
 */

public class PieceProxy {

  private Piece _piece;

  public PieceProxy() {
    //constructor does not need to do anything
  }

  //all of the methods just call their equivalents in the piece class

  public void setPiece(Piece pc) {
    _piece = pc;
  }

  public void paint(java.awt.Graphics2D brush) {
    _piece.paint(brush);
  }

  public void moveRight() {
    _piece.moveRight();
  }

  public void moveLeft() {
    _piece.moveLeft();
  }

  public void moveDown() {
    _piece.moveDown();
  }

  public void rotate() {
    _piece.rotatePiece();
  }

  public void addToArray() {
    _piece.addToArray();
  }

  public boolean checkMoveDown() {
    if (_piece.checkMoveDown() == true) {
      return true;
    }
    else {
      return false;
    }
  }

  public void moveDownAllTheWay() {
    _piece.moveDownAllTheWay();
  }
    
  public void setGhostLocation(int i) {
    _piece.setGhostLocation(i);
  }

  //these accessors will be used to set the locaiton of the ghost piece
  public int getCol() {
    return _piece.getCol();
  }
  public int getCol2() {
    return _piece.getCol2();
  }
  public int getCol3() {
    return _piece.getCol3();
  }
  public int getCol4() {
    return _piece.getCol4();
  }

  public int getRow() {
    return _piece.getRow();
  }
  public int getRow2() {
    return _piece.getRow2();
  }
  public int getRow3() {
    return _piece.getRow3();
  }
  public int getRow4() {
    return _piece.getRow4();
  }



} //end class