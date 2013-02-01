package Tetris;

/**
* This is the Piece Class
 * abok
 * 
 * 
 */

public abstract class Piece {

  private Square _sq1, _sq2, _sq3, _sq4;
  private Square _g1, _g2, _g3, _g4;
  private TetrisPanel _tetrisPanel;
  private double _pieceX, _pieceY;

  public Piece(TetrisPanel tp) {

    _tetrisPanel = tp; //associate with the tetris panel
  
    //instantiate four new squares, which will make up the piece
    _sq1 = new Square(_tetrisPanel);
    _sq2 = new Square(_tetrisPanel);
    _sq3 = new Square(_tetrisPanel);
    _sq4 = new Square(_tetrisPanel);
    
    //_g1..._g4 are the components of the ghost square. They never actually get added to the board array
    _g1 = new Square(_tetrisPanel);
    _g2 = new Square(_tetrisPanel);
    _g3 = new Square(_tetrisPanel);
    _g4 = new Square(_tetrisPanel);
    _g1.setBorderColor(java.awt.Color.WHITE);
    _g2.setBorderColor(java.awt.Color.WHITE);
    _g3.setBorderColor(java.awt.Color.WHITE);
    _g4.setBorderColor(java.awt.Color.WHITE);

    //make the piece visible
    this.setVisible(true);

  } //end constructor

  //maintains the structure of the piece while setting the location of the piece relative to the parameter i
  //the parameter will be found in the tetris panel
  public void setGhostLocation(int i) {
    _g1.setLocation(_sq1.getX(), _sq1.getY() + i);
    _g2.setLocation(_sq2.getX(), _sq2.getY() + i);
    _g3.setLocation(_sq3.getX(), _sq3.getY() + i);
    _g4.setLocation(_sq4.getX(), _sq4.getY() + i);
  }

 //these accessors will be used to set the location of the ghost piece
  public int getCol() {
    return (int) _sq1.getX()/Constants.SQR_SIZE;
  }
  public int getCol2() {
    return (int) _sq2.getX()/Constants.SQR_SIZE;
  }
  public int getCol3() {
    return (int) _sq3.getX()/Constants.SQR_SIZE;
  }
  public int getCol4() {
    return (int) _sq4.getX()/Constants.SQR_SIZE;
  }

  public int getRow() {
    return (int) _sq1.getY()/Constants.SQR_SIZE;
  }
  public int getRow2() {
    return (int) _sq2.getY()/Constants.SQR_SIZE;
  }
  public int getRow3() {
    return (int) _sq3.getY()/Constants.SQR_SIZE;
  }
  public int getRow4() {
    return (int) _sq4.getY()/Constants.SQR_SIZE;
  }


  public void setLocation(double x, double y, double x2, double y2, double x3, double y3, double x4, double y4) {
    //each square will be positioned relative to the first
    //this method will be called in each subclass's constructor
    _sq1.setLocation(x, y);
    _sq2.setLocation(x + x2, y + y2);
    _sq3.setLocation(x + x3, y + y3);
    _sq4.setLocation(x + x4, y + y4);
    //the coordinates of _sq2 will be the center of rotation
    //the locaiton of _sq2 will be set accordingly in each piece subclass
    _pieceX = x + x2; 
    _pieceY = y + y2;
    _tetrisPanel.repaint();
  }

  public void setColor(java.awt.Color c) {
    //set the color of each piece, taking in a parameter that will vary with each piece
    _sq1.setBorderColor(java.awt.Color.BLACK);
    _sq2.setBorderColor(java.awt.Color.BLACK);
    _sq3.setBorderColor(java.awt.Color.BLACK);
    _sq4.setBorderColor(java.awt.Color.BLACK);
    _sq1.setFillColor(c);
    _sq2.setFillColor(c);
    _sq3.setFillColor(c);
    _sq4.setFillColor(c);
  }

  public void paint(java.awt.Graphics2D brush) {
    //paint the piece by painting each of its components
    _sq1.paint(brush);
    _sq2.paint(brush);
    _sq3.paint(brush);
    _sq4.paint(brush);

    //paint the ghost piece
    _g1.paint(brush);
    _g2.paint(brush);
    _g3.paint(brush);
    _g4.paint(brush);
  }

  public void setVisible(boolean bool) {
    //make the piece visible through its components
    _sq1.setVisible(bool);
    _sq2.setVisible(bool);
    _sq3.setVisible(bool);
    _sq4.setVisible(bool);

    //make the ghost piece visible
    _g1.setVisible(bool);
    _g2.setVisible(bool);
    _g3.setVisible(bool);
    _g4.setVisible(bool);
  }

  public void moveRight() {
   if (this.checkMove(1) == true) {
    //sq's moveRight() defined in Square class
    _sq1.moveRight();
    _sq2.moveRight();
    _sq3.moveRight();
    _sq4.moveRight();
    //reset the values of the instance variables to match the piece's new location
    _pieceX = _sq2.getX();
    _pieceY = _sq2.getY();
    _tetrisPanel.repaint();
   }
   else {}
  }

  public void moveLeft() {
   if (this.checkMove(-1) == true) {
    //moveLeft() defined in Square clas
    _sq1.moveLeft();
    _sq2.moveLeft();
    _sq3.moveLeft();
    _sq4.moveLeft();
    _pieceX = _sq2.getX();
    _pieceY = _sq2.getY();
    _tetrisPanel.repaint();
   }
  }

  public void moveDown() {
   if (this.checkMoveDown() == true) {
    //moveDown() defined in Square clas
    _sq1.moveDown();
    _sq2.moveDown();
    _sq3.moveDown();
    _sq4.moveDown();
    _pieceX = _sq2.getX();
    _pieceY = _sq2.getY();
    _tetrisPanel.repaint();
   }
   else {}
  }

  public void moveDownAllTheWay() {
    //instead of being called when the timer ticks, this will be called when the space bar is pressed
    while (this.checkMoveDown() == true) { //while the piece can still move down
      this.moveDown(); //move the piece down
      _tetrisPanel.repaint();
    }
  }


  public void rotatePiece() {
    java.awt.Point centerOfRotation = new java.awt.Point();
    //center of rotation will be the x and y coordinates of _sq2 in each piece
    //I created the pieces so the coordinates _sq2 will be the center of rotation coordinates
    centerOfRotation.x = (int) _pieceX;
    centerOfRotation.y = (int) _pieceY;
    java.awt.Point location = new java.awt.Point();

    //generate locations for each square
    int xLocation1 = centerOfRotation.x - centerOfRotation.y + (int) _sq1.getY();
    int yLocation1 = centerOfRotation.x + centerOfRotation.y - (int) _sq1.getX();
    int xLocation2 = centerOfRotation.x - centerOfRotation.y + (int) _sq2.getY();
    int yLocation2 = centerOfRotation.x + centerOfRotation.y - (int) _sq2.getX();
    int xLocation3 = centerOfRotation.x - centerOfRotation.y + (int) _sq3.getY();
    int yLocation3 = centerOfRotation.x + centerOfRotation.y - (int) _sq3.getX();
    int xLocation4 = centerOfRotation.x - centerOfRotation.y + (int) _sq4.getY();
    int yLocation4 = centerOfRotation.x + centerOfRotation.y - (int) _sq4.getX();
    
    if (this.checkRotation(xLocation1, yLocation1, _sq1) == true
	&& this.checkRotation(xLocation2, yLocation2, _sq2) == true
	&& this.checkRotation(xLocation3, yLocation3, _sq3) == true
	&& this.checkRotation(xLocation4, yLocation4, _sq4) == true) {
	 //if each square can rotate, rotate all of them
	  _sq1.setLocation(xLocation1, yLocation1);
	  _sq2.setLocation(xLocation2, yLocation2);
	  _sq3.setLocation(xLocation3, yLocation3);
	  _sq4.setLocation(xLocation4, yLocation4);
	  _tetrisPanel.repaint();
    }//end if
  }//end rotatePiece()


  public boolean checkSquareRotation(Square sq, int newX, int newY) {
     if (_tetrisPanel.checkMove(newY/Constants.SQR_SIZE, newX/Constants.SQR_SIZE) == true) {   
      return true;
     }
     else {
      return false;
     }
  }


    public boolean checkRotation(int newX, int newY, Square sq) {
      if (this.checkSquareRotation(sq, newX, newY) == true) {
	return true;
      }
    else {
      return false;
    }
  }

   
  //this will be used to check if a square can move left or right
  //the integer parameter is used to determine a check for left or right
  //if i = 1, we are checking right; if i = -1, we are checking left
  public boolean checkSquareMove(Square sq, int i) {
     int newX, newY;
     newX = (int) sq.getX() + i*Constants.SQR_SIZE;
     newY = (int) sq.getY();
     if (_tetrisPanel.checkMove(newY/Constants.SQR_SIZE, newX/Constants.SQR_SIZE) == true) {   
      return true;
     }
     else {
      return false;
     }
  }
  
  //integer i is explained in the above method
  public boolean checkMove(int i) {
    if (this.checkSquareMove(_sq1, i) == true
	&& this.checkSquareMove(_sq2, i) == true
	&& this.checkSquareMove(_sq3, i) == true
	&& this.checkSquareMove(_sq4, i) == true){
      return true;
    }
    else {
      return false;
    }
  }

  public boolean checkSquareMoveDown(Square sq) {
     int newX, newY;
     newX = (int) sq.getX();
     newY = (int) sq.getY() + Constants.SQR_SIZE;
     if (_tetrisPanel.checkMove(newY/Constants.SQR_SIZE, newX/Constants.SQR_SIZE) == true) {  
      return true;
     }
     else {
      return false;
     }
  }

  public boolean checkMoveDown() {
    if (this.checkSquareMoveDown(_sq1) == true
	&& this.checkSquareMoveDown(_sq2) == true
	&& this.checkSquareMoveDown(_sq3) == true
	&& this.checkSquareMoveDown(_sq4) == true){
      return true;
    }
    else {
      return false;
    }
  }

  public void addToArray() {
    //adds piece to array by adding the squares to array after they are finished falling
    _tetrisPanel.addPieceToArray(_sq1, (int) _sq1.getY()/Constants.SQR_SIZE, (int) _sq1.getX()/Constants.SQR_SIZE);
    _tetrisPanel.addPieceToArray(_sq2, (int) _sq2.getY()/Constants.SQR_SIZE, (int) _sq2.getX()/Constants.SQR_SIZE);
    _tetrisPanel.addPieceToArray(_sq3, (int) _sq3.getY()/Constants.SQR_SIZE, (int) _sq3.getX()/Constants.SQR_SIZE);
    _tetrisPanel.addPieceToArray(_sq4, (int) _sq4.getY()/Constants.SQR_SIZE, (int) _sq4.getX()/Constants.SQR_SIZE);
  }



} //end class