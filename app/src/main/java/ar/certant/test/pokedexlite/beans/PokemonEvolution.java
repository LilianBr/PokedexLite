package ar.certant.test.pokedexlite.beans;

public class PokemonEvolution extends Creature{

    private Integer requiredLevel = 0;

    public PokemonEvolution() {
        super();
    }

    public Integer getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(Integer requiredLevel) {
        this.requiredLevel = requiredLevel;
    }
}
