package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pachtvertrag extends Vertrag{

	private int tenancyid ;
	private String startdate;
	private String dauerinjahren;
	private String zusätzlichekosten;
	private String estateid;
	private String personid;
	
	public int gettenancyid() {
		return getcontractid();
	}
	
	public void settenancyid(int tenancyid) {
		this.tenancyid = tenancyid;}
	
	
	public String getstartdate(){
		return startdate;
	}
	public void setstartdate(String startdate){
		this.startdate=startdate;
	}
	public String getzusätzlichekosten(){
		return zusätzlichekosten;
	}
	public void setzusätzlichekosten(String zusätzlichekosten){
		this.zusätzlichekosten=zusätzlichekosten;
	}
	public String getdauerinjahren(){
		return dauerinjahren;
	}
	public void setdauerinjahren(String dauerinjahren){
		this.dauerinjahren=dauerinjahren;
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
			rs=stmt.executeQuery("Select * from tenancy");
				while(rs.next()){
					String startdate=rs.getString("startdate"); 
					String duration=rs.getString("duration");
					String person=rs.getString("personid");
					Integer id=rs.getInt("tenancyid");
					System.out.println("Beginn: "+startdate+" "+"Dauer: "+duration+" "+"ID: "+id+" "+"Mieter: "+person);
					}
			//con.close();
			
		} catch (SQLException e){
			while (e!=null){
				System.out.println(e.toString());
				System.out.println("ErrorCode: "+ e.getErrorCode());
				System.out.println("SQLState"+e.getSQLState());
				e=e.getNextException();
			}
//			System.err.println("Got an exception!");
//			
//			System.err.println(e.getMessage());
		}
	}	
	
	// füge Person zum Vertrag hinzu
	public void updatepersonpachtvertrag(int personid,int tenancyid){
		
		try{
		Connection con = DB2ConnectionManager.getInstance().getConnection();
		String updateSQL = "UPDATE tenancy SET personid= ? WHERE tenancyid = ?";
		PreparedStatement pstmt = con.prepareStatement(updateSQL);

		// Setze Anfrage Parameter
		pstmt.setInt(1, personid);
		pstmt.setInt(2,tenancyid );
		pstmt.executeUpdate();

		pstmt.close();}
		catch (SQLException e){
			while (e!=null){
				System.out.println(e.toString());
				System.out.println("ErrorCode: "+ e.getErrorCode());
				System.out.println("SQLState"+e.getSQLState());
				e=e.getNextException();
			}
//			System.err.println("Got an exception1!");
//			System.err.println(e.getMessage());
		}
	}
	//hier müsste noch die apartmentid mit übergebenwerden
	public void insertpachtvertrag(int contractid){
		try{
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();
			//wert 12 durch apartmentid ersetzen bzw. ? pstmt.setInt(5, houseid)
			String insertSQL = "INSERT INTO tenancy(tenancyid,startdate,duration,addcosts,estateid,personid) VALUES (?,?,?,?,12,null)";
			PreparedStatement pstmt = con.prepareStatement(insertSQL);
			pstmt.setInt(1, contractid);
			pstmt.setString(2,getstartdate());
			pstmt.setString(3, getdauerinjahren());
			pstmt.setString(4, getzusätzlichekosten());
			pstmt.executeUpdate();
			con.close();
	} catch (Exception e){
		System.err.println("Got an exception2!");
		System.err.println(e.getMessage());
	}
	}
	
}

