/**
 * 
 */
package view;
import javax.swing.JFrame;
import java.awt.*;

//import view.ScoreView;
//import view.ButtonView;
import view.BoardView;

import java.awt.event.*;

/**
 * @author Frank J. Mitropoulos
 *
 */
public class GameView extends JFrame {
	// Create the HUD Panel
	// Create the Board
	
	
	private static final long serialVersionUID = 1L;
	private ScoreView scoreView;
	private ButtonView buttonView;
	private BoardView boardView;
	private levelManager levelMan; 	// LCA  = added level manager
	private int score;
	
	// Setting default to 8x8
	
	int rows = 8, cols = 8;
	int width, height;

	ActionListener newGameListener;
	ActionListener tileTouchedListener;
	
	// LCA - added
	private int threshold;
	ActionListener levelListener;
	ActionListener hintListener;
	
	/**
	 * @param title
	 * @throws HeadlessException
	 */
	
	// LCA added to parameter: levelListener, threshold and hintListener
	public GameView(String title, int rows, int cols, MouseListener listener, ActionListener newGameListener, ActionListener tileTouched, ActionListener levelListener, int threshold, ActionListener hintListener) throws HeadlessException {
		super(title);
		setResizable(false);
		
		levelMan = new levelManager(threshold);
		
		width = 400;
		height = 500;
		score = 0;
		this.rows = rows;
		this.cols = cols;
		
		this.newGameListener = newGameListener;
		this.tileTouchedListener = tileTouched;
		
		// Set up some reasonable sizes for the gameview
		setBounds(100,50,width, height);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		scoreView = new ScoreView();
		updateScoreLevel(threshold);	// LCA - added to call function to get score and level updated paramenters
    	add(scoreView, BorderLayout.NORTH);
		
    	// LCA - added levelListener and hintListener
    	buttonView = new ButtonView(newGameListener, levelListener, hintListener);
    	add(buttonView, BorderLayout.SOUTH);
    	
    	// LCA - set threshold
    	this.threshold = threshold;
		boardView = new BoardView(rows,cols, tileTouchedListener, threshold);
		boardView.setThreshold(threshold);
		add(boardView, BorderLayout.CENTER);
		
		// LCA - update the level 
		updateLevel(threshold);
		
		setVisible(true);
				
	}
	
	// Delegate to boardView
	public boolean isMoveAvailable() {
		// TODO you need to delegate this behavior to the boardView
		if (boardView.isMoveAvailable()){
			return true;
		}
		else {
			return false;
		}
	}
	
	// Call this method to start a new Game
	
	public void newGame(int threshold) {
		// Recreate some components and update the GUI.
		
		Container c = getContentPane();
		c.remove(boardView);
		c.invalidate();
		pack();

		boardView = null;
		score = 0;
		// bonus = 5;
		
		// LILLY 
		// scoreView.updateScore(score);
		scoreView.clearScore();
		
		// LILLY added thresh
		boardView = new BoardView(rows, cols, tileTouchedListener, threshold);
		
		// LILLY level stuff
		updateLevel(threshold);
		updateScoreLevel(threshold);
		
		add(boardView, BorderLayout.CENTER);
		pack();
		revalidate();
		setBounds(100,50,width, height);
		
		System.out.println(boardView);  // debug
	}
	
	public void processTouchedTile(TileView tv) {
		// TODO
		// You need to implement this method. It is called when a tileview is touched
		System.out.println("GameView == processing tile touch");
		
		// LILLY
		int x = boardView.processTouchedTile(tv);
		scoreView.updateScore(x);
		System.out.println(boardView); // debug
	}
	
	// LILLY 
	public void updateLevel(int threshold){
		this.threshold = threshold;
		boardView.setThreshold(threshold);
		System.out.println(threshold);
	}
	// LILLY 
	public void updateScoreLevel(int threshold){
		scoreView.setThreshold(threshold);
		scoreView.setNumBonus(levelMan.getNumBonus());
		scoreView.setPtsBonus(levelMan.getPtsBonus());
		scoreView.setPtsRegular(levelMan.getPtsRegular());
	}
	
	public void processHint() {
		boardView.processHint();
	}

}
