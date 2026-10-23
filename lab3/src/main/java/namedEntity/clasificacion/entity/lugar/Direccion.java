package namedEntity.clasificacion.entity.lugar;

public class Direccion extends Lugar {
    // ciudad
    private String ciudad;
    public Direccion(String name,String category, int frequency,String ciudad) { 
        super(name,category, frequency);
        this.ciudad = ciudad;
    }
    public String getCiudad() {
        return ciudad;
    }

    
}
