import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra implements Algorithme{

    // Entrées :
    // G un graphe orienté avec une pondération (poids) positive des arcs
    // A un sommet (départ) de G
    public Valeur resoudre(Graphe g, String depart) {
        // Début
        // Q <- {} // utilisation d'une liste de noeuds à traiter
        List<String> Q = new ArrayList<>(g.listeNoeuds());
        // Pour chaque sommet v de G faire
        Valeur va = new Valeur();
        for (String X : g.listeNoeuds()) {
            // v.distance <- Infini
            // v.parent <- Indéfini
            // Q <- Q U {v} // ajouter le sommet v à la liste Q
            // Fin Pour
            va.setValeur(X, Double.MAX_VALUE);
            va.setParent(X, null);
        }
        // A.distance <- 0
        va.setValeur(depart, 0);

        // Tant que Q est un ensemble non vide faire
        while (Q.size() != 0) {
            // u <- un sommet de Q telle que u.distance est minimale
            String u = null;
            double distanceMin = Double.POSITIVE_INFINITY;
            for (String sommet : Q) {
                double distance = va.getValeur(sommet);
                if (distance < distanceMin) {
                    distanceMin = distance;
                    u = sommet;
                }
            }
            // Q <- Q \ {u} // enlever le sommet u de la liste Q
            Q.remove(u);
            // Pour chaque sommet v de Q tel que l'arc (u,v) existe faire
            for (Arc v : g.suivants(u)) {
                String dest = v.getDest();
                if (Q.contains(dest)) {//Pour que ce soit un sommet de Q
                    // D <- u.distance + poids(u,v)
                    double d = va.getValeur(u) + v.getCout();
                    // Si D < v.distance
                    if (d < va.getValeur(dest)) {
                        // Alors v.distance <- D
                        va.setValeur(dest, d);
                        // v.parent <- u
                        va.setParent(dest, u);
                        // Fin Si
                    }

                }
                // Fin Pour
            }
            // Fin Tant que
        }
        return va;
    }
}
