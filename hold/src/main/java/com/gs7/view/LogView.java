package com.gs7.view;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.gs7.domain.Log;

/**
 * @author 作者 : 黎勇标
 * @version 创建时间：2013-6-1 下午1:16:12
 */
public class LogView extends Log {

    /**
     * 
     */
    private static final long serialVersionUID = 2823678689168387829L;

    private String formaterDate;

    public String getFormaterDate() {
        return formaterDate;
    }

    public void setFormaterDate(String formaterDate) {
        this.formaterDate = formaterDate;
    }

    public LogView(Log log) {
        try {
            BeanUtils.copyProperties(this, log);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        this.setFormaterDate(sdf.format(new Date(log.getDate())));

    }

}
