package test.certant.ar.pokedexlite.beans;

public class PokemonEvolution extends Creature{

    private Integer requiredLevel;

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
