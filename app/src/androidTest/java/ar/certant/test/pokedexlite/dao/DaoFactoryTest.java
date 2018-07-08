package ar.certant.test.pokedexlite.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class DaoFactoryTest {

    private Context appContext;

    @Before
    public void setUpBefore() {
        appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void loadPokemons() throws IOException {
        Context appContext = InstrumentationRegistry.getTargetContext();
        String content = "My content";
        DaoFactory.loadPokemons(appContext, content.getBytes());
        File file = new File(appContext.getFilesDir(), DaoFactory.fileName);
        assertFalse(file.createNewFile());
    }

    @Test
    public void loadPokemonsFile() {
        assertNotEquals("{}", DaoFactory.loadPokemonsFile(appContext));
    }

    @Test
    public void loadPokedex() {
        String stringExpected = DaoFactory.loadPokemonsFile(appContext);
        DaoFactory.loadPokemons(appContext, stringExpected.getBytes());
        DaoFactory.loadPokedex(appContext);
        stringExpected = stringExpected.replace("\n", "").replace("\r", "");
        assertEquals(stringExpected, DaoFactory.loadPokedex(appContext).replace("\n", "").replace("\r", ""));
    }
}
