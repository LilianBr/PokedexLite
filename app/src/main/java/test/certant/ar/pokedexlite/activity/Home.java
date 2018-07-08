package test.certant.ar.pokedexlite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import test.certant.ar.pokedexlite.R;
import test.certant.ar.pokedexlite.beans.Pokemon;
import test.certant.ar.pokedexlite.dao.DaoFactory;
import test.certant.ar.pokedexlite.dao.PokemonDao;

public class Home extends AppCompatActivity {

    private static final String ARG_POKEMON_NAME = "pokemonName";

    private PokemonDao pokemonDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pokemonDao = new PokemonDao();
        loadPage();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadPage();
    }

    private void loadPage() {
        File file = new File(this.getFilesDir(), DaoFactory.fileName);
        try {
            if (file.createNewFile()) {
                DaoFactory.loadPokemons(this.getApplicationContext(), DaoFactory.loadPokemonsFile(this.getApplicationContext()).getBytes());
            }
        } catch (IOException e) {
            // Empty
        }
        List<Pokemon> pokemons = pokemonDao.list(this.getApplicationContext());

        final ListView listview = findViewById(R.id.pokemons);
        List<String> namesPokemon = new ArrayList<>();
        for (Pokemon pokemon : pokemons) {
            namesPokemon.add(pokemon.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, namesPokemon);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String pokemonName = (String) listview.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), DetailsPage.class);
                intent.putExtra(ARG_POKEMON_NAME, pokemonName);
                startActivity(intent);
            }
        });
    }
}
