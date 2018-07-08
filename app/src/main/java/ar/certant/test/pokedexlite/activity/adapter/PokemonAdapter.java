package ar.certant.test.pokedexlite.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ar.certant.test.pokedexlite.R;
import ar.certant.test.pokedexlite.beans.Pokemon;

public class PokemonAdapter extends ArrayAdapter<Pokemon> {

    public PokemonAdapter(Context context, List<Pokemon> pokemons) {
        super(context, 0, pokemons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Pokemon pokemon = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pokemon_item, parent, false);
        }

        TextView nameView = convertView.findViewById(R.id.pokemonName);
        TextView levelTypesView = convertView.findViewById(R.id.levelTypes);

        String levelTypes = "[" + pokemon.getCurrentLevel() + "] " + pokemon.getAbilitiesString();
        levelTypesView.setText(String.valueOf(levelTypes));
        nameView.setText(pokemon.getName());

        return convertView;
    }
}
