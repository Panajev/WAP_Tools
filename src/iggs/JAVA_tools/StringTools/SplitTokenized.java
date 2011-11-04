package iggs.JAVA_tools.StringTools;

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
//String.split() che butta via le stringhe di lunghezza 0
public class SplitTokenized {

	private String regexp;
	private String str;
	private int count;
	private int index;
	private String[] arr_s;

	public SplitTokenized (String str, String regexp) {
		index = 0;
		this.str = str;
		this.regexp = regexp;
		arr_s = this.str.split(this.regexp);
		this.count = arr_s.length;

	}

	public int getTokensCount () {

		return count;

	}

	public boolean hasTokens() {
		// TODO Auto-generated method stub
		return (count > 0);
	}

	public String nextToken() {
		// TODO Auto-generated method stub
		String token = null;

		while(index < arr_s.length && this.hasTokens()) {
			// se length e' 4, index puo' essere 0,1,2,3

			if (this.arr_s[index].length() > 0) {
				token = arr_s[index];
				this.index++;
				this.count--;
				break;
			}

			else {
				index++;
				count--;
			}
			//index e' incrementato, non e' un loop infinito

		}

		return token;
	}

}
