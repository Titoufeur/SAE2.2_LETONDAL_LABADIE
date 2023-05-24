import java.util.ArrayList;
import java.util.List;
public class Noeud{
	private String nom;
	private List<Arc> adj;
	public Noeud(String n){
		this.nom=n;
		this.adj = new ArrayList<>();
	}

	//méthode
	public boolean equals(Object o){
		Noeud other = (Noeud) o;
		return (nom==other.nom);
	}

	public void ajouterArc(String destination, double cout){
		Arc sav = new Arc(destination, cout);
		this.adj.add(sav);
	}

	public List<Arc> getAdj(){
		return (ArrayList<Arc>) this.adj;
	}

	public String getNom(){
		return this.nom;
	}
}
