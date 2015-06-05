package com.stang.tang.rubik.model;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class RubikExlGenerator {

	public static RubikCube generate(int n, Workbook wbook) {
		return generate(n, wbook, 0, 1, 1);
	}

	/**
	 * 从{@code wbook}这个excel文件里的第{@code sht}页的第{@code beginRow}行第
	 * {@code beginColumn}列开始，加载阶数为{@code n}的魔方
	 * <pre>
	 * 加载顺序为
	 * 	      上
	 * 	   左 前 右 后
	 * 	      下
	 * </pre>
	 * 
	 * @param wbook
	 * @param sht
	 * @param beginRow
	 * @param beginColumn
	 * @param n
	 * @return
	 */
	public static RubikCube generate(int n, Workbook wbook, int sht, int beginRow,
			int beginColumn) {
		RubikCube cube = new RubikCube(n, true);
		int N = cube.getRank();
		Cube[][][] rubik = cube.rubik;
		if (n < 2 || wbook == null || wbook.getSheet(sht) == null || sht < 0
				|| beginRow < 0 || beginColumn < 0) {
			return null;
		}
		Sheet sheet = wbook.getSheet(sht);
		for (int i = 0; i < N; i++) {
			//up
			for (int j = N; j < 2 * N; j++) {
				Cell cell = sheet.getCell(beginColumn + j, beginRow + i);
				String desc = cell.getCellFormat().getBackgroundColour().getDescription();
				rubik[N - 1][i][j - N].setUp(getFaceplate(desc));
			}
		}
		for (int i = N; i < 2 * N; i++) {
			//left
			for (int j = 0; j < N; j++) {
				Cell cell = sheet.getCell(beginColumn + j, beginRow + i);
				String desc = cell.getCellFormat().getBackgroundColour().getDescription();
				rubik[2 * N - 1 - i][j][0].setLeft(getFaceplate(desc));
			}
			//font
			for (int j = N; j < 2 * N; j++) {
				Cell cell = sheet.getCell(beginColumn + j, beginRow + i);
				String desc = cell.getCellFormat().getBackgroundColour().getDescription();
				rubik[2 * N - 1 - i][N - 1][j - N].setFront(getFaceplate(desc));
			}
			//right
			for (int j = 2 * N; j < 3 * N; j++) {
				Cell cell = sheet.getCell(beginColumn + j, beginRow + i);
				String desc = cell.getCellFormat().getBackgroundColour().getDescription();
				rubik[2 * N - 1 - i][3 * N - 1 - j][N - 1].setRight(getFaceplate(desc));
			}
			//back
			for (int j = 3 * N; j < 4 * N; j++) {
				Cell cell = sheet.getCell(beginColumn + j, beginRow + i);
				String desc = cell.getCellFormat().getBackgroundColour().getDescription();
				rubik[2 * N - 1 - i][0][4 * N - 1 - j].setBack(getFaceplate(desc));
			}
		}
		for (int i = 2 * N; i < 3 * N; i++) {
			//down
			for (int j = N; j < 2 * N; j++) {
				Cell cell = sheet.getCell(beginColumn + j, beginRow + i);
				String desc = cell.getCellFormat().getBackgroundColour().getDescription();
				rubik[0][3 * N - 1 - i][j - N].setDown(getFaceplate(desc));
			}
		}
		return cube;
	}

	public static void show(RubikCube cube,WritableWorkbook wbook) {
		if (wbook == null) {
			return;
		}
		try {
			int N = cube.getRank();
			WritableSheet wsheet = wbook.getSheet("魔方平面图");
			if (wsheet == null) {
				wsheet = wbook.createSheet("魔方平面图", 0);
			}
			int rowBegin = wsheet.getRows();
			WritableCellFormat cBackgroud = new WritableCellFormat();
			cBackgroud.setBackground(Colour.WHITE);
			wsheet.addCell(new Label(3 * (4 * N + 2), rowBegin + 3 * N + 2,
					null, cBackgroud));

			for (int i = rowBegin; i < wsheet.getRows(); i++) {
				for (int j = 0; j < wsheet.getColumns(); j++) {
					wsheet.addCell(new Label(j, i, null, cBackgroud));
				}
			}

			WritableCellFormat[] cFormats = new WritableCellFormat[6];
			Colour[] colours = new Colour[6];
			colours[0] = Colour.YELLOW;
			colours[1] = Colour.OCEAN_BLUE;
			colours[2] = Colour.RED;
			colours[3] = Colour.GREEN;
			colours[4] = Colour.ORANGE;
			colours[5] = Colour.WHITE;
			for (int i = 0; i < 6; i++) {
				cFormats[i] = new WritableCellFormat();
				cFormats[i].setBackground(colours[i]);
				cFormats[i].setBorder(Border.ALL, BorderLineStyle.THIN);
			}

			Cube[][] faceUp = cube.getUp();
			Cube[][] faceLeft = cube.getLeft();
			Cube[][] faceFont = cube.getFront();
			Cube[][] faceRight = cube.getRight();
			Cube[][] faceBack = cube.getBack();
			Cube[][] faceDown = cube.getDown();

			for (int i = 1; i < N + 1; i++) {
				for (int j = N + 1; j < 2 * N + 1; j++) {
					Faceplate plate = faceUp[i - 1][j - N - 1].getUp();
					WritableCellFormat cell = getCellFormat(plate, cFormats);
					Label lab = new Label(j, rowBegin + i, null, cell);
					wsheet.addCell(lab);
				}
			}

			for (int i = N + 1; i < 2 * N + 1; i++) {
				for (int j = 1; j < N + 1; j++) {
					Faceplate plate = faceLeft[(2 * N - i)][j - 1].getLeft();
					WritableCellFormat cell = getCellFormat(plate, cFormats);
					Label lab = new Label(j, rowBegin + i, null, cell);
					wsheet.addCell(lab);
				}
				for (int j = N + 1; j < 2 * N + 1; j++) {
					Faceplate plate = faceFont[(2 * N - i)][j - N - 1].getFront();
					WritableCellFormat cell = getCellFormat(plate, cFormats);
					Label lab = new Label(j, rowBegin + i, null, cell);
					wsheet.addCell(lab);
				}
				for (int j = 2 * N + 1; j < 3 * N + 1; j++) {
					Faceplate plate = faceRight[(2 * N - i)][3 * N - j].getRight();
					WritableCellFormat cell = getCellFormat(plate, cFormats);
					Label lab = new Label(j, rowBegin + i, null, cell);
					wsheet.addCell(lab);
				}
				for (int j = 3 * N + 1; j < 4 * N + 1; j++) {
					Faceplate plate = faceBack[(2 * N - i)][4 * N - j].getBack();
					WritableCellFormat cell = getCellFormat(plate, cFormats);
					Label lab = new Label(j, rowBegin + i, null, cell);
					wsheet.addCell(lab);
				}
			}

			for (int i = 2 * N + 1; i < 3 * N + 1; i++) {
				for (int j = N + 1; j < 2 * N + 1; j++) {
					Faceplate plate = faceDown[3 * N - i][j - N - 1].getDown();
					WritableCellFormat cell = getCellFormat(plate, cFormats);
					Label lab = new Label(j, rowBegin + i, null, cell);
					wsheet.addCell(lab);
				}
			}

			for (int i = rowBegin; i < wsheet.getRows(); i++) {
				wsheet.setRowView(i, 1000);
			}
			for (int j = rowBegin; j < wsheet.getColumns(); j++) {
				wsheet.setColumnView(j, 10);
			}
		} catch (WriteException e) {
			e.printStackTrace();
		}

	}

	private static Faceplate getFaceplate(String desc) {
		if (desc != null) {
			desc = desc.toLowerCase();
			Faceplate[] faces = Faceplate.getAll();
			for (int i = 0; i < faces.length; i++) {
				if (desc.contains(faces[i].getDesc().toLowerCase())) {
					return faces[i];
				}
			}
		}
		return null;
	}

	private static WritableCellFormat getCellFormat(Faceplate plate,
			WritableCellFormat[] cFormats) {
		switch (plate) {
		case YELLOW:
			return cFormats[0];
		case BLUE:
			return cFormats[1];
		case RED:
			return cFormats[2];
		case GREEN:
			return cFormats[3];
		case ORANGE:
			return cFormats[4];
		case WHITE:
			return cFormats[5];
		case NONE:
			return null;
		default:
			return null;
		}
	}

}
