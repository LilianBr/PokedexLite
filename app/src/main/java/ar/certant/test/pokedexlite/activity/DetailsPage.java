package ar.certant.test.pokedexlite.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ar.certant.test.pokedexlite.R;
import ar.certant.test.pokedexlite.activity.adapter.EvolutionsAdapter;
import ar.certant.test.pokedexlite.activity.fragment.DetailPokemon;
import ar.certant.test.pokedexlite.beans.Pokemon;
import ar.certant.test.pokedexlite.beans.PokemonEvolution;
import ar.certant.test.pokedexlite.dao.DaoFactory;
import ar.certant.test.pokedexlite.dao.PokemonDao;

public class DetailsPage extends AppCompatActivity {

    public static final String ARG_POKEMON_NAME = "pokemonName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        // Fetching the selected pokemon (being the current pokemon)
        String pokemonName = getIntent().getStringExtra(ARG_POKEMON_NAME);
        Pokemon pokemonToFetch = new Pokemon();
        pokemonToFetch.setName(pokemonName);
        final List<Pokemon> pokemons = PokemonDao.list(this.getApplicationContext());
        final int indexCurrentPokemon = PokemonDao.findByName(pokemons, pokemonToFetch);

        // Initialize each components
        if (indexCurrentPokemon > 0) {

            final Pokemon currentPokemon = pokemons.get(indexCurrentPokemon);

            final EditText nameEditText = findViewById(R.id.name);
            nameEditText.setText(currentPokemon.getName());
            final EditText levelEditText = findViewById(R.id.level);
            levelEditText.setText(String.valueOf(currentPokemon.getCurrentLevel()));

            initializeEvolutionsList(currentPokemon);
            initializeAbilitiesList(currentPokemon);
            initializeSaveDetailsButton(pokemons, indexCurrentPokemon, nameEditText, levelEditText);
        }
    }

    private void initializeSaveDetailsButton(final List<Pokemon> pokemons,
                                             final int indexCurrentPokemon,
                                             final EditText nameEditText,
                                             final EditText levelEditText) {

        DetailPokemon fragment = (DetailPokemon) getSupportFragmentManager().findFragmentById(R.id.detailsFragment);
        if (fragment != null && fragment.getView() != null) {
            final Button saveDetails = fragment.getView().findViewById(R.id.saveDetails);
            saveDetails.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String newLevelString = levelEditText.getText().toString();
                    // Case: Level between 0 and 100
                    if (!newLevelString.isEmpty() && Integer.valueOf(newLevelString) <= 100) {
                        Pokemon currentPokemon = pokemons.get(indexCurrentPokemon);
                        currentPokemon.setCurrentLevel(Integer.valueOf(newLevelString));
                        currentPokemon.setEvolutions(
                                modifyNameEvolution(currentPokemon, nameEditText.getText().toString()));
                        pokemons.set(indexCurrentPokemon, currentPokemon);
                        // Rewrite the pokedex file with the changes
                        DaoFactory.loadPokemons(saveDetails.getContext(), PokemonDao.toJson(pokemons).toString().getBytes());
                    } else {
                        Toast.makeText(DetailsPage.this, "The level should be between 0 and 100", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void initializeAbilitiesList(Pokemon currentPokemon) {
        final ArrayAdapter<String> abilitiesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, currentPokemon.getAbilities());
        final ListView abilitiesView = findViewById(R.id.abilities);
        abilitiesView.setAdapter(abilitiesAdapter);
    }

    private void initializeEvolutionsList(Pokemon currentPokemon) {
        List<PokemonEvolution> evolutions = currentPokemon.getPossibleEvolutions();
        EvolutionsAdapter evolutionsAdapter = new EvolutionsAdapter(this, evolutions);
        final ListView evolutionsView = findViewById(R.id.evolutions);
        evolutionsView.setAdapter(evolutionsAdapter);
    }

    private List<PokemonEvolution> modifyNameEvolution(Pokemon pokemon, String newName) {
        List<PokemonEvolution> evolutions = pokemon.getEvolutions();
        int evolutionIndex = 0;
        for (int index = 0; index < evolutions.size(); index++) {
            if (pokemon.getCurrentLevel() >= evolutions.get(index).getRequiredLevel()) {
                evolutionIndex = index;
            }
        }
        evolutions.get(evolutionIndex).setName(newName);
        return evolutions;
    }
}
