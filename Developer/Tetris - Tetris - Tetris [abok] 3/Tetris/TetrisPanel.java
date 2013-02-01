package Tetris;
import java.awt.event.KeyEvent;
/**
 * This is the TetrisPanel class - it extends a JPanel
 * 
 * 
 *
 * abok
 * 
 */

public class TetrisPanel extends javax.swing.JPanel {

  private Square[][] _squares;
  private Square _borderSquare;
  private MyTimer _myTimer;
  private PieceFactory _factory;
  private Piece _currentPiece, _nextPiece;
  private PieceProxy _proxy;
  private int _int, _score, _lines, _dist, _dist2, _dist3, _dist4;
  private MyLabel _lbl;

  public TetrisPanel(MyLabel lbl) {
    super();
    java.awt.Dimension size = new java.awt.Dimension(Constants.NUM_COLS*Constants.SQR_SIZE, Constants.NUM_ROWS*Constants.SQR_SIZE); //set size of drawing panel  
    this.setPreferredSize(size);//helps Swing resize panels so they fit
    this.setSize(size);
    this.setBackground(java.awt.Color.BLACK); //set the background color to black
  
    _squares = new Square[Constants.NUM_ROWS][Constants.NUM_COLS]; //new array
    this.setBounds(); //defined below
    
    _lbl = lbl; //set up association with the label
    _int = 0; //to be used in the key listener for pausing
    _score = 0; //score initially set to zero
    _lines = 0; //number of lines cleared initially set to zero
    _lbl.setText("Lines Cleared: " + _lines + ".   Score: " + _score + ".");

    _factory = new PieceFactory(this); //new factory
    _currentPiece = _factory.getPiece(); //current piece has a reference to the piece created
    _nextPiece = _factory.getPiece(); //this will be our second piece and the next piece in line
    _proxy = new PieceProxy(); //new proxy
    _proxy.setPiece(_currentPiece); //proxy now has a reference to the current piece
    this.showNextPiece(); //shows the outline of the next piece
 
    _myTimer = new MyTimer(this, _proxy); //instantiate our timer
    _myTimer.start(); //start the timer

    //these will be used to set the location of the ghost piece
    _dist = 0;
    _dist2 = 0;
    _dist3 = 0;
    _dist4 = 0;


    this.addKeyListener(new MyKeyListener()); //add a key listener to the panel
    this.setFocusable(true); //enables the panel's key listener
    this.grabFocus();
  } //end constructor

  public void setGhostSquare() {
    //first, loop through every row in each square's column
    //and calculate the distance from each square to the nearest square (vertically) in the array
    for (int col = _proxy.getCol(); col == _proxy.getCol(); col++) {
	    for (int row = 2; row < Constants.NUM_ROWS; row++) {
	      if (_squares[row][_proxy.getCol()] != null) {
		_dist = (row - _proxy.getRow() - 1)*Constants.SQR_SIZE;
		break;
	      }
	    } //end nested for
    } //end for
    for (int col = _proxy.getCol2(); col == _proxy.getCol2(); col++) {
	    for (int row = 2; row < Constants.NUM_ROWS; row++) {
	      if (_squares[row][_proxy.getCol2()] != null) {
		_dist2 = (row - _proxy.getRow2() - 1)*Constants.SQR_SIZE;
		break;
	      }
	    } //end nested for
    } //end for
    for (int col = _proxy.getCol3(); col == _proxy.getCol3(); col++) {
	    for (int row = 2; row < Constants.NUM_ROWS; row++) {
	      if (_squares[row][_proxy.getCol3()] != null) {
		_dist3 = (row - _proxy.getRow3() - 1)*Constants.SQR_SIZE;
		break;
	      }
	    } //end nested for
    } //end for
    for (int col = _proxy.getCol4(); col == _proxy.getCol4(); col++) {
	    for (int row = 2; row < Constants.NUM_ROWS; row++) {
	      if (_squares[row][_proxy.getCol4()] != null) {
		_dist4 = (row - _proxy.getRow4() - 1)*Constants.SQR_SIZE;
		break;
	      }
	    } //end nested for
	    //then find the minimum distance of the four squares and a piece in the array
	    //and use that minimum distance to set the location of the ghost piece
	    int d1 = java.lang.Math.min(_dist, _dist2);
	    int d2 = java.lang.Math.min(_dist3, _dist4);
	    int d3 = java.lang.Math.min(d1, d2);
	    _proxy.setGhostLocation(d3);
    } //end for
  } //end setGhostSquare()

  public void newPiece() { //called when a piece falls all the way
    this.setBounds(); //essentially resets the outline of the next piece
    _proxy.addToArray(); //add current piece to the array
    _currentPiece = _nextPiece; //update the current piece
    _proxy.setPiece(_currentPiece); //update the proxy's reference to the piece	
    _nextPiece = _factory.getPiece(); //create a new next piece
    this.showNextPiece(); //show outline of next piece
  }

  //this shows the outline of the next piece on the right side of the panel
  public void showNextPiece() {
    if (_factory.getNextPiece() == 1) {
      _squares[2][13].setBorderColor(java.awt.Color.PINK);
      _squares[3][13].setBorderColor(java.awt.Color.PINK);
      _squares[3][12].setBorderColor(java.awt.Color.PINK);
      _squares[4][12].setBorderColor(java.awt.Color.PINK);
    }
    if (_factory.getNextPiece() == 2) {
      _squares[2][12].setBorderColor(java.awt.Color.PINK);
      _squares[3][12].setBorderColor(java.awt.Color.PINK);
      _squares[3][13].setBorderColor(java.awt.Color.PINK);
      _squares[4][13].setBorderColor(java.awt.Color.PINK);
    }
    if (_factory.getNextPiece() == 3) {
      _squares[2][13].setBorderColor(java.awt.Color.PINK);
      _squares[3][12].setBorderColor(java.awt.Color.PINK);
      _squares[3][13].setBorderColor(java.awt.Color.PINK);
      _squares[4][13].setBorderColor(java.awt.Color.PINK);
    }
    if (_factory.getNextPiece() == 4) {
      _squares[2][12].setBorderColor(java.awt.Color.PINK);
      _squares[2][13].setBorderColor(java.awt.Color.PINK);
      _squares[3][12].setBorderColor(java.awt.Color.PINK);
      _squares[3][13].setBorderColor(java.awt.Color.PINK);
    }
    if (_factory.getNextPiece() == 5) {
      _squares[2][13].setBorderColor(java.awt.Color.PINK);
      _squares[3][13].setBorderColor(java.awt.Color.PINK);
      _squares[4][13].setBorderColor(java.awt.Color.PINK);
      _squares[4][12].setBorderColor(java.awt.Color.PINK);
    }
    if (_factory.getNextPiece() == 6) {
      _squares[2][12].setBorderColor(java.awt.Color.PINK);
      _squares[3][12].setBorderColor(java.awt.Color.PINK);
      _squares[4][12].setBorderColor(java.awt.Color.PINK);
      _squares[4][13].setBorderColor(java.awt.Color.PINK);
    }
    if (_factory.getNextPiece() == 7) {
      _squares[2][12].setBorderColor(java.awt.Color.PINK);
      _squares[3][12].setBorderColor(java.awt.Color.PINK);
      _squares[4][12].setBorderColor(java.awt.Color.PINK);
      _squares[5][12].setBorderColor(java.awt.Color.PINK);
    }
  }

  public void addPieceToArray(Square sq, int row, int col) { //adds the piece to the array
    _squares[row][col] = sq;
  }

  public void setBounds() { //instantiates the gray squares that will act as boundaries for the playing board
    for (int col = 0; col < Constants.NUM_COLS; col++) { //loops through columns
      for (int row = 0; row < Constants.NUM_ROWS; row++) { //nested for loop loops through rows
	      //the following if statements creates borders in the right places
	      if (col < Constants.NUM_COLS && row < 2) {  
		this.createBorderSquare(row, col);
	      }
	      else if (col < Constants.NUM_COLS && row > Constants.NUM_ROWS - 3 && row < Constants.NUM_ROWS) {
		this.createBorderSquare(row, col);
	      }
	      else if (col < 2 && row < Constants.NUM_ROWS - 2 && row > 1) {
		this.createBorderSquare(row, col);
	      }
	      else if (row > 1 && row < Constants.NUM_ROWS - 2 && col > Constants.NUM_COLS - 3 && col < Constants.NUM_COLS) {
		this.createBorderSquare(row, col);
	      }
      } //end row loop
    } //end column loop
  } //end setBounds()
 
  public void createBorderSquare(int row, int col) {
    _borderSquare = new Square(this);
    _borderSquare.setBorderColor(java.awt.Color.BLACK);
    _borderSquare.setFillColor(java.awt.Color.GRAY);
    _borderSquare.setLocation(col*Constants.SQR_SIZE, row*Constants.SQR_SIZE);
    _squares[row][col] = _borderSquare; //add bordersquare to the array
  }

  public void paintComponent(java.awt.Graphics g) {
    super.paintComponent(g); // JPanel magic
    java.awt.Graphics2D brush = (java.awt.Graphics2D) g;//creates brush and casts it to java.awt.Graphics2D
    _proxy.paint(brush);
     for (int col = 0; col < Constants.NUM_COLS; col++) { //loops through first two columns
       for (int row = 0; row < Constants.NUM_ROWS; row++) { //nested for loop loops through first two rows 
	  if (_squares[row][col] != null) { //if a square exists, paint it
	    _squares[row][col].paint(brush);
	  }
      } //end nested for
    } //end for
  } //end paintComponent

  public boolean checkMove(int row, int col) { //checks to make sure move is valid, will be called in Piece class
	  Square square = _squares[row][col]; //define as square at the position in the array defind by the parameters
	    if (square == null || row < 2) { //if square does not exist, then the move is valid
	      return true;
	    }
	    else { //if a square exists, the move is not valid
	      return false;
	    }    
    } //end checkMove()

  public void clearLine() {
    int i = 0;
    for (int row = 0; row < Constants.NUM_ROWS; row++) { //loops through rows
      for (int col = 0; col < Constants.NUM_COLS; col++) { //nested for loop loops through columns
	  if (_squares[row][col] != null && _squares[row][col].getFillColor() != java.awt.Color.GRAY) {	
	    i = i + 1;
	  } //end if
      }//end nested for loop
      if (i == Constants.NUM_COLS - 4) { //i.e. if all of the squares in the row are filled
    	  for (int col1 = 0; col1 < Constants.NUM_COLS; col1++) { //for each column in that row:
    		  if (_squares[row][col1].getFillColor() != java.awt.Color.GRAY) { 
    			  _squares[row][col1] = null; //set the value of that square to null
			  _lines = _lines + 1;			  
			  int lines = _lines;
			  if (lines/10 == 4) { //if the user clears four lines at once, he or she get a tetris and are rewarded more points
			    _score = 8*(_lines/10 + 5);
			  }
			  else { //if the user clear less than four lines at once, he or she recieves the standard amount of points
			    _score = 2*(_lines/10 + 5);	
			  }
			  _lbl.setText("Lines Cleared: " + _lines/10 + ".   Score: " + _score + ".");
    			  this.repaint(); //when the panel is repainted, the squares will disappear
		  
			    for (int row2 = row-1; row2 > 1; --row2) { //for every row above and including the cleared row:
			      Square sq = _squares[row2][col1];
			      if (sq != null && sq.getFillColor() != java.awt.Color.GRAY) { //if the square is not null and it is not a border square:
				sq.moveDown(); //move the square down graphically
				_squares[row2 + 1][col1] = sq; //move the square down in the array
			      }
			      else if (sq == null && row2 != Constants.NUM_ROWS - 3) { //if the square is null:
				_squares[row2 + 1][col1] = null; //set the square below it to null;
			      }
			    } //end for    
		  }//end if
    	  } //end for
      }
      i = 0;
    } //end for loop
  } //end clearLine()


  public void gameOver() {
    for (int col = 2; col < Constants.NUM_COLS - 2; col++) {
      Square sq = _squares[2][col];
      if (sq != null) { //if a square exists in the top row:
	_lbl.setText("Game Over." + " Lines Cleared: " + _lines/10 + ".   Score: " + _score + "."); //set the text to game over
	_myTimer.stop(); //stop the timer
	_int = -1000; //for pausing purposes
	this.setBounds(); //call this method so the boundary squares stay gray
      }
    }
  }


  //inner class MyKeyListener
  private class MyKeyListener implements java.awt.event.KeyListener {


    //override keylistener's key pressed method so something actually happens when certain keys are pressed
    public void keyPressed(KeyEvent e) {
      int kCode = e.getKeyCode();
      if (kCode == KeyEvent.VK_RIGHT) { //if the right arrow key is pressed, move the piece one square to the right
	if (_int%2 == 0 && _int > -1) { //if the game is not paused:
	  _proxy.moveRight();
	}
	else {}
      }
      else if (kCode == KeyEvent.VK_LEFT) { //if the left arrow key is pressed, move the piece one square to the left
	if (_int%2 == 0 && _int > -1) { //if the game is not paused:
	  _proxy.moveLeft();      			   
	}
	else {}
      }
      else if (kCode == KeyEvent.VK_DOWN) { //if the down arrow key is pressed, move the piece one square down
	if (_int%2 == 0 && _int > -1) { //if the game is not paused:
	  _proxy.moveDown();
	}
	else {}
      }
      else if (kCode == KeyEvent.VK_UP) { //if the up arrow key is pressed, rotate the piece
	if (_int%2 == 0 && _int > -1) { //if the game is not paused:
	  _proxy.rotate();
	}
	else {}
      }
      else if (kCode == KeyEvent.VK_SPACE) { //if space bar is pressed, move the piece all the way down
	if (_int%2 == 0 && _int > -1) { //if the game is not paused:
	  _proxy.moveDownAllTheWay();
	}
	else {}
      }
      else if (kCode == KeyEvent.VK_P) { //if 'p' is pressed, pause or resume the game
	_int = _int + 1;
	if (_int%2 != 0) { //if p has been pressed once or an odd number of times, pause the game
	_myTimer.stop();
	_lbl.setText("Paused");
	}
	else if (_int%2 == 0 && _int > -1) { //if p has been pressed an even number of times, resume the game
	// the check for _int > -1 is to see if the game is over
	_myTimer.start();
	_lbl.setText("Lines Cleared: " + _lines/10 + ".   Score: " + _score + ".");
	}
	
      }
      else if (kCode == KeyEvent.VK_S) { //if the left arrow key is pressed, move the piece one square to the left
	_myTimer.start();
      }    
    }

    public void keyReleased(KeyEvent e) {}
    
    public void keyTyped(KeyEvent e) {}

  }//end private MyKeyListener class

} //end class