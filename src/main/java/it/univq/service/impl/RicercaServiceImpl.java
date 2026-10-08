package it.univq.service.impl;

import it.univq.datamodel.*;
import it.univq.service.RicercaService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RicercaServiceImpl implements RicercaService {

    private final Libreria libreria= Libreria.getIstanza();

    //qui restituisco un set di oggetti ricercabili , il controller provvedera poi a dividere i brani album e artisi per visualizzarli a schermo
    @Override
    public Set<Ricercabile> cerca(String testo){

    Set<Ricercabile> risultato = new HashSet<>();


        for (Brano b : libreria.getBrani().getElementi()){
            if(b.trovato(testo)) risultato.add(b);
        }

        for (Album a : libreria.getAlbum().getElementi()){
            if(a.trovato(testo)) risultato.add(a);
        }
        for (Artista a : libreria.getArtisti().getElementi()){
            if(a.trovato(testo)) risultato.add(a);
        }
        return risultato;
    }
    //se attivo il filtro avrò  solo i brani solo i brani quindi il controller non dovra dividerli
    @Override
    public Set<Brano> cercaFiltro(String testo,String filtro , String value){
        Set<Ricercabile> risultato = cerca(testo);

        Set<Brano> risultatoBrani= risultato.stream().filter(r-> r instanceof Brano).map(r->(Brano)r).collect(Collectors.toSet());

        switch (filtro.toLowerCase()){
            case "genere":
                return risultatoBrani.stream().filter(b->b.getGenereBrano().name().equalsIgnoreCase(value)).collect(Collectors.toSet());


            case "durata":
                return risultatoBrani.stream().filter(b-> b.getDurata()<= Integer.parseInt(value)).collect(Collectors.toSet());

            case "anno":
                return risultatoBrani.stream().filter(b-> b.getDataAggiunta().getYear()==Integer.parseInt(value)).collect(Collectors.toSet());


        }
        return Set.of();


    }
}
