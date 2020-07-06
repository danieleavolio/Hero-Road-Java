package application.model;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class EntityHandler {

		private static ExecutorService es = Executors.newFixedThreadPool(200); // al massimo abbiamo 100 thread
		
		public static void addThreadE(Enemy e) {
			es.submit(e);
		}
		public static void addThreadC (MyCharacter c) {
			es.submit(c);
		}
		public static void addThreadManager(EnemiesManager em) {
			es.submit(em);
		}
}
