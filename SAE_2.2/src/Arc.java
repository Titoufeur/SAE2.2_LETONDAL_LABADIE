public class Arc {
    private String dest;
    private double cout;

    public Arc(String dest, double cout){
        if (cout>=0){
            this.dest = dest;
            this.cout = cout;
        } else{
            throw new IllegalArgumentException("La valeur de cout doit être positive");
        }
    }

    public String getDest(){
        return this.dest;
    }

    public double getCout(){
        return this.cout;
    }

    public void setCout(double c){
        this.cout = c;
    }
}
