import java.io.*;
import java.util.*;

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
                    ajouterArc(String.valueOf(infos[0]), String.valueOf(infos[1]), Double.parseDouble(infos[2]));
                } else {
                    errs++;
                }
            }
            System.out.println("Graphe construit a partir du fichier " + nomFichier + " avec " + errs + " erreurs internes.");
        } catch (IOException e) {
            System.err.println("Une erreur est survenue lors de la lecture du fichier : " + e.getMessage());
        }
    }

    public GrapheListe(int nbNoeuds){
        this.ensNom = new ArrayList<>();
        this.ensNoeuds = new ArrayList<>();
        Random ran = new Random();
        for (int i=0; i<nbNoeuds*3; i++){
             int noeud = ran.nextInt(nbNoeuds);
             int noeud2 = noeud;
             while (noeud2 == noeud){
                 noeud2 = ran.nextInt(nbNoeuds);
            }
            ajouterArc(String.valueOf(noeud), String.valueOf(noeud2), ran.nextInt(nbNoeuds));
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
        Noeud dp = null;//On créée un noeud qui sera utile dans deux blocs de la méthode
        for (Noeud noeud : ensNoeuds) {//On cherche dans la liste si le noeud de départ existe
            if (noeud.getNom().equals(depart)) {
                dp = noeud;//Si oui on le récupère dans l'instance dp
                break;
            }
        }

        if (dp == null) {//S'il n'existe pas, on le créée puis l'ajoutons au graphe
            dp = new Noeud(depart);
            ensNom.add(depart);
            ensNoeuds.add(dp);
        }

        if (!ensNom.contains(destination)) {//Si une destination du même nom n'existe pas dans ce graphe
            Noeud dest = new Noeud(destination);//On créé un nouveau noeud avec ce nom
            ensNom.add(destination);//On l'ajoute a ce graphe
            ensNoeuds.add(dest);
        }

        //Noeud param = this.ensNoeuds.get(this.ensNoeuds.indexOf(dp));//On récupère le noeud qui est maintenant forcément présent dans la liste
        for (Arc arc : dp.getAdj()) { // On vérifie si l'arc existe déjà
            if (arc.getDest().equals(destination)) {
                if (arc.getCout() == cout) {
                    return;//Si il existe déjà, on sort de la méthode sans rien faire
                } else {
                    arc.setCout(cout);//S'il existe déjà mais que le cout est différent, on met à jour le cout
                    return;//Puis on sort de la méthode
                }
            }
        }
        dp.ajouterArc(destination, cout);//Sinon on peut ajouter l'arc au noeud.
    }

    public String toString() {
        List<Noeud> listriee = new ArrayList<>(this.ensNoeuds);//Liste auxiliaire pour trier la liste de noeuds
        Collections.sort(listriee, new Comparator<Noeud>() {//Tri de la liste
            public int compare(Noeud n1, Noeud n2) {
                return n1.getNom().compareTo(n2.getNom());
            }
        });
        String res = "";
        for (Noeud nd : listriee) {
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
                    res += "\"" + nd.getNom() + "\" -> " + "\"" + adj.get(i).getDest() + "\" [label = " + round(adj.get(i).getCout()) + "]";
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
