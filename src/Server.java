import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * class for handling server operations
 * @author alireza karimi
 *
 */
public class Server {
	
	private ServerSocket serverSocket;
	private static ArrayList<Record> records;
	private static String filePath = "server-files/records.txt";
	
	/**
	 * creating a new server
	 */
	public Server(){
		try{
			serverSocket = new ServerSocket(5897);
		}catch (Exception ex){
			ex.printStackTrace();
		}
				
		records = new ArrayList<Record>();

		File file = new File(filePath);
		if(!file.exists()){	
			writeFile(records, filePath);
		}
		
	}
	
	/**
	 * getting ranking of users
	 * @return
	 */
	public static String[][] getRanking(){
		
		records = readFile(filePath);
		
		//sorting arraylist
		Collections.sort(records, new Comparator<Record>(){
			public int compare(Record rec1, Record rec2){
				return Integer.valueOf(rec1.getScore()).compareTo(Integer.valueOf(rec2.getScore()));
			}
		});
		
		
		String[][] data = new String[records.size()][2];
		for(int i = 0; i < records.size(); i++){
        	data[i][0] = records.get(i).getUsername();
        	data[i][1] = String.valueOf(records.get(i).getScore());
        }
		
		return data;
	}
	
	/**
	 * accepting connections from clients
	 */
	public void makeConnection(){
		try{
			while(true){
				Socket socket = serverSocket.accept();
				ServerConnection newConnection = new ServerConnection(socket);
				Thread newThread = new Thread(newConnection);
				ThreadPool.execute(newThread);
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * saving rankings in a file
	 * @param array arraylist of ranking records
	 * @param filePath file path
	 */
	public void writeFile(ArrayList<Record> array, String filePath){
		
		FileOutputStream fileOut = null;
		
		try{
			fileOut = new FileOutputStream(filePath, false);
 
        } catch(Exception ex){
            ex.printStackTrace();
        }
		
		try{
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(array);
            objectOut.close();
 
        }catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	/**
	 * reading rankings from file
	 * @param filePath file path
	 * @return array list of rankings
	 */
	public static ArrayList<Record> readFile(String filePath){

		FileInputStream fileIn  = null;
		
		try{
			fileIn = new FileInputStream(filePath);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	
        try{
        	ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        	ArrayList<Record> obj = (ArrayList<Record>) objectIn.readObject();
            objectIn.close();     
            
            return obj;

        } catch (Exception ex){
        	ex.printStackTrace();
        }
        
        return null;

	}
	
	/**
	 * main method of server
	 * @param args
	 */
	public static void main(String[] args){
		Server server = new Server();
		server.makeConnection();
	}
	
	/**
	 * server operations inner class
	 * @author alireza
	 *
	 */
	private class ServerConnection implements Runnable{
		
		private Socket socket;
		private DataInputStream dataIntputStream;
		private DataOutputStream dataOutputStream;
		
		public ServerConnection(Socket s){
			socket = s;
			try{
				dataIntputStream = new DataInputStream(socket.getInputStream());
				dataOutputStream = new DataOutputStream(socket.getOutputStream());
			}catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		/**
		 * loop of server to handle requests
		 */
		@Override
		public void run() {
			try{
				while(true){
					String input = dataIntputStream.readUTF();
					int score = Integer.valueOf(input.split("\\*")[0]);
					String username = input.split("\\*")[1];
					
					if(!username.equals("null")){
						records = readFile(filePath);
						
						boolean flag = true;
						for(Record item : records){
							if(item.getUsername().equals(username)){
								int lastScore = item.getScore();
								item.setScore(lastScore+score);
								flag = false;
							}
						}
						if(flag){
							Record record = new Record(username, score);
							records.add(record);
						}
						
						//writing update for records
						writeFile(records, filePath);
					}
					
					
				}
			}catch (Exception ex){
				ex.printStackTrace();
			}
			
		}
		
	}
	
}
