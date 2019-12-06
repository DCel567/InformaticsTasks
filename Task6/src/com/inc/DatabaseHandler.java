package com.inc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler extends Config {
	Connection dbConnection;

	public Connection getDbConnection() throws ClassNotFoundException, SQLException{

		//Class.forName("com.mysql.jdbc.Driver");
		String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?verifyServerCertificate=false&useSSL=false&requireSSL=false&useLegacyDatetimeCode=false&amp&serverTimezone=UTC";
		dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

		return dbConnection;
	}

	public void insert_data(String contact_name, String contact_country, String contact_city,
				String contact_address, String contact_telephone, String contact_email){
		String insert = "INSERT INTO " + Const.CONTACTS_TABLE + "(" +
				Const.CONTACTS_NAME + ", " + Const.CONTACTS_COUNTRY + ", " +
				Const.CONTACTS_CITY + ", " + Const.CONTACTS_ADDRESS + ", " +
				Const.CONTACTS_TELEPHONE + ", " + Const.CONTACTS_EMAIL.toString() + ") " +
				"VALUES(?, ?, ?, ?, ?, ?)";


		try {
			PreparedStatement prSt = getDbConnection().prepareStatement(insert);
			prSt.setString(1,  contact_name);
			prSt.setString(2, contact_country);
			prSt.setString(3, contact_city);
			prSt.setString(4, contact_address);
			prSt.setString(5, contact_telephone);
			prSt.setString(6, contact_email);
			prSt.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void change_contact_name_telephone(String contact_name, String contact_telephone){
		String insert = "UPDATE " + Const.CONTACTS_TABLE + " SET " +
				Const.CONTACTS_NAME + " = " + "'" + contact_name + "', " +
				Const.CONTACTS_TELEPHONE + " = " + "'" + contact_telephone + "'" +
				" WHERE " + Const.CONTACTS_NAME + " = " + "'" + "НИИ Автоммашстрой" + "'";
		System.out.println(insert);

		try {
			PreparedStatement prSt = getDbConnection().prepareStatement(insert);
			prSt.executeUpdate();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void delete_by_id(String contact_id){
		String insert = "DELETE FROM " + Const.CONTACTS_TABLE + " WHERE " +
				Const.CONTACTS_ID + " = " + contact_id;

		try{
			PreparedStatement prSt = getDbConnection().prepareStatement(insert);
			prSt.executeUpdate();
		} catch (SQLException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}
