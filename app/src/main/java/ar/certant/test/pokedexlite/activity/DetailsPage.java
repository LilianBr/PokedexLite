package ar.certant.test.pokedexlite.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

/**
 * Pokemon Details Activity
 * Display the selected pokemon (name, level, abilities and possible evolutions) into 3 fragments (DetailPokemon, Abilities and Evolutions)
 */
public class DetailsPage extends AppCompatActivity {

    public static final String ARG_POKEMON_NAME = "pokemonName";

    /**
     * Initialize all components of the activity
     * Get the selected pokemon with the param pokemonName (ARG_POKEMON_NAME)
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);

        // Finding the selected pokemon (being the current pokemon)
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

    /**
     * Initialize the button "Save" in the activity
     * On click: get the new parameter, display an Toast if the level is wrong (ouf of 0-100)
     * and save the changes in the pokedex file by using DaoFactory.loadPokemons()
     *
     * @param pokemons            List of pokemons
     * @param indexCurrentPokemon Index of the pokemons in the parameter pokemons
     * @param newNameEdit         Name of the pokemon typed in the activity
     * @param newLevelEdit        Level typed in the activity
     */
    private void initializeSaveDetailsButton(final List<Pokemon> pokemons,
                                             final int indexCurrentPokemon,
                                             final EditText newNameEdit,
                                             final EditText newLevelEdit) {

        DetailPokemon fragment = (DetailPokemon) getSupportFragmentManager().findFragmentById(R.id.detailsFragment);
        if (fragment != null && fragment.getView() != null) {
            final Button saveDetails = fragment.getView().findViewById(R.id.saveDetails);
            saveDetails.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String newLevel = newLevelEdit.getText().toString();
                    // Case: Level between 0 and 100
                    if (!newLevel.isEmpty() && Integer.valueOf(newLevel) <= 100) {
                        Pokemon currentPokemon = pokemons.get(indexCurrentPokemon);
                        currentPokemon.setCurrentLevel(Integer.valueOf(newLevel));
                        currentPokemon.setEvolutions(
                                modifyNameEvolution(currentPokemon, newNameEdit.getText().toString()));
                        pokemons.set(indexCurrentPokemon, currentPokemon);
                        // Rewrite the pokedex file with the changes
                        Log.i("debug", newLevel);
                        DaoFactory.loadPokemons(saveDetails.getContext(), PokemonDao.toJson(pokemons).toString().getBytes());
                    } else {
                        Toast.makeText(DetailsPage.this, "The level should be between 0 and 100", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Initialize the Fragment Abilities (List of abilities)
     * @param currentPokemon Selected pokemon
     */
    private void initializeAbilitiesList(Pokemon currentPokemon) {
        final ArrayAdapter<String> abilitiesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, currentPokemon.getAbilities());
        final ListView abilitiesView = findViewById(R.id.abilities);
        abilitiesView.setAdapter(abilitiesAdapter);
    }

    /**
     * Initialize the Fragment Evolutions (List of possible evolutions)
     * @param currentPokemon Selected pokemon
     */
    private void initializeEvolutionsList(Pokemon currentPokemon) {
        List<PokemonEvolution> evolutions = currentPokemon.getPossibleEvolutions();
        EvolutionsAdapter evolutionsAdapter = new EvolutionsAdapter(this, evolutions);
        final ListView evolutionsView = findViewById(R.id.evolutions);
        evolutionsView.setAdapter(evolutionsAdapter);
    }

    /**
     * Modify the name of the current evolution
     * @param pokemon Pokemon
     * @param newName New name for the current evolution
     * @return List of evolutions with the name evolution changed
     */
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
