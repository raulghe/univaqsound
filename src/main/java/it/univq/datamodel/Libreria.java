package it.univq.datamodel;

/**
 * 
 */
public class Libreria {

    private static final Libreria istanza = new Libreria();

    private Catalogo<Brano> catalogoBrani;
    private Catalogo<Album> catalogoAlbum;
    private Catalogo<Artista> catalogoArtisti;

    /**
     * 
     */
    private Libreria() {
        this.catalogoBrani = new Catalogo<>();
        this.catalogoAlbum = new Catalogo<>();
        this.catalogoArtisti = new Catalogo<>();
    }

    /**
     * 
     * @return
     */
    public static Libreria getIstanza() {
        return istanza;
    }

    /**
     * 
     * @return
     */
    public Catalogo<Brano> getBrani(){
        return this.catalogoBrani;
    }

    /**
     * 
     * @return
     */
    public Catalogo<Artista> getArtisti(){
        return this.catalogoArtisti;
    }

    /**
     * 
     * @return
     */
    public Catalogo<Album> getAlbum(){
        return this.catalogoAlbum;
    }


}
