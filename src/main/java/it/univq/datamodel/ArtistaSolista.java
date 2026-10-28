package it.univq.datamodel;



/**
 * questa classe estende Artista e serve a rappresentare un artista singolo
 */
public class ArtistaSolista extends Artista  {

    /**
     * costruttore di ArtistaSolista
     * @param nomeArte nome d'arte dell'artista solista
     * @param biografia biografia dell artista solista
     * @param genere genere musicale dell artista solista
     */
    public ArtistaSolista(String nomeArte, String biografia, GenereMusicale genere){
        super(nomeArte, biografia, genere);
    }

}
