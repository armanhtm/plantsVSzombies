import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Game class contains all elements of game and their state in every second
 * @author alireza karimi
 *
 */
public class Game extends JFrame implements Runnable {
	
	public static final int HEIGHT = 602;
	public static final int WIDTH = 16 * HEIGHT / 9;
	private String fileBasePath = "files/";
	private Sound gameSound = new Sound("music/gaming.wav");
	
	
	//view variables
	private Graphics2D graphics;
	private BufferedImage backYard;	
	private BufferedImage sunFlowerCard;
	private BufferedImage wallNutCard;
	private BufferedImage peaCard;
	private BufferedImage cherryBombCard;
	private BufferedImage freezePeaCard;
	private BufferedImage jalapenoCard;
	private BufferedImage sunFlowerCardCover;
	private BufferedImage wallNutCardCover;
	private BufferedImage peaCardCover;
	private BufferedImage cherryBombCardCover;
	private BufferedImage freezePeaCardCover;
	private BufferedImage jalapenoCardCover;
	private MouseHandler mouseHandler;
	private BufferStrategy bufferStrategy;
	private BufferedImage pauseMenu;
	private BufferedImage pauseButton;
	private BufferedImage mainMenu;
	private BufferedImage settingMenu;
	private BufferedImage settingMenuHardOff;
	private BufferedImage settingMenuHardOn;
	private BufferedImage settingMenuMusicOff;
	private BufferedImage settingMenuMusicOn;
	private BufferedImage gameOver;
	private BufferedImage win;

	//model variables
	private final int FPS = 30;
	private int numberOfSun = 50;
	private ArrayList<Element> elements; //all available elements are stored in this arraylist
	private long countingFrames = 0;
	private boolean hard = false;
	private boolean sunFlowerCardIsSelected = false;
	private boolean wallNutCardIsSelected = false;
	private boolean peaCardIsSelected = false;
	private boolean cherryBombCardIsSelected = false;
	private boolean freezePeaCardIsSelected = false;
	private boolean jalapenoCardIsSelected = false;
	private boolean sunFlowerCardIsAvailable = false;
	private boolean wallNutCardIsAvailable = false;
	private boolean peaCardIsAvailable = false;
	private boolean cherryBombCardIsAvailable = false;
	private boolean freezePeaCardIsAvailable = false;
	private boolean jalapenoCardIsAvailable = false;
	private boolean plantPosSet = false;
	private int plantPosX;
	private int plantPosY;	
	private double sunFlowerCardFramesLeftToBeAvailable = 0;
	private double wallNutCardFramesLeftToBeAvailable = 0;
	private double peaCardFramesLeftToBeAvailable = 0;
	private double cherryBombCardFramesLeftToBeAvailable = 0;
	private double freezePeaCardFramesLeftToBeAvailable = 0;
	private double jalapenoCardFramesLeftToBeAvailable = 0;
	private boolean lawnMowerCreated = false;
	private boolean endGameStatus;
	private boolean paused = false;
	private boolean goToMain = true;
	private String username;
	private boolean goToSetting = false;
	private boolean goToSettingMenuHardOff = false;
	private boolean goToSettingMenuHardOn = false;
	private boolean goToSettingMenuMusicOff = false;
	private boolean goToSettingMenuMusicOn = false;
	private boolean music = true;
	private boolean zombieFlag = true;
	private boolean musicFlag = true;
	
	private boolean gameHasEnded = false;
	private boolean dataNotSent = true;
	private boolean shovelIsSelected = false;
	private boolean shovelPosSet = false;
	private int shovelPosX;
	private int shovelPosY;
	
	
	//network variables
	private ClientConnection connection;
	private int score = 0;
	
	/**
	 * constructing a new game
	 */
	public Game(){
		
		elements = new ArrayList<>();
		gameSound.play();
		setTitle("Plants Vs Zombies");
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		String path = new File("").getAbsolutePath();
		path = path.concat("/assets/images/");
		
		mouseHandler = new MouseHandler();
		addMouseListener(mouseHandler);
		
		try{
			backYard = ImageIO.read(new File(path.concat("backyard.jpg")));
			
			cherryBombCard = ImageIO.read(new File(path.concat("cherryBombCard.png")));
			freezePeaCard = ImageIO.read(new File(path.concat("freezePeaCard.png")));
			peaCard = ImageIO.read(new File(path.concat("peaCard.png")));
			sunFlowerCard = ImageIO.read(new File(path.concat("sunFlowerCard.png")));
			wallNutCard = ImageIO.read(new File(path.concat("wallNutCard.png")));
			jalapenoCard = ImageIO.read(new File(path.concat("jalapenoCard.jpg")));;
			cherryBombCardCover = ImageIO.read(new File(path.concat("cherryBombCardCover.jpg")));
			freezePeaCardCover = ImageIO.read(new File(path.concat("freezePeaCardCover.jpg")));
			peaCardCover = ImageIO.read(new File(path.concat("peaCardCover.jpg")));
			sunFlowerCardCover = ImageIO.read(new File(path.concat("sunFlowerCardCover.jpg")));
			wallNutCardCover = ImageIO.read(new File(path.concat("wallNutCardCover.jpg")));
			jalapenoCardCover = ImageIO.read(new File(path.concat("jalapenoCardCover.jpg")));
			pauseMenu = ImageIO.read(new File(path.concat("pauseMenu.png")));
			pauseButton = ImageIO.read(new File(path.concat("pauseButton.png")));
			mainMenu = ImageIO.read(new File(path.concat("mainMenu.png")));
			settingMenu = ImageIO.read(new File(path.concat("settingMenu.png")));
			settingMenuHardOff = ImageIO.read(new File(path.concat("settingMenuHardOff.png")));
			settingMenuHardOn = ImageIO.read(new File(path.concat("settingMenuHardOn.png")));
			settingMenuMusicOff = ImageIO.read(new File(path.concat("settingMenuMusicOff.png")));
			settingMenuMusicOn = ImageIO.read(new File(path.concat("settingMenuMusicOn.png")));
			gameOver = ImageIO.read(new File(path.concat("gameOver.png")));
			win = ImageIO.read(new File(path.concat("win.png")));
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		//tripple buffering
		createBufferStrategy(3);
		bufferStrategy = getBufferStrategy();
		
	}
	
	/**
	 * inner class to store network related things
	 * @author alireza karimi
	 *
	 */
	private class ClientConnection{
		
		private Socket socket;
		private DataInputStream dataIntputStream;
		private DataOutputStream dataOutputStream;
		
		/**
		 * constructing a new network inner class
		 */
		public ClientConnection(){
			
			try{
				socket = new Socket("localhost", 5897);
				dataIntputStream = new DataInputStream(socket.getInputStream());
				dataOutputStream = new DataOutputStream(socket.getOutputStream());
				
			}catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		/**
		 * sending username and score data to server
		 */
		public void sendData(){
			if(dataNotSent){
				try{
					dataOutputStream.writeUTF(String.valueOf(score)+"*"+username);
				}catch (Exception ex){
					ex.printStackTrace();
				}		
				
				dataNotSent = false;
			}

		}
	}
	
	/**
	 * connecting to server
	 */
	public void connectToServer(){
		connection = new ClientConnection();
	}
	
	/**
	 * storing save object
	 * @param file save object
	 * @param filePath file path to save
	 */
	public void writeSave(Save file, String filePath){
		
		FileOutputStream fileOut = null;
		
		try{
			fileOut = new FileOutputStream(filePath, false);
 
        } catch(Exception ex){
            ex.printStackTrace();
        }
		
		try{
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(file);
            objectOut.close();
 
        }catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	/**
	 * reading save object from file 
	 * @param filePath file path
	 * @return save object
	 */
	public static Save readSave(String filePath){

		FileInputStream fileIn  = null;
		
		try{
			fileIn = new FileInputStream(filePath);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	
        try{
        	ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Save obj = (Save) objectIn.readObject();
            objectIn.close();     
            return obj;

        } catch (Exception ex){
        	ex.printStackTrace();
        }
        
        return null;

	}
	
	/**
	 * getting number of files with a username in the directory
	 * @param username username
	 * @param filePath file path
	 * @return number of file with a username in the directory
	 */
	private int getNumberOfFiles(String username, String filePath){
		
		int counter = 0;
		
		File directory = new File(filePath);
		File[] directoryList = directory.listFiles();
		if(directoryList != null){
			for(File item : directoryList){
				if(item.getName().contains(username)){
					counter++;
				}
			}
		}
		  
		return counter;
	}
	
	/**
	 * loading all games of a user
	 * @param username username
	 * @param filePath file path
	 * @return a 2D string containing all games of a user
	 */
	private String[][] loadGames(String username, String filePath){
		ArrayList<String> dataList = new ArrayList<>();
		String[][] data = null;
		
		File directory = new File(filePath);
		File[] directoryList = directory.listFiles();
		if(directoryList != null){
			for(File item : directoryList) {
				if(item.getName().contains(username)){
					dataList.add(item.getName());
				}
			}
		}
		
		
		data = new String[dataList.size()][1];
		for(int i = 0; i < dataList.size(); i++){
        	data[i][0] = dataList.get(i);
        }
		
		return data;
		
	}
	
	/**
	 * starting a saved game
	 * @param gameName game name
	 */
	private void startSavedGame(String gameName){
		Save save = readSave(fileBasePath.concat(gameName));
		this.numberOfSun = save.numberOfSun;
		if(save.elements != null){
			this.elements = save.elements;
		}
		this.countingFrames = save.countingFrames;
		this.hard = save.hard;
		sunFlowerCardIsSelected = false;
		wallNutCardIsSelected = false;
		peaCardIsSelected = false;
		cherryBombCardIsSelected = false;
		freezePeaCardIsSelected = false;
		jalapenoCardIsSelected = false;
		this.sunFlowerCardIsAvailable = save.sunFlowerCardIsAvailable;
		this.wallNutCardIsAvailable = save.wallNutCardIsAvailable;
		this.peaCardIsAvailable = save.peaCardIsAvailable;
		this.cherryBombCardIsAvailable = save.cherryBombCardIsAvailable;
		this.freezePeaCardIsAvailable = save.freezePeaCardIsAvailable;
		this.jalapenoCardIsAvailable = save.jalapenoCardIsAvailable;
		plantPosSet = false;
		shovelPosSet = false;
		this.sunFlowerCardFramesLeftToBeAvailable = save.sunFlowerCardFramesLeftToBeAvailable;
		this.wallNutCardFramesLeftToBeAvailable = save.wallNutCardFramesLeftToBeAvailable;
		this.peaCardFramesLeftToBeAvailable = save.peaCardFramesLeftToBeAvailable;
		this.cherryBombCardFramesLeftToBeAvailable = save.cherryBombCardFramesLeftToBeAvailable;
		this.freezePeaCardFramesLeftToBeAvailable = save.freezePeaCardFramesLeftToBeAvailable;
		this.jalapenoCardFramesLeftToBeAvailable = save.jalapenoCardFramesLeftToBeAvailable;
		this.lawnMowerCreated = save.lawnMowerCreated;
		this.paused = save.paused;
		this.goToMain = save.goToMain;
		this.username = save.username;
		this.goToSetting = save.goToSetting;
		this.goToSettingMenuHardOff = save.goToSettingMenuHardOff;
		this.goToSettingMenuHardOn = save.goToSettingMenuHardOn;
		this.goToSettingMenuMusicOff = save.goToSettingMenuMusicOff;
		this.goToSettingMenuMusicOn = save.goToSettingMenuMusicOn;
		this.music = save.music;
		this.zombieFlag = save.zombieFlag;
	}
	
	/**
	 * saving a game
	 */
	private void save(){
		Save file = new Save(numberOfSun,
							 elements,
							 countingFrames,
							 hard,
							 sunFlowerCardIsSelected,
							 wallNutCardIsSelected,
							 peaCardIsSelected,
							 cherryBombCardIsSelected,
							 freezePeaCardIsSelected,
							 jalapenoCardIsSelected,
							 sunFlowerCardIsAvailable,
							 wallNutCardIsAvailable,
							 peaCardIsAvailable,
							 cherryBombCardIsAvailable,
							 freezePeaCardIsAvailable,
							 jalapenoCardIsAvailable,
							 plantPosSet,
							 plantPosX,
							 plantPosY,
							 sunFlowerCardFramesLeftToBeAvailable,
							 wallNutCardFramesLeftToBeAvailable,
							 peaCardFramesLeftToBeAvailable,
							 cherryBombCardFramesLeftToBeAvailable,
							 freezePeaCardFramesLeftToBeAvailable,
							 jalapenoCardFramesLeftToBeAvailable,
							 lawnMowerCreated,
							 endGameStatus,
							 paused,
							 goToMain,
							 username,
							 goToSetting,
							 goToSettingMenuHardOff,
							 goToSettingMenuHardOn,
							 goToSettingMenuMusicOff,
							 goToSettingMenuMusicOn,
							 music,
							 zombieFlag);
		writeSave(file, fileBasePath.concat(username+"-"+(getNumberOfFiles(username, fileBasePath)+1)+".ser"));
	}
	
	/**
	 * making variables to default
	 */
	private void makeDefault(){

		numberOfSun = 50;
		elements.clear();
		countingFrames = 0;
		hard = false;
		sunFlowerCardIsSelected = false;
		wallNutCardIsSelected = false;
		peaCardIsSelected = false;
		cherryBombCardIsSelected = false;
		freezePeaCardIsSelected = false;
		jalapenoCardIsSelected = false;
		sunFlowerCardIsAvailable = false;
		wallNutCardIsAvailable = false;
		peaCardIsAvailable = false;
		cherryBombCardIsAvailable = false;
		freezePeaCardIsAvailable = false;
		jalapenoCardIsAvailable = false;
		plantPosSet = false;	
		sunFlowerCardFramesLeftToBeAvailable = 0;
		wallNutCardFramesLeftToBeAvailable = 0;
		peaCardFramesLeftToBeAvailable = 0;
		cherryBombCardFramesLeftToBeAvailable = 0;
		freezePeaCardFramesLeftToBeAvailable = 0;	
		jalapenoCardFramesLeftToBeAvailable = 0;
		lawnMowerCreated = false;
		paused = false;
		username = "";
		goToSetting = false;
		gameHasEnded = false;
		dataNotSent = true;
		shovelIsSelected = false;
		shovelPosSet = false;
		zombieFlag = true;
		musicFlag = true;
	}
	
	/**
	 * checking whether the game has continue or no
	 */
	private void gameHasContinue(){
		
		//game overall time is 8 mins - user is winner
		if(countingFrames >= (FPS*480)){
			endGameStatus = true;
			gameHasEnded = true;
			if(hard){
				score = 10;
			}
			else{
				score = 3;
			}
			connection.sendData();
		}
		
		//if zombies get to house, user is looser
		for(Element element : elements){
			if(element instanceof Zombie){
				if(element.getX() < 210){
					endGameStatus = false;
					gameHasEnded = true;
					if(hard){
						score = -3;
					}
					else{
						score = -1;
					}
					connection.sendData();
				}
			}
		}

	}
	
	/**
	 * checking whether shovel can be applied in the clicked place or no
	 * @return
	 */
	private boolean shovelPosIsValid(){
		for(Element element : elements){
			if(element.getX() == shovelPosX && element.getY() == shovelPosY && element instanceof Plant){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * checking whether plant can be planted in the clicked place or no
	 * @return
	 */
	private boolean plantPosIsValid(){
		
		for(Element element : elements){
			if(element.getX() == plantPosX && element.getY() == plantPosY){
				return false;
			}
		}
		
		if(plantPosX < 265 || plantPosX < 0 || plantPosY < 0){
			return false;
		}
		
		return true;
	}
	
	/**
	 * getting standard x when clicking on the yard
	 * @param initialX initial x
	 * @return standard x
	 */
	private int getStandardX(int initialX){
		
		if(initialX >= 265 && initialX <= 368){
			return 298;
		}
		else if(initialX > 368 && initialX <= 449){
			return 368;
		}
		else if(initialX > 449 && initialX <= 544){
			return 449;
		}
		else if(initialX > 544 && initialX <= 630){
			return 544;
		}
		else if(initialX > 630 && initialX <= 720){
			return 630;
		}
		else if(initialX > 720 && initialX <= 810){
			return 720;
		}
		else if(initialX > 810 && initialX <= 895){
			return 810;
		}
		else if(initialX > 895 && initialX <= 985){
			return 900;
		}
		else if(initialX > 985 && initialX <= WIDTH){
			return 990;
		}
		
		return -1;

	}
	
	/**
	 * getting standard y when clicking on the yard
	 * @param initialY initial y
	 * @return standard y
	 */
	private int getStandardY(int initialY){

		if(initialY >= 0 && initialY <= 150){
			return 50;
		}
		if(initialY > 150 && initialY <= 260){
			return 150;
		}
		if(initialY > 260 && initialY <= 375){
			return 260;
		}
		if(initialY > 375 && initialY <= 470){
			return 375;
		}
		if(initialY > 470 && initialY <= HEIGHT){
			return 470;
		}
		
		return -1;
	}
	

	/**
	 * rendering the game
	 */
	private void render(){
		
		graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
		
        try {
            doRender(graphics);
        } finally {
            graphics.dispose();
        }
        
        bufferStrategy.show();
        Toolkit.getDefaultToolkit().sync();
        
	}
	
	/**
	 * doing the game rendering
	 * @param graphics graphics object of buffer strategy
	 */
	private void doRender(Graphics2D graphics){
		
		//game end
		if(gameHasEnded){
			if(endGameStatus){
				graphics.drawImage(win, null, 0, 0);
				if(music && musicFlag) {
					Sound win = new Sound("music/winner.wav");
					win.playSound();
					musicFlag = false;
				}
				return;
			}
			else{
				graphics.drawImage(gameOver, null, 0, 0);
				if(music && musicFlag) {
					Sound lose = new Sound("music/lose.wav");
					lose.playSound();
					musicFlag = false;
				}
				return;
			}
		}
		
		//drawing setting menu music on
		if(goToSettingMenuMusicOn){
			graphics.drawImage(settingMenuMusicOn, null, 0, 0);
			return;
		}
		
		//drawing setting menu music off
		if(goToSettingMenuMusicOff){
			graphics.drawImage(settingMenuMusicOff, null, 0, 0);
			return;
		}
		
		//drawing setting menu hard on
		if(goToSettingMenuHardOn){
			graphics.drawImage(settingMenuHardOn, null, 0, 0);
			return;
		}
		
		//drawing setting menu hard off
		if(goToSettingMenuHardOff){
			graphics.drawImage(settingMenuHardOff, null, 0, 0);
			return;
		}
		
		
		//drawing setting menu
		if(goToSetting){
			graphics.drawImage(settingMenu, null, 0, 0);
			return;
		}
		
		//drawing main menu
		if(goToMain){
			graphics.drawImage(mainMenu, null, 0, 0);
			return;
		}

	
		//drawing background
		graphics.drawImage(backYard, null, 0, 15);
		
		//drawing pause button
		graphics.drawImage(pauseButton, null, 100, 15);
		
		//drawing cards
		if(!sunFlowerCardIsAvailable){
			graphics.drawImage(sunFlowerCardCover, null, 18, 30);
		}
		else{
			graphics.drawImage(sunFlowerCard, null, 18, 30);
		}
		if(!wallNutCardIsAvailable){
			graphics.drawImage(wallNutCardCover, null, 18, 125);
		}
		else{
			graphics.drawImage(wallNutCard, null, 18, 125);
		}
		if(!peaCardIsAvailable){
			graphics.drawImage(peaCardCover, null, 18, 220);
		}
		else{
			graphics.drawImage(peaCard, null, 18, 220);
		}
		if(!cherryBombCardIsAvailable){
			graphics.drawImage(cherryBombCardCover, null, 18, 315);
		}
		else{
			graphics.drawImage(cherryBombCard, null, 18, 315);
		}
		if(!freezePeaCardIsAvailable){
			graphics.drawImage(freezePeaCardCover, null, 18, 410);
		}
		else{
			graphics.drawImage(freezePeaCard, null, 18, 410);
		}
		if(!jalapenoCardIsAvailable){
			graphics.drawImage(jalapenoCardCover, null, 18, 505);
		}
		else{
			graphics.drawImage(jalapenoCard, null, 18, 505);
		}
			
		
		//drawing number of suns
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("BTitr", Font.BOLD, 20));
		graphics.drawString(Integer.toString(numberOfSun), 155, 175);
		
		//drawing all available elements
		try{
			for(Element element : elements){
				Image image = new ImageIcon(element.getImage()).getImage();
				graphics.drawImage(image, element.getX(), element.getY(),null);
			}
		}catch(Exception ex){
			
		}
		
		//drawing pause menu
		if(paused){
			graphics.drawImage(pauseMenu, null, 0, 0);
		}
		
	}
	
	/**
	 * updating state of variables
	 * @throws IOException
	 */
	private synchronized void updateState() throws IOException{
		
		countingFrames++;
		
		//creating lawn mower if not created before
		if(!lawnMowerCreated){
			Lawnmower first = new Lawnmower(208, 50 , HEIGHT, WIDTH);
			Lawnmower second = new Lawnmower(201, 150 , HEIGHT, WIDTH);
			Lawnmower third = new Lawnmower(195, 260 , HEIGHT, WIDTH);
			Lawnmower fourth = new Lawnmower(190, 375 , HEIGHT, WIDTH);
			Lawnmower fifth = new Lawnmower(185, 470 , HEIGHT, WIDTH);
			
			elements.add(first);
			elements.add(second);
			elements.add(third);
			elements.add(fourth);
			elements.add(fifth);
			
			lawnMowerCreated = true;
		}
		
		
		//handling sun creation
		if(hard){
			
			//creating a new sun every 25 seconds
			if(countingFrames % (FPS*25) == 0){
				Sun newSun = new Sun(0, 0, HEIGHT, WIDTH, true);
				elements.add(newSun);
			}
		
		}
		else{
			
			//creating a new sun every 30 seconds
			if(countingFrames % (FPS*30) == 0){
				Sun newSun = new Sun(0, 0, HEIGHT, WIDTH, true);
				elements.add(newSun);
			}
			
		}

		
		//first wave
		if(countingFrames < (FPS*150) ){
			if(countingFrames % (FPS*30) == 0){
				NormalZombie newZombie = new NormalZombie(HEIGHT, WIDTH);
				elements.add(newZombie);
			}			
		}		
		//second wave
		else if(countingFrames < (FPS*330)){
			if(countingFrames % (FPS*15) == 0){
				ConeHeadZombie newZombie = new ConeHeadZombie(!hard, HEIGHT, WIDTH);
				elements.add(newZombie);
			}
		}
		//final wave
		else{
			if(countingFrames % (FPS*12.5) == 0){
				if(zombieFlag){
					BucketHeadZombie newZombie = new BucketHeadZombie(HEIGHT, WIDTH, !hard);
					elements.add(newZombie);
					zombieFlag = false;
				}
				else{
					FootballZombie newZombie = new FootballZombie(HEIGHT, WIDTH);
					elements.add(newZombie);
					zombieFlag = true;
				}
				
			}
		}
		
		
		//handling shovel
		if(shovelPosSet){
			if(shovelPosIsValid()){
				Iterator<Element> it = elements.iterator();
				while(it.hasNext()){
					Element element = it.next();
					if(element.getX() == shovelPosX && element.getY() == shovelPosY){
						it.remove();
						shovelPosSet = false;
					}
				}
				shovelIsSelected = false;
			}
			
		}
		
		
		//handling plants making
		if(plantPosSet){
			if(plantPosIsValid()){
				if(music){
					Sound plant = new Sound("music/plant.wav");
					plant.playSound();
				}
				if(sunFlowerCardIsSelected){
					sunFlower newSunFlower = new sunFlower(plantPosX, plantPosY, HEIGHT, WIDTH, !hard);
					plantPosSet = false;
					elements.add(newSunFlower);
					numberOfSun -= 50;
					if(hard){
						sunFlowerCardFramesLeftToBeAvailable = FPS*7.5;
					}
					else{
						sunFlowerCardFramesLeftToBeAvailable = FPS*7.5;
					}
				}
				else if(wallNutCardIsSelected){
					Wallnut newWallNut = new Wallnut(plantPosX, plantPosY, HEIGHT, WIDTH);
					plantPosSet = false;
					elements.add(newWallNut);
					numberOfSun -= 50;
					if(hard){
						wallNutCardFramesLeftToBeAvailable = FPS*30;
					}
					else{
						wallNutCardFramesLeftToBeAvailable = FPS*30;
					}
				}
				else if(peaCardIsSelected){
					PeaShooter newPeaShooter = new PeaShooter(plantPosX, plantPosY, HEIGHT, WIDTH);
					plantPosSet = false;
					elements.add(newPeaShooter);
					numberOfSun -= 100;
					if(hard){
						peaCardFramesLeftToBeAvailable = FPS*7.5;
					}
					else{
						peaCardFramesLeftToBeAvailable = FPS*7.5;
					}
				}
				else if(cherryBombCardIsSelected){
					CherryBomb newCherryBomb = new CherryBomb(plantPosX, plantPosY, HEIGHT, WIDTH, !hard);
					plantPosSet = false;
					elements.add(newCherryBomb);
					numberOfSun -= 150;
					if(hard){
						cherryBombCardFramesLeftToBeAvailable = FPS*45;
					}
					else{
						cherryBombCardFramesLeftToBeAvailable = FPS*30;
					}
				}
				else if(freezePeaCardIsSelected){
					SnowPea newFreezePea = new SnowPea(plantPosX, plantPosY, HEIGHT, WIDTH, !hard);
					plantPosSet = false;
					elements.add(newFreezePea);
					numberOfSun -= 175;
					if(hard){
						freezePeaCardFramesLeftToBeAvailable = FPS*30;
					}
					else{
						freezePeaCardFramesLeftToBeAvailable = FPS*7.5;
					}
				}
				else if(jalapenoCardIsSelected){
					Jalapeno newJalapeno = new Jalapeno(plantPosX, plantPosY, HEIGHT, WIDTH);
					plantPosSet = false;
					elements.add(newJalapeno);
					numberOfSun -= 150;
					if(hard){
						jalapenoCardFramesLeftToBeAvailable = FPS*45;
					}
					else{
						jalapenoCardFramesLeftToBeAvailable = FPS*30;
					}
				}
				sunFlowerCardIsSelected = false;
				wallNutCardIsSelected = false;
				peaCardIsSelected = false;
				cherryBombCardIsSelected = false;
				freezePeaCardIsSelected = false;
				jalapenoCardIsSelected = false;
			}

		}
		
		
		//minus frames left for cards to become available
		if(sunFlowerCardFramesLeftToBeAvailable > 0){
			sunFlowerCardFramesLeftToBeAvailable--;
		}
		if(wallNutCardFramesLeftToBeAvailable > 0){
			wallNutCardFramesLeftToBeAvailable--;
		}
		if(peaCardFramesLeftToBeAvailable > 0){
			peaCardFramesLeftToBeAvailable--;
		}
		if(cherryBombCardFramesLeftToBeAvailable > 0){
			cherryBombCardFramesLeftToBeAvailable--;
		}
		if(freezePeaCardFramesLeftToBeAvailable > 0){
			freezePeaCardFramesLeftToBeAvailable--;
		}
		if(jalapenoCardFramesLeftToBeAvailable > 0){
			jalapenoCardFramesLeftToBeAvailable--;
		}
		
		//checking cards availability
		if(numberOfSun < 50){
			sunFlowerCardIsAvailable = false;
			wallNutCardIsAvailable = false;
			peaCardIsAvailable = false;
			cherryBombCardIsAvailable = false;
			freezePeaCardIsAvailable = false;
			jalapenoCardIsAvailable = false;
		}
		else if(numberOfSun >= 50 && numberOfSun < 100){
			sunFlowerCardIsAvailable = false;
			wallNutCardIsAvailable = false;
			peaCardIsAvailable = false;
			cherryBombCardIsAvailable = false;
			freezePeaCardIsAvailable = false;
			jalapenoCardIsAvailable = false;
			if(sunFlowerCardFramesLeftToBeAvailable == 0){
				sunFlowerCardIsAvailable = true;
			}
			if(wallNutCardFramesLeftToBeAvailable == 0){
				wallNutCardIsAvailable = true;
			}	
		}
		else if(numberOfSun >= 100 && numberOfSun < 150){
			sunFlowerCardIsAvailable = false;
			wallNutCardIsAvailable = false;
			peaCardIsAvailable = false;
			cherryBombCardIsAvailable = false;
			freezePeaCardIsAvailable = false;
			jalapenoCardIsAvailable = false;
			if(sunFlowerCardFramesLeftToBeAvailable == 0){
				sunFlowerCardIsAvailable = true;
			}
			if(wallNutCardFramesLeftToBeAvailable == 0){
				wallNutCardIsAvailable = true;
			}
			if(peaCardFramesLeftToBeAvailable == 0){
				peaCardIsAvailable = true;
			}
		}
		else if(numberOfSun >= 150 && numberOfSun < 170){
			sunFlowerCardIsAvailable = false;
			wallNutCardIsAvailable = false;
			peaCardIsAvailable = false;
			cherryBombCardIsAvailable = false;
			freezePeaCardIsAvailable = false;
			jalapenoCardIsAvailable = false;
			if(sunFlowerCardFramesLeftToBeAvailable == 0){
				sunFlowerCardIsAvailable = true;
			}
			if(wallNutCardFramesLeftToBeAvailable == 0){
				wallNutCardIsAvailable = true;
			}
			if(peaCardFramesLeftToBeAvailable == 0){
				peaCardIsAvailable = true;
			}
			if(cherryBombCardFramesLeftToBeAvailable == 0){
				cherryBombCardIsAvailable = true;
			}
			if(jalapenoCardFramesLeftToBeAvailable == 0){
				jalapenoCardIsAvailable = true;
			}
		}
		else if(numberOfSun >= 170){
			sunFlowerCardIsAvailable = false;
			wallNutCardIsAvailable = false;
			peaCardIsAvailable = false;
			cherryBombCardIsAvailable = false;
			freezePeaCardIsAvailable = false;
			jalapenoCardIsAvailable = false;
			if(sunFlowerCardFramesLeftToBeAvailable == 0){
				sunFlowerCardIsAvailable = true;
			}
			if(wallNutCardFramesLeftToBeAvailable == 0){
				wallNutCardIsAvailable = true;
			}
			if(peaCardFramesLeftToBeAvailable == 0){
				peaCardIsAvailable = true;
			}
			if(cherryBombCardFramesLeftToBeAvailable == 0){
				cherryBombCardIsAvailable = true;
			}
			if(jalapenoCardFramesLeftToBeAvailable == 0){
				jalapenoCardIsAvailable = true;
			}
			if(freezePeaCardFramesLeftToBeAvailable == 0){
				freezePeaCardIsAvailable = true;
			}		
		}
		
		
		//calling Action for all elements
		try{
			for(Element element : elements){
				element.Action(elements);
			}
		}catch (Exception ex){
			
		}

		
	}
	
	/**
	 * game loop
	 */
	@Override
	public void run() {
		while(true){
			
			gameHasContinue();
			
			try{
				long start = System.currentTimeMillis();
				
				if(!paused && !goToMain){
					updateState();
				}			
				render();
				long delay = (1000 / FPS) - (System.currentTimeMillis() - start); //FPS (frame per second)
				
				if(delay > 0){
					Thread.sleep(delay );
				}
				
			}catch (Exception ex){
				ex.printStackTrace();
			}
		}

		
	}
	
	/**
	 * mouse handler inner class
	 * @author alireza karimi
	 *
	 */
	class MouseHandler implements MouseListener{

		/**
		 * handlign mouse clicks
		 */
		@Override
		public void mouseClicked(MouseEvent arg0) {
			
			int xMouse = arg0.getX();
            int yMouse = arg0.getY();
            boolean flag = true;
            
            //clicking on pause button
            if(xMouse >= 104 && xMouse <= 196 && yMouse >= 30 && yMouse <= 66 && flag){
            	paused = true;
            	flag = false;
            }
            
            //clicking on shovel
            if(xMouse >= 135 && xMouse <= 209 && yMouse >= 454 && yMouse <= 593 && flag){
            	shovelIsSelected = true;
            	flag = false;
            }
            
            
            //handling game end
            if(gameHasEnded && flag){
            	
            	if(xMouse >= 502 && xMouse <= 619 && yMouse >= 366 && yMouse <= 415){
            		makeDefault();
            		gameHasEnded = false;
        			goToMain = true;
            	}
            	
            	flag = false;
            }
            
            //handling music page
            if(goToSettingMenuMusicOn || goToSettingMenuMusicOff && flag){
            	
            	//click on toggle
            	if(xMouse >= 506 && xMouse <= 611 && yMouse >= 253 && yMouse <= 309){
            		if(music){
            			music = false;
            			gameSound.Stop();
            			goToSettingMenuMusicOn = false;
            			goToSettingMenuMusicOff = true;
            		}
            		else{
            			music = true;
            			gameSound = new Sound("music/gaming.wav");
            			gameSound.play();
            			goToSettingMenuMusicOn = true;
            			goToSettingMenuMusicOff = false;
            		}
            	}
            	
            	//click on OK
            	if(xMouse >= 502 && xMouse <= 619 && yMouse >= 366 && yMouse <= 415){
            		goToSettingMenuMusicOn = false;
        			goToSettingMenuMusicOff = false;
        			goToSetting = true;
            	}
            	
            	flag = false;
            }
            
            
            
            //handling level page
            if(goToSettingMenuHardOn || goToSettingMenuHardOff && flag){
            	
            	//click on toggle
            	if(xMouse >= 506 && xMouse <= 611 && yMouse >= 253 && yMouse <= 309){
            		if(hard){
            			hard = false;
            			goToSettingMenuHardOn = false;
            			goToSettingMenuHardOff = true;
            		}
            		else{
            			hard = true;
            			goToSettingMenuHardOn = true;
            			goToSettingMenuHardOff = false;
            		}
            	}
            	
            	//click on OK
            	if(xMouse >= 502 && xMouse <= 619 && yMouse >= 366 && yMouse <= 415){
            		goToSettingMenuHardOn = false;
            		goToSettingMenuHardOff = false;
        			goToSetting = true;
            	}
            	
            	flag = false;
            }
            
            
            //handling setting page
            if(goToSetting && flag){
        	
            	//clicking on main menu
            	if(xMouse >= 470 && xMouse <= 646 && yMouse >= 349 && yMouse <= 426){
            		goToSetting = false;
            		goToMain = true;
            	}
        	
        	
            	//clicking on music
            	if(xMouse >= 470 && xMouse <= 646 && yMouse >= 241 && yMouse <= 318){
            		goToSetting = false;
            		if(music){
            			goToSettingMenuMusicOn = true;
            		}
            		else{
            			goToSettingMenuMusicOff = true;
            		}           		
            	}
            	
            	//clicking on level
            	if(xMouse >= 470 && xMouse <= 646 && yMouse >= 134 && yMouse <= 213){
            		goToSetting = false;
            		if(hard){
            			goToSettingMenuHardOn = true;
            		}
            		else{
            			goToSettingMenuHardOff = true;
            		}           		
            	}
            
            	flag = false;
        	
            }
            
            
            if(paused && flag){
            	
            	//clicking on resume
                if(xMouse >= 398 && xMouse <= 695 && yMouse >= 397 && yMouse <= 484){
                	paused = false;
                }
                
                //clicking on save
                if(xMouse >= 584 && xMouse <= 777 && yMouse >= 303 && yMouse <= 370){
                	save();
                	makeDefault();
                	goToMain = true;
                }
                
                //clicking on main menu
                if(xMouse >= 347 && xMouse <= 539 && yMouse >= 304 && yMouse <= 369){
                	makeDefault();
                	goToMain = true;
                }
                
                flag = false;
                
            }
            
            if(goToMain && flag){
            	
            	//clicking on new game
                if(xMouse >= 462 && xMouse <= 652 && yMouse >= 39 && yMouse <= 118){
                	                	
                	String input = JOptionPane.showInputDialog("Enter your username:", null);
                	if(input != null && input.length() > 0){
                		makeDefault();
                		username = input;
                    	goToMain = false;
                	}
                	else{
                		JOptionPane.showMessageDialog(null, "You must enter a username.");
                	}

                }
            	
            	//clicking on load game
                if(xMouse >= 462 && xMouse <= 652 && yMouse >= 144 && yMouse <= 225){
                	
                	String input = JOptionPane.showInputDialog("Enter your username:", null);
                	if(input != null && input.length() > 0){
                		String[][] data = loadGames(input, fileBasePath); 
                		String[] columnNames = { "Game Name"};
                		
                		JTable table = new JTable(data, columnNames); 
                		JScrollPane sp = new JScrollPane(table);
                		sp.addMouseListener(mouseHandler);
                		int foo = JOptionPane.showOptionDialog(null, sp, "The title", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                		if(foo == JOptionPane.OK_OPTION){
                			Point point = arg0.getPoint();
                	        int row = table.rowAtPoint(point);
                	        String gameName = (String)table.getValueAt(table.getSelectedRow(), 0);
                	        startSavedGame(gameName);
                	        goToMain = false;
                		}  	
                	}
                	else{
                		JOptionPane.showMessageDialog(null, "You must enter a username.");
                	}

                }
                
            	
            	//clicking on ranking
                if(xMouse >= 462 && xMouse <= 652 && yMouse >= 249 && yMouse <= 328){
                	String[][] data = Server.getRanking();
                	String[] columnNames = { "username", "score"};
                	JTable table = new JTable(data, columnNames);
                	JScrollPane sp = new JScrollPane(table);
                	JOptionPane.showOptionDialog(null, sp, "The title", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                }
            	
            	//clicking on settings
                if(xMouse >= 470 && xMouse <= 646 && yMouse >= 351 && yMouse <= 429){
                	goToSetting = true;
                	goToMain = false;
                }
            	
            	//clicking on quit game
                if(xMouse >= 470 && xMouse <= 646 && yMouse >= 452 && yMouse <= 528){
                	System.exit(0);
                }
                
                flag = false;
            }

            
            //storing plant position
            if(sunFlowerCardIsSelected || wallNutCardIsSelected || peaCardIsSelected || cherryBombCardIsSelected || freezePeaCardIsSelected || jalapenoCardIsSelected && flag){
            	plantPosX = getStandardX(xMouse);
            	plantPosY = getStandardY(yMouse);
            	plantPosSet = true;
            	
            	flag = false;
            }
            
            //handling card click
            if(xMouse <= 80 && xMouse >= 18 && yMouse <= 120 && yMouse >= 30 && sunFlowerCardIsAvailable && flag){
            	sunFlowerCardIsSelected = true;
            	
				wallNutCardIsSelected = false;
				peaCardIsSelected = false;
				cherryBombCardIsSelected = false;
				freezePeaCardIsSelected = false;
				jalapenoCardIsSelected = false;
				
				flag = false;
            }
            else if(xMouse <= 82 && xMouse >= 18 && yMouse <= 215 && yMouse >= 125 && wallNutCardIsAvailable && flag){
            	wallNutCardIsSelected = true;
            	
				sunFlowerCardIsSelected = false;
				peaCardIsSelected = false;
				cherryBombCardIsSelected = false;
				freezePeaCardIsSelected = false;
				
				flag = false;
            }
            else if(xMouse <= 82 && xMouse >= 18 && yMouse <= 310 && yMouse >= 220 && peaCardIsAvailable && flag){
            	peaCardIsSelected = true;
            	
				sunFlowerCardIsSelected = false;
				wallNutCardIsSelected = false;
				cherryBombCardIsSelected = false;
				freezePeaCardIsSelected = false;
				jalapenoCardIsSelected = false;
				
				flag = false;
            }
            else if(xMouse <= 82 && xMouse >= 18 && yMouse <= 405 && yMouse >= 315 && cherryBombCardIsAvailable && flag){
            	cherryBombCardIsSelected = true;
            	
				sunFlowerCardIsSelected = false;
				wallNutCardIsSelected = false;
				peaCardIsSelected = false;
				freezePeaCardIsSelected = false;
				jalapenoCardIsSelected = false;
				
				flag = false;
            }
            else if(xMouse <= 82 && xMouse >= 18 && yMouse <= 500 && yMouse >= 410 && freezePeaCardIsAvailable && flag){
            	freezePeaCardIsSelected = true;
            	
				sunFlowerCardIsSelected = false;
				wallNutCardIsSelected = false;
				peaCardIsSelected = false;
				cherryBombCardIsSelected = false;
				jalapenoCardIsSelected = false;
				
				flag = false;
            }
            else if(xMouse <= 82 && xMouse >= 18 && yMouse <= 600 && yMouse >= 505 && jalapenoCardIsAvailable && flag){
            	jalapenoCardIsSelected = true;
            	
				sunFlowerCardIsSelected = false;
				wallNutCardIsSelected = false;
				peaCardIsSelected = false;
				cherryBombCardIsSelected = false;
				freezePeaCardIsSelected = false;
				
				flag = false;
            }
            
            
            //shovel is selected
            if(shovelIsSelected && flag){
            	shovelPosX = getStandardX(xMouse);
            	shovelPosY = getStandardY(yMouse);
            	shovelPosSet = true;
            	
            	flag = false;
            }
            
            //handling clicks on stars
            Iterator<Element> it = elements.iterator();
            while(it.hasNext()){
            	Element element = it.next();
            	int elementPosX = element.getX();
            	int elementPosY = element.getY();
            	
            	if((xMouse-elementPosX) <= 50 && (yMouse-elementPosY) <= 50 && (xMouse-elementPosX) >= 0 && (yMouse-elementPosY) >= 0 && element instanceof Sun){
            		numberOfSun += 25;
            		if(music) {
            			Sound sun = new Sound("music/sun.wav");
            			sun.playSound();
            		}
            		it.remove();
            	}
            }
            
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
