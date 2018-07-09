package ar.certant.test.pokedexlite.dao;

import android.content.Context;
import android.util.Log;

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
            JSONArray pokemonsJson = pokedex.getJSONObject("pokedex").getJSONArray("pokemon");
            for (int i = 0; i < pokemonsJson.length(); i++) {
                Pokemon pokemon = new Pokemon();
                pokemon.setCurrentLevel(pokemonsJson.getJSONObject(i).getInt("currentLevel"));

                JSONArray evolutionsJson = pokemonsJson.getJSONObject(i).getJSONArray("evolution");
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
        Log.i("debug", String.valueOf(pokemons.size()));
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
                pokemonJson.put("currentLevel", pokemon.getCurrentLevel());
                pokemonJson.put("evolution", PokemonEvolutionDao.toJson(pokemon.getEvolutions()));
                pokemonsJson.put(pokemonJson);
            }
            pokedexJson.put("pokemon", pokemonsJson);
            rootJson.put("pokedex", pokedexJson);
        } catch (JSONException e) {
            rootJson = new JSONObject();
        }
        return rootJson;
    }
}
