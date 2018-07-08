package test.certant.ar.pokedexlite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import test.certant.ar.pokedexlite.R;
import test.certant.ar.pokedexlite.activity.adapter.PokemonAdapter;
import test.certant.ar.pokedexlite.beans.Pokemon;
import test.certant.ar.pokedexlite.dao.DaoFactory;
import test.certant.ar.pokedexlite.dao.PokemonDao;

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
        /*File file = new File(this.getFilesDir(), DaoFactory.fileName);
        try {
            if (file.createNewFile()) {
                DaoFactory.loadPokemons(this.getApplicationContext(), DaoFactory.loadPokemonsFile(this.getApplicationContext()).getBytes());
            }
        } catch (IOException e) {
            // Empty
        }*/
        DaoFactory.loadPokemons(this.getApplicationContext(), DaoFactory.loadPokemonsFile(this.getApplicationContext()).getBytes());
        final List<Pokemon> pokemons = PokemonDao.list(this.getApplicationContext());

        initializePokemonsList(pokemons);
    }

    private void initializePokemonsList(final List<Pokemon> pokemons) {
        final ListView listview = findViewById(R.id.pokemons);

        PokemonAdapter adapter = new PokemonAdapter(this, pokemons);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
