package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CssSeparateAni {
	private String originFilePath;
	private String targetFilePath;
	private String aniCssFilePath;
	
	private int count = 0;
	
	public CssSeparateAni(String origin,String target,String css){
		originFilePath = origin;
		targetFilePath = target;
		aniCssFilePath = css;
		
		File targetFile = new File(targetFilePath);
		if(targetFile.exists()){
			targetFile.delete();
		}
		try {
			targetFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File aniCssFile = new File(aniCssFilePath);
		if(aniCssFile.exists()){
			aniCssFile.delete();
		}
		try {
			aniCssFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void separareAni(){
		FileReader fr=null;
		BufferedReader bf = null;
		BufferedWriter out = null ;  
		BufferedWriter css = null;
		try {
			//文件输入
			fr = new FileReader(originFilePath);
			bf = new BufferedReader(fr);
			
			//文件输出 
			out = new  BufferedWriter( new  OutputStreamWriter(  
                    new  FileOutputStream(targetFilePath,  true )));  
             
			css = new  BufferedWriter( new  OutputStreamWriter(  
                    new  FileOutputStream(aniCssFilePath,  true ))); 
			
			String str;
			String result;
			
			String[] outs = new String[2];
			// 按行读取字符串
			while ((str = bf.readLine()) != null) {
				separateAni(str,outs);
				if(outs[0]!=null){
					out.write(outs[0]);
				}
				
				if(outs[1]!=null){
					css.write(outs[1]);
					css.newLine();
				}
			}
			  
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(bf!=null){
					bf.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(fr!=null){
					fr.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(out!=null){
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(css!=null){
				try {
					css.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void separateAni(String inputString,String[] outs){
		//out(inputString);
		String result = inputString;
		String css = null;
		String tmp;
		
		if(result!=null && result.contains("style")){
			int index = result.indexOf("animation");
			if(index>=0){
				int end = result.indexOf(";",index);
				tmp = result.substring(index, end+1);
				//
				result = result.replace(tmp, "") +" v-bind:class=\"anirun ? '"+"div"+count+"Ani' : null\"";
				
				out(result);
				css = ".div"+count+"Ani{"+tmp+"}";
				out(css);
				count++;
			}
		}
		outs[0] = result;
		outs[1] = css;
	}
	
	
	private void out(String str){
		//System.out.println("hhh "+str);
	}
}
