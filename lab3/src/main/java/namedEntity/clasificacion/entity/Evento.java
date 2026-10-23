package namedEntity.clasificacion.entity;

import namedEntity.NamedEntity;
//forma canónica, fecha, recurrente
public class Evento extends NamedEntity {
    private String  forma_canonica;
    private String fecha;
    private String recurrente;
    public Evento(String name,String category, int frequency,String forma_canonica,String fecha,String recurrente) { 
        super(name,category, frequency);
        this.forma_canonica = forma_canonica;
        this.fecha = fecha;
        this.recurrente = recurrente;
    }
    public String getForma_canonica() {
        return forma_canonica;
    }
    public String getFecha() {
        return fecha;
    }
    public String getRecurrente() {
        return recurrente;
    }
}
