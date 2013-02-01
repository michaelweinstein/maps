package Tetris;

/**
* This is the Factory Class,it will randomly generate a subclass of Piece
 * Each piece will have an equally likely chance of being instantiated
 *
 * abok
 * 
 * 
 */

public class PieceFactory {
  
  private TetrisPanel _tetrisPanel;
  private int _nextPiece;

  public PieceFactory(TetrisPanel tp) {
    _tetrisPanel = tp;
  }

  public Piece getPiece() {

    int rand = (int) (Math.random()*7); //generate random number between 0 and 6 to serve as selector
    Piece pc = null; //piece not yet created
    
     //this switch statement randomly generates a piece; each piece has an equal chance of being generated
     //the integer that is updated will be used in the tetris panel to show the outline of the next piece
     switch (rand) {
 
       case 0:
 	pc = new Piece1(_tetrisPanel);
	_nextPiece = 1;
 	break;
       case 1:
 	pc = new Piece2(_tetrisPanel);
	_nextPiece = 2;
 	break;
       case 2:
 	pc = new Piece3(_tetrisPanel);
	_nextPiece = 3;
 	break;
       case 3:
 	pc = new Piece4(_tetrisPanel);
	_nextPiece = 4;
 	break;
       case 4:
 	pc = new Piece5(_tetrisPanel);
	_nextPiece = 5;
 	break;
       case 5:
 	pc = new Piece6(_tetrisPanel);
	_nextPiece = 6;
 	break;
       case 6:
 	pc = new Piece7(_tetrisPanel);	
	_nextPiece = 7;
 	break;
 
     }
     return pc;
   }

   //accessor used to show the outline of the next piece
   public int getNextPiece() {
      return _nextPiece;
   }









}