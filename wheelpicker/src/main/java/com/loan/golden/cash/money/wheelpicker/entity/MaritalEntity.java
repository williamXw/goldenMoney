package com.loan.golden.cash.money.wheelpicker.entity;

import androidx.annotation.Nullable;

import com.loan.golden.cash.money.wheelview.contract.TextProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @Author : hxw
 * @Date : 2023/10/7 14:17
 * @Describe :
 */
public class MaritalEntity implements TextProvider, Serializable {

    private String id;
    private Long created;
    private Long modified;
    private ArrayList<String> countryIds;
    private String categoryId;
    private String name;
    private String value;
    private Boolean hasChildren;
    private String color;
    private ValuesBean values;
    private Integer sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }

    public ArrayList<String> getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(ArrayList<String> countryIds) {
        this.countryIds = countryIds;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ValuesBean getValues() {
        return values;
    }

    public void setValues(ValuesBean values) {
        this.values = values;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String provideText() {
        return name;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MaritalEntity that = (MaritalEntity) o;
        return Objects.equals(id, that.id) ||
                Objects.equals(name, that.name) ||
                Objects.equals(created, that.created) ||
                Objects.equals(modified, that.modified) ||
                Objects.equals(countryIds, that.countryIds) ||
                Objects.equals(categoryId, that.categoryId) ||
                Objects.equals(value, that.value) ||
                Objects.equals(hasChildren, that.hasChildren) ||
                Objects.equals(color, that.color) ||
                Objects.equals(values, that.values) ||
                Objects.equals(sort, that.sort);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, created, modified, countryIds, categoryId, value, hasChildren, color, values, sort);
    }

    class ValuesBean implements TextProvider, Serializable {

        private ValuesBeanChild values;
        private String _t;

        public ValuesBeanChild getValues() {
            return values;
        }

        public void setValues(ValuesBeanChild values) {
            this.values = values;
        }

        public String get_t() {
            return _t;
        }

        public void set_t(String _t) {
            this._t = _t;
        }

        @Override
        public String provideText() {
            return null;
        }

        class ValuesBeanChild implements TextProvider, Serializable {

            private String id;
            private String es_NI;
            private String en_us;
            private String hi;
            private String vi;
            private String zh_TW;
            private String ur;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getEs_NI() {
                return es_NI;
            }

            public void setEs_NI(String es_NI) {
                this.es_NI = es_NI;
            }

            public String getEn_us() {
                return en_us;
            }

            public void setEn_us(String en_us) {
                this.en_us = en_us;
            }

            public String getHi() {
                return hi;
            }

            public void setHi(String hi) {
                this.hi = hi;
            }

            public String getVi() {
                return vi;
            }

            public void setVi(String vi) {
                this.vi = vi;
            }

            public String getZh_TW() {
                return zh_TW;
            }

            public void setZh_TW(String zh_TW) {
                this.zh_TW = zh_TW;
            }

            public String getUr() {
                return ur;
            }

            public void setUr(String ur) {
                this.ur = ur;
            }

            @Override
            public String provideText() {
                return null;
            }
        }
    }
}
