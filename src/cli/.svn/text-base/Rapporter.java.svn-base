package cli;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;


public class Rapporter {
	public static void main(String[] args){
		SAXBuilder parser = new SAXBuilder();
		Document fichierXml = null;
		try {
			fichierXml = parser.build(args[1]);
		} catch (Exception e1) {
			System.err.println("Fichier CSV invalide");
		}
		Element eltXml = fichierXml.getRootElement();
		List<Element> evenements = eltXml.getChildren("evenement");
		List<Element> dbResa = eltXml.getChildren("resa");
		String path = args[2];
		String text = "<html>"+
		"<head>\n<META http-equiv='Content-Type' content='text/html; charset=UTF-8'>\n"+
		"<title>Patinoire</title>\n<style type='text/css'>"+
		" * {font-family: verdana; background-color:#FAFAFA}"+
		"tr {height: 30px;} td.col1 {width: 100px; align:center;}td.col2 { width: 400px;}td.title {background: #efe7d9 ;border-bottom: 1px solid #336699;font: 16 verdana; padding: 0 0 0 15px;}"+
		".red {color: #ff0000;}.green {color: #249821;}td.greenRow{background: #d6efd6;border-bottom: 1px solid #437841;}td.blueRow{background: #3399FF;border-bottom: 1px solid #437841;}"+
		"h3 {background-color:#F5DEB3; align:center;}"+
		".number{float: right;background-color:#F5DEB3;}"+
		".late{background-color:#FF8C00;}"+
		".late:before{content:'[ARCHIVE] >>'}"+
		"#tot {position:fixed; top:50px; right:25px; background-color:#F5DEB3; }"+
		"</style></head><body>\n<table>\n<tr>";
		int[] nbResa = new int [500];
		float[] earnByType = new float[3];
		// Contient la recette suivant le type d'evt 0 =>Concert, 1=>Hockey 2=>Patinage
		int averageAge=0;		
		float totEarn=0;
		
		for(Element elt : evenements) {	
			float earn = 0;
			int age = 0;
			for(Element resa:dbResa){
				if(resa.getAttributeValue("id_evt").equals(elt.getAttributeValue("id_evt"))){
					int indice = Integer.parseInt(resa.getAttributeValue("id_evt"));
					nbResa[indice]++;	//Count Place

					float discountPrice = Integer.parseInt(elt.getChild("prix").getText())-(((float)Integer.parseInt(elt.getChild("prix").getText())*((float)Integer.parseInt(resa.getChildText("reduction"))/100)));
					earn=earn + discountPrice;
					age = age+Integer.parseInt(resa.getChild("age").getText());
				}
			}

			totEarn = totEarn+earn;
			Date xmlDate = null;
			Date dateJour = null;
			SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yy" );
			Date date = (new GregorianCalendar()).getTime();
			String dateJourSdf = new SimpleDateFormat("DD/MM/yy").format(date);
			try {
				xmlDate = sd.parse(elt.getChild("date").getText());
				dateJour = sd.parse(dateJourSdf);
			} catch (ParseException e1) {
				System.err.println("Erreur lors de la parse des dates");
			}

			
			if(elt.getAttributeValue("type").equalsIgnoreCase("concert")){
				earnByType[0]=earnByType[0]+earn;
				String dateDepassee = "title";
				if(xmlDate.compareTo(dateJour)<1){
					dateDepassee="late";
				}
				try {
					if(nbResa[Integer.parseInt(elt.getAttributeValue("id_evt"))] ==0)
						averageAge=0;
					else
						averageAge = age/nbResa[Integer.parseInt(elt.getAttributeValue("id_evt"))];
				} catch (NumberFormatException e) {}
				catch (ArrayIndexOutOfBoundsException k){}
				text = text+"\n<td class='"+dateDepassee+"'  colspan='2'>Concert N°"+elt.getAttributeValue("id_evt")+"</td></tr><tr>"+
				"\n<td class='col1'>Artiste:</td><td class='col2'>"+elt.getChild("artiste").getText()+"</td>"+
				"</tr>\n<tr><td class='col1'>Date:</td><td class='col2'>"+elt.getChild("date").getText()+", de "+elt.getChild("heure_debut").getText()+" à "+elt.getChild("heure_fin").getText()+"</td>"+
				"</tr>\n<tr><td class='col1'>Prix:</td><td class='col2 red'>"+elt.getChild("prix").getText()+"€</td>"+
				"</tr>\n<tr><td class='col1'>Places:</td><td class='col2'>"+Integer.parseInt(elt.getChild("places").getText())+"</td></tr>"+
				"</tr>\n<tr><td class='col1'>Places restantes:</td><td class='col2'>"+(Integer.parseInt(elt.getChild("places").getText()) - nbResa[Integer.parseInt(elt.getAttributeValue("id_evt"))])+"</td></tr>"+
				"</tr>\n<tr><td class='col1'>Recette estimée:</td><td class='col2'>"+(float)earn+"€</td></tr>"+
				"</tr>\n<tr><td class='col1'>Moyenne age:</td><td class='col2'>"+(averageAge)+"</td></tr>"+
				
				"<tr>\n<td class='col1'>Style:</td><td class='col2'>"+elt.getChild("style").getText()+"</td></tr>";
			} 

			
			
			
			if(elt.getAttributeValue("type").equalsIgnoreCase("hockey")){
				earnByType[1]=earnByType[1]+earn;
				String dateDepassee = "title  BlueRow";
				if(xmlDate.compareTo(dateJour)<1){
					dateDepassee="late";
				}
				try {
					if(nbResa[Integer.parseInt(elt.getAttributeValue("id_evt"))] ==0)
						averageAge=0;
					else
						averageAge = age/nbResa[Integer.parseInt(elt.getAttributeValue("id_evt"))];
				} catch (NullPointerException e) {	}
				text = text+"\n<td class='"+dateDepassee+"' colspan='2'>"+elt.getChild("competition").getText()+" de Hockey N°"+elt.getAttributeValue("id_evt")+"</td></tr><tr>"+
				"\n<td class='col1'>Match :</td><td class='col2'>"+elt.getChild("equipe_1").getText()+"/"+elt.getChild("equipe_2").getText()+"</td>"+
				"</tr>\n<tr><td class='col1'>Date:</td><td class='col2'>"+elt.getChild("date").getText()+", de "+elt.getChild("heure_debut").getText()+" à "+elt.getChild("heure_fin").getText()+"</td>"+
				"</tr>\n<tr><td class='col1'>Prix:</td><td class='col2 red'>"+elt.getChild("prix").getText()+"€</td>"+
				"</tr>\n<tr><td class='col1'>Places:</td><td class='col2'>"+elt.getChild("places").getText()+"</td></tr>"+
				"</tr>\n<tr><td class='col1'>Places restantes:</td><td class='col2'>"+(Integer.parseInt(elt.getChild("places").getText()) - nbResa[Integer.parseInt(elt.getAttributeValue("id_evt"))])+"</td></tr>"+
				"</tr>\n<tr><td class='col1'>Recette estimée:</td><td class='col2'>"+(float)earn+"€</td></tr>"+
				"</tr>\n<tr><td class='col1'>Moyenne age:</td><td class='col2'>"+(averageAge)+"</td></tr>"+
				"<tr>\n<td class='col1'>Arbitre:</td><td class='col2'>"+elt.getChild("arbitre").getText()+"</td></tr>";
			}
			if(elt.getAttributeValue("type").equalsIgnoreCase("patinage")){
				earnByType[2]=earnByType[2]+earn;
				String dateDepassee = "title  greenRow";
				if(xmlDate.compareTo(dateJour)<1){
					dateDepassee="late";
				}
				try {
					if(nbResa[Integer.parseInt(elt.getAttributeValue("id_evt"))] ==0)
						averageAge=0;
					else
						averageAge = age/nbResa[Integer.parseInt(elt.getAttributeValue("id_evt"))];
				} catch (NumberFormatException e) {
					averageAge=0;
				}
				text = text+"\n<tr><td class='"+dateDepassee+"' colspan='2'>Patinage</td>"+
				"\n</tr>\n<tr><td class='col1'>Date:</td><td class='col2'>"+elt.getChild("date").getText()+", de "+elt.getChild("heure_debut").getText()+" à "+elt.getChild("heure_fin").getText()+"</td>"+
				"\n</tr>\n<tr><td class='col1'>Prix:</td><td class='col2 green'>"+elt.getChild("prix").getText()+"€</td></tr>\n"+
				"</tr>\n<tr><td class='col1'>Moyenne age:</td><td class='col2'>"+(averageAge)+"</td></tr>"+
				"</tr>\n<tr><td class='col1'>Recette estimée:</td><td class='col2'>"+(float)earn+"€</td></tr>";
			}
		}

		float earnConcert = (float) Math.rint((earnByType[0]/totEarn)*100);
		float earnHockey = (float) Math.rint((earnByType[1]/totEarn)*100);
		
		if(totEarn ==0){//Aucune reservation
			earnConcert = 0;
			earnHockey = 0;
		}
		text = text+"</table><div id='tot'><h3>BILAN</h3>Chiffre affaire estimé :&nbsp; &nbsp;<div class='number'>"+totEarn+"€</div><br />"+
		"Concerts (~"+earnConcert+"%) : <div class='number'>"+earnByType[0]+"€</div> <br />Hockey (~"+earnHockey+"%) : <div class='number'>"+earnByType[1]+"€</div><br /> "+
    		"</div>\n</body>\n</html>";//fermetures des commandes html
    		PrintWriter fichierHtml ;
    		try
    		{
    			fichierHtml = new PrintWriter(new FileWriter(path));
    			fichierHtml.print(text);
    			fichierHtml.flush();
    			fichierHtml.close();
			}//try
			catch (NullPointerException a)
			{
				System.err.println("Erreur : pointeur null");
			}
			catch (IOException a)
			{
				System.err.println("Problème d'IO");
			}
			System.out.println("Rapport généré.");
		}//ecrire

	}
