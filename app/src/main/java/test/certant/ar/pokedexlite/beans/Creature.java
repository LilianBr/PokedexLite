package test.certant.ar.pokedexlite.beans;

import java.util.ArrayList;
import java.util.List;

public class Creature {

    private String name;
    private List<String> abilities;

    protected Creature() {
        abilities = new ArrayList<>();
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
