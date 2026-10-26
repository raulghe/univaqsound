package it.univq.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.univq.datamodel.Artista;
import it.univq.datamodel.GenereMusicale;
import it.univq.datamodel.Brano;
import it.univq.datamodel.Utente;
import it.univq.service.ArtistaService;
import it.univq.service.BranoService;
import it.univq.service.StatisticheService;

public class StatisticheServiceImpl implements StatisticheService {
    private BranoService branoservice=new BranoServiceImpl();
    private ArtistaService artistaService= new ArtistaServiceImpl();
    
    @Override
    public List<Brano> braniPiuAscoltati(){
        Set<Brano> brani=this.branoservice.getTuttiBrani();
        List<Brano> listaBrani= brani.stream().sorted((b1,b2)->b2.getNumeroAscolti()-b1.getNumeroAscolti()).limit(10).toList();
        return listaBrani;
    }
    
    @Override 
    public List<Artista> artistiPiuAscoltati(){
      Set<Artista> artisti= artistaService.getTuttiArtisti();
      List<Artista> listaArtisti= artisti.stream().sorted((a1,a2)->artistaService.getAscoltiTotali(a2)-artistaService.getAscoltiTotali(a1)).limit(10).toList();
      return listaArtisti;
    }
    @Override 
    public Integer tempoTotaleAscoltato(Utente u){
    Integer totaleAscolto= u.getCronologia().getVociCronologia().stream().mapToInt(v->v.getDurataAscolto()).sum();
    return totaleAscolto;
    }
    @Override  //in base al genere più ascoltato dall’utente(uso la l attrivuto che contiene la lista delle preferenze preferenze dell utente)
    public List<Brano> braniProposti(Utente u){
        Set<GenereMusicale> preferenze=u.getPreferenzeMusicali();
        
        List<Brano> tuttiBrani= this.branoservice.getTuttiBrani().stream().collect(Collectors.toList());
        
        List<Brano> ascoltatiUtente= u.getCronologia().getVociCronologia().stream().map(v->v.getBranoVoce()).toList();
       tuttiBrani.removeAll(ascoltatiUtente); //le specifiche richiedono brani suggeriti non ancora ascoltati 
       List<Brano> listaBrani= tuttiBrani.stream().filter(b->preferenze.contains(b.getGenereBrano())).limit(10).toList();

       return listaBrani;
        
    }
}
