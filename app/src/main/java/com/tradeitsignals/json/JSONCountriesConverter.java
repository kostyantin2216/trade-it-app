package com.tradeitsignals.json;

import com.tradeitsignals.SignalsApplication;
import com.tradeitsignals.datamodel.data.Country;
import com.tradeitsignals.logging.TILogger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Format for JSON country_mappings assets file:
 *
 *  "name": "Afghanistan",
 *  "name_fr": "Afghanistan",
 *  "ISO3166-1-Alpha-2": "AF",
 *  "ISO3166-1-Alpha-3": "AFG",
 *  "ISO3166-1-numeric": "004",
 *  "ITU": "AFG",
 *  "MARC": "af",
 *  "WMO": "AF",
 *  "DS": "AFG",
 *  "Dial": "93",
 *  "FIFA": "AFG",
 *  "FIPS": "AF",
 *  "GAUL": "1",
 *  "IOC": "AFG",
 *  "currency_alphabetic_code": "AFN",
 *  "currency_country_name": "AFGHANISTAN",
 *  "currency_minor_unit": "2",
 *  "currency_name": "Afghani",
 *  "currency_numeric_code": "971",
 *  "is_independent": "Yes"
 *
 *  For more Information: <a>http://data.okfn.org/data/core/country-codes#data</a>
 *
 *  Currently only name and ISO3166-1-Alpha-3 are in use;
 *
 * Created by Kostyantin on 10/19/2015.
 */
public class JSONCountriesConverter {

    public final static String VAR_NAME = "name";
    public final static String VAR_ISO_3 = "ISO3166-1-Alpha-3";
    public final static String VAR_ISO_2 = "ISO3166-1-Alpha-2";

    private final static String ASSETS_JSON_FILE_NAME = "country-codes.json";

    public List<Country> getAllCountries() {
        JSONFetcher jsonFetcher = new JSONFetcher(SignalsApplication.getAppContext());

        List<Country> countries = new ArrayList<>();
        try {
            JSONArray jsonCountriesArray = jsonFetcher.getJsonArrayFromAssets(ASSETS_JSON_FILE_NAME);

            for(int i = 0; i < jsonCountriesArray.length(); i++) {
                JSONObject jsonCountry = jsonCountriesArray.getJSONObject(i);
                Country country = new Country();
                country.setName(jsonCountry.getString(VAR_NAME));
                country.setIso3(jsonCountry.getString(VAR_ISO_3));
                country.setIso2(jsonCountry.getString(VAR_ISO_2));
                countries.add(country);
                if(TILogger.isDebug()) {
                    TILogger.getLog().i("Added country: " + country.getName() + ", from countries mapping file");
                }
            }

        } catch (IOException | JSONException e) {
            TILogger.getLog().e("Failure while getting countries from countries mapping");
        }

        return countries;
    }
}
