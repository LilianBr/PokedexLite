package ar.certant.test.pokedexlite.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ar.certant.test.pokedexlite.beans.PokemonEvolution;

public class PokemonEvolutionDao {

    public static List<PokemonEvolution> list(JSONArray evolutionsJson) {
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
            evolutions = new ArrayList<>();
        }
        return evolutions;
    }


    public static JSONArray toJson(List<PokemonEvolution> evolutions) throws JSONException {
        JSONArray evolutionsJson = new JSONArray();
        for (PokemonEvolution evolution : evolutions) {
            JSONObject evolutionJson = new JSONObject();
            evolutionJson.put("name", evolution.getName());
            evolutionJson.put("requiredLevel", evolution.getRequiredLevel());

            // Abilities Array
            JSONArray abilities = new JSONArray();
            for (String ability : evolution.getAbilities()) {
                abilities.put(ability);
            }
            evolutionJson.put("abilities", abilities);

            evolutionsJson.put(evolutionJson);
        }
        return evolutionsJson;
    }
}
