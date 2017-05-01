package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.dis2011.data.DB2ConnectionManager;

/**
 * Makler-Bean
 * 
 * Beispiel-Tabelle:
 * CREATE TABLE makler(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
 * name varchar(255),
 * address varchar(255),
 * login varchar(40) UNIQUE,
 * password varchar(40));
 */
public class Makler {
	private int id = -1;
	private String name;
	private String address;
	private String login;
	private String password;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String addresse) {
		this.address = address;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	//Ändert bereits vorhandene Makler
	public void updateMakler(String name,String eingabename,String eingabeaddress,String eingabelogin,String eingabepassword){
	
		try{
			Connection con = DB2ConnectionManager.getInstance().getConnection();
			String updateSQL = "UPDATE makler SET  name = ?, adresse = ?, login = ?, passwort = ? WHERE name = ?";
			PreparedStatement pstmt = con.prepareStatement(updateSQL);

			// Setze Anfrage Parameter
			
			pstmt.setString(1, eingabename);
			pstmt.setString(2, eingabeaddress);
			pstmt.setString(3, eingabelogin);
			pstmt.setString(4, eingabepassword);
			pstmt.setString(5, name);
			pstmt.executeUpdate();

			pstmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
	}
	}
	//löscht bereits vorhandenen Makler
	public void deleteMakler(String name){
		try{
			// Hole Verbindung
						Connection con = DB2ConnectionManager.getInstance().getConnection();

						// Erzeuge Anfrage
						String deleteSQL = "DELETE FROM makler where name=?";
						PreparedStatement pstmt = con.prepareStatement(deleteSQL);
						pstmt.setString(1, name);
						
						pstmt.executeUpdate();
		
		} catch (Exception e){
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		

		
	}
	
	//*zeigt Makler an
	public void Maklershow(){
		try{
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs;
			rs=stmt.executeQuery("Select * from makler");
				while(rs.next()){
					String address=rs.getString("adresse"); 
					String name=rs.getString("name");
					String login=rs.getString("login");
					String password=rs.getString("passwort");
					System.out.println("Name: "+name+" "+"Adresse: "+address+" "+"Login: "+login+" "+"Passwort: "+password);}
//			con.close();
		} catch (Exception e){
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
	}	
			
			
	
	
	/**
	 * Lädt einen Makler aus der Datenbank
	 * @param id ID des zu ladenden Maklers
	 * @return Makler-Instanz
	 */
	public static Makler load(int id) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM makler WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Makler ts = new Makler();
				ts.setId(id);
				ts.setName(rs.getString("name"));
				ts.setAddress(rs.getString("adresse"));
				ts.setLogin(rs.getString("login"));
				ts.setPassword(rs.getString("passwort"));

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
	 * Speichert den Makler in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 */
	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (getId() == -1) {
				// Achtung, hier wird noch ein Parameter mitgegeben,
				// damit spC$ter generierte IDs zurC<ckgeliefert werden!
				String insertSQL = "INSERT INTO makler(name, adresse, login, passwort) VALUES (?, ?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL,
						Statement.RETURN_GENERATED_KEYS);

				// Setze Anfrageparameter und fC<hre Anfrage aus
				pstmt.setString(1, getName());
				pstmt.setString(2, getAddress());
				pstmt.setString(3, getLogin());
				pstmt.setString(4, getPassword());
				pstmt.executeUpdate();

				// Hole die Id des engefC<gten Datensatzes
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setId(rs.getInt(1));
				}

				rs.close();
				pstmt.close();
			} else {
				// Falls schon eine ID vorhanden ist, mache ein Update...
				String updateSQL = "UPDATE makler SET name = ?, adresse = ?, login = ?, passwort = ? WHERE id = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setString(1, getName());
				pstmt.setString(2, getAddress());
				pstmt.setString(3, getLogin());
				pstmt.setString(4, getPassword());
				pstmt.setInt(5, getId());
				pstmt.executeUpdate();

				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
