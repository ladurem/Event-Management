package cli;


public class PM {
	public static void main(String[] args){
		switch(args[0]){
			case "importer":
			System.out.println("IMPORT DE FICHIERS :");
			Importer.main(args);
			break;
			case "reserver": 
			System.out.println("Reservation :");
			Reserver.main(args);
			break;
			case "manifestation":
			System.out.println("Ajout d'une manifestation");
			Manifestation.main(args);
			break;
			case "rapport": 
			System.out.println("Génération d'un rapport HTML");
				Rapporter.main(args);
			break;
			default:
			System.err.println("Bad Usage : cli.PM {manifesation/importer/reserver} ./fichier_csv ./bdd.xml");
			break;
		}
		
		
	}	
}