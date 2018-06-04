package mila.tools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class myListener implements ServletContextListener {

	public static long applicationInitialized = 0L;

	/* Application Shutdown Event */
	@Override
	public void contextDestroyed(ServletContextEvent ce) {
	}

	/* Application Startup Event */
	@Override
	public void contextInitialized(ServletContextEvent ce) {
		String command2 = "/home/gtabajah/mennyTagger/runServer.sh";
		System.out.println("*********************************" + command2);

		try {
			Runtime.getRuntime().exec(" /home/gtabajah/mennyTagger/runServer.sh");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
}