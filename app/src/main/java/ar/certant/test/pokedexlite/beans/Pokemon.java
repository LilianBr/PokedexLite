package ar.certant.test.pokedexlite.beans;

import java.util.ArrayList;
import java.util.List;

public class Pokemon extends Creature{

    private List<PokemonEvolution> evolutions;

    private Integer currentLevel;

    public Pokemon() {
        super();
        evolutions = new ArrayList<>();
        currentLevel = 0;
    }

    public List<PokemonEvolution> getEvolutions() {
        return evolutions;
    }

    /**
     * Select evolutions with a required level superior or equal to the current level
     *
     * @return List of the possible evolutions according to the current level of the pokemon
     */
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
