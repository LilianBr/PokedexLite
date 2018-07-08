package test.certant.ar.pokedexlite.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import test.certant.ar.pokedexlite.R;
import test.certant.ar.pokedexlite.activity.adapter.EvolutionsAdapter;
import test.certant.ar.pokedexlite.activity.fragment.DetailPokemon;
import test.certant.ar.pokedexlite.beans.Pokemon;
import test.certant.ar.pokedexlite.beans.PokemonEvolution;
import test.certant.ar.pokedexlite.dao.DaoFactory;
import test.certant.ar.pokedexlite.dao.PokemonDao;

public class DetailsPage extends AppCompatActivity {

    public static final String ARG_POKEMON_NAME = "pokemonName";

    PokemonDao pokemonDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        String pokemonName = getIntent().getStringExtra(ARG_POKEMON_NAME);
        pokemonDao = new PokemonDao();
        Pokemon pokemonToFetch = new Pokemon();
        pokemonToFetch.setName(pokemonName);
        final List<Pokemon> pokemons = pokemonDao.list(this.getApplicationContext());
        final int indexCurrentPokemon = pokemonDao.findByName(pokemons, pokemonToFetch);
        if (indexCurrentPokemon != -1) {

            final Pokemon currentPokemon = pokemons.get(indexCurrentPokemon);

            // Complete the pokemon details
            DetailPokemon fragment = (DetailPokemon) getSupportFragmentManager().findFragmentById(R.id.detailsFragment);
            final EditText nameEditText = findViewById(R.id.name);
            nameEditText.setText(currentPokemon.getName());
            final EditText levelEditText = findViewById(R.id.level);
            levelEditText.setText(String.valueOf(currentPokemon.getCurrentLevel()));

            // Complete the evolutions list
            List<PokemonEvolution> evolutions = currentPokemon.getEvolutions();
            EvolutionsAdapter evolutionsAdapter = new EvolutionsAdapter(this, evolutions);
            final ListView evolutionsView = findViewById(R.id.evolutions);
            evolutionsView.setAdapter(evolutionsAdapter);

            // Complete the abilities list
            final ArrayAdapter<String> abilitiesAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, currentPokemon.getAbilities());
            final ListView abilitiesView = findViewById(R.id.abilities);
            abilitiesView.setAdapter(abilitiesAdapter);

            //Add saving details
            final Button saveDetails = fragment.getView().findViewById(R.id.saveDetails);
            saveDetails.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    currentPokemon.setCurrentLevel(Integer.valueOf(levelEditText.getText().toString()));
                    modifyNameEvolution(currentPokemon, nameEditText.getText().toString());
                    pokemons.set(indexCurrentPokemon, currentPokemon);
                    String debug = pokemonDao.toJson(pokemons).toString();
                    DaoFactory.loadPokemons(evolutionsView.getContext(), debug.getBytes());
                }
            });
        }
    }

    private Pokemon modifyNameEvolution(Pokemon pokemon, String newName) {
        boolean evolutionFound = false;
        List<PokemonEvolution> evolutions = pokemon.getEvolutions();
        int index = 0;
        while (!evolutionFound && index < evolutions.size()) {
            if (pokemon.getCurrentLevel() >= evolutions.get(index).getRequiredLevel()) {
                evolutions.get(index).setName(newName);
                evolutionFound = true;
            }
            index++;
        }
        return pokemon;
    }
}
