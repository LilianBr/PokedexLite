package test.certant.ar.pokedexlite.beans;

import java.util.ArrayList;
import java.util.List;

public class Pokemon extends Creature{

    private List<PokemonEvolution> evolutions;

    private Integer currentLevel = 0;

    public Pokemon() {
        super();
        evolutions = new ArrayList<>();
    }

    public List<PokemonEvolution> getEvolutions() {
        return evolutions;
    }

    public List<PokemonEvolution> getPossibleEvolutions() {
        List<PokemonEvolution> possibleEvolutions = new ArrayList<>();
        for (PokemonEvolution evolution : getEvolutions()) {
            if (currentLevel < evolution.getRequiredLevel()) {
                possibleEvolutions.add(evolution);
            }
        }
        return possibleEvolutions;
    }

    public void setEvolutions(List<PokemonEvolution> evolutions) {
        this.evolutions = evolutions;
    }

    public Integer getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Integer currentLevel) {
        this.currentLevel = currentLevel;
    }
}
