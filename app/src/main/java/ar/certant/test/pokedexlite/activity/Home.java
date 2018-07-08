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

    private void initializePokemonsList(final List<Pokemon> pokemons) {
        final ListView pokemonsView = findViewById(R.id.pokemons);
        PokemonAdapter adapter = new PokemonAdapter(this, pokemons);
        pokemonsView.setAdapter(adapter);

        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setRefreshing(false);
        pokemonsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                String pokemonName = pokemons.get(position).getName();
                Intent intent = new Intent(getApplicationContext(), DetailsPage.class);
                intent.putExtra(ARG_POKEMON_NAME, pokemonName);
                startActivity(intent);
            }
        });
    }
}
