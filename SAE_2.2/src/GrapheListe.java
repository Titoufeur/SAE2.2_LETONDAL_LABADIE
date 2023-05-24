import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

public class GrapheListe implements Graphe {
    private ArrayList<String> ensNom;
    private ArrayList<Noeud> ensNoeuds;

    public GrapheListe() {
        this.ensNom = new ArrayList<>();
        this.ensNoeuds = new ArrayList<>();
    }

    public GrapheListe(String nomFichier) {
        this.ensNom = new ArrayList<>();
        this.ensNoeuds = new ArrayList<>();
        int errs = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(nomFichier));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] infos = line.split("\t");
                if (infos.length == 3) {
                    ajouterArc(infos[0], infos[1], Double.parseDouble(infos[2]));
                } else {
                    errs++;
                }
            }
            System.out.println("Graphe construit à partir du fichier " + nomFichier + " avec 0 erreurs internes.");
        } catch (IOException e) {
            System.err.println("Une erreur est survenue lors de la lecture du fichier : " + e.getMessage());
        }
    }

    public ArrayList<String> listeNoeuds() {
        return ensNom;
    }

    public List<Arc> suivants(String n) {
        Noeud index = new Noeud(n);
        Noeud param = this.ensNoeuds.get(ensNoeuds.indexOf(index));
        return (ArrayList<Arc>) param.getAdj();
    }


    public void ajouterArc(String depart, String destination, double cout) {
        Noeud dp = new Noeud(depart);//On créée un noeud qui sera utile dans deux blocs de la méthode
        if (!ensNom.contains(depart)) {//Si un noeud du même nom n'existe pas dans ce graphe
            ensNom.add(depart);//On l'ajoute à la liste du graphe
            ensNoeuds.add(dp);//idem
        }

        if (!ensNom.contains(destination)) {//Si une destination du même nom n'existe pas dans ce graphe
            Noeud dest = new Noeud(destination);//On créé un nouveau noeud avec ce nom
            ensNom.add(destination);//On l'ajoute a ce graphe
            ensNoeuds.add(dest);
        }
        //Noeud index = new Noeud(depart);//Noeud temporaire avec le nom passé en paramètre afin de pouvoir utiliser la méthode indexOf
        Noeud param = this.ensNoeuds.get(ensNoeuds.indexOf(dp));//Si le noeud existe deja, on le recupere
        for (Arc arc : param.getAdj()) { // On vérifie si l'arc existe déjà
            if (arc.getDest().equals(destination)) {
                if (arc.getCout() == cout) {
                    throw new IllegalArgumentException("L'arc saisi existe deja.");//S'il existe déjà, exception
                } else {
                    arc.setCout(cout);//S'il existe déjà mais que le cout est différent, on met à jour le cout
                    return;//Puis on sort de la méthode
                }
            }
        }
        param.ajouterArc(destination, cout);//Sinon on peut ajouter l'arc au noeud.
    }

    public String toString() {
        String res = "";
        for (Noeud nd : this.ensNoeuds) {
            List<Arc> adj = nd.getAdj();
            if (adj.size() != 0) {
                res += nd.getNom() + " ->";
                for (int i = 0; i < adj.size(); i++) {
                    res += " " + adj.get(i).getDest() + "(" + adj.get(i).getCout() + ")";
                }
                res += "\n";
            }
        }
        return res;
    }

    public String toGraphviz() {
        String res = "";
        for (Noeud nd : this.ensNoeuds) {
            List<Arc> adj = nd.getAdj();
            if (adj != null) {
                for (int i = 0; i < adj.size(); i++) {
                    res += nd.getNom() + " -> " + adj.get(i).getDest() + " [label = " + round(adj.get(i).getCout()) + "]";
                    res += "\n";
                }

            }

        }
        return res;
    }

    public void matriceAdj(String nomFichier, String nomSortant) {
        try {
            //Création des flux de lecture et d'écriture
            BufferedReader br = new BufferedReader(new FileReader(nomFichier));
            BufferedWriter bw = new BufferedWriter(new FileWriter(nomSortant));
            String line;
            line = br.readLine();
            //Initialisation des noeuds du fichier en première ligne
            String[] noeuds = line.split("\t");
            while ((line = br.readLine()) != null) {
                String[] arcs = line.split("\t");
                for (int i = 1; i < arcs.length; i++) {
                    //Lorsque la valuation est non nulle, on ajoute l'arc
                    if (Double.parseDouble(arcs[i]) != 0) {
                        String result = arcs[0];
                        result += "\t" + noeuds[i] + "\t" + arcs[i];
                        System.out.println(result);
                        bw.write(result);
                        bw.newLine();
                    }
                }
            }
            br.close();
            bw.close();
        } catch (IOException e) {
            System.err.println("Une erreur est survenue lors de la lecture du fichier : " + e.getMessage());
        }
    }
}
