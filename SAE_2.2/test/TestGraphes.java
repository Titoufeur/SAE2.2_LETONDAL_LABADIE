import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;

public class TestGraphes {

    @Test
    public void testAjouterArc() {
        GrapheListe graphe = new GrapheListe();
        graphe.ajouterArc("A", "B", 5);
        graphe.ajouterArc("B", "C", 3);
        graphe.ajouterArc("A", "C", 7);

        // Vérifier si les arcs ont été ajoutés correctement
        List<Arc> arcsA = graphe.suivants("A");
        assertEquals(2, arcsA.size());
        assertEquals("B", arcsA.get(0).getDest());
        assertEquals(5, arcsA.get(0).getCout(), 0);
        assertEquals("C", arcsA.get(1).getDest());
        assertEquals(7, arcsA.get(1).getCout(), 0);

        List<Arc> arcsB = graphe.suivants("B");
        assertEquals(1, arcsB.size());
        assertEquals("C", arcsB.get(0).getDest());
        assertEquals(3, arcsB.get(0).getCout(), 0);

    }

    @Test
    public void testToString() {
        GrapheListe graphe = new GrapheListe();
        graphe.ajouterArc("A", "B", 5);
        graphe.ajouterArc("B", "C", 3);
        graphe.ajouterArc("A", "C", 7);

        String expected = "A -> B(5.0) C(7.0)\n" +
                "B -> C(3.0)\n";
        assertEquals(expected, graphe.toString());
    }

    @Test
    public void testToGraphviz() {
        GrapheListe graphe = new GrapheListe();
        graphe.ajouterArc("A", "B", 5);
        graphe.ajouterArc("B", "C", 3);
        graphe.ajouterArc("A", "C", 7);

        String expected = "A -> B [label = 5]\n" +
                "A -> C [label = 7]\n" +
                "B -> C [label = 3]\n";
        assertEquals(expected, graphe.toGraphviz());
    }

    @Test
    public void testListeNoeuds() {
        GrapheListe graphe = new GrapheListe();
        graphe.ajouterArc("A", "B", 5);
        graphe.ajouterArc("B", "C", 3);
        graphe.ajouterArc("A", "C", 7);

        ArrayList<String> expected = new ArrayList<>();
        expected.add("A");
        expected.add("B");
        expected.add("C");

        assertEquals(expected, graphe.listeNoeuds());
    }

    @Test
    public void testBellmanFord(){
        GrapheListe gl = new GrapheListe();
        gl.ajouterArc("A", "B", 12);
        gl.ajouterArc("B", "E", 11);
        gl.ajouterArc("A", "D", 87);
        gl.ajouterArc("E", "D", 43);
        gl.ajouterArc("D", "B",23);
        gl.ajouterArc("D", "C", 10);
        gl.ajouterArc("C", "A", 19);
        BellmanFord bf = new BellmanFord();
        Valeur v = bf.resoudre(gl, "A");
        assertEquals("A ->  V:0.0 p:null\n" + "B ->  V:12.0 p:A\n" + "C ->  V:76.0 p:D\n" + "D ->  V:66.0 p:E\n" + "E ->  V:23.0 p:B\n", v.toString());
    }
}