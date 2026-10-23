package namedEntity.clasificacion.entity.lugar;

public class Ciudad extends Lugar{
    //país, capital, población
    private String pais;
    private String capital;
    private String poblacion;
    public Ciudad(String name,String category, int frequency,String pais,String capital,String poblacion) { 
        super(name,category, frequency);
        this.pais = pais;
        this.capital = capital;
        this.poblacion = poblacion;
    }
    public String getPais() {
        return pais;
    }
    public String getCapital() {
        return capital;
    }
    public String getPoblacion() {
        return poblacion;
    }

    
}
