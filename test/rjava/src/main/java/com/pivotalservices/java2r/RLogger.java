/**
 * 
 */
package com.pivotalservices.java2r;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

//import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Mino Togna
 * 
 */
public class RLogger {
	private static final Pattern LINE_PATTERN = Pattern.compile("\n*(.+)\n*");

	private static final String CALC_ERROR_SIGNAL = "CALC-ERROR";

	private final List<RLoggerLine> lines;

	@JsonIgnore
	private boolean containsCalcErrorSignal;

	private RLoggerLine tempLine;

	public RLogger() {
		lines = new ArrayList<RLogger.RLoggerLine>();
		containsCalcErrorSignal = false;
	}

	public void append(int oType, String text) {
		if (StringUtils.hasText(text) && !text.equals("[1]")) {
			// check if there has been an error
			if (CALC_ERROR_SIGNAL.equals(text.trim())) {
				containsCalcErrorSignal = true;
			} else {
				getTempLine(oType).append(text);
			}
		}
	}

	/**
	 * Appends a error message.
	 */
	public void appendError(String text) {
		flush();
		append(1, text);
		flush();
	}

	void flush() {
		if (tempLine != null) {
			String text = tempLine.getText();
			// find new lines to add
			Matcher matcher = LINE_PATTERN.matcher(text);
			while (matcher.find()) {
				String lineText = matcher.group(1);
				if (lineText.contains("\\")) {
					System.out.println("aaaa");
				}
				RLoggerLine line = new RLoggerLine(tempLine.oType, lineText);
				lines.add(line);
			}
			tempLine = null;
		}
	}

	private RLoggerLine getTempLine(int oType) {
		if (tempLine == null) {
			tempLine = new RLoggerLine(oType);
		}
		return tempLine;
	}

	public boolean containsCalcErrorSignal() {
		return containsCalcErrorSignal;
	}

	public List<RLoggerLine> getLines() {
		return lines;
	}

	static class RLoggerLine {

		private final int oType;
		private final StringBuilder sb;

		RLoggerLine(int oType, String text) {
			this.oType = oType;
			sb = new StringBuilder();
			if (text != null) {
				sb.append(text);
			}
		}

		void append(String text) {
			sb.append(text);
		}

		RLoggerLine(int oType) {
			this(oType, null);
		}

		public int getoType() {
			return oType;
		}

		@JsonInclude
		public String getText() {
			return sb.toString();
		}
	}

}
