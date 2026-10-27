package it.univq.datamodel;

/**
 * classe singleton che contiene tutti i brani, album e artisti divisi in tre cataloghi 
 */
public class Libreria {

    private static final Libreria istanza = new Libreria();

    private Catalogo<Brano> catalogoBrani;
    private Catalogo<Album> catalogoAlbum;
    private Catalogo<Artista> catalogoArtisti;

    /**
     * costruttore della classe {@code Libreria}
     */
    private Libreria() {
        this.catalogoBrani = new Catalogo<>();
        this.catalogoAlbum = new Catalogo<>();
        this.catalogoArtisti = new Catalogo<>();
    }

    /**
     * metodo che serve per ottenere l istanza unica della classe {@code Libreria}
     * @return {@code Libreria} istanza unica della classe {@code Libreria}
     */
    public static Libreria getIstanza() {
        return istanza;
    }

    /**
     * metodo che serve per ottenere i brani
     * @return {@code Catalogo<Brano>} catalogo dei brani
     */
    public Catalogo<Brano> getBrani(){
        return this.catalogoBrani;
    }

    /**
     * metodo che serve per ottenere gli artisti
     * @return {@code Catalogo<Artista>} catalogo degli artisti
     */
    public Catalogo<Artista> getArtisti(){
        return this.catalogoArtisti;
    }

    /**
     * metodo che serve per ottenere gli album
     * @return {@code Catalogo<Album>} catalogo degli album
     */
    public Catalogo<Album> getAlbum(){
        return this.catalogoAlbum;
    }


}
