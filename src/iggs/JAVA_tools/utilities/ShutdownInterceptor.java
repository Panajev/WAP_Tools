package iggs.JAVA_tools.utilities;
import iggs.JAVA_tools.console.IGGS_console;
import iggs.JAVA_tools.utilities.SafeAppShutdown;;

public class ShutdownInterceptor extends Thread {

	private SafeAppShutdown app;

	public ShutdownInterceptor(SafeAppShutdown app) {
		this.app = app;
	}

	public void run() {
		//System.out.println("\nCall the shutdown routine");
		IGGS_console console = new IGGS_console(null, null);
		console.println ("Preparing to shut down...");
		app.shutDown();
	}
}
