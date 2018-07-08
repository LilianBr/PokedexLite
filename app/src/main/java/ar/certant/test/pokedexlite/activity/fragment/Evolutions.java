package ar.certant.test.pokedexlite.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.certant.test.pokedexlite.R;

public class Evolutions extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_evolutions, container, false);
        return view;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }
}
