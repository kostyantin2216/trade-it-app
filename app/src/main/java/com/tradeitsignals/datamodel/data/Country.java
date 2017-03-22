package com.tradeitsignals.datamodel.data;

import com.tradeitsignals.datamodel.DataModelObject;

/**
 * Created by Kostyantin on 10/19/2015.
 */
public class Country implements DataModelObject {

    private Integer id;
    private String name;
    private String iso3;
    private String iso2;
    private String dialCode;

    public Country() { }

    public Country(Integer id, String name, String iso3, String iso2, String dialCode) {
        this.id = id;
        this.name = name;
        this.iso3 = iso3;
        this.iso2 = iso2;
        this.dialCode = dialCode;
    }

    public Country(String name, String iso3, String iso2, String dialCode) {
        this.name = name;
        this.iso3 = iso3;
        this.iso2 = iso2;
        this.dialCode = dialCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iso3='" + iso3 + '\'' +
                ", iso2='" + iso2 + '\'' +
                ", dialCode='" + dialCode + '\'' +
                '}';
    }
}
