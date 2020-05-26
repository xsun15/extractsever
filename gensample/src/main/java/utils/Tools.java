package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tools {
	public static void getAllFileName(String path, ArrayList<String> listFileName){
		File file = new File(path);
		File [] files = file.listFiles();
		String [] names = file.list();
		if(names != null){
			String [] completNames = new String[names.length];
			for(int i=0;i<names.length;i++){
				completNames[i]=path+names[i];
			}
			listFileName.addAll(Arrays.asList(completNames));
		}
	}
	//打开文件
	public static String readFile(String m) {
		String pathname = m;
		File file=new File(pathname);
		BufferedReader br=null;
		String result = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			String line = "";
			//网友推荐更加简洁的写法
			while ((line = br.readLine()) != null) {
				// 一次读入一行数据
				result += line + "\n" ;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<String> getFileContextList(String path)  {
		List<String> list =new ArrayList<>();
		try {
			FileReader fileReader =new FileReader(path);
			BufferedReader bufferedReader =new BufferedReader(fileReader);
			String str=null;
			while((str=bufferedReader.readLine())!=null) {
				if(str.trim().length()>=2) {
					str = str.trim().replaceAll("\n", "");
					list.add(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void saveAsFileWriter(String savepath, String content) {
		FileWriter fwriter = null;
		try {
			// true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
			fwriter = new FileWriter(savepath, true);
			fwriter.write(content);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fwriter.flush();
				fwriter.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
