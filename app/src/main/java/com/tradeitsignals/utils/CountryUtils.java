package com.tradeitsignals.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.tradeitsignals.datamodel.dao.CountryDAO;
import com.tradeitsignals.datamodel.dao.UserDAO;
import com.tradeitsignals.datamodel.data.Country;
import com.tradeitsignals.datamodel.data.User;
import com.tradeitsignals.logging.TILogger;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kostyantin on 10/28/2015.
 */
public class CountryUtils {

    public static Country getCountryFromUser() {
        return getCountryFromUser(UserDAO.getInstance().getUser());
    }

    public static Country getCountryFromUser(User user) {
        Country userCountry = null;
        if(user != null) {
            CountryDAO dao = CountryDAO.getInstance();
            userCountry = dao.findById(user.getCountryId());
        }

        if(userCountry == null){
            userCountry = getCountryFromDevice();
        }

        return userCountry;
    }

    public static Country getCountryFromDevice() {
        CountryDAO dao = CountryDAO.getInstance();
        Country userCountry = null;
        String countryCode = CommonUtils.getUserCountryCode();

        if(CommonUtils.notEmpty(countryCode)) {
            userCountry = dao.findByIso2(countryCode);
        }

        if(userCountry == null) {
            String country = CommonUtils.getUserCountryName();

            if(CommonUtils.notEmpty(country)) {
                userCountry = dao.findByName(country);
            }
        }

        return userCountry;
    }

    public static String getCountryName(Context context, Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            TILogger.getLog().i("LOG", latitude + " " + longitude);
            Geocoder geocoder = new Geocoder(context);
            try {
                List<Address> address = geocoder.getFromLocation(latitude, longitude, 1);
                return address.get(0).getCountryName();
            } catch (IOException ex) {
                TILogger.getLog().e("#GEOCODER", "could not get country name using geocoder", ex, false, true);
            }
        }
        return null;
    }
}
