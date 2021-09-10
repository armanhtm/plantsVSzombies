/**
 * main class of program
 * @author alireza karimi
 * 
 */
public class Main {
	
	/**
	 * main method of program - this will start the program
	 * @param args
	 */
	public static void main(String[] args) {
		
		Game game = new Game();
		//connecting the game to server
		game.connectToServer();
		ThreadPool.execute(game);

	}
	
}
