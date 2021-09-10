import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * thread pool of the program
 * @author alireza karimi
 *
 */
public class ThreadPool {
	
	private static ExecutorService pool = Executors.newCachedThreadPool();
	
	/**
	 * executing a new thread
	 * @param task a new runnable
	 */
	public static void execute(Runnable task){
		pool.execute(task);
	}
	
	/**
	 * shutting down the threadpool
	 */
	public static void shutDown(){
		pool.shutdown();
	}
}
