package it.univq.service.impl;

import it.univq.datamodel.*;
import it.univq.service.Ordinabile;
import it.univq.service.RicercaService;

import java.util.ArrayList;

import java.util.List;

import java.util.stream.Collectors;



public class RicercaServiceImpl implements RicercaService, Ordinabile<List<Ricercabile>> {

    private final Libreria libreria= Libreria.getIstanza();

    //qui restituisco un set di oggetti ricercabili , il controller provvedera poi a dividere i brani album e artisi per visualizzarli a schermo
    @Override
    public List<Ricercabile> cerca(String testo, Filtro filtro, Ordine ordine) {

        
            List<Ricercabile> risultato = new ArrayList<>();

        // qui controllo se ho il filtro o no, se filtro attivo allora filtro i brani
        for (Brano b : libreria.getBrani().getElementi()) {
            if ((filtro==null) && b.trovato(testo)) risultato.add(b);
            else if(filtro!=null) {
                switch (filtro.getTipo()) {
                    case GENERE ->{
                        if(b.trovato(testo) && filtro.getValore().equalsIgnoreCase(b.getGenereBrano().toString())) risultato.add(b);
                    }
                    case DURATA ->{
                        if(b.trovato(testo) && b.getDurata()<= Integer.parseInt(filtro.getValore()))risultato.add(b);
                                
                    }
                    case ANNO -> {
                        if( b.trovato(testo) && Integer.parseInt(filtro.getValore())==(b.getDataAggiunta().getYear()))risultato.add(b);
                    }

                }
            }

        }

        for (Album a : libreria.getAlbum().getElementi()) {
            if ((filtro==null) && a.trovato(testo)) risultato.add(a);
            else if(filtro!=null) {
                switch (filtro.getTipo()) {
                    case GENERE ->{
                        if(a.trovato(testo) && filtro.getValore().equalsIgnoreCase(a.getGenereAlbum().toString())) risultato.add(a);
                    }
                    case DURATA ->{
                        //non ha senso per album
                    }
                    case ANNO -> {
                        if( a.trovato(testo) && Integer.parseInt(filtro.getValore())==(a.getAnnoPubblicazione()))risultato.add(a);
                    }

                }
            }
        }
        for (Artista a : libreria.getArtisti().getElementi()) {
            if ((filtro==null) &&  a.trovato(testo)) risultato.add(a);
            else if(filtro!=null) {
                switch (filtro.getTipo()) {
                    case GENERE ->{
                        if(a.trovato(testo) && filtro.getValore().equalsIgnoreCase(a.getGenereArtista().toString())) risultato.add(a);
                    }
                    case DURATA ->{
                        //non ha senso per artista
                    }
                    case ANNO -> {
                        //non ha senso per artista
                    }

                }
            }
        }
        
        
        
       //controllo se volgio ordinare 
        if(ordine!=null ){
            ordina(risultato,ordine);
        }
        return risultato;
    }
    

    @Override  //questo metodo capisce il criterio secondo cui deve ordinare e ordina cio che puo 
    public void ordina(List<Ricercabile> o, Ordine ordine) {
        List<Brano> brani= o.stream().filter(r-> r instanceof Brano).map(r->(Brano)r).collect(Collectors.toList());
        List<Album> album= o.stream().filter(r-> r instanceof Album).map(r->(Album)r).collect(Collectors.toList());
        List<Artista> artisti= o.stream().filter(r-> r instanceof Artista).map(r->(Artista)r).collect(Collectors.toList());
        
        switch (ordine) {
            case NOME -> {brani.sort((b1, b2) -> b1.getTitolo().compareToIgnoreCase(b2.getTitolo()));
                album.sort((a1, a2) -> a1.getTitolo().compareToIgnoreCase(a2.getTitolo()));
                artisti.sort((a1, a2) -> a1.getNomeArte().compareToIgnoreCase(a2.getNomeArte()));
            }
            case DURATA -> brani.sort((b1, b2) -> Integer.compare(b1.getDurata(), b2.getDurata()));
            case NUMEROASCOLTI -> brani.sort((b1, b2) -> Integer.compare(b2.getNumeroAscolti(), b1.getNumeroAscolti()));
            case DATA -> brani.sort((b1, b2) -> b1.getDataAggiunta().compareTo(b2.getDataAggiunta()));
        }
        o.clear();
        o.addAll(brani);
        o.addAll(album);
        o.addAll(artisti);
        
    }



}
