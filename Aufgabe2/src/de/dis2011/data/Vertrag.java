package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Vertrag {
	
		private int contractid = -1;
		private String datum;
		private String ort;
	
		
		public int getcontractid() {
			return contractid;
		}
		
		public void setcontractid(int contractid) {
			this.contractid = contractid;
		}
		
		public String getdatum() {
			return datum;
		}
		
		public void setdatum(String datum) {
			this.datum=datum;
		}
		
		public String getort() {
			return ort;
		}
		
		public void setort(String ort) {
			this.ort = ort;
		}
	
		public static Vertrag load(int contractid) {
			try {
				// Hole Verbindung
				Connection con = DB2ConnectionManager.getInstance().getConnection();

				// Erzeuge Anfrage
				String selectSQL = "SELECT * FROM contract WHERE contractid = ?";
				PreparedStatement pstmt1 = con.prepareStatement(selectSQL);
				pstmt1.setInt(1, contractid);

				// Führe Anfrage aus
				ResultSet RS = pstmt1.executeQuery();
				if (RS.next()) {
					Vertrag c = new Vertrag();
					c.setcontractid(contractid);
					c.setort(RS.getString("Date"));
					c.setdatum(RS.getString("Place"));

					RS.close();
					pstmt1.close();
					return c;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
		//zeigt Verträge an
		
		public void vertragshow(){
			try{
				// Hole Verbindung
				Connection con = DB2ConnectionManager.getInstance().getConnection();
				Statement stmt=con.createStatement();
				ResultSet rs;
				rs=stmt.executeQuery("Select * from contract");
					while(rs.next()){
						String datum=rs.getString("date"); 
						String ort=rs.getString("place");
						System.out.println("Datum: "+datum+" "+"Ort: "+ort);}
//				con.close();
				
			} catch (Exception e){
				System.err.println("Got an exception!");
				System.err.println(e.getMessage());
			}
		}	
			
		
		/**
		 * Speichert den vertrag in der Datenbank. Ist noch keine ID vergeben
		 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
		 */
		public void save() {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			try {
				// FC<ge neues Element hinzu, wenn das Objekt noch keine ID hat.
				if (getcontractid() == -1) {
					// Achtung, hier wird noch ein Parameter mitgegeben,
					// damit spC$ter generierte IDs zurC<ckgeliefert werden!
					String insertSQL = "INSERT INTO contract(date, place) VALUES (?, ?)";

					PreparedStatement pstmt = con.prepareStatement(insertSQL,
							Statement.RETURN_GENERATED_KEYS);

					// Setze Anfrageparameter und fC<hre Anfrage aus
					pstmt.setString(1, getdatum());
					pstmt.setString(2, getort());
					pstmt.executeUpdate();

					// Hole die Id des engefC<gten Datensatzes
					ResultSet rs = pstmt.getGeneratedKeys();
					if (rs.next()) {
						setcontractid(rs.getInt(1));
					}

					rs.close();
					pstmt.close();
				} else {
					// Falls schon eine ID vorhanden ist, mache ein Update...
					String updateSQL = "UPDATE contract SET date = ?, place = ? WHERE contractid = ?";
					PreparedStatement pstmt = con.prepareStatement(updateSQL);

					// Setze Anfrage Parameter
					pstmt.setString(1, getdatum());
					pstmt.setString(2, getort());
					pstmt.setInt(3, getcontractid());
					pstmt.executeUpdate();

					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

