package it.univq.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.univq.datamodel.*;
import it.univq.service.CriterioOrdinamento;
import it.univq.service.Filtro;
import it.univq.service.RicercaService;
import it.univq.service.Ricercabile;

public class RicercaServiceImpl implements RicercaService {


    private final Libreria libreria = Libreria.getIstanza();

    @Override  //questo metodo serve per la ricerca generale
     public List<Ricercabile> cerca(String testo){
        //se non scrivo niente su cerca mi esce lista vuota
        if (testo == null || testo.isBlank())return List.of();

        List<Ricercabile> trovati = new ArrayList<>();

        trovati.addAll(libreria.getBrani().getElementi().stream().filter(b->b.trovato(testo)).toList());
        trovati.addAll(libreria.getAlbum().getElementi().stream().filter(a->a.trovato(testo)).toList());
        trovati.addAll(libreria.getArtisti().getElementi().stream().filter(a->a.trovato(testo)).toList());

        return trovati;
     }
     
     //questo metodo serve per quando si esegue  la ricerca con il filtro 
     @Override                      //il contoller definirà il filtro
     public List<Brano> filtraBrani(Filtro<Brano> filtro, CriterioOrdinamento<Brano> criterio){
        List<Brano> filtrati = new ArrayList<>();
      
      if(filtro !=null)
        filtrati= libreria.getBrani().getElementi().stream().filter(brano->filtro.filtra(brano)).collect(Collectors.toList());
      else {
         filtrati=libreria.getBrani().getElementi().stream().collect(Collectors.toList());
      }
      if(criterio !=null)
        filtrati.sort((b1, b2)-> criterio.ordina(b1,b2));
      return filtrati;
     }
    

    
}
