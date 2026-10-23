package feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import namedEntity.NamedEntity;
import namedEntity.clasificacion.multiple.ApellidoDeFutbolero;
import namedEntity.clasificacion.multiple.EntidadSinCategoria;
import namedEntity.clasificacion.multiple.EventoDeCine;
import namedEntity.clasificacion.multiple.EventoDeMusica;
import namedEntity.clasificacion.multiple.TituloDeCine;
import namedEntity.heuristic.Heuristic;
import utility.Tuple;

/*Esta clase modela el contenido de un articulo (ie, un item en el caso del rss feed) */

public class Article {
    private String title;
    private String text;
    private Date publicationDate;
    private String link;
    private Heuristic heuristic;

    private boolean namedEntityListCalculated = false;

    private List<NamedEntity> namedEntityList = new ArrayList<NamedEntity>();

    public Article(String title, String text, Date publicationDate, String link) {
        super();
        this.title = title;
        this.text = text;
        this.publicationDate = publicationDate;
        this.link = link;
    }

    public String getHeuristic() {
        if (this.heuristic != null) {
            return this.heuristic.getHeuristicName();
        } else {
            return "Sin Heuristica";
        }
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Article [title=" + title + ", text=" + text + ", publicationDate=" + publicationDate + ", link=" + link + "]";
    }


    public NamedEntity getNamedEntity(String namedEntity) {
        for (NamedEntity n : namedEntityList) {
            if (n.getName().compareTo(namedEntity) == 0) {
                return n;
            }
        }
        return null;
    }

    public void computeNamedEntities(Heuristic h) {
        // al computar las entidades nombradas lo habilitamos
        this.namedEntityListCalculated = true;
        this.heuristic = h;
        String text = this.getTitle() + " " + this.getText();

        String charsToRemove = ".,;:()'!?\n";
        for (char c : charsToRemove.toCharArray()) {
            text = text.replace(String.valueOf(c), "");
        }

        for (String word : text.split(" ")) {
            if (h.isEntity(word)) {
                NamedEntity ne = this.getNamedEntity(word);
                if (ne == null) {
                    Tuple<String, String> tuple = h.getCategory(word);
                    String entityType = tuple.getFirst(); // Persona, Organizacion
                    String tema = tuple.getSecond(); // Politica, Pais

                    // Mapeo de entidades nombradas
                    NamedEntity entity;
                    if (entityType.equalsIgnoreCase("futbol") && tema.equalsIgnoreCase("apellido")) {
                        // Esto es representativo, como es un prototipo queda asi pero sino se llamara a alguna funcion que trae de alguna
                        // base da datos los datos solicitados
                        String ID = "TODO_ID";
                        String forma_canonica = "TODO_CANONIC_FORM";
                        String origen = "TODO_ORIGEN";
                        entity = new ApellidoDeFutbolero(word, entityType, 1, ID, forma_canonica, origen);
                    } else if (entityType.equalsIgnoreCase("evento") && tema.equalsIgnoreCase("cine")) {
                        String forma_canonica = "TODO_CANONIC_FORM";
                        String fecha = "TODO_DATE";
                        String recurrente = "TODO_RECURRENTE";
                        entity = new EventoDeCine(word, entityType, 1, forma_canonica, fecha, recurrente);
                    } else if (entityType.equalsIgnoreCase("titulo") && tema.equalsIgnoreCase("cine")) {
                        String ID = "TODO_ID";
                        String forma_canonica = "TODO_CANONIC_FORM";
                        String profesional = "TODO_PROFESIONAL";
                        entity = new TituloDeCine(word, entityType, 1, ID, forma_canonica, profesional);
                    } else if (entityType.equalsIgnoreCase("evento") && tema.equalsIgnoreCase("musica")) {
                        String forma_canonica = "TODO_CANONIC_FORM";
                        String fecha = "TODO_DATE";
                        String recurrente = "TODO_RECURRENTE";
                        entity = new EventoDeMusica(word, entityType, 1, forma_canonica, fecha, recurrente);
                    } else if (entityType.equalsIgnoreCase("N/C") || tema.equalsIgnoreCase("N/C")) {
                        entity = new EntidadSinCategoria(word, 1);
                    } else {
                        entity = new NamedEntity(word, entityType, 1);
                    }
                    this.namedEntityList.add(entity);
                } else {
                    ne.incFrequency();
                }
            }
        }
    }


    public void prettyPrint() {
        System.out.println("**********************************************************************************************");
        System.out.println("Title: " + this.getTitle());
        System.out.println("Publication Date: " + this.getPublicationDate());
        System.out.println("Link: " + this.getLink());
        System.out.println("Text: " + this.getText());
        // si estan calculadas la entidades, printeamos cada nombre
        if (this.namedEntityListCalculated) {
            System.out.println("Heuristica: " + this.getHeuristic());
            System.out.println("Entidades nombradas:");
            for (NamedEntity entity : this.namedEntityList)
                entity.prettyPrint();
        }
        System.out.println("**********************************************************************************************");

    }


}



