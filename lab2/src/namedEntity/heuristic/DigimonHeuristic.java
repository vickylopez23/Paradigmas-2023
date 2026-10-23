package namedEntity.heuristic;

public class DigimonHeuristic extends Heuristic {

    public String getHeuristicName() {
        return "DigimonHeuristic";
    }

    // comprobara si la entidad es un digimon o no
    public boolean isEntity(String word) {
        return word.endsWith("mon") && word.substring(0, 1).compareTo(word.substring(0, 1).toUpperCase()) == 0;
    }
}
