package cli;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import au.com.bytecode.opencsv.CSVReader;


public class Manifestation {
	public static void main(String[] args){
		SAXBuilder parser = new SAXBuilder();
		Document fichierXml = null;
		try {
			fichierXml = parser.build(args[3]);
		} catch (JDOMException e1) {
			System.err.println("Erreur lors de l'ouverture du fichier XML (JDOM)");
		} catch (IOException e1) {
			System.err.println("Erreur lors de l'ouverture du fichier XML (IO)");
		}
		Element eltXml = fichierXml.getRootElement();
		List<Element> evenements = eltXml.getChildren("evenement");
		List<String> error = new ArrayList<String>();

		switch(args[1]){
			case"concert":
			CSVReader reader = null;
			try {
				reader = new CSVReader(new InputStreamReader(new FileInputStream("concerts.csv")), ';', '"', 1);
			} catch (FileNotFoundException e) {
				System.err.println("Fichier concerts.csv inconnu");
				e.printStackTrace();
			}
			String[] nextLine;
			error.clear();
			boolean idExist = false;
			try {
				while ((nextLine = reader.readNext()) != null) {
					if (nextLine != null) {
						Concert infoConcert = (Concert) new Concert();
						SimpleDateFormat sdfHeure = null;
						sdfHeure = new SimpleDateFormat("dd/MM/yyyy");
						Date heureDebut = null;
						try {
							heureDebut = new SimpleDateFormat("kk:mm").parse(nextLine[2].replace('h', ':'));
						} catch (ParseException e) {
							System.err.println("Probléme de parse pour heure_debut dans concerts.csv");
							e.printStackTrace();
						}
						Date heureFin = null;
						try {
							heureFin = new SimpleDateFormat("kk:mm").parse(nextLine[3].replace('h', ':'));
						} catch (ParseException e) {
							System.err.println("Probléme de parse pour heure_fin dans concerts.csv");
						}
						
						Date date1=null;
						try {
							date1 = sdfHeure.parse(nextLine[1]);
						} catch(ParseException e){
							System.err.println("Probléme de parse pour la date dans concerts.csv");
						}

						int nbError=0;
						idExist=false;
						for(Element elt : evenements) {	
							Date xmlDate = null;
							Date dateJour = null;
							SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yy" );
							Date date = (new GregorianCalendar()).getTime();
							String dateJourSdf = new SimpleDateFormat("DD/MM/yy").format(date);
							try {
								xmlDate = sd.parse(nextLine[1]);
							} catch (ParseException e) {
							}
							try {
								dateJour = sd.parse(dateJourSdf);
							} catch (ParseException e) {}
							
							if(elt.getAttributeValue("type").equalsIgnoreCase("concert")){
							if(elt.getAttributeValue("id_evt").equals(nextLine[0])){
									 idExist = true;
								}
							if(xmlDate.compareTo(dateJour)<1 ){
									error.add("Erreur de type M2 sur le concert de  "+nextLine[6]);
									nbError++;
									break;
								}
							
								if(elt.getChild("date").getText().equals(nextLine[1]) && elt.getChild("heure_debut").getText().equals(nextLine[2]) && elt.getChild("heure_fin").getText().equals(nextLine[3])){
									error.add("Erreur de type M3 sur le concert de  "+nextLine[6]);
									nbError++;
									break;
								}
								if(Integer.parseInt(nextLine[4])<0){
									error.add("Erreur de M4 sur le concert de "+nextLine[6]);
									nbError++;
									break;
								}
								if(nextLine[6].equals("")){
									error.add("Erreur de M5 sur le concert id = "+nextLine[0]);
									nbError++;
									break;
								}
								
								
							}
						}
						if(idExist){
							error.add("Erreur de type M1 sur le concert de "+nextLine[6]);
							nbError++;
							}
						if(nbError == 0){
							System.out.println("Concert enregistré "+nextLine[6]);
							try {
								infoConcert.setNumeroEvt(Integer.parseInt(nextLine[0]));
								infoConcert.setDate(date1);
								infoConcert.setHeureDebut(heureDebut);
								infoConcert.setHeureFin(heureFin);
								infoConcert.setPrix(Integer.parseInt(nextLine[4]));
								infoConcert.setPlace(Integer.parseInt(nextLine[5]));
								infoConcert.setArtiste(nextLine[6]);
								infoConcert.setStyle(nextLine[7]);
							} catch (Exception e) {
								System.err.println("Problème de PARSE dans les options du CSV");
								break;
							}
							
						//Si tout est bon on ajoute au XML
							Element concert = new Element("evenement");
							concert.setAttribute(new Attribute("type","concert"));
							concert.setAttribute(new Attribute("id_evt",nextLine[0]));
							concert.addContent(new Element("date").setText(nextLine[1]));
							concert.addContent(new Element("heure_debut").setText(nextLine[2]));
							concert.addContent(new Element("heure_fin").setText(nextLine[3]));
							concert.addContent(new Element("prix").setText(nextLine[4]));
							concert.addContent(new Element("places").setText(nextLine[5]));
							concert.addContent(new Element("artiste").setText(nextLine[6]));
							concert.addContent(new Element("style").setText(nextLine[7]));
							eltXml.addContent(concert);
						}
					}
				}
			} catch (NumberFormatException e) {
				System.err.println("Mauvaise lecture des entiers du csv.");
			} catch (IOException e) {
				System.err.println("Probléme de lecture de concerts.csv");
			}
			try {
				reader.close();
			} catch (IOException e) {
				System.err.println("Probléme de fermeture de concert.csv");//err
			}
			try {
				reader.close();
			} catch (IOException e) {
				System.err.println("Probléme de fermeture de concert.csv");//err
				e.printStackTrace();
			}
			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			try {
				xmlOutput.output(eltXml, new FileWriter(args[3]));
			} catch (IOException e) {
			System.err.println("Erreur lors de l'ecriture du fichier");
			}
			break;
			case "hockey"://meme traitement pour que pour concert, mais des champs changent
			System.out.println("Ajout des evenements de Hockey");
			CSVReader readerHockey = null;
			try {
				readerHockey = new CSVReader(new InputStreamReader(new FileInputStream(args[2])), ';', '"', 1);
			} catch (FileNotFoundException e) {
				System.err.println("Fichier hockey.csv inconnu");
				e.printStackTrace();
			}
			
			try {
				error.clear();
				boolean idExist1 = false;
				while ((nextLine = readerHockey.readNext()) != null) {
					
					if (nextLine != null) {
						Hockey infoHockey = new Hockey();
						SimpleDateFormat sdfDate = null;
						sdfDate = new SimpleDateFormat("dd/MM/yyyy");
						Date heureDebut = null;
						
						int nbError=0;
						idExist1=false;
						try {
							heureDebut = new SimpleDateFormat("kk:mm").parse(nextLine[2].replace('h', ':'));
						} catch (ParseException e) {
							System.err.println("Probléme de parse pour heure_debut dans concerts.csv");
							e.printStackTrace();
						}
						Date heureFin = null;
						try {
							heureFin = new SimpleDateFormat("kk:mm").parse(nextLine[3].replace('h', ':'));
						} catch (ParseException e) {
							System.err.println("Probléme de parse pour heure_fin dans hockey.csv");
							e.printStackTrace();
						}
						
						Date date1=null;
						try {
							date1 = sdfDate.parse(nextLine[1]);
						} catch(ParseException e){
							System.err.println("Probléme de parse pour la date dans hockey.csv");
							e.printStackTrace();
						}
						Date xmlDate = null;
						Date dateJour = null;
						SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yy" );
						Date date = (new GregorianCalendar()).getTime();
						String dateJourSdf = new SimpleDateFormat("DD/MM/yy").format(date);
						try {
							xmlDate = sd.parse(nextLine[1]);
						} catch (ParseException e) {
						}
						try {
							dateJour = sd.parse(dateJourSdf);
						} catch (ParseException e) {}
						for(Element elt : evenements) {							
							if(elt.getAttributeValue("type").equalsIgnoreCase("hockey")){
								if(elt.getAttributeValue("id_evt").equals(nextLine[0])){
									 idExist1 = true;
								}
							if(xmlDate.compareTo(dateJour)<=1 ){
								error.add("Erreur de type M2 sur le match de Hockey de  "+nextLine[6]);
									nbError++;
									break;
								}
								if(elt.getChild("date").getText().equals(nextLine[1]) && elt.getChild("heure_debut").getText().equals(nextLine[2]) && elt.getChild("heure_fin").getText().equals(nextLine[3])){
									error.add("Erreur de type M3 sur le match de Hockey de  "+nextLine[6]+"/"+nextLine[7]);
								nbError++;
									break;
								}
								if(Integer.parseInt(nextLine[4])<0){
									error.add("Erreur de M4 sur le match de Hockey de "+nextLine[6]+"/"+nextLine[7]);
									nbError++;
									break;
								}
								if(nextLine[6].equals("") || nextLine[7].equals("") ){
									error.add("Erreur de M5 sur le match de Hockey de "+nextLine[6]+"/"+nextLine[7]);
									nbError++;
									break;
								}
								
								
								
											
								
							}
						}
						if(idExist1){
							error.add("Erreur de type M1 sur le concert de "+nextLine[6]);
							nbError++;
							}
						if(nbError == 0){
							try {
								infoHockey.setNumeroEvt(Integer.parseInt(nextLine[0]));
								infoHockey.setDate(date1);
								infoHockey.setHeureDebut(heureDebut);
								infoHockey.setHeureFin(heureFin);
								infoHockey.setPrix(Integer.parseInt(nextLine[4]));
								infoHockey.setPlace(Integer.parseInt(nextLine[5]));
								infoHockey.setEquipe1(nextLine[6]);
								infoHockey.setEquipe2(nextLine[7]);
								infoHockey.setArbitre(nextLine[7]);
							} catch (Exception e) {
							System.err.println("Problème lors de la parse des informations");
							break;
							}
						//Si tout est bon on ajoute au XML
							Element hockey = new Element("evenement");
							hockey.setAttribute(new Attribute("type","hockey"));
							hockey.setAttribute(new Attribute("id_evt",nextLine[0]));
							hockey.addContent(new Element("date").setText(nextLine[1]));
							hockey.addContent(new Element("heure_debut").setText(nextLine[2]));
							hockey.addContent(new Element("heure_fin").setText(nextLine[3]));
							hockey.addContent(new Element("prix").setText(nextLine[4]));
							hockey.addContent(new Element("places").setText(nextLine[5]));
							hockey.addContent(new Element("equipe_1").setText(nextLine[6]));
							hockey.addContent(new Element("equipe_2").setText(nextLine[7]));
							hockey.addContent(new Element("competition").setText(nextLine[8]));
							hockey.addContent(new Element("arbitre").setText(nextLine[9]));
							eltXml.addContent(hockey);
							System.out.println("Match Hockey "+nextLine[6]+"/"+nextLine[7]+" enregistré.");
						}
					}
					
				}
				
			} catch (NumberFormatException e) {
				System.err.println("Mauvaise lecture des entiers du csv.");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Probléme de lecture de concerts.csv");
				e.printStackTrace();
			}
			try {
				readerHockey.close();
			} catch (IOException e) {
				System.err.println("Probléme de fermeture de concert.csv");//err
				e.printStackTrace();
			}
			XMLOutputter xmlOutput1 = new XMLOutputter();

			// display nice nice
			xmlOutput1.setFormat(Format.getPrettyFormat());
			try {
				xmlOutput1.output(eltXml, new FileWriter(args[3]));
			} catch (IOException e) {
			System.err.println("Erreur lors de l'ecriture du fichier");
			}
		break;
			default:
			System.err.println("Fatal Error: Bad Usage ");
		}
		for(String element :error) {
			System.err.println(element);
		}
		
		
		
		
		

	}
}
