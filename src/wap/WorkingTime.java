package wap;

import java.util.Calendar;

import iggs.JAVA_tools.StringTools.Check_Test;
import iggs.JAVA_tools.console.IGGS_console;
import iggs.JAVA_tools.utilities.SafeAppShutdown;
import iggs.JAVA_tools.utilities.SysTools;

/** Copyright (c) 2009, Goffredo Marocchi
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the
 *       names of any contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY GOFFREDO MAROCCHI "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL GOFFREDO MAROCCHI BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

public class WorkingTime implements SafeAppShutdown{

	static String fname;

	public static final String crlf = System.getProperty("line.separator");
	/**
	 * @param args
	 */
	private static void record_time (String fileName) {

		String delim = "\"";

		while (true) {
			IGGS_console console = new IGGS_console(null, null);

			Calendar t1,t2;

			String welcome = "> Press Enter when you start working\n" +
			"or type either \"quit\" to exit, \"undo\" to roll-back one operation\n" +
			"or \"text\" to make a comment about the work you are about to do...";
			String err_msg = "Invalid input, please try again...\n";

			String comment = null;

			int result = Check_Test.check_input (welcome, "quit,undo,text", delim, err_msg, console, true);	
			if(1 == result) {
				SysTools.writeFileFlushBuffer(fileName);
				break;
			}
			else if (3 == result) {
				console.println ("Enter the comment you want to make: ");
				comment = console.readLine();
				console.println();
				welcome = "> Press Enter when you start working\n" +
				"or type either \"quit\" to exit...";
				result = Check_Test.check_input(welcome, "quit", delim, err_msg, console, true);	
				if(result > 0) break;
			}
			if (2 == result) {
				console.println ("(!) Last step was deleted (!)");
				SysTools.undoStep();
				welcome = "> Press Enter when you start working\n" +
				"or type either \"quit\" to exit...";
				result = Check_Test.check_input(welcome, "quit", delim, err_msg, console, true);	
				if(result > 0) break;
			}
			else SysTools.writeFileFlushBuffer(fileName);

			t1 = Calendar.getInstance();

			console.println("> Press Enter when you stop working or take a break");
			console.readLine();
			t2 = Calendar.getInstance();

			SysTools.writeFile(fileName, SysTools.getDateString() + ",");
			SysTools.write_time_HHMMSS(fileName, t1);
			SysTools.writeFile (fileName, ",");
			SysTools.write_time_HHMMSS(fileName, t2);
			
			int hourDelta = t2.get(Calendar.HOUR_OF_DAY) - t1.get(Calendar.HOUR_OF_DAY);
			int minutesDelta = t2.get(Calendar.MINUTE) - t1.get(Calendar.MINUTE);
			SysTools.writeFile (fileName, "," + hourDelta + ":" + minutesDelta);
			
			if (null != comment) SysTools.writeFile (fileName, "," + comment);
			SysTools.writeFile (fileName, "\n");
		}	
	}
	public static void main(String[] args) {

		/*Safe shutdown*/
		SysTools.enableSafeShutdown((SafeAppShutdown)new WorkingTime());

		if (!SysTools.isWindows()) SysTools.install(new SysTools(), "TSTP");
		SysTools.install(new SysTools(), "TERM");
		SysTools.install(new SysTools(), "INT");
		SysTools.install(new SysTools(), "ABRT");
		SysTools.install(new SysTools(), "SEGV");

		IGGS_console console = new IGGS_console(null, null);
		String delim = "\"";

		String welcome = "Welcome to the WAP test working time tool, " +
		"press Enter to go ahead " +
		"or type \"quit\" and press Enter to exit...";
		String err_msg = "Invalid input, please try again...\n";

		int result = Check_Test.check_input(welcome, "quit", delim, err_msg, console, true);	
		if(result > 0) {
			SysTools.delayLoop (1);
			System.exit(0);
		}

		do {
			console.println("Please enter date in YYYYMMDD format and project being worked on\n"
					+ "example: 20091110_TIM_RT1");
		} while ((fname = new String (console.readLine())).compareTo("") == 0);

		fname = fname + ".csv";

		SysTools.addFname(fname);

		while(!SysTools.create_reset_File(fname));
		record_time (fname);
		System.exit(0);
	}

	@Override
	public void shutDown() {
		// TODO Auto-generated method stub
		SysTools.cleanShutdown();
		IGGS_console console = new IGGS_console(null, null);
		console.println ("The Application is shutting down...");
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
