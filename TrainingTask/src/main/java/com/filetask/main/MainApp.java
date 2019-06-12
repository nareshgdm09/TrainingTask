package com.filetask.main;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xslf.usermodel.Placeholder;

import com.google.gson.Gson;

public class MainApp {
	static Logger logger = Logger.getLogger(MainApp.class);
	// to store dbrecords filename for next iteration check
	static List<String> DBList = new ArrayList<String>();
	// to store dbrecords for next iteration check while generating excel files
	static List<Player> DBListRecords = new ArrayList<Player>();

	public static void main(String[] args) throws SQLException {
		// json and xml files directory paths below two lines
		String jsonPath = "C:\\Users\\Naresh\\Desktop\\JSONDirectory";
		String xmlPath = "C:\\Users\\Naresh\\Desktop\\XMLDirectory";
		listFilesForFolder(jsonPath, xmlPath);
	}

	@SuppressWarnings("deprecation")
	public static void listFilesForFolder(String JSONdirectory, String XMLDirectory) throws SQLException {

		List<Player> playerDBRecords = new ArrayList<Player>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sqlQuery = "insert into player" + " values (?,?,?,?,?)";

		// First time check to get already inserted records from db
		if (DBList.isEmpty()) {
			DBList = GetDbRecords.getRecordsById();
			DBListRecords = GetDbRecords.getAllRecords();
		}

		while (true) {
			
			File JSOONDir = new File(JSONdirectory);
			File XMLDir = new File(XMLDirectory);

			File[] JSONFileArr = JSOONDir.listFiles();
			File[] XMLFileArr = XMLDir.listFiles();

			List<String> jsonFileList = new ArrayList<String>();
			List<String> xmlFileList = new ArrayList<String>();

			for (File file_name : JSONFileArr) {
				jsonFileList.add(file_name.getName());
			}

			for (File file_name : XMLFileArr) {
				xmlFileList.add(file_name.getName().replace(".xml", ".json"));
			}

			// finding common elements from the json and xml directory
			jsonFileList.retainAll(xmlFileList);

			// remove the files which are in db
			jsonFileList.removeAll(DBList);

			//goto insert code if the files > 0
			if (jsonFileList.size() > 0) {

				conn = DatabaseConnection.getInstance().getConnection();

				for (int i = 0; i < jsonFileList.size(); i++) {
					Player playerpojo = new Player();
					try {

						File file = new File(JSONFileArr[i].getPath());
						File file1 = new File(XMLFileArr[i].getPath());

						// convert file data to String
						String jsonString = FileUtils.readFileToString(file);
						String xmlString = FileUtils.readFileToString(file1);

						// getting the file name as the prmary key
						int id = Integer.valueOf(jsonFileList.get(i).replace(".json", ""));

						java.sql.Date create_time = new java.sql.Date(new Date().getTime());

						Gson g = new Gson();
						Player player = g.fromJson(jsonString, Player.class);

						playerpojo.setId(id);
						playerpojo.setCreate_time(create_time);

						playerpojo.setJson_file(jsonString);
						playerpojo.setXml_file(xmlString);
						playerpojo.setStatus("Playing");

						playerDBRecords.add(playerpojo);

					} catch (Exception e) {

						logger.debug("Error Record" + jsonFileList.get(i));
						logger.debug("Your description here", e);
					} finally {

					}
				}

				try {
					pstmt = conn.prepareStatement(sqlQuery);

					for (Player player : playerDBRecords) {

						pstmt.setInt(1, player.getId());
						pstmt.setDate(2, (java.sql.Date) player.getCreate_time());
						pstmt.setString(3, player.getStatus());
						pstmt.setString(4, player.getJson_file());
						pstmt.setString(5, player.getXml_file());
						pstmt.addBatch();
					}

					int[] result = pstmt.executeBatch();

					if (result.length > 0) {
						DBList = GetDbRecords.getRecordsById();
						List<Player> tempRecords = DBListRecords;
						List<Player> players = GetDbRecords.getAllRecords();
						players.removeAll(tempRecords);
						Writesheet.writetoExcel(players);
					}
				} catch (Exception e) {
					logger.debug("Exception : ", e);
				} finally {
					DBList = GetDbRecords.getRecordsById();
					if (pstmt != null) {
						System.out.println("Prepared statement Closed");
						pstmt.close();
					}
					if (conn != null) {
						System.out.println("DB Connection Closed");
						conn.close();
						conn=null;
					}
				}
			} else {
				System.out.println("no new records found");
			}
			try {
				//giving 30 seconds time for next iteration
				Thread.sleep(1 * 10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}