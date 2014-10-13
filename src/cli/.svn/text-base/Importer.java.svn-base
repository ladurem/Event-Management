package cli;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import au.com.bytecode.opencsv.CSVReader;
public class Importer {

	public static void main(String[] args) {
		Element db = new Element("evts");
		Document doc = new Document(db);
		/* Gestion de evts.csv */
		CSVReader reader111 = null;
		try {
			reader111 = new CSVReader(new InputStreamReader(new FileInputStream(args[1])), ';', '"', 1);
		} catch (FileNotFoundException e1) {
			System.err.println("Fichier evts.csv inconnu");
			e1.printStackTrace();
		}
		String[] nextLine111;
		try {
			while ((nextLine111 = reader111.readNext()) != null) {
				switch(nextLine111[1]){//On va traiter chaque type d'evt
					case "Hockey":
					System.out.println("HOCKEY");
					Hockey infoHockey = (Hockey) new Hockey();
					infoHockey.setNumeroEvt(Integer.parseInt(nextLine111[0]));
					Date date = null;
					try {
						date = new SimpleDateFormat("dd/MM/yyyy").parse(nextLine111[2]);
					} catch (ParseException e) {
						System.err.println("Probleme de parse pour la date dans hockey.csv");
						e.printStackTrace();
					}
					Date heureDebut = null;
					try {
						heureDebut = new SimpleDateFormat("HH:MM").parse(nextLine111[3].replace('h', ':'));
					} catch (ParseException e) {
						System.err.println("Probleme de parse pour heure_debut dans hockey.csv");
						e.printStackTrace();
					}
					Date heureFin = null;
					try {
						heureFin = new SimpleDateFormat("HH:MM").parse(nextLine111[4].replace('h', ':'));
					} catch (ParseException e) {
						System.err.println("Probleme de parse pour heure_fin dans hockey.csv");
						e.printStackTrace();
					}
					infoHockey.setDate(date);
					infoHockey.setHeureDebut(heureDebut);
					infoHockey.setHeureFin(heureFin);
					infoHockey.setPrix(Integer.parseInt(nextLine111[5]));
					infoHockey.setPlace(Integer.parseInt(nextLine111[6]));
					infoHockey.setEquipe1(nextLine111[9]);
					infoHockey.setEquipe2(nextLine111[10]);
					infoHockey.setCompetition(nextLine111[11]);
					infoHockey.setArbitre(nextLine111[12]);

					Element hockey = new Element("evenement");
					hockey.setAttribute(new Attribute("type","hockey"));
					hockey.setAttribute(new Attribute("id_evt",nextLine111[0]));
					hockey.addContent(new Element("date").setText(nextLine111[2]));
					hockey.addContent(new Element("heure_debut").setText(nextLine111[3]));
					hockey.addContent(new Element("heure_fin").setText(nextLine111[4]));
					hockey.addContent(new Element("prix").setText(nextLine111[5]));
					hockey.addContent(new Element("places").setText(nextLine111[6]));
					hockey.addContent(new Element("equipe_1").setText(nextLine111[9]));
					hockey.addContent(new Element("equipe_2").setText(nextLine111[10]));
					hockey.addContent(new Element("competition").setText(nextLine111[11]));
					hockey.addContent(new Element("arbitre").setText(nextLine111[12]));
					doc.getRootElement().addContent(hockey);	

					break;
					case "Concert": 	
					System.out.println("Concert");
					Concert infoConcert = (Concert) new Concert();
					infoConcert.setNumeroEvt(Integer.parseInt(nextLine111[0]));
					SimpleDateFormat sdfDate = null;
					sdfDate = new SimpleDateFormat("dd/MM/yyyy");
					Date heureDebut1 = null;
					try {
						heureDebut1 = new SimpleDateFormat("kk:mm").parse(nextLine111[3].replace('h', ':'));
					} catch (ParseException e) {
						System.err.println("Probleme de parse pour heure_debut dans concerts.csv1");
						e.printStackTrace();
					}
					Date heureFin1 = null;
					try {
						heureFin1 = new SimpleDateFormat("kk:mm").parse(nextLine111[4].replace('h', ':'));
					} catch (ParseException e) {
						System.err.println("Probleme de parse pour heure_fin dans concerts.csv 2");
						e.printStackTrace();
					}

					Date date1=null;
					try {
						date1 = sdfDate.parse(nextLine111[2]);
					} catch(ParseException e){
						System.err.println("Probleme de parse pour la date dans concerts.csv 3 ");
						e.printStackTrace();
					}
					infoConcert.setDate(date1);
					infoConcert.setHeureDebut(heureDebut1);
					infoConcert.setHeureFin(heureFin1);
					infoConcert.setPrix(Integer.parseInt(nextLine111[5]));
					infoConcert.setPlace(Integer.parseInt(nextLine111[6]));
					infoConcert.setArtiste(nextLine111[7]);
					infoConcert.setStyle(nextLine111[8]);
					Element concert = new Element("evenement");
					concert.setAttribute(new Attribute("type","concert"));
					concert.setAttribute(new Attribute("id_evt",nextLine111[0]));
					concert.addContent(new Element("date").setText(nextLine111[2]));
					concert.addContent(new Element("heure_debut").setText(nextLine111[3]));
					concert.addContent(new Element("heure_fin").setText(nextLine111[4]));
					concert.addContent(new Element("prix").setText(nextLine111[5]));
					concert.addContent(new Element("places").setText(nextLine111[6]));
					concert.addContent(new Element("artiste").setText(nextLine111[7]));
					concert.addContent(new Element("style").setText(nextLine111[8]));
					doc.getRootElement().addContent(concert);
					break;
					
					case "Patinage": 
					System.out.println("Patinage");
					Patinage infoPatinage = (Patinage) new Patinage();

					Date date11 = null;
					try {
						date11 = new SimpleDateFormat("dd/MM/yyyy").parse(nextLine111[2]);
					} catch (ParseException e) {
						System.err.println("Probleme de parse pour la date dans evts.csv");
						e.printStackTrace();
					}
					Date heureDebutPatinage = null;
					try {
						heureDebutPatinage = new SimpleDateFormat("HH:MM").parse(nextLine111[3].replace('h', ':'));
					} catch (ParseException e) {
						System.err.println("Probleme de parse pour heure_debut dans evts.csv");
						e.printStackTrace();
					}
					Date heureFinPatinage = null;
					try {
						heureFinPatinage = new SimpleDateFormat("HH:MM").parse(nextLine111[4].replace('h', ':'));
					} catch (ParseException e) {
						System.err.println("Probleme de parse pour heure_fin dans hockey.csv");
						e.printStackTrace();
					}

					infoPatinage.setDate(date11);
					infoPatinage.setHeureDebut(heureDebutPatinage);
					infoPatinage.setHeureFin(heureFinPatinage);
					infoPatinage.setPrix(Integer.parseInt(nextLine111[5]));
					Element patinage = new Element("evenement");
					patinage.setAttribute(new Attribute("type","patinage"));
					patinage.addContent(new Element("date").setText(nextLine111[2]));					
					patinage.addContent(new Element("heure_debut").setText(nextLine111[3]));
					patinage.addContent(new Element("heure_fin").setText(nextLine111[4]));
					patinage.addContent(new Element("prix").setText(nextLine111[5]));
					doc.getRootElement().addContent(patinage);


					break;

				}
			}




		} catch (NumberFormatException e) {
			System.err.println("Mauvaise lecture des entiers du csv.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Probleme de lecture de resa.csv");
			e.printStackTrace();
		}
		try {
			reader111.close();
		} catch (IOException e) {
			System.err.println("Probleme de fermeture de resa.csv");
			e.printStackTrace();
		}
		try{
			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(args[2]));

			System.out.println("Fichier XML("+args[2]+") Genere a l'aide du fichier EVTS("+args[1]+").");
		} catch (IOException e) {
			System.err.println("Probleme de creation de XML");
			e.printStackTrace();
		}
	}

}