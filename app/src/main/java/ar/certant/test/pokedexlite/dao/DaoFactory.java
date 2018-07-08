package ar.certant.test.pokedexlite.dao;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import ar.certant.test.pokedexlite.R;

public class DaoFactory {

    public static final String fileName = "pokemons.json";

    private DaoFactory() {
        super();
    }

    public static void loadPokemons(Context context, byte[] content) {
        File pokedexFile = new File(context.getFilesDir(), fileName);
        FileOutputStream outputStream;
        try {
            outputStream = context.getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(content);
            outputStream.close();
        } catch (Exception e) {
            // Empty
        }
    }

    public static String loadPokemonsFile(Context context) {
        String pokemonsFile;
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try (InputStream inputStream = context.getResources().openRawResource(R.raw.pokemons)) {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int character;
            while ((character = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, character);
            }
            pokemonsFile = writer.toString();
        } catch (Exception e) {
            pokemonsFile = "{}";
        }
        return pokemonsFile;
    }

    public static String loadPokedex(Context context) {
        File pokedexFile = new File(context.getFilesDir(), fileName);
        FileInputStream fileInputStream;
        String pokedexString;
        try {
            fileInputStream = new FileInputStream(pokedexFile);
            pokedexString = convertStreamToString(fileInputStream);
            fileInputStream.close();
        } catch (Exception e) {
            pokedexString = "{}";
        }
        return pokedexString;
    }

    private static String convertStreamToString(InputStream inputStream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        reader.close();
        return stringBuilder.toString();
    }
}
