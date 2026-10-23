package namedEntity.clasificacion.entity;
//forma canónica, número de miembros, tipo de organización

import namedEntity.NamedEntity;

public class Organizacion extends NamedEntity{
    private String  forma_canonica;
    private int numero_miembros;
    private String tipo_organizacion;

    public Organizacion(String name,String category, int frequency,String forma_canonica,int numero_miembros,String tipo_organizacion) {
        super(name,category, frequency);
        this.forma_canonica = forma_canonica;
        this.numero_miembros = numero_miembros;
        this.tipo_organizacion = tipo_organizacion;

    }

    public String getForma_canonica() {
        return forma_canonica;
    }
    public int getNumero_miembros() {
        return numero_miembros;
    }   
    public String getTipo_organizacion() {
        return tipo_organizacion;
    }
}
