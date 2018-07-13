package cn.dd.CodeRecommend.minning;

import java.util.Scanner;

public class Match {

	public static void main(String[] args){
		Match m = new Match();
		m.getMethodName();
	}
	
	public void getMethodName(){
		Scanner s = new Scanner(System.in);
		System.out.println("Please input method name:");
		String methodName = s.next();
		System.out.println(methodName);
		s.close();
	}
	
	
}
