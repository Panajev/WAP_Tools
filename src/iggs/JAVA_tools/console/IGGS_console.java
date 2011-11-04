package iggs.JAVA_tools.console;

import java.io.IOException;
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

public class IGGS_console {

	public static final String crlf = System.getProperty("line.separator");

	private String input_buf;
	private StringBuffer out_buf;
	private boolean visible_output = true;
	private StringTokenizer str_tok;

	public IGGS_console () {
		setInput_buf(null);
		out_buf = null;
	}

	public IGGS_console (String input, StringBuffer output) {
		if (null != input) {
			this.str_tok = new StringTokenizer(input, "\n");
		}

		setInput_buf(input);
		this.out_buf = output;

	}

	public String readLine() {
		StringBuffer temp = new StringBuffer();
		//String crlf = System.getProperty("line.separator");

		if (null == crlf) {
			this.println ("The system property \"line.separator\"" +
			"does not exists...");
			System.exit(-1);
		}

		if (null != this.str_tok) {		
			String line = str_tok.nextToken();
			if (this.visible_output) System.out.println(line);
			return line;
		}	
		else {
			int i_char;
			try {
				while (true) {			
					StringBuffer newline = new StringBuffer();
					i_char = System.in.read();

					if ("\r\n".equals(crlf)) {
						newline.append((char)i_char);	
						if ('\r' == i_char) {
							newline.append((char)System.in.read());		
							if (new String(newline).equals(crlf)) break;
							else continue;
						}
						else {
							temp.append((char)(i_char));
						}
					}					
					else if ("\n".equals(crlf)) {		
						if ('\n' == i_char) break;
						else {
							temp.append((char)(i_char));
						}
					}
					else {
						this.println ("Unsupported OS...");
						System.exit(-1);
					}
				} 		
			}catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}

		}
		return temp.toString();
	}

	public void println () {
		if (this.visible_output) System.out.println();
		if (null != out_buf) out_buf.append("\n");
	}

	public void print(String s) {
		if (this.visible_output) System.out.print(s);
		if (null != out_buf) out_buf.append(s);

	}
	public void println (String s) {
		if (this.visible_output) System.out.println(s);
		if (null != out_buf) out_buf.append(s).append("\n");
	}

	public void print(Object s) {
		if (this.visible_output) System.out.print(s);
		if (null != out_buf) out_buf.append(s);

	}
	public void println (Object s) {
		if (this.visible_output) System.out.println(s);
		if (null != out_buf) out_buf.append(s).append("\n");
	}

	public void print(int s) {
		if (this.visible_output) System.out.print(s);
		if (null != out_buf) out_buf.append(s);

	}
	public void println (int s) {
		if (this.visible_output) System.out.println(s);
		if (null != out_buf) out_buf.append(s).append("\n");
	}

	public void print(float s) {
		if (this.visible_output) System.out.print(s);
		if (null != out_buf) out_buf.append(s);			
	}
	public void println (float s) {
		if (this.visible_output) System.out.println(s);
		if (null != out_buf) out_buf.append(s).append("\n");
	}
	public void print(double s) {
		if (this.visible_output) System.out.print(s);
		if (null != out_buf) out_buf.append(s);

	}
	public void println (double s) {
		if (this.visible_output) System.out.println(s);
		if (null != out_buf) out_buf.append(s).append("\n");
	}
	public void print(char s) {
		if (this.visible_output) System.out.print(s);
		if (null != out_buf) out_buf.append(s);

	}
	public void println (char s) {
		if (this.visible_output) System.out.println(s);
		if (null != out_buf) out_buf.append(s).append("\n");
	}

	public void print(long s) {
		if (this.visible_output) System.out.print(s);
		if (null != out_buf) out_buf.append(s);

	}
	public void println (long s) {
		if (this.visible_output) System.out.println(s);
		if (null != out_buf) out_buf.append(s).append("\n");
	}

	public void print(StringBuffer s) {
		if (this.visible_output) System.out.print(s);
		if (null != out_buf) out_buf.append(s);

	}
	public void println (StringBuffer s) {
		if (this.visible_output) System.out.println(s);
		if (null != out_buf) out_buf.append(s).append("\n");
	}

	///////////////////////////////////////////
	/////Setters and Getters
	///////////////////////////////////////////
	public void setInput_buf(String input_buf) {
		this.input_buf = input_buf;
	}

	public String getInput_buf() {
		return input_buf;
	}

	public StringBuffer getOut_buf() {
		return out_buf;
	}

	public void setOut_buf(StringBuffer outBuf) {
		out_buf = outBuf;
	}

	public boolean isVisible_output() {
		return visible_output;
	}

	public void setVisible_output(boolean visibleOutput) {
		visible_output = visibleOutput;
	}

}
