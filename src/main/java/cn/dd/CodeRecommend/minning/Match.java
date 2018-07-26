package cn.dd.CodeRecommend.minning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Match {
	
	private static String codeBasePath = "C:\\Users\\dlydd\\Desktop\\Senior\\ise\\code-recommend\\build_code_base\\code_base\\Datalog";
	private static int depth=1;
	ArrayList<String> list = new ArrayList<String>();
	
	
	
//	public String getMethodName(){
//		Scanner s = new Scanner(System.in);
//		System.out.println("Please input method name:");
//		String methodName = s.next();
//		//System.out.println(methodName);
//		s.close();
//		return methodName;
//	}
	
//	public String getSrcMethod(){
//		String methodName = getMethodName();
//		String srcMethod = methodName.substring(4);
//		//System.out.println(srcMethod);
//		return srcMethod;
//	}
	
	public ArrayList<String> findFiles(String codeBasePath,int depth) throws IOException{
//		int filecount=0;
		//get File object of pathName
		File dirFile = new File(codeBasePath);
		//judge whether the file exists
		if (!dirFile.exists()) {
			System.out.println("do not exist");
			return list;
		}
		//judge whether it is a directory,then judge file
		if (!dirFile.isDirectory()) {
			if (dirFile.isFile()) {
				File f = dirFile.getCanonicalFile();
//				System.out.println(f.toString());
				list.add(f.toString());
			}
			return list;
		}
		
//		for (int j = 0; j < depth; j++) {
//			System.out.print("  ");
//		}
//		System.out.print("|--");
//		System.out.println(dirFile.getName());
		//get all files and directories under this directory
		String[] fileList = dirFile.list();
		int currentDepth=depth+1;
		for (int i = 0; i < fileList.length; i++) {
			//go through
			String string = fileList[i];
			//File("documentName","fileName") is another contributor of File
			File file = new File(dirFile.getPath(),string);
			String name = file.getName();
			//if is a directory，depth++，output，recursion
			if (file.isDirectory()) {
				//recursion
				findFiles(file.getCanonicalPath(),currentDepth);
			}else{
				//if is file
//				for (int j = 0; j < currentDepth; j++) {
//					System.out.print("   ");
//				}
//				System.out.print("|--");
//				System.out.println(name);
				File f = file.getCanonicalFile();
				list.add(f.toString());
			}
		}
		return list;
	}
	
	public ArrayList<String> matchMethodName(){
//		String srcMethod = getSrcMethod();
		String input = getInput();
		input = input.replace(" ", "");
//		System.out.println(input);
		ArrayList<String> pathList = new ArrayList<String>();
		try {
			ArrayList<String> list = findFiles(codeBasePath, depth);
//			System.out.println(list.size());
			for(String s:list){
//				System.out.println(s);
				if(s.contains("equals")){
//					System.out.println(s);
					pathList.add(s);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pathList;
	}
	
	public void matchParamAndReturn(){
		
	}
	
	public ArrayList<String> matchClassName(ArrayList<String> pathList1){
		ArrayList<String> pathList2 = new ArrayList<String>();
		for(String path : pathList1){
			ArrayList<String> lineList = readFileByLines(path);
			for(String line : lineList){
				if(line.contains("Datalog")){
					pathList2.add(path);
					System.out.println(path);
					break;
				}
			}
		}
		return pathList2;
	}
	
	public static void main(String[] args){
		Match m = new Match();
//		String input = m.getInput();
		ArrayList<String> pathList1 = m.matchMethodName();
		ArrayList<String> pathList2 = m.matchClassName(pathList1);
//		m.matchParamAndReturn();
		m.output(pathList2);
	}
	
	public String getInput(){
		String input = "public boolean equals(Object o) {if (o == null)		return false;		if (o instanceof Datalog) {return ((Datalog) o).predicate.equals(this.predicate)					&& Arrays.equals(((Datalog) o).arguments, this.arguments);		}		return false;	}";
		return input;
	}
	
	public static ArrayList<String> readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // read line by line，until null
            while ((tempString = reader.readLine()) != null) {
                // output line number
//                System.out.println("line " + line + ": " + tempString);
            	list.add(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return list;
    }  
	
	public void output(ArrayList<String> pathList){		
//		String fileName = "C:\\Users\\dlydd\\Desktop\\Senior\\ise\\code-recommend\\build_code_base\\code_base\\Datalog\\Datalog\\equals(Object).txt";
		for(String path : pathList){
			ArrayList<String> codeList = readFileByLines(path);
			for(String code : codeList){
				System.out.println(code);
			}
		}
		
	}
	
}
