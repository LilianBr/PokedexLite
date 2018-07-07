package test.certant.ar.pokedexlite.dao;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import test.certant.ar.pokedexlite.beans.PokemonEvolution;

public class PokemonEvolutionDao {

    public List<PokemonEvolution> list(JSONArray evolutionsJson) {
        List<PokemonEvolution> evolutions = new ArrayList<>();
        try {
            for(int i = 0 ; i<evolutionsJson.length() ; i++) {
                PokemonEvolution evolution = new PokemonEvolution();
                evolution.setName(evolutionsJson.getJSONObject(i).getString("name"));
                evolution.setRequiredLevel(evolutionsJson.getJSONObject(i).getInt("requiredLevel"));
                JSONArray abilitiesJson = evolutionsJson.getJSONObject(i).getJSONArray("abilities");
                List<String> abilities = new ArrayList<>();
                for (int j = 0 ; j < abilitiesJson.length() ; j++) {
                     abilities.add(abilitiesJson.getString(j));
                }
                evolution.setAbilities(abilities);
                evolutions.add(evolution);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return evolutions;
    }
}
