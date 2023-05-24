import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;

/**
 * Classe fournie, permet de stocker des valeurs associées au noeud et des parents
 * - un noeud est represente par un String (son nom)
 * - on accede avec des get (getValeur et getParent)
 * - on modifie avec des set (setValeur et setParent)
 */
public class Valeur {

    /**
     * attributs pour stocker les informations (type Table = Dictionnaire)
     * dans le programme de 2 annee.
     */
    Map<String, Double> valeur;
    Map<String, String> parent;

    /**
     * constructeur vide (initialise la possibilité de stocker des valeurs)
     */
    public Valeur() {
        this.valeur = new TreeMap<>();
        this.parent = new TreeMap<>();
    }

    /**
     * permet d'associer une valeur a un nom de noeud (ici L(X))
     *
     * @param nom    le nom du noeud
     * @param valeur la valeur associée
     */
    public void setValeur(String nom, double valeur) {
        // modifie valeur
        this.valeur.put(nom, valeur);
    }

    /**
     * * permet d'associer un parent a un nom de noeud (ici parent(X))
     *
     * @param nom    nom du noeud
     * @param parent nom du noeud parent associe
     */
    public void setParent(String nom, String parent) {
        this.parent.put(nom, parent);
    }

    /**
     * accede au parent stocke associe au noeud nom passe en parametre
     *
     * @param nom nom du noeud
     * @return le nom du noeud parent
     */
    public String getParent(String nom) {
        return this.parent.get(nom);
    }


    /**
     * accede a la valeur associee au noeud nom passe en parametre
     *
     * @param nom nom du noeud
     * @return la valeur stockee
     */
    public double getValeur(String nom) {
        return this.valeur.get(nom);
    }

    /**
     * retourne une chaine qui affiche le contenu
     * - par noeud stocke
     * - a chaque noeud, affiche la valeur puis le noeud parent
     *
     * @return descriptif du noeud
     */
    public String toString() {
        String res = "";
        // pour chaque noeud
        for (String s : this.valeur.keySet()) {
            // ajoute la valeur et le noeud parent
            Double valeurNoeud = valeur.get(s);
            String noeudParent = parent.get(s);
            res += s + " ->  V:" + valeurNoeud + " p:" + noeudParent + "\n";
        }
        return res;

    }

    public List<String> calculerChemin(String destination){
        List<String> res = new ArrayList<>();
        String valeur = this.toString();
        String[] chaines = valeur.split("\n");//On split pour avoir un tableau contenant chaque ligne une à une
        String parent = destination;
        res.add(parent);
        int posDest = trouverPos(chaines, destination);//On cherche la position du noeud recherché grâce à la méthode auxiliaire
        for (int i=0;i<chaines.length;i++){
            parent = String.valueOf(chaines[posDest].charAt(chaines[posDest].length() - 1));//On cherche son parent qui est le dernier caractère
            if (!parent.equals("l")){
                res.add(0, parent);
            } else{
                break;
            }
            posDest = trouverPos(chaines, parent);
        }
        return res;
    }

    public int trouverPos(String[] chaines, String destination){
        int indiceDest = 0;
        for (int i=0;i<chaines.length;i++){
            if (String.valueOf(chaines[i].charAt(0)).equals(destination)){
                indiceDest = i;
                break;
            }
        }
        return indiceDest;
    }
    //A ->  V:0.0 p:null
    //B ->  V:12.0 p:A
    //C ->  V:76.0 p:D
    //D ->  V:66.0 p:E
    //E ->  V:23.0 p:B

}
