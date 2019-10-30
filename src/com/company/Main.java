package com.company;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //动画css代码分离
        separate();
        //控件大小屏幕适配
        normalization();
        //字体大小屏幕适配
        fontSizeNormalization();

    }

    private static void fontSizeNormalization() {
        String originFileName = "/Users/xiezhaofei/html/tmp2.txt";
        String targeFileName = "/Users/xiezhaofei/html/result.txt";
        CssFontSizeNormalization cssFontSizeNormalization = new CssFontSizeNormalization(originFileName, targeFileName, 64.69f);
        cssFontSizeNormalization.exec();
    }

    private static void normalization() {
        String originFileName = "/Users/xiezhaofei/html/tmp1.txt";
        String targeFileName = "/Users/xiezhaofei/html/tmp2.txt";

        if (new File(targeFileName).exists()) {
            new File(targeFileName).delete();
        }
        try {
            new File(targeFileName).createNewFile();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        CssNormalization cssMaker = new CssNormalization(414, 652.05);
        FileReader fr = null;
        BufferedReader bf = null;
        BufferedWriter out = null;
        try {
            //文件输入
            fr = new FileReader(originFileName);
            bf = new BufferedReader(fr);

            //文件输出
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(targeFileName, true)));


            String str;
            String result;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                result = cssMaker.trimCss(str);
                out.write(result);
                out.newLine();
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

    private static void separate() {
        String originFileName = "/Users/xiezhaofei/html/origin.txt";
        String targeFileName = "/Users/xiezhaofei/html/tmp1.txt";
        String cssFileName = "/Users/xiezhaofei/html/css.txt";
        CssSeparateAni cssSeparate = new CssSeparateAni(originFileName, targeFileName, cssFileName);
        cssSeparate.separareAni();
    }
}
