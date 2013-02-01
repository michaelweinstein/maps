package Tetris;

/**
 * It's time for Tetris! This is the  main class to get things started.
 * The main method of this application calls the App constructor. You 
 * will need to fill in the constructor to instantiate your Tetris game.
 *
 * This is the top level class. It inherits a JFrame and instantiates a main panel
 *
 * Added "bells and whistles": my program shows a "ghost piece" (where the piece will land) and
 * shows the next piece that will appear
 *
 *
 * @author abok
 * Did you discuss your design with another student?
 * If so, list their login here:
 *
 */

public class App extends javax.swing.JFrame {

	public App() {
	  super("Tetris"); //text on top of window will display: Tetris
	  this.setDefaultCloseOperation(EXIT_ON_CLOSE); //program will exit and window will close when top right exit button is clicked
	  MainPanel _mainPanel = new MainPanel(); //instantiate a new main panel
	
	  this.add(_mainPanel); //add the main panel to the JFrame

	  this.pack(); //resize frame to fit panels
	  this.setVisible(true); //show the Frame

	}

	/*Here's the mainline!*/
	public static void main(String[] argv) {
		new App();
	}

}
