package test.certant.ar.pokedexlite.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import test.certant.ar.pokedexlite.R;
import test.certant.ar.pokedexlite.beans.Pokemon;

public class PokemonAdapter extends ArrayAdapter<Pokemon>

{

    public PokemonAdapter(Context context, List<Pokemon> pokemons) {
        super(context, 0, pokemons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Pokemon pokemon = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.pokemon_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.pokemonName);
        TextView levelTypes = convertView.findViewById(R.id.levelTypes);

        name.setText(pokemon.getName());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[").append(pokemon.getCurrentLevel()).append("]");
        stringBuilder.append(" ").append(pokemon.getAbilitiesString());
        levelTypes.setText(String.valueOf(stringBuilder.toString()));

        return convertView;
    }
}
