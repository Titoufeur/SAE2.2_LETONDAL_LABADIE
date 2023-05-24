import java.util.List;

public class main {
    public static void main(String[] args){
        GrapheListe gl = new GrapheListe();
        System.out.println("gl cree");
        gl.ajouterArc("A", "B", 12);
        gl.ajouterArc("B", "E", 11);
        gl.ajouterArc("A", "D", 87);
        gl.ajouterArc("E", "D", 43);
        gl.ajouterArc("D", "B",23);
        gl.ajouterArc("D", "C", 10);
        gl.ajouterArc("C", "A", 19);
        System.out.println(gl);
        System.out.println(gl.toGraphviz());
        System.out.println("graphviz");

        GrapheListe gl2 = new GrapheListe();
        gl2.matriceAdj("D:\\Documents\\Cours\\SAE_Graphes\\SAE_2.2\\graphes\\Adjacence.txt", "Graphe1Essai.txt");
        BellmanFord bf = new BellmanFord();
        Valeur v = bf.resoudre(gl, "A");
        List<String> ls = v.calculerChemin("C");
        System.out.println(ls);
    }
}
