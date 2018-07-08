package test.certant.ar.pokedexlite.beans;

import java.util.ArrayList;
import java.util.List;

public class Pokemon extends Creature{

    private List<PokemonEvolution> evolutions;

    private Integer currentLevel;

    public Pokemon() {
        super();
        evolutions = new ArrayList<>();
    }

    public List<PokemonEvolution> getEvolutions() {
        return evolutions;
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
