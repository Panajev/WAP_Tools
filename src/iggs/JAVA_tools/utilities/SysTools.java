package iggs.JAVA_tools.utilities;

import iggs.JAVA_tools.StringTools.Check_Test;
import iggs.JAVA_tools.console.IGGS_console;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import sun.misc.Signal;
import sun.misc.SignalHandler;

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

public class SysTools implements SignalHandler {

	private SignalHandler oldHandler;
	private static String [] fname;
	private static int count_fname;
	private static boolean enable_safe_shutdown = false;

	private static File f_handle;
	private static FileWriter fw_handle;

	public static void addFname (String str) {
		if (enable_safe_shutdown && (null != str) && (count_fname < fname.length)) {
			fname[count_fname++] = str;
		}
	}

	public static void cleanShutdown() {
		// TODO Auto-generated method stub
		//System.err.println ("...Shutdown...");
		for (String s : fname)  {
			SysTools.OnExitwriteFileFlushBuffer(s);
			SysTools.fixEmptyFileBug(s);
		}
	}

	public static boolean isWindows() {
		// TODO Auto-generated method stub
		String os = System.getProperty("os.name");
		if (os.contains("Windows")) {
			//System.err.println (os);
			return true;
		}
		return false;
	}

	public static SignalHandler install(SysTools instance, String signalName) {
		Signal diagSignal = new Signal(signalName);
		instance.oldHandler = Signal.handle(diagSignal, instance);
		return instance;
	}

	public static void enableSafeShutdown (SafeAppShutdown app) {		
		fname = new String [256];
		for (int i = 0; i < fname.length; i++) {
			fname[i] = null;
			count_fname = 0;
		}
		enable_safe_shutdown = true;
		ShutdownInterceptor shutdownInterceptor = new ShutdownInterceptor(app);
		Runtime.getRuntime().addShutdownHook(shutdownInterceptor);
		app.start();
	}

	public static final String crlf = System.getProperty("line.separator");
	private static StringBuffer buffer_str = new StringBuffer ();

	public final static void delayLoop (int seconds) {
		//simple delay loop
		long time_s = System.currentTimeMillis();
		while ((System.currentTimeMillis() - time_s) < (seconds * 1000));
	}
	/**
	 * This function returns a date in YYYY-MM-DD format.
	 * @return
	 */
	public static String getDateString () {	
		Calendar t;
		t = Calendar.getInstance();

		return (new String (Integer.toString(t.get(Calendar.YEAR)) + "-" + 
				Integer.toString(t.get(Calendar.MONTH)+1) + "-" +
				Integer.toString(t.get(Calendar.DAY_OF_MONTH))));
	}
	
	public static void write_time_HHMMSS (String fileName, 
			Calendar t) {
		String hh, mm, ss;
		hh = Integer.toString(t.get(Calendar.HOUR_OF_DAY));
		mm = Integer.toString(t.get(Calendar.MINUTE));
		ss = Integer.toString(t.get(Calendar.SECOND));
		write_time_HHMMSS(fileName, hh, mm, ss);
	}
	
	private static void write_time_HHMMSS (String fileName, 
			String hh, String mm, String ss) {
		SysTools.writeFile (fileName,  hh);
		SysTools.writeFile (fileName, ":");
		SysTools.writeFile (fileName,  mm);
		SysTools.writeFile (fileName, ":");
		SysTools.writeFile (fileName,  ss);
	}

	public static void write_time_HHMMSS_AMPM (String fileName, 
			Calendar t) {
		String hh, mm, ss;
		boolean am = false;
		
		Integer hour = t.get(Calendar.HOUR_OF_DAY);
		int hourInt = hour.intValue();
		
		if (hourInt > 12 && hourInt < 24) {
			hour = Integer.valueOf(hourInt - 12);
			hh = Integer.toString(hour);
		}
		else if (hourInt < 12 && hourInt > 0){
			am = true;
			hh = Integer.toString(t.get(Calendar.HOUR_OF_DAY));
		}
		else if (hourInt == 12) {
			hh = Integer.toString(t.get(Calendar.HOUR_OF_DAY));
		}
		else {
			//hh == 0 or 24
			am = true;
			hh = Integer.toString(12);
			
		}
		mm = Integer.toString(t.get(Calendar.MINUTE));
		ss = Integer.toString(t.get(Calendar.SECOND));
		write_time_HHMMSS_AMPM(fileName, hh, mm, ss, am);
	}
	
	private static void write_time_HHMMSS_AMPM (String fileName, 
			String hh, String mm, String ss, boolean am) {
		SysTools.writeFile (fileName,  hh);
		SysTools.writeFile (fileName, ":");
		SysTools.writeFile (fileName,  mm);
		SysTools.writeFile (fileName, ":");
		SysTools.writeFile (fileName,  ss);
		if (am) SysTools.writeFile (fileName,  " A.M.");
		else SysTools.writeFile (fileName,  " P.M.");
	}
	
	public static void write_time_interval (String fileName, Calendar tStartIntervalTest) {
		Calendar tEndIntervalTest = Calendar.getInstance();
		int hourDelta = tEndIntervalTest.get(Calendar.HOUR_OF_DAY) - tStartIntervalTest.get(Calendar.HOUR_OF_DAY);
		int minutesDelta = tEndIntervalTest.get(Calendar.MINUTE) - tStartIntervalTest.get(Calendar.MINUTE);
		SysTools.writeFile (fileName, "\n,Test Duration:," + hourDelta + ":" + minutesDelta + "\n\n");
	}
	
	public static boolean create_reset_File (String fileName) {
		//File f;
		IGGS_console console = new IGGS_console(null, null);
		String delim = "\"";

		if (null == fileName) return false;
		f_handle=new File(fileName);
		if(!f_handle.exists()){
			try {
				f_handle.createNewFile();
			} 
			catch (IOException e) {
				System.err.println ("Error creating a file...");
				e.printStackTrace();
			}

			System.out.println("New file \" " + fileName + " \" has been created " + 
			"to the current directory\n");
		}
		else {
			String welcome = "The file already exists, do you want to delete it? Y/N";
			String err_msg = "Invalid input, please try again...\n";
			int result = Check_Test.check_input(welcome, "Y,N", delim, err_msg, console, false);
			if (1 == result) {				
				if (false == f_handle.delete()) {
					console.println ("There was a problem deleting the file, " +
							"the file might be currently opened.\nPlease close the file \"" +
							fileName);
					return false;
				}
				try {
					f_handle.createNewFile();
				} 
				catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println ("System error creating a file...");
					e.printStackTrace();
					System.exit(-1);
				}
				System.out.println("New file \"" + fileName + "\" has been created " + 
				"to the current directory\n");
			}		
			else {
				welcome = "Do you want to append data to the file? Y/N";
				result = Check_Test.check_input(welcome, "Y,N", delim, err_msg, console, false);
				if (1 != result) {
					SysTools.delayLoop (1);
					System.exit(0);
				}
			}
		}	
		if (!f_handle.setReadOnly()) {
			System.exit(-1);
			System.err.println ("System error setting file's permission...");
		}
		return true;
	}

	public static void writeFile(String fileName, String content) {
		if (null == fileName) System.exit(-1);
		buffer_str.append(content);
	}

	public static void fixEmptyFileBug (String fileName) {

		//to be called when the program is shutting down.
		if (null == fileName) return;	
		f_handle=new File(fileName);	
		if(!f_handle.exists()){
			System.err.println ("The specified file \"" + fileName + "\" does not exist...");
			return; //System.exit(-1) would block the shutdown process.
		}			
		if (!f_handle.setWritable(true)) {
			System.err.println ("System error setting file's permission...");
			return;
		}
		if (0 != f_handle.length()) return;	
		if (false == f_handle.delete()) {
			System.err.println ("There was a problem deleting the file at shutdown, " +
					"the file might be currently opened.\nPlease close the file \"" +
					fileName + "\"");
			return;
		}
		IGGS_console console = new IGGS_console(null, null);
		console.println ("Empty file has been cleaned up...");
	}

	public static void writeFileFlushBuffer (String fileName) {
		if (null == fileName) return;
		try{
			//System.out.println("\n" + content);
			//File f = new File (fileName);
			f_handle=new File(fileName);

			if(!f_handle.exists()){
				System.err.println ("The specified file \"" + fileName + "\" does not exist...");
				System.exit(-1);	
			}

			//Set file RW
			if (!f_handle.setWritable(true)) {
				System.exit(-1);
				System.err.println ("System error setting file's permission...");
			}	
			fw_handle = new FileWriter(f_handle, true); //file must be in RW mode 
			//for FileWriter() to succeed
			fw_handle.write(buffer_str.toString());
			fw_handle.flush();
			fw_handle.close();

			//Set file RO
			if (!f_handle.setReadOnly()) {
				System.exit(-1);
				System.err.println ("System error setting file's permission...");
			}

		}catch(IOException ioe){
			throw new RuntimeException(ioe.getMessage());
		}
		clearWriteBuffer();
	}

	public static void OnExitwriteFileFlushBuffer (String fileName) {
		if (null == fileName) return;
		try{
			//System.out.println("\n" + content);
			f_handle=new File(fileName);
			if(!f_handle.exists()){
				return; //System.exit(-1) would block the shutdown process.
			}

			//Set the file RW
			if (!f_handle.setWritable(true)) {
				System.err.println ("System error setting file's permission...");
				return;
			}	
			fw_handle = new FileWriter(f_handle, true); //file must be in RW mode 
			//for FileWriter() to succeed
			fw_handle.write(buffer_str.toString());
			fw_handle.flush();
			fw_handle.close();
		}catch(IOException ioe){
			throw new RuntimeException(ioe.getMessage());
		}
		clearWriteBuffer();
	}

	public static void writeFileFlush (String fileName, String content) {
		writeFile(fileName, content);
		writeFileFlushBuffer(fileName);
		return;
	}

	private static void clearWriteBuffer() {
		buffer_str = null;
		buffer_str = new StringBuffer();
		return;
	}

	public static void undoStep() {
		clearWriteBuffer();
		return;
	}
	@Override
	public void handle(Signal signal) {
		// TODO Auto-generated method stub
		//CTRL+Z on OSX is intercepted to flush the write buffer, on Windows we use CTRL+C
		//thus ignoring SIGINT
		if ("TSTP".equalsIgnoreCase(signal.getName()) ||
				(isWindows() && "INT".equalsIgnoreCase(signal.getName())))  {
			System.out.println("\nFlushing write buffers...");
			for (String s : fname) {
				if (null != s) writeFileFlushBuffer(s);
			}
			return; //let's ignore SIGTSTP
		}

		System.err.println("Signal handler called for signal "
				+ signal);
		try {
			signalAction(signal);

			// Chain back to previous handler, if one exists
			if (oldHandler != SIG_DFL && oldHandler != SIG_IGN) {
				oldHandler.handle(signal);
			}

		} catch (Exception e) {
			System.err.println("handle|Signal handler failed, reason " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void signalAction(Signal signal) {
		// TODO Auto-generated method stub
		System.err.println("Handling " + signal.getName());
		//SysTools.cleanShutdown();
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			System.err.println("Interrupted: " + e.getMessage());
		}
	}

}


