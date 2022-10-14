package application;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.ncsu.csc316.dsa.list.SinglyLinkedList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainPage {
	@FXML
	private TextField txtReplace;

	@FXML
	private TextField txtReplaced;

	@FXML
	private TextArea taText;

	public void execute() {
		String strReplaced = txtReplaced.getText();
		String strText = taText.getText();
		String strReplace = txtReplace.getText();

		strReplaced = stringFormat(strReplaced);

		// Generates Replacer Groups
		SinglyLinkedList<String> llStrParseReplaces = new SinglyLinkedList<String>();
		strReplace = stringFormat(strReplace);
		int i = strReplace.indexOf("\\(");
		if (i != -1) {
			while (i != -1) {
				int j = strReplace.indexOf("\\)", i);
				llStrParseReplaces.addLast(stringFormat(strReplace.substring(i + 2, j)));
				i = strReplace.indexOf("\\(", j);
			}
		}
		Pattern patternReplaced = Pattern.compile(strReplaced);
		Matcher matcher = patternReplaced.matcher(strText);

		if (matcher.groupCount() != 0) {
			while (matcher.find()) {
				String strReplacedPart = matcher.group();
				Iterator<String> iterator = llStrParseReplaces.iterator();
				for (i = 1; i <= matcher.groupCount(); i++) {
					strReplacedPart = strReplacedPart.replace(matcher.group(i), iterator.next());
				}
				strText = strText.replace(matcher.group(), strReplacedPart);
				matcher = patternReplaced.matcher(strText);
			}
		} else {
			while (matcher.find()) {
				strText = strText.replace(matcher.group(), strReplace);
				matcher = patternReplaced.matcher(strText);
			}
		}
		taText.setText(strText);
	}

	private String stringFormat(String str) {
		StringBuilder sbParseReplacers = new StringBuilder(str.length());
		// Generates Replaced Regex
		for (int i = 0; i < str.length(); i++) {
			char charThis = str.charAt(i);
			if (charThis == '\\') {
				i++;
				char charSwitch = str.charAt(i);
				switch (charSwitch) {
				case 't':
					sbParseReplacers.append('\t');
					break;
				case 'b':
					sbParseReplacers.append('\b');
					break;
				case 'n':
					sbParseReplacers.append('\n');
					break;
				case 'r':
					sbParseReplacers.append('\r');
					break;
				case 'f':
					sbParseReplacers.append('\f');
					break;
				default:
					sbParseReplacers.append("\\" + charSwitch);
					break;
				}
			} else {
				sbParseReplacers.append(charThis);
			}
		}
		return sbParseReplacers.toString();
	}
}
