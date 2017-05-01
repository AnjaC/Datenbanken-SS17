package de.dis2011;

import java.util.Scanner;
import de.dis2011.data.Vertrag;
import de.dis2011.data.Immobilie;
import de.dis2011.data.Makler;
import de.dis2011.data.Person;
import de.dis2011.data.Pachtvertrag;
import de.dis2011.data.Kaufvertrag;

/**
 * Hauptklasse
 */
public class Main {
	static int contractid;
	static int estateid;
	static int personid;
	static int tenancyid;
	static int purchaseid;
	static int apartmentid;
	static int houseid;
	/**
	 * Startet die Anwendung
	 */
	public static void main(String[] args) {
		showMainMenu();
	}
	
	/**
	 * Zeigt das Hauptmenü
	 */
	public static void showMainMenu() {
		//Menüoptionen
		final int MENU_MAKLER = 0;
		final int MENU_IMMOBILIE=1;
		final int MENU_VERTRAG=2;
		final int QUIT = 3;
		
		//Erzeuge Menü
		Menu mainMenu = new Menu("Hauptmenü");
		mainMenu.addEntry("Makler-Verwaltung", MENU_MAKLER);
		mainMenu.addEntry("Immobilienverwaltung", MENU_IMMOBILIE);
		mainMenu.addEntry("Vertrags-Verwaltung", MENU_VERTRAG);
		mainMenu.addEntry("Beenden", QUIT);
	
		//Verarbeite Eingabe
		
		while(true) {
			int response = mainMenu.show();
			
			switch(response) {
			case MENU_VERTRAG:
				showvertragsMenu();
					break;
			case MENU_IMMOBILIE:
				showImmobilienMenu();
				break;
				
				case MENU_MAKLER:
					showMaklerMenu();
					break;
				case QUIT:
					return;
			}
		}}
	
	//*Erzeuge VertragsMenu
		public static void showvertragsMenu(){
			final int NEW_VERTRAG=2;
			final int BACK=1;
			final int NEW_PERSON=0;
			final int PACHTVERTRAG=4;
			final int KAUFVERTRAG=5;
			final int VERTRÄGE=6;
			
			Menu vertragsMenu = new Menu("Vertrags-Verwaltung");
			vertragsMenu.addEntry("Kaufvertragsverwaltung", KAUFVERTRAG);
			vertragsMenu.addEntry("Pachtvertragsverwaltung", PACHTVERTRAG);
			vertragsMenu.addEntry("Vertragsübersicht", VERTRÄGE);
			vertragsMenu.addEntry("Zurück zum Hauptmenü", BACK);
			
			
			//Verarbeite Eingabe
			while(true) {
				
				int response = vertragsMenu.show();
				
				switch(response) {

				case PACHTVERTRAG:
					showpvertragsMenu();
					break;
				case KAUFVERTRAG:
					showkvertragsMenu();
						break;
				case VERTRÄGE:
					showverträge();
					break;
					case BACK:
						return;
				}
			}
			}
		
		//Pachtvertragsmenü

		public static void showpvertragsMenu(){
			final int NEW_PVERTRAG=0;
			final int NEW_MIETER=1;
			final int BACK=2;
			
			
			Menu vertragspMenu = new Menu("Vertrags-Verwaltung");
			vertragspMenu.addEntry("Neuen Pachtertrag", NEW_PVERTRAG);
			vertragspMenu.addEntry("Mieter zum Kaufvertrag hinzufügen", NEW_MIETER);
			vertragspMenu.addEntry("Zurück zum Hauptmenü", BACK);
			
			//Verarbeite Eingabe
			while(true) {
				
				int response = vertragspMenu.show();
				
				switch(response) {

				case NEW_PVERTRAG:
					insertpachtvertrag();
						break;
				case NEW_MIETER:
					newmieter();
					break;
					case BACK:
						return;
				}
			}
			}
		public static void showkvertragsMenu(){
			final int NEW_KVERTRAG=0;
			final int NEW_KÄUFER=1;
			final int BACK=2;
			
			
			Menu vertragspMenu = new Menu("Vertrags-Verwaltung");
			vertragspMenu.addEntry("Neuen Kaufvertrag", NEW_KVERTRAG);
			vertragspMenu.addEntry("Käufer zum Kaufvertrag hinzufügen", NEW_KÄUFER);
			vertragspMenu.addEntry("Zurück zum Hauptmenü", BACK);
			
			//Verarbeite Eingabe
			while(true) {
				
				int response = vertragspMenu.show();
				
				switch(response) {

				case NEW_KVERTRAG:
					insertkaufvertrag();
						break;
				case NEW_KÄUFER:
					newkäufer();
					break;
					case BACK:
						return;
				}
			}
			}


//		zeigt alle Verträge
		public static void showverträge(){
			Vertrag v=new Vertrag();
			v.vertragshow();
		}
		//legt neuen mieter an
		public static void newmieter() {
			Person p = new Person();
			p.setfirstName(FormUtil.readString("Vorname"));
			p.setlastName(FormUtil.readString("Nachname"));
			p.save();
			personid=p.getpersonId();
						
			System.out.println("Person mit der Personennummer "+p.getpersonId()+" wurde erzeugt.");
			Pachtvertrag v=new Pachtvertrag();
			v.pvertragshow();
			//showverträge();
			System.out.println("Geben den ID des PVertrages ein:");
			Scanner scan= new Scanner(System.in);
			tenancyid=scan.nextInt();
			v.updatepersonpachtvertrag(personid,tenancyid);
		}
		
		//legt neuen Käufer an
		public static void newkäufer() {
			Person p = new Person();
			p.setfirstName(FormUtil.readString("Vorname"));
			p.setlastName(FormUtil.readString("Nachname"));
			p.save();
			personid=p.getpersonId();
						
			System.out.println("Person mit der Personennummer "+p.getpersonId()+" wurde erzeugt.");
			Kaufvertrag v=new Kaufvertrag();
			//showverträge();
			System.out.println("Geben den ID des KVertrages ein: ");
			Scanner scan= new Scanner(System.in);
			purchaseid=scan.nextInt();
			v.updatepersonpachtvertrag(personid,purchaseid);
		}
		/**
		 * Legt eine neuen Kaufvertrag/Pachtvertrag an, nachdem der Benutzer
		 * die entprechenden Daten eingegeben hat.
		 */
	
		public static void insertkaufvertrag(){
			Vertrag c=new Vertrag();
			c.setdatum(FormUtil.readString("Datum"));
			c.setort(FormUtil.readString("Ort"));
			c.save();
			contractid=c.getcontractid();
			System.out.println("Vertrag mit der Vertragsnummer "+c.getcontractid()+" wurde erzeugt.");
			
//			hier müsste noch die houseid mit übergeben werden 
			Kaufvertrag k=new Kaufvertrag();
//			k.setestateid(apartmentid);
			k.setinterestrate(FormUtil.readString("Interestrate"));
			k.setinstallments(FormUtil.readString("Installments"));
			k.insertkaufvertrag(contractid);
			System.out.println("Kaufvertrag mit Vertragsnummer"+contractid+"wurde erstellt");
			
		}
		public static void insertpachtvertrag(){
			Vertrag c=new Vertrag();
			c.setdatum(FormUtil.readString("Datum"));
			c.setort(FormUtil.readString("Ort"));
			c.save();
			contractid=c.getcontractid();
			System.out.println("Vertrag mit der Vertragsnummer "+c.getcontractid()+" wurde erzeugt.");
			
//			hier müssten noch die apartmentid mit übergeben werden
			Pachtvertrag p=new Pachtvertrag();
//			p.setapartmentid();
			p.setstartdate(FormUtil.readString("Stardate"));
			p.setdauerinjahren(FormUtil.readString("duration"));
			p.setzusätzlichekosten(FormUtil.readString("Addcosts"));
			//apartmentid müsste noch mit übergeben werden
			p.insertpachtvertrag(contractid);
			System.out.println("Pachtvertrag mit Vertragsnummer"+contractid+"wurde erstellt");
		}
	
	
			
	
	//*Erzeuge ImmoblienMenu
	
	public static void showImmobilienMenu(){
		final int NEW_IMMOBILIE=0;
		final int BACK=1;
		
		Menu immobilieMenu = new Menu("Immobilien-Verwaltung");
		immobilieMenu.addEntry("Neue Immobilie", NEW_IMMOBILIE);
		immobilieMenu.addEntry("Lösche Immobilie",2);
		immobilieMenu.addEntry("Update Immoblie", 0);
		immobilieMenu.addEntry("Zurück zum Hauptmenü", BACK);
		
		
		//Verarbeite Eingabe
		while(true) {
			
			int response = immobilieMenu.show();
			
			switch(response) {
				case NEW_IMMOBILIE:
					newImmobilie();
					break;
				case BACK:
					return;
			}
		}
		}
					
	
	/**
	 * Legt eine neue Immobliie an, nachdem der Benutzer
	 * die entprechenden Daten eingegeben hat.
	 */
	public static void newImmobilie() {
		Immobilie i = new Immobilie();
		
		i.setstadt(FormUtil.readString("Stadt"));
		i.setplz(FormUtil.readString("PLZ"));
		i.setstraße(FormUtil.readString("Straße"));
		i.sethausnummer(FormUtil.readString("Hausnummer"));
		i.setfläche(FormUtil.readString("Fläche"));
	
		
		System.out.println("Immobilie mit der ID "+i.getId()+" wurde erzeugt.");
	}
		
	
	/**
	 * Zeigt die Maklerverwaltung und fragt Passwort ab
	 */
	
	public static void showMaklerMenu() {
		//Menüoptionen
		final int NEW_MAKLER = 0;
		final int BACK = 1;
		final int ZEIGE_MAKLER=3;
		final int DELETE_MAKLER=4;
		final int UPDATE_MAKLER=2;
		
		
		//Passwortabfrage
		String eingabe;
		String passwort="DIS_2017";
		Scanner scan= new Scanner(System.in);
		System.out.println("Gebe Passwort ein:");
		eingabe= scan.nextLine();
		
		if(eingabe.equals(passwort)){	
		//Maklerverwaltungsmenü
		Menu maklerMenu = new Menu("Makler-Verwaltung");
		maklerMenu.addEntry("Neuer Makler", NEW_MAKLER);
		maklerMenu.addEntry("Lösche Makler",DELETE_MAKLER);
		maklerMenu.addEntry("Update Makler", UPDATE_MAKLER);
		maklerMenu.addEntry("Zeige Makler", ZEIGE_MAKLER);
		maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);
		
		
		//Verarbeite Eingabe
		while(true) {
			
			int response = maklerMenu.show();
			
			switch(response) {
			case UPDATE_MAKLER:
				showMakler();
				updateMakler();
				break;
			case DELETE_MAKLER:
				showMakler();
				deleteMakler();
				break;
				case NEW_MAKLER:
					newMakler();
					break;
					case ZEIGE_MAKLER: 
						showMakler();
					break;
				case BACK:
					return;
			}
		}
		}else System.out.println("Zugang verweigert");}
	
	public static  void deleteMakler(){
		Makler m = new Makler();
		//showMakler();
		String eingabe;
		Scanner scan= new Scanner(System.in);
		System.out.println("Gebe zu löschenden Namen ein:");
		eingabe= scan.nextLine();
		m.deleteMakler(eingabe);
		System.out.println("Makler mit dem Namen "+eingabe+" wurde gelöscht.");
		

		
	}
	
	//zeigt Maklerliste an
	public static void showMakler(){
		Makler M=new Makler();
		M.Maklershow();
		
	}
	public static void updateMakler(){
		Makler m=new Makler();
		String name;
		String eingabename,eingabeaddress,eingabelogin,eingabepassword;
		System.out.println("Geben den Namen des zu ändernen Maklers ein:");
		Scanner scan= new Scanner(System.in);
		name=scan.nextLine();
		
		System.out.println("Geben neuen Namen ein:");
		eingabename= scan.nextLine();
		System.out.println("Gebe neue Adresse ein:");
		eingabeaddress=scan.nextLine();
		System.out.println("Gebene neuen Login ein:");
		eingabelogin=scan.nextLine();
		System.out.println("Geben neues Passwort ein:");
		eingabepassword=scan.nextLine();
		m.updateMakler(name,eingabename,eingabeaddress,eingabelogin,eingabepassword);
	}
	/**
	 * Legt einen neuen Makler an, nachdem der Benutzer
	 * die entprechenden Daten eingegeben hat.
	 */
	public static void newMakler() {
		Makler m = new Makler();
		m.setName(FormUtil.readString("Name"));
		m.setAddress(FormUtil.readString("Adresse"));
		m.setLogin(FormUtil.readString("Login"));
		m.setPassword(FormUtil.readString("Passwort"));
		m.save();
		
		System.out.println("Makler mit der ID "+m.getId()+" wurde erzeugt.");
	}
	


}
