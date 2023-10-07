/*
 * Copyright (c) 2016-present 贵州纳雍穿青人李裕江<1032694760@qq.com>
 *
 * The software is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *     http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package com.loan.golden.cash.money.wheel.widget;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.loan.golden.cash.money.wheel.OptionPicker;
import com.loan.golden.cash.money.wheel.annotation.EthnicSpec;
import com.loan.golden.cash.money.wheel.dialog.DialogLog;
import com.loan.golden.cash.money.wheel.entity.MaritalEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 民族选择器
 *
 * @author 贵州山野羡民（1032694760@qq.com）
 * @since 2021/6/12 13:50
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class SinglePicker extends OptionPicker {
    public static String JSON = "";
    private int ethnicSpec = EthnicSpec.DEFAULT;

    public SinglePicker(@NonNull Activity activity, String mJSON) {
        super(activity);
        this.JSON = mJSON;
    }

    public SinglePicker(@NonNull Activity activity, int themeResId) {
        super(activity, themeResId);
    }

    public void setEthnicSpec(@EthnicSpec int ethnicSpec) {
        this.ethnicSpec = ethnicSpec;
        setData(provideData());
    }

    @Override
    public void setDefaultValue(Object item) {
        if (item instanceof String) {
            setDefaultValueByName(item.toString());
        } else {
            super.setDefaultValue(item);
        }
    }

    public void setDefaultValueByCode(String id) {
        MaritalEntity entity = new MaritalEntity();
        entity.setId(id);
        super.setDefaultValue(entity);
    }

    public void setDefaultValueByName(String name) {
        MaritalEntity entity = new MaritalEntity();
        entity.setName(name);
        super.setDefaultValue(entity);
    }

    @Override
    protected List<MaritalEntity> provideData() {
        ArrayList<MaritalEntity> data = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(JSON);
            for (int i = 0, n = jsonArray.length(); i < n; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MaritalEntity entity = new MaritalEntity();
                entity.setId(jsonObject.getString("id"));
                entity.setName(jsonObject.getString("name"));
                entity.setValue(jsonObject.getString("value"));
                data.add(entity);
            }
        } catch (JSONException e) {
            DialogLog.print(e);
        }
        return data;
    }

}
