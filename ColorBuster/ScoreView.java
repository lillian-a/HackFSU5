/**
 * 
 */
package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.*;


/**
 * @author Frank J. Mitropoulos
 * 
 *  A very simple score panel
 *  call updateScore and pass in the score to update the display
 */
public class ScoreView extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private static int threshold;
	private static int bonus;
	private static int pointsReg;
	private static int pointsBonus;
	
	private JLabel scoreLabel;
	private int saveScore;
	
	public ScoreView() {
		saveScore = 0;
		String s = "Score: "+Integer.toString(saveScore);
		scoreLabel = new JLabel(s);
		add(scoreLabel);

	}
	public void setupScoreView(int threshold, int bonus, int pointsBonus, int pointsReg) {
		setThreshold(threshold);
		setNumBonus(bonus);
		setPtsBonus(pointsBonus);
		setPtsRegular(pointsReg);
		
	}
	// LCA - commented out
	//public void updateScore(int score) {
	// Lilly add +=
	//	saveScore += score;
	//	scoreLabel.setText("Score: " + saveScore);
	//}
	
	// LCA - new update score function
	 public void updateScore(int tiles) {
		int s = calculateScore(tiles);
		saveScore += s;
		scoreLabel.setText("Score: " + saveScore);
	}
	
	 // LCA - calculates additional score to add to original
	public int calculateScore(int tiles) {		
		int b = 0;
		int s = 0;
		if(tiles >= bonus) {
			if(tiles == bonus){
				b = pointsBonus;
			} else {
				b = (tiles - bonus)*pointsBonus;
			}
		}
		s = tiles*pointsReg;
		s +=b;
		// System.out.println(s); // LCA - for debug
		return s;
	}
	
	// clears score
	public void clearScore() {
		saveScore = 0;
		scoreLabel.setText("Score: " + saveScore);
	}
	
	// setter functions - for changing levels
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	public void setNumBonus(int bonus) {
		this.bonus = bonus;
	}
	public void setPtsBonus(int pointsBonus) {
		this.pointsBonus = pointsBonus;
	}
	public void setPtsRegular(int pointsReg) {
		this.pointsReg = pointsReg;
	}
	
}
