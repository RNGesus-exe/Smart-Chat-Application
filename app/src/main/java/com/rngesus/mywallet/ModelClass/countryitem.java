package com.rngesus.mywallet.ModelClass;

public class countryitem {
    private String mCountryName;
    private int mFlagImage;
    private String language;

    public countryitem(String countryName, int flagImage,String lan) {
        mCountryName = countryName;
        mFlagImage = flagImage;
        language=lan;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountryName() {
        return mCountryName;
    }

    public int getFlagImage() {
        return mFlagImage;
    }
}