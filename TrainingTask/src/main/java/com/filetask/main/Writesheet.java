package com.filetask.main;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;

public class Writesheet {
	@SuppressWarnings("deprecation")
	public static void writetoExcel(List<Player> players) throws Exception {

		for (Player player : players) {

			// Create blank workbook
			XSSFWorkbook workbook = new XSSFWorkbook();

			// Create a blank sheet
			XSSFSheet spreadsheet = workbook.createSheet(" Employee Info ");

			// Create row object
			XSSFRow row;
			String jsonFile = player.getJson_file();
			Gson g = new Gson();
			Player tempPlayer = g.fromJson(jsonFile, Player.class);
			Date date = tempPlayer.getDob();
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
			String strDate = dateFormat.format(date);

			Map<String, Object[]> empinfo = new TreeMap<String, Object[]>();
			empinfo.put("1", new Object[] { "ID", "NAME", "ADDRESS", "DOB", "RUNS", "WICKETS" });
			empinfo.put("2", new Object[] { "123", tempPlayer.getName(), tempPlayer.getAddress(), strDate,
					Integer.toString(tempPlayer.getRuns()), Integer.toString(tempPlayer.getWickets()) });

			// Iterate over data and write to sheet
			Set<String> keyid = empinfo.keySet();
			int rowid = 0;

			for (String key : keyid) {
				row = spreadsheet.createRow(rowid++);
				Object[] objectArr = empinfo.get(key);
				int cellid = 0;

				for (Object obj : objectArr) {
					Cell cell = row.createCell(cellid++);
					cell.setCellValue((String) obj);
				}
			}

			String excelpath = System.getProperty("user.home") + "\\desktop\\ExcelOutput\\";

			File file1 = new File(excelpath);
			if (!file1.exists()) {
				file1.mkdir();
			}
			// Write the workbook in file system
			FileOutputStream out = new FileOutputStream(new File(excelpath + player.getId() + ".xlsx"));
			workbook.write(out);
			out.close();
			File file = new File(excelpath + player.getId() + ".xml");
			String xmlString = player.getXml_file();
			FileUtils.writeStringToFile(file, xmlString);

		}
	}
}