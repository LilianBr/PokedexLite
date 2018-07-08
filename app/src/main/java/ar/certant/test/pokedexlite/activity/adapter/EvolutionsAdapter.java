package ar.certant.test.pokedexlite.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ar.certant.test.pokedexlite.R;
import ar.certant.test.pokedexlite.beans.PokemonEvolution;

public class EvolutionsAdapter extends ArrayAdapter<PokemonEvolution> {

    public EvolutionsAdapter(Context context, List<PokemonEvolution> pokemons) {
        super(context, 0, pokemons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PokemonEvolution evolution = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.evolution_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.evolutionName);
        TextView level = convertView.findViewById(R.id.requiredLevel);
        TextView types = convertView.findViewById(R.id.types);

        name.setText(evolution.getName());
        level.setText(String.valueOf(evolution.getRequiredLevel()));
        types.setText(evolution.getAbilitiesString());
        return convertView;
    }
}
