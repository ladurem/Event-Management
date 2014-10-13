package cli;

public class Hockey extends Evenement {
	private int place;
	private String equipe1,equipe2,competition,arbitre;

	public int getPlace(){
		return place;
	}    
	public String getEquipe1(){
		return equipe1;
	}
	public String getEquipe2(){
		return equipe2;
	}
	public String getCompetition(){
		return competition;
	}
	public String getArbitre(){
		return arbitre;
	}
	public void setPlace(int place){
		this.place = place;
	}
	public void setEquipe1(String equipe1){
		this.equipe1 = equipe1;
	}
	public void setEquipe2(String equipe2){
		this.equipe2 = equipe2;
	}
	public void setCompetition(String competition){
		this.competition = competition;
	}
	public void setArbitre(String arbitre){

		this.arbitre = arbitre;
	}

}
