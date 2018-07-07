package test.certant.ar.pokedexlite.dao;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import test.certant.ar.pokedexlite.beans.Pokemon;
import test.certant.ar.pokedexlite.beans.PokemonEvolution;

public class PokemonDao {

    private PokemonEvolutionDao pokemonEvolutionDao;

    public PokemonDao() {
        super();
        pokemonEvolutionDao = new PokemonEvolutionDao();
    }

    public List<Pokemon> list(Context context) {
        List<Pokemon> pokemons = new ArrayList<>();
        try {
            Log.i("hello2",DaoFactory.loadPokedex(context));
            JSONObject pokedex = new JSONObject(DaoFactory.loadPokedex(context));
            JSONArray pokemonsJson = pokedex.getJSONObject("pokedex").getJSONArray("pokemon");
            for(int i = 0 ; i<pokemonsJson.length() ; i++) {
                Pokemon pokemon = new Pokemon();
                pokemon.setCurrentLevel(pokemonsJson.getJSONObject(i).getInt("currentLevel"));

                JSONArray evolutionsJson = pokemonsJson.getJSONObject(i).getJSONArray("evolution");
                pokemon.setEvolutions(pokemonEvolutionDao.list(evolutionsJson));
                pokemons.add(chooseEvolution(pokemon));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pokemons;
    }

    private Pokemon chooseEvolution(Pokemon pokemon) {
        boolean evolutionFound = false;
        List<PokemonEvolution> evolutions = pokemon.getEvolutions();
        int index = 0;
        while(!evolutionFound && index < evolutions.size()) {
            if(pokemon.getCurrentLevel() <= evolutions.get(index).getRequiredLevel()) {
                pokemon.setName(evolutions.get(index).getName());
                evolutionFound = true;
            }
            index++;
        }
        return pokemon;
    }
}
