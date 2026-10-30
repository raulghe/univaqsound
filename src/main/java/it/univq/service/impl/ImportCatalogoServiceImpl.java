package it.univq.service.impl;

import it.univq.datamodel.*;
import it.univq.service.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ImportCatalogoServiceImpl implements ImportCatalogoService {

    private static final String SEPARATORE = ";";
    private static final int NUM_COLONNE = 12;

    private final ArtistaService artistaService = new ArtistaServiceImpl();
    private final AlbumService albumService = new AlbumServiceImpl();
    private final BranoService branoService = new BranoServiceImpl();
    private final LoggerService logger = LoggerServiceImpl.getIstanza();

    @Override
    public int importaCatalogoDaCsv(String percorsoRisorsa) throws IOException {
        InputStream is = getClass().getResourceAsStream(percorsoRisorsa);
        if (is == null) {
            throw new FileNotFoundException("File CSV non trovato: " + percorsoRisorsa);
        }

        // Mappe per riusare le istanze già create (Set + equals non sostituisce l'oggetto esistente)
        Map<String, Artista> artisti = new HashMap<>();
        Map<String, Album> albums = new HashMap<>();
        for (Artista a : artistaService.getTuttiArtisti()) {
            artisti.put(a.getNomeArte(), a);
        }
        for (Album al : albumService.getTuttiAlbum()) {
            albums.put(al.getArtistaAlbum().getNomeArte() + "|" + al.getTitolo(), al);
        }

        int importati = 0;
        int numeroRiga = 1; // l'intestazione è la riga 1

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            br.readLine(); // salto l'intestazione
            String riga;
            while ((riga = br.readLine()) != null) {
                numeroRiga++;
                if (riga.isBlank()) continue;

                String[] c = riga.split(SEPARATORE, -1);
                if (c.length != NUM_COLONNE) {
                    logger.log("ERRORE_IMPORT", "Riga " + numeroRiga + ": numero di colonne non valido");
                    continue;
                }

                try {
                    // 1) parsing e validazione di tutti i campi PRIMA di creare oggetti
                    String tipo = c[0].trim().toUpperCase();
                    String nomeArtista = c[1].trim();
                    String biografia = c[2].trim();
                    GenereMusicale genereArtista = GenereMusicale.valueOf(c[3].trim().toUpperCase());
                    String titoloAlbum = c[4].trim();
                    int anno = Integer.parseInt(c[5].trim());
                    GenereMusicale genereAlbum = GenereMusicale.valueOf(c[6].trim().toUpperCase());
                    String copertina = c[7].trim();
                    String titoloBrano = c[8].trim();
                    int durata = Integer.parseInt(c[9].trim());
                    GenereMusicale genereBrano = GenereMusicale.valueOf(c[10].trim().toUpperCase());
                    String percorsoFile = c[11].trim();

                    if (!tipo.equals("SOLISTA") && !tipo.equals("GRUPPO")) {
                        throw new IllegalArgumentException("tipo artista sconosciuto: " + c[0]);
                    }

                    // 2) artista
                    Artista artista = artisti.get(nomeArtista);
                    if (artista == null) {
                        artista = tipo.equals("GRUPPO")
                                ? new ArtistaGruppo(nomeArtista, biografia, genereArtista)
                                : new ArtistaSolista(nomeArtista, biografia, genereArtista);
                        artistaService.aggiungiArtista(artista);
                        artisti.put(nomeArtista, artista);
                    }

                    // 3) album
                    String chiaveAlbum = nomeArtista + "|" + titoloAlbum;
                    Album album = albums.get(chiaveAlbum);
                    if (album == null) {
                        album = new Album(titoloAlbum, anno, genereAlbum, copertina, artista);
                        albumService.aggiungiAlbum(album);
                        artista.getDiscografia().add(album);
                        albums.put(chiaveAlbum, album);
                    }

                    // 4) brano (equals = titolo + durata, quindi il controllo è sul catalogo intero)
                    Brano brano = new Brano(titoloBrano, durata, genereBrano, album);
                    brano.setPercorsoFile(percorsoFile);
                    if (branoService.getTuttiBrani().contains(brano)) {
                        logger.log("ERRORE_IMPORT", "Riga " + numeroRiga + ": brano già presente (" + titoloBrano + ")");
                        continue;
                    }
                    branoService.aggiungiBrano(brano);
                    album.getBrani().add(brano);
                    importati++;

                } catch (IllegalArgumentException elem) { // include NumberFormatException e valueOf dell'enum
                    logger.log("ERRORE_IMPORT", "Riga " + numeroRiga + ": " + elem.getMessage());
                }
            }
        }

        logger.log("IMPORT_CATALOGO", "Importati " + importati + " brani da " + percorsoRisorsa);
        return importati;
    }
}