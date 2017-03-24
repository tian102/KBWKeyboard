
package com.homemade.tianp.kbwkeyboard;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import static android.view.KeyEvent.ACTION_DOWN;

/**Used to handle all keyboard related activities.
 *
 * @author  Tian Pretorius
 * @version 1.0
 * @since   2017-03-15
 *
 * Created by tianp on 24 Mar 2017.
 */

public class MyKeyboard extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {


    private KeyboardView kv;
    private Keyboard keyboard;
    private boolean caps = false;
    private boolean syms = false;
    private boolean scan = false;
    public static InputConnection inputConnection;
    public View mInputView;

    @Override
    public View onCreateInputView() {
        syms = false;

        InputMethodManager keyboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(mInputView, 0);
        return createKeyboard1();
    }

    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

        inputConnection = getCurrentInputConnection();
        playClick(primaryCode);
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE:
                inputConnection.deleteSurroundingText(1,0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                inputConnection.sendKeyEvent(new KeyEvent(ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case -113:
                // Switch to symbols layout
                syms = !syms;
                caps = false;
                scan = false;
                createKeyboard();
                break;
            case 13:
                // Scan barcode
                clearID();
                barcodeScanButton();
                break;
            case 14:
                // Scan tag
                clearID();
                tagScanButton();
                break;
            case 15:
                //Print to screen
                CheckAndDisp();
                break;
            case 16:
                // Switch to scan layout
                scan = !scan;
                syms = false;
                caps = false;
                createKeyboard();
                break;
            default:
                char code = (char) primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                if((primaryCode != 13) || (primaryCode != 14) || (primaryCode != 15) || (primaryCode != 16) || (primaryCode != 16)){
                    ToScreen(code);
                }
                break;
        }
    }

    /** Used to choose between layouts e.g. Alphabet and Symbols*/
    private void createKeyboard(){
        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard,null);
        if(syms){
            keyboard = new Keyboard(this, R.xml.symbols);
        }else {
            keyboard = new Keyboard(this, R.xml.qwerty);
        }
        if(scan){
            keyboard = new Keyboard(this, R.xml.scanner);
        }
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        mInputView = kv;
        this.setInputView(mInputView);
    }

    /** Used only to set keyboard at first launch
     * */
    private View createKeyboard1(){
        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard,null);
        if(syms){
            keyboard = new Keyboard(this, R.xml.symbols);
        }else {
            keyboard = new Keyboard(this, R.xml.qwerty);
        }
        if(scan){
            keyboard = new Keyboard(this, R.xml.scanner);
        }
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        mInputView = kv;
        return kv;
    }

    /** Check if scan successful and display if true
     * */
    public static void CheckAndDisp(){
        if(!ScanResult.GetProductID().equals("Default ID")){
            String result = ScanResult.GetProductID();
            StringToWedge(result);
            //ScanResult.SetProductID("Default ID");
        }
    }

    /** Prints string to screen
     * */
    public static void StringToWedge(String string){
        for(int i = 0; i < string.length(); i++){
            ToScreen(string.charAt(i));
        }
    }

    /** Prints character to screen
     * */
    public static void ToScreen(char code){
        inputConnection.commitText(String.valueOf(code),1);
    }

    /**Keyboard sound effects
     * */
    private void playClick(int keyCode){
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (keyCode){
            case 32:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case 10:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    /** Start the barcode scanner
     * */
    private void barcodeScanButton(){
        Intent intent = new Intent(this, BarcodeScanner.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /** Start the barcode scanner
     * */
    private void tagScanButton(){
        Intent intent = new Intent(this, TagScanner.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /** Clear scan result
     * */
    private void clearID(){
        ScanResult.SetProductID("Default ID");
    }


    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {


    }

    @Override
    public void swipeUp() {

    }
}