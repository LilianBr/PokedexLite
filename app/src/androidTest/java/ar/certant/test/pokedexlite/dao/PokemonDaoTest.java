package ar.certant.test.pokedexlite.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ar.certant.test.pokedexlite.beans.Pokemon;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PokemonDaoTest {
    private static final int NUMBER_POKEMONS = 10;
    private static final int NUMBER_ABILITIES = 1;
    private static final Integer LEVEL = 32;
    private static final String NAME_1 = "Bulbasaur";
    private static final String NAME_5 = "Pikachu";
    private static final String NAME_10 = "Mew";
    private Context appContext;

    @Before
    public void setUpBefore() {
        appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void list() {
        DaoFactory.loadPokemons(appContext, DaoFactory.loadPokemonsFile(appContext).getBytes());
        List<Pokemon> pokemons = PokemonDao.list(appContext);

        assertEquals(NUMBER_POKEMONS, pokemons.size());
        assertEquals(NAME_1, pokemons.get(0).getName());
        assertEquals(NAME_5, pokemons.get(4).getName());
        assertEquals(NAME_10, pokemons.get(9).getName());
    }

    @Test
    public void findByName() {
        DaoFactory.loadPokemons(appContext, DaoFactory.loadPokemonsFile(appContext).getBytes());
        Pokemon pokemonToFetch = new Pokemon();
        pokemonToFetch.setName(NAME_5);
        List<Pokemon> pokemons = PokemonDao.list(appContext);
        int indexPokemonFound = PokemonDao.findByName(pokemons, pokemonToFetch);
        Pokemon pokemonFound = pokemons.get(indexPokemonFound);
        assertEquals(NUMBER_ABILITIES, pokemonFound.getAbilities().size());
        assertEquals(NAME_5, pokemonFound.getName());
        assertEquals(LEVEL, pokemonFound.getCurrentLevel());
    }
}
