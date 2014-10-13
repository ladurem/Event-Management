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
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import au.com.bytecode.opencsv.CSVReader;
public class Reserver {

	public static void main(String[] args) {
		SAXBuilder parser = new SAXBuilder();
		Document fichierXml = null;
		try {
			fichierXml = parser.build(args[2]);
		} catch (Exception e2) {
			System.err.println("Erreur du fichier CSV");
		}
		Element eltXml = fichierXml.getRootElement();
		List<String> error = new ArrayList<String>();//Contient toutes les erreurs detectées
		List<Element> dbResa = eltXml.getChildren("resa");
		List<Element> dbEvt = eltXml.getChildren("evenement");
		int nbError=0;
		boolean idExist = false;
		int[] nbResa = new int [10000];//Nombre maximal de reservation adminissible

		//Comptage des places :
		for(Element resa:dbResa){
			int indice = Integer.parseInt(resa.getAttributeValue("id_evt"));
			nbResa[indice]++;
		}
		/* Gestion de resa.csv */
		CSVReader reader11 = null;
		try {
			reader11 = new CSVReader(new InputStreamReader(new FileInputStream(args[1])), ';', '"', 1);
		} catch (FileNotFoundException e1) {
			System.err.println("Fichier resa.csv inconnu");
			e1.printStackTrace();
		}
		String[] nextLine11;


// AJOUT DES CLIENTS
		try {
			while ((nextLine11 = reader11.readNext()) != null) {
				if (nextLine11 != null) {
					nbError = 0;
					idExist = false;
					Client infoClient = (Client) new Client();
					infoClient.setIdClient(Integer.parseInt(nextLine11[1]));
					infoClient.setNom(nextLine11[2]);
					infoClient.setPrenom(nextLine11[3]);
					infoClient.setAge(Integer.parseInt(nextLine11[4]));

//Verification des erreurs 
for(Element event : dbEvt){ 	 // Verification ID et DATE
	Date xmlDate = null;
	Date dateJour = null;
	SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yy" );
	Date date = (new GregorianCalendar()).getTime();
	String dateJourSdf = new SimpleDateFormat("DD/MM/yy").format(date);
	xmlDate = sd.parse(event.getChild("date").getText());
	dateJour = sd.parse(dateJourSdf);

	try {
		if(event.getAttributeValue("id_evt").equals(nextLine11[0])){
			idExist = true;
			if(xmlDate.compareTo(dateJour)<1 ){
				error.add("Erreur de type R2 sur la reservation du client N°"+nextLine11[1]+"(ID_evt="+nextLine11[0]+",ID_client = "+nextLine11[1]+")");
				nbError++;
			}
			if(Integer.parseInt(event.getChild("places").getText()) - nbResa[Integer.parseInt(event.getAttributeValue("id_evt"))]<=0 ){
				error.add("Erreur de type R3 sur la reservation du client N°"+nextLine11[1]+"(ID_evt="+nextLine11[0]+",ID_client = "+nextLine11[1]+")");
				nbError++;
			}
			break;
		} 
	} catch (Exception e) {
	} 
}
if(!idExist){
	System.err.println(">Erreur de type R1 sur la reservation du client N°"+nextLine11[1]+"(ID_evt="+nextLine11[0]+",ID_client = "+nextLine11[1]);
		nbError++;
	}


//fin de verif des erreurs
	if(nbError == 0){
		Element resa = new Element("resa");
		resa.setAttribute(new Attribute("id",nextLine11[1]));
		resa.setAttribute(new Attribute("id_evt",nextLine11[0]));
		resa.addContent(new Element("nom").setText(infoClient.getNom()));
		resa.addContent(new Element("prenom").setText(infoClient.getPrenom()));
		resa.addContent(new Element("age").setText(nextLine11[4]));
		resa.addContent(new Element("reduction").setText((nextLine11[5].replaceAll("%","").replaceAll("-", ""))));
		System.out.println("Reservation de Mr "+infoClient.getNom()+" est enregistrée(ID_evt="+nextLine11[0]+",ID_client = "+nextLine11[1]);
			fichierXml.getRootElement().addContent(resa);
		}


	}


}//end while
} catch (NumberFormatException e1) {
	System.err.println("Erreur lors de la lecture de chaque ligne du CSV");
} catch (IOException e1) {
	System.err.println("Erreur lors de l'appel a la fonction");
} catch (ParseException e1) {
	System.err.println("Erreur lors de l'appel a la fonction");
}
try {
	for(String element :error) {
		System.err.println(">"+element);
	}
} catch (Exception e) { }


try {
	reader11.close();
} catch (IOException e) {
	System.err.println("Probleme de fermeture de resa.csv");
	e.printStackTrace();
}

XMLOutputter xmlOutput = new XMLOutputter();

	// display nice nice
	xmlOutput.setFormat(Format.getPrettyFormat());
		try {
			xmlOutput.output(eltXml, new FileWriter(args[2]));
		} catch (IOException e) {
			System.err.println("Erreur lors de l'ecriture du XML");
		}
	}
}
