package com.loan.golden.cash.money.loan.data.response;

import cn.mtjsoft.multiimagelibrary.imp.ImageInfo;

/**
 * @Author : hxw
 * @Date : 2023/6/28 9:25
 * @Describe :
 */
public class ImageModel implements ImageInfo {

    private Object path;

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

    /**
     * @return 图片地址
     */
    @Override
    public Object getImagePath() {
        return path;
    }

    /**
     * @return 固定返回 false
     */
    @Override
    public boolean isLastAddViewData() {
        return false;
    }

}
