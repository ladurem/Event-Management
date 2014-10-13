package cli;

import java.util.Date;

public abstract class Evenement {
	private int numeroEvt,prix;
	private Date date,heureDebut,heureFin;
	private static String type;
	public int getNumeroEvt() {
		return numeroEvt;
	}
	public Date getHeureDebut() {
		return heureDebut;
	}
	public Date getHeureFin() {
		return heureFin;
	}
	public int getPrix() {
		return prix;
	}
	public Date getDate() {
		return date;
	}
	public static String getType() {
		return type;
	}
	public void setNumeroEvt(int numeroEvt) {
		this.numeroEvt = numeroEvt;
	}
	public void setHeureDebut(Date heureDebut) {
		this.heureDebut = heureDebut;
	}
	public void setHeureFin(Date heureFin) {
		this.heureFin = heureFin;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public static void setType(String type) {
		Evenement.type = type;
	}
}
