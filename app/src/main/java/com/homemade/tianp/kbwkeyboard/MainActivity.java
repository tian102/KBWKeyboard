
package com.homemade.tianp.kbwkeyboard;

        import android.content.Intent;
        import android.os.Bundle;
        import android.provider.Settings;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;


/**Used to convey instructions to the user.
 *
 * @author  Tian Pretorius
 * @version 1.0
 * @since   2017-03-15
 *
 * Created by tianp on 24 Mar 2017.
 */

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**Takes user to the settings where they can then choose to enable KBW Keyboard
      */
    public void openSettings(View view){
        startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
    }

    /**setDefaultKeyboard:
     * Takes user to the settings where they then have to set up the default keyboard
     */
    public void setDefaultKeyboard(View view){
        startActivity(new Intent(Settings.ACTION_SETTINGS));
    }



}

