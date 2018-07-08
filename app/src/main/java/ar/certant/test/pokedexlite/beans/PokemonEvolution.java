package ar.certant.test.pokedexlite.beans;

public class PokemonEvolution extends Creature{

    private Integer requiredLevel;

    public PokemonEvolution() {
        super();
        requiredLevel = 0;
    }

    public Integer getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(Integer requiredLevel) {
        this.requiredLevel = requiredLevel;
    }
}
