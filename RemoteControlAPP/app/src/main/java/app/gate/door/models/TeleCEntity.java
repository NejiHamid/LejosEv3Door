package app.gate.door.models;

public class TeleCEntity {
    private int idTC;
    private String libelle;
    private String adrMAC;
    private int proprietaire;

    public TeleCEntity(int idTC, String libelle, String adrMAC, int proprietaire) {
        this.idTC = idTC;
        this.libelle = libelle;
        this.adrMAC = adrMAC;
        this.proprietaire = proprietaire;
    }

    public int getIdTC() {
        return idTC;
    }

    public void setIdTC(int idTC) {
        this.idTC = idTC;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getAdrMAC() {
        return adrMAC;
    }

    public void setAdrMAC(String adrMAC) {
        this.adrMAC = adrMAC;
    }

    public int getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(int proprietaire) {
        this.proprietaire = proprietaire;
    }

    @Override
    public String toString() {
        return "TeleCEntity{" +
                "idTC=" + idTC +
                ", libelle='" + libelle + '\'' +
                ", adrMAC='" + adrMAC + '\'' +
                ", proprietaire=" + proprietaire +
                '}';
    }
}
