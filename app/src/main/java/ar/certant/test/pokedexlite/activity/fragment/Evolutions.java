package ar.certant.test.pokedexlite.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.certant.test.pokedexlite.R;

/**
 * Android Fragment
 * Display a list of possible evolutions
 */
public class Evolutions extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_evolutions, container, false);
    }
}
