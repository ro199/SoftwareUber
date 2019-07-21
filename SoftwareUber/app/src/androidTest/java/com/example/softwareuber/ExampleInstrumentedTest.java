package com.example.softwareuber;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> menuActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.softwareuber", appContext.getPackageName());
    }

    @Test
    public void  ingreso_cedula_usuario(){
        onView(withId(R.id.id_cedula)).perform(typeText("1725918997"));
    }

    @Test
    public void ingres_nombre_y_apellido(){
        onView(withId(R.id.id_Nombre)).perform(typeText("Christian"));
    }

    @Test
    public void correo(){

        onView(withId(R.id.id_correo)).perform(typeText("prueba@hotmail.com"));

    }
}
