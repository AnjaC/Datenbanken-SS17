package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Person {
	private int personid = -1;
	private String firstname;
	private String lastname;
	private String address;
	
	public int getpersonId() {
		return personid;
	}
	
	public void setpersonId(int personid) {
		this.personid = personid;
	}
	
	public String getfirstName() {
		return firstname;
	}
	
	public void setfirstName(String firstname){
		this.firstname=firstname;
	}
	
	public String getlastName(){
		return lastname;
	}
	public void setlastName(String lastname) {
		this.lastname = lastname;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	

	
	/**
	 * Lädt einen Person aus der Datenbank
	 * @param id ID der zu ladenden Person
	 * @return Person-Instanz
	 */
	public static Person load(int personid) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM Person WHERE personid = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, personid);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Person ts = new Person();
				ts.setpersonId(personid);
				ts.setfirstName(rs.getString("Vorname"));
				ts.setlastName(rs.getString("Nachnahme"));
				ts.setAddress(rs.getString("Adresse"));
	

				rs.close();
				pstmt.close();
				return ts;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Speichert die Person in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 */
	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (getpersonId() == -1) {
				// Achtung, hier wird noch ein Parameter mitgegeben,
				// damit spC$ter generierte IDs zurC<ckgeliefert werden!
				String insertSQL = "INSERT INTO person(firstname, lastname, address) VALUES (?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				// Setze Anfrageparameter und fC<hre Anfrage aus
				pstmt.setString(1, getfirstName());
				pstmt.setString(2, getlastName());
				pstmt.setString(3, getAddress());
				pstmt.executeUpdate();

				// Hole die Id des engefC<gten Datensatzes
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setpersonId(rs.getInt(1));
				}

				rs.close();
				pstmt.close();
			} else {
				// Falls schon eine ID vorhanden ist, mache ein Update...
				String updateSQL = "UPDATE makler SET firstname = ?, lastname = ?, address = ? WHERE personid = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setString(1, getfirstName());
				pstmt.setString(2, getlastName());
				pstmt.setString(3, getAddress());
				pstmt.setInt(5, getpersonId());
				pstmt.executeUpdate();

				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
