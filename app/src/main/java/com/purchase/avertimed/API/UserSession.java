package com.purchase.avertimed.API;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import java.util.HashMap;

/**
 * Created by kshitij on 12/18/17.
 */

public class UserSession {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "UserSessionPref";

    // First time logic Check
    public static final String FIRST_TIME = "firsttime";
    public static final String CURRENCYSIGN = "CURRENCYSIGN";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String KEY_PARENT_NAME = "name";
    public static final String KEY_MNAME = "KEY_MNAME";
    public static final String KEY_LNAME = "KEY_LNAME";
    public static final String KEY_USERTYPE = "KEY_USERTYPE";
    public static final String KEY_LANGUAGE = "KEY_LANGUAGE";
    public static final String KEY_CURRENCY = "KEY_CURRENCY";
    public static final String KEY_APITOKEN = "KEY_APITOKEN";
    public static final String KEY_PARENT_LNAME = "KEY_LNAME";
    public static final String KEY_isActive = "KEY_isActive";
    public static final String KEY_isEnablePushNotification = "KEY_isEnablePushNotification";
    public static final String KEY_USER_ID = "KEY_USER_ID";
    public static final String KEY_PARENT_USER_ID = "KEY_PARENT_USER_ID";
    public static final String KEY_SELECTED_USER_ID = "KEY_SELECTED_USER_ID";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PARENT_EMAIL = "PARENT_email";

    // Mobile number (make variable public to access from outside)
    public static final String KEY_MOBiLE = "mobile";
    public static final String KEY_PARENT_MOBiLE = "PARENT_mobile";

    // user avatar (make variable public to access from outside)
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_PARENT_PHOTO = "PARENT_photo";

    // number of items in our cart
    public static final String KEY_CART = "cartvalue";
    public static final String KEY_PINCODE = "KEY_PINCODE";
    public static final String KEY_ADDRESS = "KEY_ADDRESS";
    public static final String KEY_P_COUNTRY = "KEY_P_COUNTRY";
    public static final String KEY_P_STATE = "KEY_P_STATE";
    public static final String KEY_P_CITY = "KEY_P_CITY";
    public static final String KEY_P_ADDRESS = "KEY_P_ADDRESS";
    public static final String KEY_P_MOBILE= "KEY_P_MOBILE";
    public static final String KEY_FirebaseToken= "KEY_FirebaseToken";

    // number of items in our wishlist
    public static final String KEY_WISHLIST = "wishlistvalue";

    // check first time app launch
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";


    public static final String CUR_Name = "Currency_name";


    //User Details Location
    public static final String KEY_FeatureName = "FeatureName";
    public static final String KEY_Thoroughfare = "Thoroughfare";
    public static final String KEY_Locality = "Locality";
    public static final String KEY_SubAdminArea = "SubAdminArea";
    public static final String KEY_AdminArea = "AdminArea";
    public static final String KEY_CountryName = "CountryName";
    public static final String KEY_PostalCode = "PostalCode";


    // for language change

    private static final String LANGUAGENAME = "language";
    private static final String LANGUAGECODE = "languagecode";
    private static final String PRODUCTTITLE = "productTitle";
    private static final String CATEGORYNAME = "categoryName";
    private static final String SUBCATEGORYNAME = "subCategoryName";
    private static final String DESCRIPTION = "description";
    private static final String ISLANCHANGE = "isLangChange";


    //for notification
    private final String NOTIFICATION_ALERT = "notification_alert";
    private final String FIRST_TIME_NOTIFICATION_ON = "first_time_on";


    // Constructor
    public UserSession(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * @param userID
     * @param firstName
     * @param lastName
     * @param email
     * @param userType
     * @param language
     * @param currency
     * @param APIToken
     * @param isEnablePushNotification
     * @param isActive
     */
    public void createLoginSession(String userID, String firstName, String lastName, String email, String userType, String language, String currency, String APIToken, int isEnablePushNotification, int isActive,String Currency_name,String Currency_Sign) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USER_ID, userID);

        editor.putString(KEY_NAME, firstName);
        editor.putString(KEY_LNAME, lastName);
        editor.putString(KEY_USERTYPE, userType);
        editor.putString(KEY_LANGUAGE, language);
        editor.putString(KEY_CURRENCY, currency);
        editor.putString(KEY_APITOKEN, APIToken);
        editor.putInt(KEY_isEnablePushNotification, isEnablePushNotification);
        editor.putInt(KEY_isActive, isActive);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // Storing phone number in pref
     //   editor.putString(KEY_MOBiLE, mobile);

        // Storing image url in pref
     //   editor.putString(KEY_PHOTO, photo);
    //    editor.putString(KEY_PARENT_PHOTO, photo);
      //  editor.putString(KEY_PARENT_MOBiLE, mobile);
        editor.putString(KEY_PARENT_EMAIL, email);
        editor.putString(KEY_PARENT_LNAME, lastName);
        editor.putString(KEY_PARENT_NAME, firstName);
        editor.putString(KEY_PARENT_NAME, firstName);
        editor.putString(CUR_Name, Currency_name);
        editor.putString(CURRENCYSIGN, Currency_Sign);
      //  editor.putString(KEY_PARENT_USER_ID, user_id);
     //   editor.putString(KEY_SELECTED_USER_ID, user_id);
        // commit changes
        editor.commit();
    }


    public void createProfileSession(String Country, String State, String City, String Address, String Mobile) {
        // Storing login value as TRUE

        // Storing name in pref
        editor.putString(KEY_P_COUNTRY, Country);

        editor.putString(KEY_P_STATE, State);
        editor.putString(KEY_P_CITY, City);
        editor.putString(KEY_P_ADDRESS, Address);
        editor.putString(KEY_P_MOBILE, Mobile);


        // Storing email in pref

        // Storing phone number in pref
        //   editor.putString(KEY_MOBiLE, mobile);

        // Storing image url in pref
        //   editor.putString(KEY_PHOTO, photo);
        //    editor.putString(KEY_PARENT_PHOTO, photo);
        //  editor.putString(KEY_PARENT_MOBiLE, mobile);
        //  editor.putString(KEY_PARENT_USER_ID, user_id);
        //   editor.putString(KEY_SELECTED_USER_ID, user_id);
        // commit changes
        editor.commit();
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */


    public  void UserAddress(String FeatureName,String Thoroughfare,String Locality
            ,String SubAdminArea,String AdminArea,String CountryName,String PostalCode){

        editor.putString(KEY_FeatureName, FeatureName);
        editor.putString(KEY_Thoroughfare, Thoroughfare);
        editor.putString(KEY_Locality, Locality);
        editor.putString(KEY_SubAdminArea, SubAdminArea);
        editor.putString(KEY_AdminArea, AdminArea);
        editor.putString(KEY_CountryName, CountryName);
        editor.putString(KEY_PostalCode, PostalCode);
        editor.commit();
    }


    public String getUserAddress() {

        return pref.getString(KEY_FeatureName, "") + "\n" + pref.getString(KEY_Thoroughfare, "") + "\n" +
                "Locality: " + pref.getString(KEY_Locality, "") + "\n" + "City: " + pref.getString(KEY_SubAdminArea, "") + "\n" +
                "State: " + pref.getString(KEY_AdminArea, "") + "\n" + "Country: " + pref.getString(KEY_CountryName, "") + "\n" +
                "Postal Code: " + pref.getString(KEY_PostalCode, "") + "\n";

    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_MNAME, pref.getString(KEY_MNAME, null));
        user.put(KEY_LNAME, pref.getString(KEY_LNAME, null));
        user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // user phone number
        user.put(KEY_MOBiLE, pref.getString(KEY_MOBiLE, null));

        // user avatar
        user.put(KEY_PHOTO, pref.getString(KEY_PHOTO, null));

        // return user
        return user;
    }

    /**
     * Get stored session data
     */
    public UserAccount getParentUserDetails() {
        // user name


        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(pref.getString(KEY_PARENT_NAME, null));
        userAccount.setLastname(pref.getString(KEY_PARENT_LNAME, null));
        userAccount.setImg(pref.getString(KEY_PARENT_PHOTO, null));
        userAccount.setEmail(pref.getString(KEY_PARENT_EMAIL, null));
        userAccount.setUserId(pref.getString(KEY_PARENT_USER_ID, null));
        return userAccount;
    }


    /**
     * Clear session details
     */
    public String getAddress() {
        return pref.getString(KEY_ADDRESS, "");
    }

    public String getAPIToken() {
        return pref.getString(KEY_APITOKEN, "");
    }

    public String getPicode() {
        return pref.getString(KEY_PINCODE, "");
    }

    public String getUserId() {
        return pref.getString(KEY_USER_ID, "");
    }

    public String getFirstName() {
        return pref.getString(KEY_NAME, "");
    }

    public String getLastName() {
        return pref.getString(KEY_LNAME, "");
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "");
    }


    public String getUserImagd() {
        return pref.getString(KEY_PHOTO, "");
    }

    public String getProfileCountry() {
        return pref.getString(KEY_P_COUNTRY, "");
    }

    public String getProfileState() {
        return pref.getString(KEY_P_STATE, "");
    }

    public String getProfileCity() {
        return pref.getString(KEY_P_CITY, "");
    }

    public String getProfileAddress() {
        return pref.getString(KEY_P_ADDRESS, "");
    }

    public String getProfileMobile() {
        return pref.getString(KEY_P_MOBILE, "");
    }
    public void setFirstTime() {
        pref.edit().putBoolean("FIRST_TIME", true).commit();
    }


    public boolean getIsFirstTime() {
        return pref.getBoolean("FIRST_TIME", true);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean logout() {
        return pref.edit().clear().commit();
    }

    public int getCartValue() {
        return pref.getInt(KEY_CART, 0);
    }

    public int getWishlistValue() {
        return pref.getInt(KEY_WISHLIST, 0);
    }

    public Boolean getFirstTime() {
        return pref.getBoolean(FIRST_TIME, true);
    }

    public void setFirstTime(Boolean n) {
        editor.putBoolean(FIRST_TIME, n);
        editor.commit();
    }



    public String getCurremcySign() {
        return pref.getString(CURRENCYSIGN, "");
    }

    public void setSelctedUser(String user_id) {
        editor.putString(KEY_USER_ID, user_id);
        editor.commit();
    }


    public void increaseCartValue() {
        int val = getCartValue() + 1;
        editor.putInt(KEY_CART, val);
        editor.commit();
        Log.e("Cart Value PE", "Var value : " + val + "Cart Value :" + getCartValue() + " ");
    }

    public void increaseWishlistValue() {
        int val = getWishlistValue() + 1;
        editor.putInt(KEY_WISHLIST, val);
        editor.commit();
        Log.e("Cart Value PE", "Var value : " + val + "Cart Value :" + getCartValue() + " ");
    }

    public void decreaseCartValue() {
        int val = getCartValue() - 1;
        editor.putInt(KEY_CART, val);
        editor.commit();
        Log.e("Cart Value PE", "Var value : " + val + "Cart Value :" + getCartValue() + " ");
    }

    public void decreaseWishlistValue() {
        int val = getWishlistValue() - 1;
        editor.putInt(KEY_WISHLIST, val);
        editor.commit();
        Log.e("Cart Value PE", "Var value : " + val + "Cart Value :" + getCartValue() + " ");
    }

    public void setCartValue(int count) {
        editor.putInt(KEY_CART, count);
        editor.commit();
    }


    public void setAddress(String address) {
        editor.putString(KEY_ADDRESS, address);
        editor.commit();
    }

    public void setPincode(String pincode) {
        editor.putString(KEY_PINCODE, pincode);
        editor.commit();
    }


    public void setWishlistValue(int count) {
        editor.putInt(KEY_WISHLIST, count);
        editor.commit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void createFirebasetoken(String token) {
        editor.putString(KEY_FirebaseToken, token);
        editor.commit();

    }


    public String GetFirebasetoken() {
        return pref.getString(KEY_FirebaseToken, "");
    }


    public void setLanguageName(String name){
        editor.putString(LANGUAGENAME,name);
        editor.commit();
    }/* public void setLanguageName(String name){
        editor.putString(LANGUAGENAME,name);
        editor.commit();
    }*/

    public String getLanguage(){
        return pref.getString(LANGUAGENAME,"English");
    }

    public void setLanguageCode(String name){
        editor.putString(LANGUAGECODE,name);
        editor.commit();
    }

    public String getLanguageCode(){
        return pref.getString(LANGUAGECODE,"en");
    }

    public void setDescription(String name){
        editor.putString(DESCRIPTION,name);
        editor.commit();
    }

    public String getDescription(){
        return pref.getString(DESCRIPTION,"DescriptionEn");
    }

    public void setProductTitle(String name){
        editor.putString(PRODUCTTITLE,name);
        editor.commit();
    }

    public String getProductTitle(){
        return pref.getString(PRODUCTTITLE,"ProductTitleEn");
    }

    public void setCategoryname(String name){
        editor.putString(CATEGORYNAME,name);
        editor.commit();
    }

    public String getCategoryname(){
        return pref.getString(CATEGORYNAME,"CategoryNameEn");
    }

    public void setSubcategoryname(String name){
        editor.putString(SUBCATEGORYNAME,name);
        editor.commit();
    }

    public String getSubcategoryname(){
        return pref.getString(SUBCATEGORYNAME,"SubCategoryNameEn");
    }


    public void setIslanchange(boolean first){
        editor.putBoolean(ISLANCHANGE, first);
        editor.commit();
    }

    public boolean isLangChange(){
        return pref.getBoolean(ISLANCHANGE,false);
    }


    public void setNOTIFICATION_ALERT(String importantnotify) {
        editor.putString(NOTIFICATION_ALERT, importantnotify);
        editor.commit();
    }

    public String getNOTIFICATION_ALERT() {
        return pref.getString(NOTIFICATION_ALERT, "1");
    }

    /*public void setFIRST_TIME_NOTIFICATION_ON(String importantnotify) {
        editor.putString(FIRST_TIME_NOTIFICATION_ON, importantnotify);
        editor.commit();
    }

    public String getFIRST_TIME_NOTIFICATION_ON() {
        return pref.getString(FIRST_TIME_NOTIFICATION_ON, "1");
    }*/

}