package ar.certant.test.pokedexlite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ar.certant.test.pokedexlite.R;
import ar.certant.test.pokedexlite.activity.adapter.PokemonAdapter;
import ar.certant.test.pokedexlite.beans.Pokemon;
import ar.certant.test.pokedexlite.dao.DaoFactory;
import ar.certant.test.pokedexlite.dao.PokemonDao;

/**
 * Home Activity
 * Display a list of the lastest pokemons
 */
public class Home extends AppCompatActivity {

    private static final String ARG_POKEMON_NAME = "pokemonName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initialize();
    }

    /**
     * Initialize all components of the view
     */
    private void initialize() {
        // Initialization of the pokemon.json in the device
        File file = new File(this.getFilesDir(), DaoFactory.fileName);
        try {
            if (file.createNewFile()) {
                DaoFactory.loadPokemons(this.getApplicationContext(), DaoFactory.loadPokemonsFile(this.getApplicationContext()).getBytes());
            }
        } catch (IOException e) {
            // Empty
        }
        final List<Pokemon> pokemons = PokemonDao.list(this.getApplicationContext());

        initializePokemonsList(pokemons);
        initializeRefreshLayout();
    }

    /**
     * Initialize the layout to refresh by swiping
     * Refresh means reload the list of pokemons from the file in the internal storage
     */
    private void initializeRefreshLayout() {
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        final List<Pokemon> pokemons = PokemonDao.list(getApplicationContext());
                        initializePokemonsList(pokemons);
                    }
                }
        );
    }

    /**
     * Initialize the ListView of the latest pokemons and stop the refreshing
     * This ListView uses the adapter PokemonAdapter
     *
     * @param pokemons List of displayed pokemons
     */
    private void initializePokemonsList(final List<Pokemon> pokemons) {
        final ListView pokemonsView = findViewById(R.id.pokemons);
        PokemonAdapter pokemonAdapter = new PokemonAdapter(this, pokemons);
        pokemonsView.setAdapter(pokemonAdapter);

        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setRefreshing(false);

        pokemonsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                // Start the DetailPokemon activity of the selected pokemon
                String pokemonName = pokemons.get(position).getName();
                Intent intent = new Intent(getApplicationContext(), DetailsPage.class);
                intent.putExtra(ARG_POKEMON_NAME, pokemonName);
                startActivity(intent);
            }
        });
    }
}
