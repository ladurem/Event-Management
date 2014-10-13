package cli;

public class Concert extends Evenement {
	private int place;
	private String artiste,style;
	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
	}
	public String getArtiste() {
		return artiste;
	}
	public void setArtiste(String artiste) {
		this.artiste = artiste;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}

}
