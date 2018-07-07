package test.certant.ar.pokedexlite.dao;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import test.certant.ar.pokedexlite.R;

public class DaoFactory {

    private static final String fileName = "pokemons.json";

    private DaoFactory() {
        super();
    }

    public static void loadPokemons(Context context) {
        File file = new File(context.getFilesDir(), fileName);
        FileOutputStream outputStream;
        try {
            outputStream = context.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(loadPokemonsFile(context).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String loadPokemonsFile(Context context) {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try (InputStream is = context.getResources().openRawResource(R.raw.pokemons)) {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public static String loadPokedex(Context context) throws IOException {
        File file = new File(context.getFilesDir(), fileName);
        FileInputStream fin = new FileInputStream(file);
        String pokedexString;
        try {
            pokedexString = convertStreamToString(fin);
        } catch (Exception e) {
            pokedexString = "{}";
        }
        fin.close();
        return pokedexString;
    }

    private static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
}
