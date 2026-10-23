package namedEntity.clasificacion.entity.lugar;

public class Pais  extends Lugar{
    //población, lengua oficial
    private String poblacion;
    private String lenguaOficial;
    public Pais(String name,String category, int frequency,String poblacion,String lenguaOficial) { 
        super(name,category, frequency);
        this.poblacion = poblacion;
        this.lenguaOficial = lenguaOficial;
    }
    public String getPoblacion() {
        return poblacion;
    }
    public String getLenguaOficial() {
        return lenguaOficial;
    }

    
}
