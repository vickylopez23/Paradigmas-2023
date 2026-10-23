package namedEntity.clasificacion.entity.persona;

import namedEntity.NamedEntity;

public class Persona extends NamedEntity {
    private String  ID;
    
    public Persona(String name,String category, int frequency,String ID) { 

        super(name,category, frequency);
        this.ID = ID;
    }
    public String getID() {
        return ID;
    }
    
}
