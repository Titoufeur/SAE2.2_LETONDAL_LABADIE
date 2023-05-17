import java.util.ArrayList;
Public class Noeud{
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
		Arc sav;
		boolean res;
		for(int i=0; i<liste.size(); i++){
			sav=adj.get(i);
			if (!sav.nom.equals(destination)){
				res=true;
			}
		}
		if(res==true){
			sav=new Arc(destination, cout);
			adj.add(sav);
		}
	}
}
