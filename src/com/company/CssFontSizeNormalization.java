package com.company;

import java.io.*;

//字体大小屏幕适配
public class CssFontSizeNormalization {
    private String originFilePath;
    private String targetFilePath;
    private float htmlFontSize;


    public CssFontSizeNormalization(String originFilePath, String targetFilePath, float htmlFontSize) {
        this.originFilePath = originFilePath;
        this.targetFilePath = targetFilePath;
        this.htmlFontSize = htmlFontSize;
        File targetFile = new File(targetFilePath);
        if (targetFile.exists()) {
            targetFile.delete();
        }
        try {
            targetFile.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void exec() {
        FileReader fr = null;
        BufferedReader bf = null;
        BufferedWriter out = null;
        try {
            //文件输入
            fr = new FileReader(originFilePath);
            bf = new BufferedReader(fr);

            //文件输出
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(targetFilePath, true)));

            String str;

            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                str = exec2(str, "font-size");
                str = exec2(str, "letter-spacing");
                if (str != null) {
                    out.write(str);
                    out.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bf != null) {
                    bf.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    private String exec2(String inputString, String fontKey) {
        String result = inputString;
        int from = 0;
        while (result.indexOf(fontKey, from) >= 0) {
            String size = getValue(inputString, fontKey);
            String keyValue = getKey_Value(result, fontKey);
            out("size=" + size);
            out("keyvalue=" + keyValue);
            from = result.indexOf(fontKey, from) + fontKey.length();
            if (size.contains("px")) {
                size = size.replace("px", "");
                float fsize = Float.parseFloat(size);
                float remSize = fsize / htmlFontSize;
                result = result.replace(keyValue, fontKey + ": " + remSize + "rem;");
            }
        }
        return result;
    }


    private String getKey_Value(String inputString, String key) {
        if (inputString.contains(key)) {
            int keyIndex = inputString.indexOf(key);
            int semicolon = inputString.indexOf(";", keyIndex);
            String result = inputString.substring(keyIndex, semicolon + 1);
            out("result =" + result);
            return result;
        }
        return null;
    }

    private String getValue(String inputString, String key) {
        String keyValue = getKey_Value(inputString, key);
        if (keyValue == null) {
            return null;
        }
        int start, end;
        int kl = key.length();
        while (true) {
            if (keyValue.charAt(kl) != ':' && keyValue.charAt(kl) != ' ') {
                break;
            }
            kl++;
        }
        start = kl;
        end = keyValue.indexOf(";");
        return keyValue.substring(start, end);
    }

    private void out(String string) {
        System.out.println("CssFontSizeNormalization " + string);
    }


}
