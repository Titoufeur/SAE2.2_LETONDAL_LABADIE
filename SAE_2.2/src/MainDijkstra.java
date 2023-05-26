import java.util.List;
import java.util.Scanner;

public class MainDijkstra {
    public static void main(String[] args){
        GrapheListe gl = new GrapheListe("Graphe1Essai.txt");
        System.out.println(gl);
        Dijkstra dk = new Dijkstra();
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le noeud de depart à calculer :");
        String noeud = sc.nextLine();
        Valeur va = dk.resoudre(gl, noeud);
        System.out.println("Entrez le noeud de destination a calculer :");
        String dest = sc.nextLine();
        List<String> ls = va.calculerChemin(noeud, dest);
        System.out.println("Le chemin le plus court de " + noeud + " a " + dest + " est : " + ls);
    }
}
