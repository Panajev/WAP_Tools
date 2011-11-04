package iggs.JAVA_tools.StringTools;
import iggs.JAVA_tools.console.IGGS_console;

import java.util.StringTokenizer;

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

public final class Check_Test {

	public static final String crlf = System.getProperty("line.separator");

	public static int check_Text (String input, String match, String delim) {

		int result = 0;	
		StringTokenizer str;			
		if (null == delim) {
			str = new StringTokenizer(input);

		}
		else {
			delim = " \t\n\r\f" + delim;
			str = new StringTokenizer(input, delim);				
		}

		if (!str.hasMoreTokens()) {
			result = -1;
			return result;
		}	

		while (str.hasMoreTokens()) {
			input = str.nextToken();
			if (0 == match.compareToIgnoreCase(input)) {
				result = 1;
				return result;
			}
		}
		return result;
	}


	public static int check_input (final String welcome, final String match_i, String delim, 
			final String err_msg, final IGGS_console console, boolean newline) {

		boolean test = false;
		String input;
		String local_delim = " \t\n\r\f" + ",";

		StringTokenizer t = new StringTokenizer(match_i, local_delim);


		if (!t.hasMoreTokens()) {
			console.println ("... no matches provided... error calling check_input()...");
			System.exit(-1);
		}

		String [] match = new String [t.countTokens()];
		for (int i = 0; t.hasMoreTokens(); i++) {	
			match[i] = t.nextToken();
		}

		while (true) {
			console.println(welcome);
			input = console.readLine();
			for (int i = 0; i < match.length; i++) {
				int resultA = Check_Test.check_Text(input, match[i], delim);
				if (resultA > 0 ) {
					return (i+1);
				}
				else test = false;
			}

			if (!test) {
				if (0 == input.compareTo("") && (newline)) return 0;
				console.println (err_msg);
				continue;
			}
			else break;
		}	
		return -1;
	}

}
