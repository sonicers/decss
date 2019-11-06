package com.company;

import java.io.*;

public class CssStyle2Id {
    private String originFilePath;
    private String targetFilePath;
    private String idCssFilePath;

    private int count = 0;

    public CssStyle2Id(String origin, String target, String idCssFilePath) {
        originFilePath = origin;
        targetFilePath = target;
        this.idCssFilePath = idCssFilePath;

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
        File aniCssFile = new File(idCssFilePath);
        if (aniCssFile.exists()) {
            aniCssFile.delete();
        }
        try {
            aniCssFile.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void exec() {
        FileReader fr = null;
        BufferedReader bf = null;
        BufferedWriter out = null;
        BufferedWriter id = null;
        try {
            //文件输入
            fr = new FileReader(originFilePath);
            bf = new BufferedReader(fr);

            //文件输出
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(targetFilePath, true)));

            id = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(idCssFilePath, true)));

            String str;
            String result;

            String[] outs = new String[2];
            StringBuilder stringBuilder = null;
            boolean isDivItem = false;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                if (str.equals("")) {
                    continue;
                }

                if (str.contains("<div")) {
                    isDivItem = true;
                    stringBuilder = new StringBuilder();
                }

                if (isDivItem) {
                    stringBuilder.append(str);
                    if (!str.contains(">")) {
                        stringBuilder.append(" ");
                        continue;
                    }
                }

                if (isDivItem) { //id
                    str = stringBuilder.toString();
                }

                //separateAni(str, outs);
                if (isDivItem) {
                    exec2(str, outs);
                    if (outs[0] != null) {
                        out.write(outs[0]);
                        out.newLine();
                    }

                    if (outs[1] != null) {
                        id.write(outs[1]);
                        id.newLine();
                    }
                } else {
                    out.write(str);
                    out.newLine();
                }


                isDivItem = false;
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
            if (id != null) {
                try {
                    id.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private void exec2(String inputString, String[] outs) {
        if (inputString.contains("style=")) {
            String styleValue = getStyle(inputString);
            String id = "#div" + count + "Id";
            outs[1] = id + "{" + styleValue + "}";
            outs[0] = inputString.replace(styleValue, "div" + count + "Id").replace("style", "id");
            count++;
        } else {
            outs[0] = inputString;
            outs[1] = null;
        }
    }

    private String getStyle(String inputString) {
        int styleIndex = inputString.indexOf("style=");
        int start, end;
        if (styleIndex >= 0) {
            char ch = inputString.charAt(styleIndex + "style=".length());
            start = styleIndex + "style=".length() + 1;
            end = inputString.indexOf(ch, start);
            return inputString.substring(start, end);
        }

        return null;
    }
}
