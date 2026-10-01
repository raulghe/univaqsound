package it.univq.datamodel;

public abstract class Artista {
    private String nomeArte;
    private String biografia;
    private boolean isGroup;// serve per indicare se è un gruppo
    private GenereMusicale genereArtista;

    public Artista(String nomeArte, String biografia, boolean isGroup) {
        this.nomeArte = nomeArte;
        this.biografia = biografia;
        this.isGroup = isGroup;
    }

    public String getNomeArte() {
        return nomeArte;
    }

    public void setNomeArte(String nomeArte) {
        this.nomeArte = nomeArte;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public GenereMusicale getGenereArtista() {
        return genereArtista;
    }

    public void setGenereArtista(GenereMusicale genereArtista) {
        this.genereArtista = genereArtista;
    }
}