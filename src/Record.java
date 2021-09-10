import java.io.Serializable;

/**
 * this class contains every record (username and score) in score board
 * @author alireza karimi
 *
 */
public class Record implements Serializable {
	private String username;
	private int score;
	
	/**
	 * creating a new record
	 * @param username username
	 * @param score score
	 */
	public Record(String username, int score){
		this.username = username;
		this.score = score;
	}
	
	/**
	 * getting username
	 * @return username
	 */
	public String getUsername(){
		return username;
	}
	
	/**
	 * getting score
	 * @return score
	 */
	public int getScore(){
		return score;
	}
	
	/**
	 * setting score
	 * @param score score
	 */
	public void setScore(int score){
		this.score = score;
	}

}
