import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class contains every thing that we wish to save
 * @author alireza karimi
 *
 */
public class Save implements Serializable {
	
	public int numberOfSun;
	public ArrayList<Element> elements; //all available elements are stored in this arraylist
	public long countingFrames;
	public boolean hard;
	public boolean sunFlowerCardIsSelected;
	public boolean wallNutCardIsSelected;
	public boolean peaCardIsSelected;
	public boolean cherryBombCardIsSelected;
	public boolean freezePeaCardIsSelected;
	public boolean jalapenoCardIsSelected;
	public boolean sunFlowerCardIsAvailable;
	public boolean wallNutCardIsAvailable;
	public boolean peaCardIsAvailable;
	public boolean cherryBombCardIsAvailable;
	public boolean freezePeaCardIsAvailable;
	public boolean jalapenoCardIsAvailable;
	public boolean plantPosSet;
	public int plantPosX;
	public int plantPosY;
	public double sunFlowerCardFramesLeftToBeAvailable;
	public double wallNutCardFramesLeftToBeAvailable;
	public double peaCardFramesLeftToBeAvailable;
	public double cherryBombCardFramesLeftToBeAvailable;
	public double freezePeaCardFramesLeftToBeAvailable;	
	public double jalapenoCardFramesLeftToBeAvailable = 0;
	public boolean lawnMowerCreated;
	public boolean endGameStatus;
	public boolean paused;
	public boolean goToMain;
	public String username;
	public boolean goToSetting;
	public boolean goToSettingMenuHardOff;
	public boolean goToSettingMenuHardOn;
	public boolean goToSettingMenuMusicOff;
	public boolean goToSettingMenuMusicOn;
	public boolean music;
	public boolean zombieFlag;
	
	/**
	 * creating a new save file
	 * @param numberOfSun
	 * @param elements
	 * @param countingFrames
	 * @param hard
	 * @param sunFlowerCardIsSelected
	 * @param wallNutCardIsSelected
	 * @param peaCardIsSelected
	 * @param cherryBombCardIsSelected
	 * @param freezePeaCardIsSelected
	 * @param jalapenoCardIsSelected
	 * @param sunFlowerCardIsAvailable
	 * @param wallNutCardIsAvailable
	 * @param peaCardIsAvailable
	 * @param cherryBombCardIsAvailable
	 * @param freezePeaCardIsAvailable
	 * @param jalapenoCardIsAvailable
	 * @param plantPosSet
	 * @param plantPosX
	 * @param plantPosY
	 * @param sunFlowerCardFramesLeftToBeAvailable
	 * @param wallNutCardFramesLeftToBeAvailable
	 * @param peaCardFramesLeftToBeAvailable
	 * @param cherryBombCardFramesLeftToBeAvailable
	 * @param freezePeaCardFramesLeftToBeAvailable
	 * @param jalapenoCardFramesLeftToBeAvailable
	 * @param lawnMowerCreated
	 * @param endGameStatus
	 * @param paused
	 * @param goToMain
	 * @param username
	 * @param goToSetting
	 * @param goToSettingMenuHardOff
	 * @param goToSettingMenuHardOn
	 * @param goToSettingMenuMusicOff
	 * @param goToSettingMenuMusicOn
	 * @param music
	 * @param zombieFlag
	 */
	public Save(int numberOfSun,
				ArrayList<Element> elements,
				long countingFrames,
				boolean hard,
				boolean sunFlowerCardIsSelected,
				boolean wallNutCardIsSelected,
				boolean peaCardIsSelected,
				boolean cherryBombCardIsSelected,
				boolean freezePeaCardIsSelected,
				boolean jalapenoCardIsSelected,
				boolean sunFlowerCardIsAvailable,
				boolean wallNutCardIsAvailable,
				boolean peaCardIsAvailable,
				boolean cherryBombCardIsAvailable,
				boolean freezePeaCardIsAvailable,
				boolean jalapenoCardIsAvailable,
				boolean plantPosSet,
				int plantPosX,
				int plantPosY,
				double sunFlowerCardFramesLeftToBeAvailable,
				double wallNutCardFramesLeftToBeAvailable,
				double peaCardFramesLeftToBeAvailable,
				double cherryBombCardFramesLeftToBeAvailable,
				double freezePeaCardFramesLeftToBeAvailable,
				double jalapenoCardFramesLeftToBeAvailable,
				boolean lawnMowerCreated,
				boolean endGameStatus,
				boolean paused,
				boolean goToMain,
				String username,
				boolean goToSetting,
				boolean goToSettingMenuHardOff,
				boolean goToSettingMenuHardOn,
				boolean goToSettingMenuMusicOff,
				boolean goToSettingMenuMusicOn,
				boolean music,
				boolean zombieFlag){
		
		this.numberOfSun = numberOfSun;
		//System.out.println(elements);
		this.elements = elements;
		this.countingFrames = countingFrames;
		this.hard = hard;
		this.sunFlowerCardIsSelected = sunFlowerCardIsSelected;
		this.wallNutCardIsSelected = wallNutCardIsSelected;
		this.peaCardIsSelected = peaCardIsSelected;
		this.cherryBombCardIsSelected = cherryBombCardIsSelected;
		this.freezePeaCardIsSelected = freezePeaCardIsSelected;
		this.jalapenoCardIsSelected = jalapenoCardIsSelected;
		this.sunFlowerCardIsAvailable = sunFlowerCardIsAvailable;
		this.wallNutCardIsAvailable = wallNutCardIsAvailable;
		this.peaCardIsAvailable = peaCardIsAvailable;
		this.cherryBombCardIsAvailable = cherryBombCardIsAvailable;
		this.freezePeaCardIsAvailable = freezePeaCardIsAvailable;
		this.jalapenoCardIsAvailable = jalapenoCardIsAvailable;
		this.plantPosSet = plantPosSet;
		this.plantPosX = plantPosX;
		this.plantPosY = plantPosY;
		this.sunFlowerCardFramesLeftToBeAvailable = sunFlowerCardFramesLeftToBeAvailable;
		this.wallNutCardFramesLeftToBeAvailable = wallNutCardFramesLeftToBeAvailable;
		this.peaCardFramesLeftToBeAvailable = peaCardFramesLeftToBeAvailable;
		this.cherryBombCardFramesLeftToBeAvailable = cherryBombCardFramesLeftToBeAvailable;
		this.freezePeaCardFramesLeftToBeAvailable = freezePeaCardFramesLeftToBeAvailable;	
		this.jalapenoCardFramesLeftToBeAvailable = jalapenoCardFramesLeftToBeAvailable;
		this.lawnMowerCreated = lawnMowerCreated;
		this.endGameStatus = endGameStatus;
		this.paused = paused;
		this.goToMain = goToMain;
		this.username = username;
		this.goToSetting = goToSetting;
		this.goToSettingMenuHardOff = goToSettingMenuHardOff;
		this.goToSettingMenuHardOn = goToSettingMenuHardOn;
		this.goToSettingMenuMusicOff = goToSettingMenuMusicOff;
		this.goToSettingMenuMusicOn = goToSettingMenuMusicOn;
		this.music = music;
		this.zombieFlag = zombieFlag;
	}
	
	
}
