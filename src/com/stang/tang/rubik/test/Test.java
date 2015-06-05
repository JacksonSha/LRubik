package com.stang.tang.rubik.test;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.stang.tang.rubik.model.Cube;
import com.stang.tang.rubik.model.Faceplate;
import com.stang.tang.rubik.model.RubikCube;
import com.stang.tang.rubik.model.RubikCube2R;
import com.stang.tang.rubik.model.RubikCube3R;
import com.stang.tang.rubik.model.RubikException;
import com.stang.tang.rubik.scan.RelativePoint;
import com.stang.tang.rubik.scan.ScanFace;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Test {
	public static void test1() {
		Cube c = new Cube(Faceplate.YELLOW, Faceplate.WHITE, Faceplate.BLUE,
				Faceplate.GREEN, Faceplate.RED, Faceplate.ORANGE);
		System.out.println(c.show());
		System.out.println("------------------");
		c.turnFClockwise(1);
		System.out.println(c.show());
		System.out.println("------------------");
		c.turnFClockwise(-3);
		System.out.println(c.show());
		System.out.println("------------------");
		c.turnFClockwise(2);
		System.out.println(c.show());
		System.out.println("------------------");
	}

	public static void test2() {
		int[][][] cube = new int[][][] {
				{ { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } },
				{ { 10, 11, 12 }, { 13, 14, 15 }, { 16, 17, 18 } },
				{ { 19, 20, 21 }, { 22, 23, 24 }, { 25, 26, 27 } } };
		for (int i = 0; i < cube.length; i++) {
			System.out.println("========================================");
			for (int j = 0; j < cube[i].length; j++) {
				for (int k = 0; k < cube[i][j].length; k++) {
					System.out.print(cube[i][j][k]+"("+i+","+j+","+k+")\t");
				}
				System.out.println();
			}
		}
	}

	public static void test3() {
		RubikCube2R r = new RubikCube2R();
		r.showMe();
		r.turnSteps("RUR'U'R'FRF'");
		r.showMe();
		r.turnSteps("RUR'U'R'FRF'");
		r.showMe();
		r.turnSteps("RUR'U'R'FRF'");
		r.showMe();
	}

	public static void test4() {
		RubikCube2R r = new RubikCube2R();
		System.out.println(r.turnSteps("RU'R'U'F2U'RUR'DR2"));
		System.out.println(Pattern.matches(
				RubikCube2R.STEP_ONE_PATTERN.pattern(), "R'"));
		System.out.println("R'".charAt(1));
	}

	public static void test5() throws BiffException, IOException {
		WritableWorkbook wbook = null;
		File file=new File("C:/Documents and Settings/jacsha/Desktop/rubik.xls");
//		File file2=new File("C:/Documents and Settings/jacsha/Desktop/rubik22.xls");
		wbook = Workbook.createWorkbook(file);
		RubikCube r = new RubikCube2R();
		r.showMe();
		r.showMe(wbook);
		r.turnSteps("RU'R'U'F2U'RUR'DR2");
		r.showMe();
		r.showMe(wbook);
		r.turnSteps("Y");
		r.showMe();
		r.showMe(wbook);
		r.turnSteps("RU'R'U'F2U'RUR'DR2");
		r.showMe();
		r.showMe(wbook);
		r.turnSteps("R");
		r.showMe();
		r.showMe(wbook);
		System.out.print(r.isLayerOrderly(0));
		System.out.println(r.isLayerOrderly(1));
		try {
			wbook.write();
			wbook.close();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void test52() throws BiffException, IOException {
		WritableWorkbook wbook = null;
		File file=new File("C:/Documents and Settings/jacsha/Desktop/rubik.xls");
//		File file2=new File("C:/Documents and Settings/jacsha/Desktop/rubik22.xls");
		wbook = Workbook.createWorkbook(file);
		RubikCube r = new RubikCube3R();
		r.showMe();
		r.showMe(wbook);
		r.turnSteps("RU'RURURU'R'U'R2");
		r.showMe();
		r.showMe(wbook);
		r.turnSteps("R2URUR'U'R'U'R'UR'");
		r.showMe();
		r.showMe(wbook);
		r.turnSteps("R");
		r.showMe();
		r.showMe(wbook);
		System.out.print(r.isLayerOrderly(0));
		System.out.println(r.isLayerOrderly(1));
		try {
			wbook.write();
			wbook.close();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isChinese(char c) {
		String regEx = "[\u4e00-\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(c + "");
		if (m.find()) {
			return true;
		}
		return false;
	}

	public static void printChinese(){
		char word='医';
		int begin=word;
		for(int i=1;i<10;i++){
			System.out.println(i+" "+word +" "+(int)word);
			word=(char)(begin+i);
		}
		System.out.println("================");
		 word='醫';
		 begin=word;
		for(int i=1;i<10;i++){
			System.out.println(i+" "+word+" "+(int)word);
			word=(char)(begin+i);
		}
	}
	
	public static void test6(){
		String blank="";
		System.out.println("空".length());
		System.out.println("|空|");
		for(int i=0;i<"空".length();i++){
			blank+=" ";
		}
		System.out.println("|"+blank+"|");
	}
	
	public static void test7() throws BiffException, IOException{
		Workbook wbook=Workbook.getWorkbook(new File("C:/Documents and Settings/jacsha/Desktop/rubik22.xls"));
		WritableWorkbook wbook2=Workbook.createWorkbook(new File("C:/Documents and Settings/jacsha/Desktop/rubik23.xls"));
		RubikCube r=new RubikCube(2,wbook,0,10,1);
		r.showMe();
		r.showMe(wbook2);
		try {
			wbook.close();
			wbook2.write();
			wbook2.close();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
	
	public static void test8() {
		Workbook wbook;
		try {
			wbook = Workbook.getWorkbook(new File(
					"C:/Documents and Settings/jacsha/Desktop/rubik22.xls"));
			RubikCube2R r = new RubikCube2R(wbook);
			r.showMe();
			String[] steps;
			steps = r.recoverRubik();
			for (String s : steps) {
				System.out.println(s);
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RubikException e) {
			e.printStackTrace();
		}
	}

	public static void test9() {
	}

	public static void main(String[] args) {
		test3();
	}

}
