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

package com.loan.golden.cash.money.wheelpicker.widget;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.loan.golden.cash.money.wheelpicker.OptionPicker;
import com.loan.golden.cash.money.wheelpicker.annotation.EthnicSpec;
import com.loan.golden.cash.money.wheelpicker.dialog.DialogLog;
import com.loan.golden.cash.money.wheelpicker.entity.MaritalEntity;

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
public class QuestionTypePicker extends OptionPicker {
    public static String JSON = "";

    public QuestionTypePicker(@NonNull Activity activity, String mJSON) {
        super(activity);
        this.JSON = mJSON;
    }

    public QuestionTypePicker(@NonNull Activity activity, int themeResId) {
        super(activity, themeResId);
    }

    public void setEthnicSpec(@EthnicSpec int ethnicSpec) {
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

    @Override
    public void setDefaultPosition(int position) {
        super.setDefaultPosition(position);
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
                entity.setCreated(jsonObject.getLong("created"));
                entity.setModified(jsonObject.getLong("modified"));
                entity.setCategoryId(jsonObject.getString("curName"));
                entity.setName(jsonObject.getString("curDescription"));
                entity.setSort(jsonObject.getInt("sort"));
                data.add(entity);
            }
        } catch (JSONException e) {
            DialogLog.print(e);
        }
        return data;
    }

}
