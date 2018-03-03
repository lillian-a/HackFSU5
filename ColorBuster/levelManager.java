package view;
/**
 * @author Lillian Arguelles
 *
 */
public class levelManager {
	
	/* LEVEL BREAKDOWN
	 * 
	 * Level:				1	=	EASY
	 * # Tiles for Match:	3
	 * # Tiles for Bonus:	5
	 * Points For Regular:	5
	 * Points For Bonus:	10
	 * 
	 * Level:				2	=	MEDIUM
	 * # Tiles for Match:	4
	 * # Tiles for Bonus:	6
	 * Points For Regular:	5
	 * Points For Bonus:	10
	 * 
	 * 	Level:				3	=	HARD
	 * # Tiles for Match:	5
	 * # Tiles for Bonus:	7
	 * Points For Regular:	5
	 * Points For Bonus:	10
	 * 
	 */
	
	private static int threshold;	// amount of tiles needed for valid match
	private static int bonus;		// amount of tiles needed for bonus to be applied
	private static int pointsReg;	// points for regular non-bonus match tiles
	private static int pointsBonus;	// points for bonus tiles
	private static int level;		// level variables are under
	
	// Constructor - sets level by threshold
	// TODO: would be better to set by level than threshold
	public levelManager(int threshold ) {
		setByThreshold(threshold);
		// setByLevel(level);
	}
	
	// getters for level changing
	public int getThreshold() {
		return threshold;
	}
	public int getNumBonus() {
		return bonus;
	}
	public int getPtsBonus() {
		return pointsBonus;
	}
	public int getPtsRegular() {
		return pointsReg;
	}
	
	// getLevel is not currently used. this would be used if set by level implementation was used
	public int getLevel(){
		return level;
	}
	
	// sets variables by level - not currently used - see setByThreshold
	public void setByLevel(int level) {
		if(level == 3){
			this.level = level;
			threshold = 5;
			bonus = 7;
			pointsReg = 5;
			pointsBonus = 10;
		} else if (level == 2) {
			this.level = level;
			threshold = 4;
			bonus = 6;
			pointsReg = 5;
			pointsBonus = 10;
		} else {
			this.level = level;
			threshold = 3;
			bonus = 5;
			pointsReg = 5;
			pointsBonus = 10;
		}
	}
	
	// sets variables by threshold
	public void setByThreshold(int threshold) {
		if(threshold == 5){
			this.threshold = threshold;
			level = 3;
			bonus = 7;
			pointsReg = 5;
			pointsBonus = 10;
		} else if (threshold == 4) {
			this.threshold = threshold;
			level = 2;
			bonus = 6;
			pointsReg = 5;
			pointsBonus = 10;
		} else {
			this.threshold = threshold;
			level = 1;
			bonus = 5;
			pointsReg = 5;
			pointsBonus = 10;
		}
	}
}
