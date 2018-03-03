/**
 * 
 */
package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.util.Timer;
import java.util.TimerTask;

import model.Board;
import model.Tile;
import view.TileView;

/**
 * @author Frank J. Mitropoulos
 * 
 * View class over the board model.
 *
 *
 */
public class BoardView extends JPanel {
	
	
	private static final long serialVersionUID = 1L;
	private Board b;
	private int rows;
	private int cols;
	private int tileSize;
	private ActionListener listener;
	private int spacer;
	
	// LCA
	private int threshold;
	
	// LCA - for hint functions
	private Image originalImg;
	private Image hintImg;
	private String hintString = "Resources/images/Black.jpg";
	Timer timer;
	private int tr;
	private int tc;
	
	
	private TileView [][] tileGrid;
	// Create  BoardView with rows and cols and lis is the actionListener for all the TileViews
	
	// LCA - added threshold
	public BoardView(int rows, int cols, ActionListener lis, int threshold) {
	    spacer = 4;
		listener = lis;
		Dimension s = getPreferredSize();
		System.out.println("BoardView: " + s);
		this.rows = rows;
		this.cols = cols;	
		this.threshold = threshold;
		
		int totalSpace = (cols+1) * spacer;
		System.out.println("totalspace : " + totalSpace);
		
		tileSize = (s.width-totalSpace) / cols;
		System.out.println("TileSize : " + tileSize);
		
		// I'm not using a layout manager here since I'm using XY to layout the TileViews
		// Quick and simple
		
		setLayout(null);
		tileGrid = new TileView[rows][cols];
		b = new Board(rows,cols, threshold);
		for (int row=0; row<rows; row++) {
			for (int col=0; col<cols; col++) {
				TileView tv = new TileView(b.tileAt(row, col));
				add(tv);
				tileGrid[row][col] = tv;
			
				tv.setBounds((tileSize * col + spacer), (tileSize * ((rows-1)-row) + spacer),
							             tileSize, tileSize);
				tv.setPosition(tileSize * col + spacer, (tileSize * ((rows-1)-row) + spacer));
				
				tv.addListener(listener);

				
			}
		}
		
	}
	
// Call this method whenever you want to update the boardView on the display from the current status of the board
	
	public void updateBoardViewFromBoard() {
		System.out.println("In updateBoardViewFromBoard...");
		TileView tv;
		removeAll();
		tileGrid = new TileView[rows][cols];
		for (int row=0; row<rows; row++) {
			for (int col=0; col<cols; col++) {
				if (b.hasTileAt(row,col)) {
					 tv = new TileView(b.tileAt(row, col));
				}
				else  {
					tv = new TileView(row,col);
					
				}
				add(tv);
				tileGrid[row][col] = tv;
			
			
				tv.setBounds((tileSize * col + spacer), (tileSize * ((rows-1)-row) + spacer),
							             tileSize, tileSize);
				tv.setPosition(tileSize * col + spacer, (tileSize * ((rows-1)-row) + spacer));
				
				tv.addListener(listener);

				
			}
		}
		
		
	}
	

	// returns the score which would be however you decide to score matched tiles.
	// You don't have to handle the score this way -- do it however you decide works best for you
	
	
	public int processTouchedTile(TileView tv) {
		// TODO
		// You implement this method.
		// locate the neighbors, collapse, and then call updateBoardViewFromBoard() to update the display
		
		// LCA
		HashSet<Tile> matches = new HashSet<Tile>();
		b.locateNeighbors(tv.getRow(), tv.getCol(), tv.getColor(), matches);
		//if (matches.size() >= 3){
		if (matches.size() >= threshold){
			b.removeMatchingTiles(matches);
			b.collapseColumns();
			
			// Don' forget to call updateBoardViewFromBoard() at the end of this method
			updateBoardViewFromBoard();
			System.out.println(b);

			// return 100; // you figure out the score
			return matches.size();
		}
		else {
			return 0;
		}
	}

	public String toString() {
		return b.toString();
		
		// Just call b.toString().
		// The commented code below you can use to verify that the BoardView is actually synchronized with the Board
		
//		String v = "";
//		for (int row=rows-1;row>=0;row--) {
//			for (int col=0;col<cols;col++)  {
//				if (tileGrid[row][col] != null)
//					v += tileGrid[row][col].toString();
//				else
//					v += "[-----]";
//			}
//			v += "\n";
//		}	return v + b.toString();
	}
	

	public boolean isMoveAvailable() {
		return b.isMoveAvailable();
	}
	
	// set the default size of the boardview to a reasonable size
	public Dimension getPreferredSize() {
		return new Dimension(400,400);
	}
	
	// LCA - for threshold when updating level
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
	// LCA - Hint
	public void processHint() {
		System.out.println("Hi 2");
		mainLoop:
		for (int col=0; col<cols;col++) {
			for (int row=0; row< rows; row++) {
				HashSet<Tile> matches = new HashSet<Tile>();
				b.locateNeighbors(tileGrid[row][col].getRow(), tileGrid[row][col].getCol(), tileGrid[row][col].getColor(), matches);
				if (matches.size() >= threshold){
					originalImg = tileGrid[row][col].getImg();
					createHintImage();
					tr = row;
					tc = col;
					blink();
					break mainLoop;
				}
			}
		}
	}
	
	// LCA - Create Hint Image
	private void createHintImage(){
		hintImg = null;
		try {
			hintImg = ImageIO.read(new FileImageInputStream(new File(hintString)));
		} catch (IOException ex) {
		}
	}
	// LCA - Change Hint Image
	private void blink(){
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			int count = 3;
			public void run() {
				if (count > 0){
					if(count%2 == 0){
						System.out.println("Time's up!");
					    tileGrid[tr][tc].blinkImg(hintImg);
					}
					else{
						System.out.println("Time's up!");
					    tileGrid[tr][tc].blinkImg(originalImg);
					}
				    System.out.println(count);
				    count--;
				}
				else{
					this.cancel();
				}
			}
		}, 1000, 300);
	}
	
}