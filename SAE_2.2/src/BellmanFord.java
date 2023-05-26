import java.util.List;

public class BellmanFord implements Algorithme{
    public Valeur resoudre(Graphe g, String depart){
        Valeur v = new Valeur();
        for (String X : g.listeNoeuds()){
            v.setValeur(X, Double.MAX_VALUE);
            v.setParent(X, null);
        }
        v.setValeur(depart, 0);
        boolean modif = true;
        while(modif){//Tant qu'il y a eu une modification
            modif = false;
            for (String X : g.listeNoeuds()){//Pour chaque noeud :
                //System.out.println("Plus court chemin de A vers "+ X + ": " + v.getValeur(X));
                for (Arc asuiv : g.suivants(X)){ // Pour chaque arcs partants de ce noeud
                    if ((asuiv.getCout() + v.getValeur(X)) < v.getValeur(asuiv.getDest())){//Si la destination + le poids actuel est plus petit que la valeur de la destination
                        v.setValeur(asuiv.getDest(),(asuiv.getCout() + v.getValeur(X)));// On remplace la valeur de la destination par cette nouvelle
                        v.setParent(asuiv.getDest(), X);//On met a jour le parent
                        modif = true;
                    }
                }
            }
        }
        return v;
    }
}
