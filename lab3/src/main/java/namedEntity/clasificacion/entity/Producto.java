package namedEntity.clasificacion.entity;

import namedEntity.NamedEntity;
// comercial, productor

public class Producto extends NamedEntity{
    private String comercial;
    private String productor;
    public Producto(String name,String category, int frequency,String comercial,String productor) { 
        super(name,category, frequency);
        this.comercial = comercial;
        this.productor = productor;
    }
    public String getComercial() {
        return comercial;
    }
    public String getProductor() {
        return productor;
    }


}
