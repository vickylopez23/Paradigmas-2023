package namedEntity.clasificacion.entity;

import namedEntity.NamedEntity;

public class Otros extends NamedEntity{
    private String comentarios;
    public Otros(String name,String category, int frequency,String comentarios)  { 
        super(name,category, frequency);
        this.comentarios = comentarios;

    }
    public String getComentarios() {
        return comentarios;
    }

    
}
