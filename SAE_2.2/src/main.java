import java.io.IOException;
import java.util.List;

public class main {
    public static void main(String[] args){
        GrapheListe graphe = new GrapheListe(750);
        BellmanFord bellmanFord = new BellmanFord();
        long debutBellmanFord = System.nanoTime();
        Valeur resultatBellmanFord = bellmanFord.resoudre(graphe, graphe.listeNoeuds().get(0));
        long finBellmanFord = System.nanoTime();

        Dijkstra dijkstra = new Dijkstra();
        long debutDijkstra = System.nanoTime();
        Valeur resultatDijkstra = dijkstra.resoudre(graphe, graphe.listeNoeuds().get(0));
        long finDijkstra = System.nanoTime();

        long tempsExecutionBellmanFord = (finBellmanFord - debutBellmanFord) / 1000; // Convertir en microsecondes
        long tempsExecutionDijkstra = (finDijkstra - debutDijkstra) / 1000; // Convertir en microsecondes

        System.out.println("Temps d'execution (Bellman-Ford) : " + tempsExecutionBellmanFord + " us");
        System.out.println("Temps d'execution (Dijkstra) : " + tempsExecutionDijkstra + " us");
        try{
            Labyrinthe lb = new Labyrinthe("laby/laby1.txt");
            GrapheListe glaby = lb.genererGraphe();
            System.out.println(glaby.toString());
            Dijkstra dk = new Dijkstra();
            Valeur resulLaby = dk.resoudre(glaby, "1, 1");
            List<String> resLaby = resulLaby.calculerChemin("1, 1", "3, 5");
            System.out.println(resLaby);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }



    }
}
