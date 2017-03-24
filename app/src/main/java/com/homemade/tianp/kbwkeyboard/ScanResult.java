package com.homemade.tianp.kbwkeyboard;

/** Used to get and/or set the scanned UID.
 *
 * @author  Tian Pretorius
 * @version 1.0
 * @since   2017-03-15
 *
 * Created by tianp on 24 Mar 2017.
 */

public class ScanResult {
    public static String ProductID = "Default ID";

    public static String GetProductID(){
        return ProductID;
    }

    public static boolean SetProductID(String productID){
        ProductID = productID;
        return true;
    }
}
