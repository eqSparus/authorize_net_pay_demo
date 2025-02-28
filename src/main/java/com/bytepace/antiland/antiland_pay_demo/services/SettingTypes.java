package com.bytepace.antiland.antiland_pay_demo.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.authorize.api.contract.v1.ArrayOfSetting;
import net.authorize.api.contract.v1.SettingType;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class SettingTypes {

    ArrayOfSetting arrayOfSetting;

    public SettingTypes() {
        arrayOfSetting = new ArrayOfSetting();
    }

    public void append(String name, String value) {
        var type = new SettingType();
        type.setSettingName(name);
        type.setSettingValue(value);
        arrayOfSetting.getSetting().add(type);
    }
}
