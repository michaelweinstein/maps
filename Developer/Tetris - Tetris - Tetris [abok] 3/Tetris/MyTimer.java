package Tetris;

/**
 * This is the MyTimer class
 * it provides a timer to be used to make our pieces move
 * and check other things that are described below
 * 
 * abok
 */

public class MyTimer extends javax.swing.Timer {

  private TetrisPanel _tetrisPanel;
  private PieceProxy _proxy;

  public MyTimer(TetrisPanel tp, PieceProxy proxy) {
    //400 is approximate number of milliseconds between ticks
    //null is value of ActionLisener (subject to change)
    super(400, null); 
    //set up associations with tetrisPanel and piece proxy
    _tetrisPanel = tp;
    _proxy = proxy;
    this.addActionListener(new TetrisListener());
  }

  private class TetrisListener implements java.awt.event.ActionListener {

    public void actionPerformed(java.awt.event.ActionEvent e) {
    
    //first check to see if the game is over
    _tetrisPanel.gameOver();

    //piece will move down on every tick
    _proxy.moveDown();
    
    
    //if a piece is finished moving down, generate a new piece
    if (_proxy.checkMoveDown() == false) {
     _tetrisPanel.newPiece();
    }
    _tetrisPanel.setGhostSquare();
    //check for full lines every tick
    _tetrisPanel.clearLine();
    
    _tetrisPanel.repaint(); //repaint the panel (calls paintComponent(...) in the tetris panel)
    
    }//end actionPerformed
  
  } //end private class TetrisListener 
}

