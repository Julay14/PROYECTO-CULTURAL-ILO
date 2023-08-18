package SANLUIS.proyecto;

public class Ent_Mat {
    private String dis,niv,fre,hor,doc;

    public Ent_Mat(String dis, String niv, String fre, String hor, String doc) {
        this.dis = dis;
        this.niv = niv;
        this.fre = fre;
        this.hor = hor;
        this.doc = doc;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getNiv() {
        return niv;
    }

    public void setNiv(String niv) {
        this.niv = niv;
    }

    public String getFre() {
        return fre;
    }

    public void setFre(String fre) {
        this.fre = fre;
    }

    public String getHor() {
        return hor;
    }

    public void setHor(String hor) {
        this.hor = hor;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }
}
