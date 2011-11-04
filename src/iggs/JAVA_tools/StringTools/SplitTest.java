package iggs.JAVA_tools.StringTools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
public class SplitTest{

	public void splitting(String arr_s, String regex) {

		String[] ary = arr_s.split(regex);

		for(int i=0;i<ary.length;i++){
			System.out.println("array["+i+"]\tlength("+ary[i].length()+")\t>"+ary[i]+"<");
		}

		//String regex = argv[1];
		if(regex.indexOf("\\")>=0){
			regex = regex.replaceAll("\\\\", "\\\\\\\\");
			System.out.println("\n\nYour delimiter expression contains at least one \\ character.\n"+
					"To prevent the java compiler interpreting this as an escape\n"+
					"code, you should use the following split() command in your \n.java sourcecode:\n\n"+
					"myString.split(\""+regex+"\");");
		}else{
			System.out.println("\n\nUse the following split() command in your .java sourcecode:\n\n"+
					"myString.split(\""+regex+"\");");
		}

		return;

	}

	public static void main(String[] argv){
		System.out.println("Usage: java SplitTest \"string to split\" \"regex to split by\"\n");

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String arr_s = null;
		String regex = null;
		String fileName = "test.txt";

		SplitTest sp = new SplitTest();
		boolean eclipse = true;

		try {	

			boolean console = false;

			while (true) {

				System.out.print ("Enter [1] to input a line of text from cmd.exe/WIN32_console\n" +
						"      [2] to read from a file (default: test.txt)(Eclipse's console)\n" +
				"      [3] to read from a file (default: test.txt)(cmd.exe/WIN32_console)\n>");

				String str = in.readLine();

				try { 

					if (1 == Integer.valueOf(str)) { 
						console = true;
						break;
					}

					else if (2 == Integer.valueOf(str)) {
						console = false;
						break;
					}

					else if (3 == Integer.valueOf(str))  {
						console = false;
						eclipse = false;
						break;
					}

					else {
						System.out.println("\nNot a valid choice, please re-try...");
					} 
				}

				catch (NumberFormatException n) {

					System.out.println("\nNot a valid choice, please re-try...");

				}

			}


			if (console) {
				System.out.print("\nInsert the string to be split as is and press ENTER\n>");

				arr_s = in.readLine();

				System.out.print("\nInsert the string to be used as regexp as is and press ENTER\n>");
				regex = in.readLine();
				sp.splitting(arr_s, regex);

			}

			else {

				System.out.print("\nInsert the string to be used as regexp as is and press ENTER\n>");
				regex = in.readLine();

				while (true) {
					try {

						if (null == fileName) {

							fileName = in.readLine();
						}

						BufferedReader in1 = null;

						if (null == fileName) break;

						if (eclipse) in1 = new BufferedReader(new FileReader("bin\\"+fileName));

						else in1 = new BufferedReader(new FileReader(fileName));

						while (true ) {

							String line1 = in1.readLine();

							if (line1 == null) {
								break;
							}

							if (line1.equals("")) continue;

							System.out.println("\n\nWe are splitting: \n>" + line1 +
									"\nUsing this regular expression:\n>" +
									regex);

							sp.splitting(line1, regex);

						}
						break;
					}

					catch (FileNotFoundException f) {
						System.out.print ("\n<!!!!> The file \"" + fileName + "\" was not found...\n" +
								"\nPlease enter the desired .txt file name complete with extension \n(press CTRL+C " +
						"or the red button on Eclipse's console to exit this program):\n>");
						fileName = null;
					}

				}

			}

		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


