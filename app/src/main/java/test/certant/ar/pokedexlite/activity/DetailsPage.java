package test.certant.ar.pokedexlite.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        // Fetching the selected pokemon
        String pokemonName = getIntent().getStringExtra(ARG_POKEMON_NAME);
        Pokemon pokemonToFetch = new Pokemon();
        pokemonToFetch.setName(pokemonName);
        final List<Pokemon> pokemons = PokemonDao.list(this.getApplicationContext());
        final int indexCurrentPokemon = PokemonDao.findByName(pokemons, pokemonToFetch);

        if (indexCurrentPokemon != -1) {

            final Pokemon currentPokemon = pokemons.get(indexCurrentPokemon);

            // Complete the pokemon details
            final EditText nameEditText = findViewById(R.id.name);
            nameEditText.setText(currentPokemon.getName());
            final EditText levelEditText = findViewById(R.id.level);
            levelEditText.setText(String.valueOf(currentPokemon.getCurrentLevel()));
            final ListView evolutionsView = initializeEvolutionsList(currentPokemon);

            initializeAbilitiesList(currentPokemon);
            initializeSaveDetailsButton(pokemons, indexCurrentPokemon, currentPokemon, nameEditText, levelEditText, evolutionsView);
        }
    }

    private void initializeSaveDetailsButton(final List<Pokemon> pokemons,
                                             final int indexCurrentPokemon,
                                             final Pokemon currentPokemon,
                                             final EditText nameEditText,
                                             final EditText levelEditText,
                                             final ListView evolutionsView) {
        DetailPokemon fragment = (DetailPokemon) getSupportFragmentManager().findFragmentById(R.id.detailsFragment);
        final Button saveDetails = fragment.getView().findViewById(R.id.saveDetails);
        saveDetails.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentPokemon.setCurrentLevel(Integer.valueOf(levelEditText.getText().toString()));
                ;
                currentPokemon.setEvolutions(
                        modifyNameEvolution(currentPokemon, nameEditText.getText().toString()));
                pokemons.set(indexCurrentPokemon, currentPokemon);
                String debug = PokemonDao.toJson(pokemons).toString();
                DaoFactory.loadPokemons(evolutionsView.getContext(), debug.getBytes());
            }
        });
    }

    private void initializeAbilitiesList(Pokemon currentPokemon) {
        // Complete the abilities list
        final ArrayAdapter<String> abilitiesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, currentPokemon.getAbilities());
        final ListView abilitiesView = findViewById(R.id.abilities);
        abilitiesView.setAdapter(abilitiesAdapter);
    }

    @NonNull
    private ListView initializeEvolutionsList(Pokemon currentPokemon) {
        // Complete the evolutions list
        List<PokemonEvolution> evolutions = currentPokemon.getEvolutions();
        EvolutionsAdapter evolutionsAdapter = new EvolutionsAdapter(this, evolutions);
        final ListView evolutionsView = findViewById(R.id.evolutions);
        evolutionsView.setAdapter(evolutionsAdapter);
        return evolutionsView;
    }

    private List<PokemonEvolution> modifyNameEvolution(Pokemon pokemon, String newName) {
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
        return evolutions;
    }
}
