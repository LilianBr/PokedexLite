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

/**
 * Dao Factory
 * Manage the internal storage
 */
public class DaoFactory {

    public static final String FILE_NAME = "pokemons.json";

    private DaoFactory() {
        super();
    }

    /**
     * Save the content into a file named pokemons.json (FILE_NAME) in the internal storage
     *
     * @param context Current context
     * @param content Content file
     */
    public static void loadPokemons(Context context, byte[] content) {
        File pokedexFile = new File(context.getFilesDir(), FILE_NAME);
        FileOutputStream outputStream;
        try {
            outputStream = context.getApplicationContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(content);
            outputStream.close();
        } catch (Exception e) {
            // Empty
        }
    }

    /**
     * Load the pokemons file (in the /raw directory of the project)
     *
     * @param context Current context
     * @return Content of the pokedex (Json file)
     */
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

    /**
     * Load the pokemons file (in the internal storage)
     * @param context Current context
     * @return Content of the pokedex (Json file stored in the internal storage)
     */
    public static String loadPokedex(Context context) {
        File pokedexFile = new File(context.getFilesDir(), FILE_NAME);
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

    /**
     * Convert a stream to a String
     * @param inputStream
     * @return
     * @throws Exception
     */
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
