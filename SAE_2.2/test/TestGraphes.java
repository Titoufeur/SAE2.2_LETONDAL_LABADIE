import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


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
    public void testConsGrapheFichier(){
        GrapheListe gl = new GrapheListe("Graphe1Essai.txt");
        String expected = "A -> B(12.0) D(87.0)\n" +
                "B -> E(11.0)\n" +
                "C -> A(19.0)\n" +
                "D -> B(23.0) C(10.0)\n" +
                "E -> D(43.0)\n";
        assertEquals(expected, gl.toString());
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

        String expected = "\"A\" -> \"B\" [label = 5]\n" +
                "\"A\" -> \"C\" [label = 7]\n" +
                "\"B\" -> \"C\" [label = 3]\n";
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

    @Test
    public void testDijkstra(){
        GrapheListe gl = new GrapheListe();
        gl.ajouterArc("A", "B", 12);
        gl.ajouterArc("B", "E", 11);
        gl.ajouterArc("A", "D", 87);
        gl.ajouterArc("E", "D", 43);
        gl.ajouterArc("D", "B",23);
        gl.ajouterArc("D", "C", 10);
        gl.ajouterArc("C", "A", 19);
        Dijkstra dk = new Dijkstra();
        Valeur va = dk.resoudre(gl, "A");
        assertEquals("A ->  V:0.0 p:null\n" +
                "B ->  V:12.0 p:A\n" +
                "C ->  V:76.0 p:D\n" +
                "D ->  V:66.0 p:E\n" +
                "E ->  V:23.0 p:B\n", va.toString());
    }

    @Test
    public void testPlusCourtChemin(){
        GrapheListe gl = new GrapheListe("Graphe1Essai.txt");
        Dijkstra dk = new Dijkstra();
        Valeur va = dk.resoudre(gl, "A");
        List<String> ls = va.calculerChemin("A", "C");
        assertEquals("[B, E, D, C]", ls.toString());
    }

    public void testLabyToGraphe(){
        try{
            Labyrinthe lb = new Labyrinthe("laby/laby1.txt");
            GrapheListe glaby = lb.genererGraphe();
            assertEquals("1, 1 -> 2, 1(1.0) 1, 2(1.0)\n" +
                    "1, 2 -> 1, 1(1.0) 1, 3(1.0)\n" +
                    "1, 3 -> 2, 3(1.0) 1, 2(1.0) 1, 4(1.0)\n" +
                    "1, 4 -> 2, 4(1.0) 1, 3(1.0) 1, 5(1.0)\n" +
                    "1, 5 -> 2, 5(1.0) 1, 4(1.0)\n" +
                    "2, 1 -> 1, 1(1.0) 3, 1(1.0)\n" +
                    "2, 3 -> 1, 3(1.0) 3, 3(1.0) 2, 4(1.0)\n" +
                    "2, 4 -> 1, 4(1.0) 3, 4(1.0) 2, 3(1.0) 2, 5(1.0)\n" +
                    "2, 5 -> 1, 5(1.0) 2, 4(1.0)\n" +
                    "3, 1 -> 2, 1(1.0) 4, 1(1.0) 3, 2(1.0)\n" +
                    "3, 2 -> 4, 2(1.0) 3, 1(1.0) 3, 3(1.0)\n" +
                    "3, 3 -> 2, 3(1.0) 4, 3(1.0) 3, 2(1.0) 3, 4(1.0)\n" +
                    "3, 4 -> 2, 4(1.0) 4, 4(1.0) 3, 3(1.0)\n" +
                    "4, 1 -> 3, 1(1.0) 5, 1(1.0) 4, 2(1.0)\n" +
                    "4, 2 -> 3, 2(1.0) 5, 2(1.0) 4, 1(1.0) 4, 3(1.0)\n" +
                    "4, 3 -> 3, 3(1.0) 5, 3(1.0) 4, 2(1.0) 4, 4(1.0)\n" +
                    "4, 4 -> 3, 4(1.0) 5, 4(1.0) 4, 3(1.0)\n" +
                    "5, 1 -> 4, 1(1.0) 5, 2(1.0)\n" +
                    "5, 2 -> 4, 2(1.0) 6, 2(1.0) 5, 1(1.0) 5, 3(1.0)\n" +
                    "5, 3 -> 4, 3(1.0) 6, 3(1.0) 5, 2(1.0) 5, 4(1.0)\n" +
                    "5, 4 -> 4, 4(1.0) 6, 4(1.0) 5, 3(1.0) 5, 5(1.0)\n" +
                    "5, 5 -> 6, 5(1.0) 5, 4(1.0)\n" +
                    "6, 2 -> 5, 2(1.0) 7, 2(1.0) 6, 3(1.0)\n" +
                    "6, 3 -> 5, 3(1.0) 6, 2(1.0) 6, 4(1.0)\n" +
                    "6, 4 -> 5, 4(1.0) 7, 4(1.0) 6, 3(1.0) 6, 5(1.0)\n" +
                    "6, 5 -> 5, 5(1.0) 7, 5(1.0) 6, 4(1.0)\n" +
                    "7, 2 -> 6, 2(1.0) 8, 2(1.0)\n" +
                    "7, 4 -> 6, 4(1.0) 8, 4(1.0) 7, 5(1.0)\n" +
                    "7, 5 -> 6, 5(1.0) 8, 5(1.0) 7, 4(1.0)\n" +
                    "8, 1 -> 8, 2(1.0)\n" +
                    "8, 2 -> 7, 2(1.0) 8, 1(1.0) 8, 3(1.0)\n" +
                    "8, 3 -> 8, 2(1.0) 8, 4(1.0)\n" +
                    "8, 4 -> 7, 4(1.0) 8, 3(1.0) 8, 5(1.0)\n" +
                    "8, 5 -> 7, 5(1.0) 8, 4(1.0)\n", glaby.toString());
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}