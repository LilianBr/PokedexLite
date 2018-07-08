package test.certant.ar.pokedexlite.beans;

import android.util.Log;

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

    public String getAbilitiesString() {
        String abilitiesString = "None ability";
        if (!abilities.isEmpty()) {
            StringBuilder abilitiesBuilder = new StringBuilder();
            String separator = " - ";
            Log.i("debug", "starting abilities");
            for (String ability : getAbilities()) {
                Log.i("debug", ability);
                abilitiesBuilder.append(ability);
                abilitiesBuilder.append(separator);
            }
            int indexSeparator = abilitiesBuilder.lastIndexOf(separator);
            abilitiesBuilder.replace(indexSeparator, indexSeparator + separator.length(), "");
            abilitiesString = abilitiesBuilder.toString();
        }
        return abilitiesString;
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
