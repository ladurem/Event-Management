package cli;

public class Patinage extends Evenement{
	/* RIEN */
	public void write_all(){
		System.out.println("N0 "+getNumeroEvt()+",Date="+getDate()+", heure debut"+getHeureDebut()+",heure de fin"+getHeureFin());
	}
}
