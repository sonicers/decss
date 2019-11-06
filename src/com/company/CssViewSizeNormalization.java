package com.company;

//控件大小屏幕适配
public class CssViewSizeNormalization {

	private String unit = "px";
	private double width = 400;
	private double height = 600;

	private String[] WIDHT = new String[]{"width", "margin-left", "left", "margin-right", "right"};
	private String[] HEIGHT = new String[]{"height", "margin-top", "top", "margin-bottom", "bottom"};


	public CssViewSizeNormalization(double w, double h) {
		width = w;
		height = h;
	}

	public String trimCss(String inputString) {

		inputString = removeUnusedAttri(inputString);

		int lastIndex = 0;
		String result = inputString;
		while (true) {
			int pxindex = indexOfUnit(result, lastIndex);

			if (pxindex < 0) {
				break;
			}

			if (lastIndex == pxindex) {
				pxindex = indexOfUnit(result, lastIndex + 1);
			}

			if (pxindex < 0) {
				break;
			}

			String tmp = trimCss(result, pxindex);
			if (tmp != null) {
				result = tmp;
			}

			lastIndex = pxindex;


		}
		out("result=" + result);
		return result;

	}

	public int indexOfUnit(String inputString) {
		return inputString.indexOf(unit);
	}

	public int indexOfUnit(String inputString, int fromIndex) {
		return inputString.indexOf(unit, fromIndex);
	}

	//进行预处理，把没有用到的字段清除掉
	private String removeUnusedAttri(String inputString) {
		//去除margin-xxx;这个属性不能使用百分比来表示。所以不好进行屏幕适配。发现模板H5页面中只是用这个属性进行居中，故可以用其他方式进行居中。
		if (inputString.contains("margin-left") || inputString.contains("margin-top")) {
			String l = getKey_Value(inputString, "margin-left");
			String t = getKey_Value(inputString, "margin-top");
			String left = getKey_Value(inputString, "left");
			String top = getKey_Value(inputString, "top");
			if (l != null) {
				inputString = inputString.replace(l, "");
			}
			if (t != null) {
				inputString = inputString.replace(t, "");
			}
			if (left != null) {
				inputString = inputString.replace(left, "");
			}
			if (top != null) {
				inputString = inputString.replace(top, "");
			}
		}
		///////////
		//去掉transform
		if (inputString.contains("transform")) {
			String keyValue = getKey_Value(inputString, "transform");
			if (keyValue != null) {
				inputString = inputString.replace(keyValue, "");
			}
		}

		return inputString;
	}

	private String getKey_Value(String inputString, String key) {
		if (inputString.contains(key)) {
			int keyIndex = inputString.indexOf(key);
			int semicolon = inputString.indexOf(";", keyIndex);
			String result = inputString.substring(keyIndex, semicolon + 1);
			out("result ="+result);
			return result;
		}
		return null;
	}

	private String getValue(String inputString, String key) {

		return null;
	}

	public String trimCss(String inputString, int index) {

		out("inputString = " + inputString);
		int pre = index - 1;
		int next = index + 2;
		char nextChar = inputString.charAt(next);
		out("nextChar = " + nextChar);
		if (nextChar != ' ' && nextChar != ';') {
			return null;
		}
		int emptyChar = indexOfFirstInvalidChar(inputString, pre);
		out("emptyChar index = " + emptyChar);
		if (emptyChar < 0) {
			return null;
		}
		String num = inputString.substring(emptyChar + 1, index);


		out("num =" + num);

		if (!isNumber(num)) {
			return null;
		}


		String aheadkey = aheadKey(inputString, emptyChar);
		out("ahead key =" + aheadkey);

		double f = -1;
		boolean isWidth = false;
		boolean isHeight = false;
		for (int i = 0; i < WIDHT.length; i++) {
			if (aheadkey.contains(WIDHT[i])) {
				double intNum = Double.parseDouble(num);
				out("int num =" + intNum);
				f = intNum * 1.0f / width;
				out("f =" + f);
				isWidth = true;
				break;
			}
		}
		if (!isWidth) {
			for (int i = 0; i < HEIGHT.length; i++) {
				if (aheadkey.contains(HEIGHT[i])) {
					double intNum = Double.parseDouble(num);
					out("int num =" + intNum);
					f = intNum * 1.0f / height;
					out("f =" + f);
					isHeight = true;
					break;
				}
			}
		}

		if (isWidth || isHeight) {
			StringBuilder sb = new StringBuilder();
			sb = sb.append(inputString.substring(0, emptyChar + 1));
			sb.append(f * 100);
			sb.append("%");
			sb.append(inputString.substring(index + unit.length()));
			out(sb.toString());
			return sb.toString();
		} else {
			return inputString;
		}
	}

	private boolean isNumber(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private int indexOfFirstInvalidChar(String inputString, int index) {
		for (int i = index - 1; i >= 0; i--) {
			char ch = inputString.charAt(i);
			if (!isValid(ch)) {
				return i;
			}
		}
		return -1;
	}

	private String aheadKey(String inputString, int index) {
		int x = 0;
		for (int i = index - 1; i >= 0; i--) {
			char ch = inputString.charAt(i);
			if (ch == ';' || ch == ' ') {
				if (inputString.charAt(i + 1) != ':') {
					x = i;
					break;
				}
			}
		}
		return inputString.substring(x, index);
	}

	private boolean isValid(char ch) {
		if (ch == '+' || ch == '-' || ch == '.') {
			return true;
		}
		if (ch == '0' || ch == '1' || ch == '2' || ch == '3' || ch == '4' || ch == '5' || ch == '6' || ch == '7' || ch == '8' || ch == '9') {
			return true;
		}
		return false;
	}


	private void out(String str) {
		System.out.println("xzf " + str);
	}

}