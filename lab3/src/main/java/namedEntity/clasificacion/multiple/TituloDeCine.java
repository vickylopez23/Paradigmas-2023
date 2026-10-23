package namedEntity.clasificacion.multiple;

import namedEntity.clasificacion.entity.persona.Titulo;
import namedEntity.clasificacion.tema.cultura.Cine;

public class TituloDeCine extends Titulo implements Cine {
    public TituloDeCine(String name, String category, int frequency, String ID, String forma_canonica, String profesional)
    {
        super(name,category, frequency,ID,forma_canonica,profesional);
    }

    @Override
    public String getCultura() {
        return "Cine";
    }

    @Override
    public String getTema() {
        return "Cine";
    }

    @Override
    public String getCine() {
        return "Titulo";
    }
    
    
}
