package ar.certant.test.pokedexlite.dao;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ar.certant.test.pokedexlite.beans.Pokemon;
import ar.certant.test.pokedexlite.beans.PokemonEvolution;

/**
 * DAO of the pokemon entity
 */
public class PokemonDao {

    private static final String JSON_TAG_POKEDEX = "pokedex";
    private static final String JSON_TAG_POKEMEN = "pokemon";
    private static final String JSON_TAG_CURRENT_LEVEL = "currentLevel";
    private static final String JSON_TAG_EVOLUTION = "evolution";

    /**
     * List all pokemons available in the pokedex
     *
     * @param context Current context
     * @return List of pokemons
     */
    public static List<Pokemon> list(Context context) {
        List<Pokemon> pokemons = new ArrayList<>();
        try {
            JSONObject pokedex = new JSONObject(DaoFactory.loadPokedex(context));
            JSONArray pokemonsJson = pokedex.getJSONObject(JSON_TAG_POKEDEX).getJSONArray(JSON_TAG_POKEMEN);
            for (int i = 0; i < pokemonsJson.length(); i++) {
                Pokemon pokemon = new Pokemon();
                pokemon.setCurrentLevel(pokemonsJson.getJSONObject(i).getInt(JSON_TAG_CURRENT_LEVEL));

                JSONArray evolutionsJson = pokemonsJson.getJSONObject(i).getJSONArray(JSON_TAG_EVOLUTION);
                pokemon.setEvolutions(PokemonEvolutionDao.list(evolutionsJson));
                pokemons.add(chooseEvolution(pokemon));
            }
        } catch (Exception e) {
            pokemons = new ArrayList<>();
        }
        return pokemons;
    }

    /**
     * Choose the pokemon evolution regarding to the current level
     *
     * @param pokemon
     * @return Pokemon with the name and the abilities of its evolution
     */
    private static Pokemon chooseEvolution(Pokemon pokemon) {
        List<PokemonEvolution> evolutions = pokemon.getEvolutions();
        // Default evolution
        PokemonEvolution pokemonEvolutionFound = new PokemonEvolution();
        pokemonEvolutionFound.setName("Egg");
        pokemonEvolutionFound.setAbilities(new ArrayList<String>());

        for (PokemonEvolution evolution : evolutions) {
            if (pokemon.getCurrentLevel() >= evolution.getRequiredLevel()) {
                pokemonEvolutionFound = evolution;
            }
        }
        pokemon.setName(pokemonEvolutionFound.getName());
        pokemon.setAbilities(pokemonEvolutionFound.getAbilities());
        return pokemon;
    }

    /**
     * Find the pokemon in the list by name
     * @param pokemons List of pokemons
     * @param pokemon Pokemon to fetch
     * @return Index of the found pokemon the list (return -1 if not found)
     */
    public static int findByName(List<Pokemon> pokemons, Pokemon pokemon) {
        boolean pokemonFound = false;
        int index = -1;
        while (!pokemonFound && (index + 1) < pokemons.size()) {
            index++;
            if (pokemons.get(index).getName().equals(pokemon.getName())) {
                pokemonFound = true;
            }
        }
        return index;
    }

    /**
     * Build a JSON object with all pokemons
     * The JSON format is the same than res/raw/pokemons.json
     * @param pokemons List of pokemons
     * @return Json object
     */
    public static JSONObject toJson(List<Pokemon> pokemons) {
        JSONObject rootJson = new JSONObject();
        JSONObject pokedexJson = new JSONObject();
        JSONArray pokemonsJson = new JSONArray();
        try {
            for (Pokemon pokemon : pokemons) {
                JSONObject pokemonJson = new JSONObject();
                pokemonJson.put(JSON_TAG_CURRENT_LEVEL, pokemon.getCurrentLevel());
                pokemonJson.put(JSON_TAG_EVOLUTION, PokemonEvolutionDao.toJson(pokemon.getEvolutions()));
                pokemonsJson.put(pokemonJson);
            }
            pokedexJson.put(JSON_TAG_POKEMEN, pokemonsJson);
            rootJson.put(JSON_TAG_POKEDEX, pokedexJson);
        } catch (JSONException e) {
            rootJson = new JSONObject();
        }
        return rootJson;
    }
}
