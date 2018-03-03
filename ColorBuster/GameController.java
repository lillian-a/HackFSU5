/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import view.GameView;
import view.TileView;

/**
 * @author Frank J. Mitropoulos
 *
 */
public class GameController {
	// These aren't used, but could be depending on your game and what you want to do
	private int score;
	private int gameStatus;
	private int rows;
	private int cols;
	private final int DEFAULT = 3;
	
	private int moveNumber = 0;
	
	private GameView gameView;
	
	private int threshold;

	/**
	 * Create the GameView and pass in the appropriate listeners
	 */
	public GameController() {
		super();		
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				// default
				threshold = DEFAULT;
				// LCA - changed Title to Color Buster
				// LCA - added new LevelListener(), new HintListener() and threshold
				gameView = new GameView("Color Buster",6,6,null,new NewGameListener(), new TileTouchedListener(), new LevelListener(), threshold, new HintListener());
				gameView.setVisible(true);	
				
			}
		});
		
	}
	
	// Listener used to process touches on TileView
	class TileTouchedListener implements ActionListener {
		@Override 
		public void actionPerformed(ActionEvent event) {
			TileView tv = (TileView) event.getSource();
			System.out.println("Tile touched..." + tv.toString());
			
			// TODO -- you implement this
			// delegate to GameView!
			// LCA - done
			gameView.processTouchedTile(tv);
			
			// LCA =  Comment Out
			// System.out.println("Not implemented yet");
			
			// If no move is available display a message
			
			if (!gameView.isMoveAvailable()) {
				JOptionPane.showMessageDialog(gameView,
					    "No more moves...");
			}
		}
		
	}
	
	// Listener used to process click on New Game Button
	class NewGameListener implements ActionListener {
		@Override 
		public void actionPerformed(ActionEvent event) {
			System.out.println("Starting new game...");
			// LCA =  Comment Out
			// System.out.println("Not yet implemented..");

			// TODO
			// You implement this method. Delegate to GameView!
			// LCA - done
			gameView.newGame(threshold);
		}
		
	}
	// LCA - added for Level changing
	class LevelListener implements ActionListener {
		@Override 
		public void actionPerformed(ActionEvent event) {
			System.out.println("Changing level...");
			JComboBox cb = (JComboBox)event.getSource();
	        String level = (String)cb.getSelectedItem();
	        threshold = Integer.parseInt(level);
	        gameView.updateLevel(threshold);
	        System.out.println("Level Changed To: " + level);
		}
		
	}
	
	// LCA - added for hint functionality
	class HintListener implements ActionListener {
		@Override 
		public void actionPerformed(ActionEvent event) {
			System.out.println("Getting Hint...");
			gameView.processHint();
		}
	}
	
	
}
