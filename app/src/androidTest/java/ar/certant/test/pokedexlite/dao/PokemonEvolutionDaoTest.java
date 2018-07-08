package ar.certant.test.pokedexlite.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ar.certant.test.pokedexlite.beans.PokemonEvolution;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PokemonEvolutionDaoTest {

    private static final int NUMBER_EVOLUTIONS = 3;
    private static final int NUMBER_ABILITIES = 2;
    private static final Integer REQUIRED_LEVEL = 0;
    private static final String NAME_1 = "Bulbasaur";
    private static final String NAME_2 = "Ivysaur";
    private static final String NAME_3 = "Venusaur";
    private Context appContext;

    @Before
    public void setUpBefore() {
        appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void list() throws JSONException {
        DaoFactory.loadPokemons(appContext, DaoFactory.loadPokemonsFile(appContext).getBytes());
        JSONObject pokedex = new JSONObject(DaoFactory.loadPokedex(appContext));
        JSONArray pokemonsJson = pokedex.getJSONObject("pokedex").getJSONArray("pokemon");
        JSONArray evolutionsJson = pokemonsJson.getJSONObject(0).getJSONArray("evolution");
        List<PokemonEvolution> pokemonEvolutions = PokemonEvolutionDao.list(evolutionsJson);

        assertEquals(NUMBER_EVOLUTIONS, pokemonEvolutions.size());

        PokemonEvolution pokemonEvolutionTested = pokemonEvolutions.get(0);
        assertEquals(NUMBER_ABILITIES, pokemonEvolutionTested.getAbilities().size());
        assertEquals(REQUIRED_LEVEL, pokemonEvolutionTested.getRequiredLevel());
        assertEquals(NAME_1, pokemonEvolutionTested.getName());

        assertEquals(NAME_2, pokemonEvolutions.get(1).getName());
        assertEquals(NAME_3, pokemonEvolutions.get(2).getName());
    }
}
