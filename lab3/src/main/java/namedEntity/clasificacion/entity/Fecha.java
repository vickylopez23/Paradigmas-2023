package namedEntity.clasificacion.entity;

import namedEntity.NamedEntity;
// precisa, forma canónica

public class Fecha extends NamedEntity{
    String precisa;
    String forma_canonica;
    public Fecha(String name,String category, int frequency,String precisa,String forma_canonica) { 
        super(name,category, frequency);
        this.precisa = precisa;
        this.forma_canonica = forma_canonica;
    }
    public String getPrecisa() {
        return precisa;
    }
    public String getForma_canonica() {
        return forma_canonica;
    }

    
}
