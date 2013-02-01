package Tetris;

/**
 * This is the MainPanel class - it extends a JPanel
 * It is instantiated in the App class and
 * it instantiates a QuitButton, JLabel, and TetrisPanel
 * 
 * abok
 * 
 */

public class MainPanel extends javax.swing.JPanel {

  private MyLabel _lbl;
  private TetrisPanel _tetrisPanel;

 public MainPanel() {
    super();
    
    _lbl = new MyLabel(); //instantiate a new label
    _tetrisPanel = new TetrisPanel(_lbl); //instantiate a new tetrispanel
    //set size of main panel
    java.awt.Dimension size = new java.awt.Dimension(Constants.NUM_COLS*Constants.SQR_SIZE, Constants.NUM_ROWS*Constants.SQR_SIZE + 15);   
    this.setLayout(new java.awt.BorderLayout()); //set the layout with a BorderLayout class, which implements LayoutManager2

    this.add(_lbl, java.awt.BorderLayout.NORTH); //add the label to the main panel 
    this.add(_tetrisPanel,java.awt.BorderLayout.CENTER); //add the tetris panel to the main panel

    this.setPreferredSize(size);//helps Swing resize panels so they fit
    this.setSize(size);
    this.setBackground(java.awt.Color.WHITE); //set the background color to white


 } //end constructor



}//end class