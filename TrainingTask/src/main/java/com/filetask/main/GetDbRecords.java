package com.filetask.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class GetDbRecords {
	static Logger logger = Logger.getLogger(GetDbRecords.class);
	static Connection conn = null;
	static PreparedStatement pstmt = null;
	static String getIdQuery = "select * from player";
	static String getAllRecordsQuery = "select * from player";

	static List<String> getRecordsById() {
		List<String> records = new ArrayList<String>();
		try {
			conn = DatabaseConnection.getInstance().getConnection();
			pstmt = conn.prepareStatement(getIdQuery);
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String toJson = id + ".json";
				records.add(toJson);
			}
		} catch (Exception e) {
			logger.debug("Your description here", e);
		} finally {
		}
		return records;
	}

	static List<Player> getAllRecords() {
		List<Player> records = new ArrayList<Player>();
		try {
			conn = DatabaseConnection.getInstance().getConnection();
			pstmt = conn.prepareStatement(getAllRecordsQuery);
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				Player p = new Player();
				p.setId(resultSet.getInt(1));
				p.setCreate_time(resultSet.getDate(2));
				p.setStatus(resultSet.getString(3));
				p.setJson_file(resultSet.getString(4));
				p.setXml_file(resultSet.getString(5));

				records.add(p);
			}
		} catch (Exception e) {
			logger.debug("Your description here", e);
		} finally {
		}
		return records;
	}

}
