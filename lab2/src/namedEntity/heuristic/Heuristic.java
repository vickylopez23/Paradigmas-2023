package namedEntity.heuristic;

import java.util.HashMap;
import java.util.Map;
import utility.Tuple;
public abstract class Heuristic {

    private static Map<String, Tuple<String, String>> categoryMap = new HashMap<>();

    static {
        categoryMap.put("Microsft", new Tuple<>("Company", "Electronic"));
        categoryMap.put("Apple", new Tuple<>("Company", "Electronic"));
        categoryMap.put("Google", new Tuple<>("Company", "Electronic"));
        categoryMap.put("Musk", new Tuple<>("Nombre", "Cine"));
        categoryMap.put("Biden", new Tuple<>("Titulo","Nacional"));
        categoryMap.put("Trump", new Tuple<>("Nombre","Nacional"));
        categoryMap.put("Messi", new Tuple<>("Apellido","Futbol"));
        categoryMap.put("Federer", new Tuple<>("Apellido","Tenis"));
        categoryMap.put("USA", new Tuple<>("Pais","Nacional"));
        categoryMap.put("Russia", new Tuple<>("Pais","Nacional"));
        categoryMap.put("Amazon", new Tuple<>("Company","Electronic"));
        categoryMap.put("Tesla", new Tuple<>("Company","Electronic"));
        categoryMap.put("Ford", new Tuple<>("Company","Nacional"));
        categoryMap.put("Coca-Cola", new Tuple<>("Company","InterNacional"));
        categoryMap.put("McDonald's", new Tuple<>("Company","InterNacional"));
        categoryMap.put("Nike", new Tuple<>("Company","InterNacional"));
        categoryMap.put("Sony", new Tuple<>("Titulo","Nacional"));
        categoryMap.put("IBM", new Tuple<>("Company","Electronic"));
        categoryMap.put("Samsung", new Tuple<>("Nombre","InterNacional"));
        categoryMap.put("Oracle", new Tuple<>("Company","Electronic"));
        categoryMap.put("HP", new Tuple<>("Company","InterNacional"));
        categoryMap.put("Facebook", new Tuple<>("Nombre","InterNacional"));
        categoryMap.put("Twitter", new Tuple<>("Nombre","InterNacional"));
        categoryMap.put("Uber", new Tuple<>("Company","Nacional"));
        categoryMap.put("Elon Musk", new Tuple<>("Nombre","Cine"));
        categoryMap.put("Jeff Bezos", new Tuple<>("Nombre","Cine"));
        categoryMap.put("Bill Gates", new Tuple<>("Nombre","Musica"));
        categoryMap.put("Mark Zuckerberg", new Tuple<>("Nombre","Cine"));
        categoryMap.put("Warren Buffett", new Tuple<>("Nombre","Musica"));
        categoryMap.put("Oprah Winfrey", new Tuple<>("Apellido","Musica"));
        categoryMap.put("Queen Elizabeth II", new Tuple<>("Titulo","Musica"));
        categoryMap.put("Barack Obama", new Tuple<>("Nombre","InterNacional"));
        categoryMap.put("Vladimir Putin", new Tuple<>("Nombre","InterNacional"));
        categoryMap.put("Angela Merkel", new Tuple<>("Nombre","InterNacional"));
        categoryMap.put("Emmanuel Macron", new Tuple<>("Apellido","InterNacional"));
        categoryMap.put("Xi Jinping", new Tuple<>("Apellido","InterNacional"));
        categoryMap.put("Joe Biden", new Tuple<>("Nombre","Nacional"));
        categoryMap.put("Donald Trump", new Tuple<>("Nombre","Nacional"));
        categoryMap.put("Cristiano Ronaldo", new Tuple<>("Apellido","Futbol"));
        categoryMap.put("Selena Gomez", new Tuple<>("Apellido","Musica"));
        categoryMap.put("Roger Federer", new Tuple<>("Apellido","Tenis"));
        categoryMap.put("Simone Biles", new Tuple<>("Apellido","Tenis"));
        categoryMap.put("Usain Bolt", new Tuple<>("Apellido","Futbol"));
        categoryMap.put("USA", new Tuple<>("Pais","Nacional"));
        categoryMap.put("Russia", new Tuple<>("Pais","InterNacional"));
        categoryMap.put("China", new Tuple<>("Pais","InterNacional"));
        categoryMap.put("India", new Tuple<>("Pais","InterNacional"));
        categoryMap.put("United Kingdom", new Tuple<>("Pais","InterNacional"));
        categoryMap.put("France", new Tuple<>("Pais","InterNacional"));
        categoryMap.put("Germany", new Tuple<>("Pais","InterNacional"));
        categoryMap.put("Italy", new Tuple<>("Pais","InterNacional"));
        categoryMap.put("Spain", new Tuple<>("Pais","InterNacional"));
        categoryMap.put("Japan", new Tuple<>("Pais","InterNacional"));
        categoryMap.put("Canada", new Tuple<>("Pais","InterNacional"));
        categoryMap.put("Mexico", new Tuple<>("Pais","InterNacional"));
        categoryMap.put("Brazil", new Tuple<>("Pais","InterNacional"));
        categoryMap.put("Australia", new Tuple<>("Pais","InterNacional"));
        categoryMap.put("New Zealand", new Tuple<>("Pais","InterNacional"));
        categoryMap.put("iPhone", new Tuple<>("Titulo", "Otros"));
        categoryMap.put("iPad", new Tuple<>("Producto", "Internacional"));
        categoryMap.put("MacBook", new Tuple<>("Producto", "Internacional"));
        categoryMap.put("Windows",  new Tuple<>("Producto", "Internacional"));
        categoryMap.put("Xbox", new Tuple<>("Producto", "Internacional"));

    }


    public Tuple<String, String> getCategory(String entity) {
        Tuple<String, String> cat = categoryMap.get(entity);
        return cat == null ? new Tuple<>("N/C", "N/C") : cat;
    }

    public abstract String getHeuristicName();
    public abstract boolean isEntity(String word);

}
