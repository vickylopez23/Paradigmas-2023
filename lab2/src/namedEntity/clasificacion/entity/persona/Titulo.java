package namedEntity.clasificacion.entity.persona;

public class Titulo extends Persona {
    // forma canónica, profesional
    private String forma_canonica;
    private String profesional;
    public Titulo(String name,String category, int frequency,String ID,String forma_canonica,String profesional) { 
        super(name,category, frequency,ID);
        this.forma_canonica = forma_canonica;
        this.profesional = profesional;
    }
    public String getForma_canonica() {
        return forma_canonica;
    }
    public String getProfesional() {
        return profesional;
    }

    
}
