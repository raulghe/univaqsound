package it.univq.datamodel;

public class Libreria {

    private static final Libreria istanza = new Libreria();

    private Catalogo<Brano> catalogoBrani;
    private Catalogo<Album> catalogoAlbum;
    private Catalogo<Artista> catalogoArtisti;

    private Libreria() {
        this.catalogoBrani = new Catalogo<>();
        this.catalogoAlbum = new Catalogo<>();
        this.catalogoArtisti = new Catalogo<>();
    }

    public static Libreria getIstanza() {
        return istanza;
    }

    public Catalogo<Brano> getBrani(){
        return this.catalogoBrani;
    }
    public Catalogo<Artista> getArtisti(){
        return this.catalogoArtisti;
    }
    public Catalogo<Album> getAlbum(){
        return this.catalogoAlbum;
    }


}
