package namedEntity.clasificacion.multiple;

import namedEntity.clasificacion.entity.Evento;
import namedEntity.clasificacion.tema.cultura.Musica;

public class EventoDeMusica extends Evento implements Musica{
    public EventoDeMusica(String name, String category, int frequency, String forma_canonica, String fecha, String recurrente){
        super(name,category, frequency,forma_canonica,fecha,recurrente);
    }
        
    @Override
    public String getCultura() {
        return "Musica";
    }
    @Override
    public String getTema() {
        return "Musica";
    }
    @Override
    public String getMusica() {
        return "Evento";
    }
    

}
