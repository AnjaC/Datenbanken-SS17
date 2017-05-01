package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Kaufvertrag extends Vertrag{
	private int purchaseid ;
	private String installments;
	private String interestrate;
	private String estateid;
	private String personid;
	
	public int getpurchaseid() {
		return purchaseid;
	}
	
	public void setpurchaseid(int purchaseid) {
		this.purchaseid = purchaseid;}
	
	
	public String getinstallments(){
		return installments;
	}
	public void setinstallments(String installments){
		this.installments=installments;
	}
	public String getinterestrate(){
		return interestrate;
	}
	public void setinterestrate(String interestrate){
		this.interestrate=interestrate;
	}
	public void setpersonid(String personid){
		this.personid=personid;
	}
	public String getpersonid(){
		return personid;
	}
	public void setestateid(String estateid){
		this.estateid=estateid;
		
	}
	public String getestateid(){
		return estateid;
	}
	public void pvertragshow(){
		try{
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs;
			rs=stmt.executeQuery("Select * from purchase");
				while(rs.next()){
					String interestrate=rs.getString("interestrate"); 
					String installments=rs.getString("installments");
					String person=rs.getString("personid");
					Integer id=rs.getInt("tenancyid");
					System.out.println("Interressenrate: "+interestrate+" "+"Installments: "+installments+" "+"ID: "+id+" "+"Käufer: "+person);
					}
			//con.close();
			
		} catch (Exception e){
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
	}	
	public void updatepersonpachtvertrag(int personid,int purchaseid){
		
		try{
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		String updateSQL = "UPDATE purchase SET personid= ? WHERE purchaseid = ?";
		PreparedStatement pstmt = con.prepareStatement(updateSQL);

		// Setze Anfrage Parameter
		pstmt.setInt(1, personid);
		pstmt.setInt(2,purchaseid );
		pstmt.executeUpdate();

		pstmt.close();}
		catch (Exception e){
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
	}
	//hier müsste noch die houseid mit übergeben werden
	public void insertkaufvertrag(int contractid){
		try{
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();
			//der wert20 müsste durch pstmt.setInt(4,houseid) ersetzt werden
			String insertSQL = "INSERT INTO purchase(purchaseid,interestrate,installments,estateid,personid) VALUES (?,?,?,20,null)";
			PreparedStatement pstmt = con.prepareStatement(insertSQL);
			pstmt.setInt(1, contractid);
			pstmt.setString(2,getinterestrate());
			pstmt.setString(3, getinstallments());

			pstmt.executeUpdate();
			con.close();
	} catch (Exception e){
		System.err.println("Got an exception!");
		System.err.println(e.getMessage());
	}
	}
	
}


