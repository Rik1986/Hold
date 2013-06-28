package com.gs7.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.chinaren.framework.core.dao.base.LongBaseDao;
import com.chinaren.framework.model.LongBaseObject;

public abstract class AbstractBackendDao<T extends LongBaseObject<Long>> extends LongBaseDao<T> {
    @Override
    protected void init() {
        super.init();
        this.enableObjectCache = false;
    }

    @Transactional
    @Override
    protected Long __insert(T model, Long pair) throws DataAccessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        long time = System.currentTimeMillis();
        model.setCtime(time);
        model.setUtime(time);

        String updateSQL = String.format("INSERT INTO %s ( %s )  values( %s )", getTableByPair(pair), this.COLUMNS,
                this.INSERT_BEAN_COLUMNS);
        int ret = getJdbcTemplateByPair(pair).getNamedParameterJdbcOperations().update(updateSQL,
                new BeanPropertySqlParameterSource(model), keyHolder);
        return keyHolder.getKey().longValue();

    }

    @Override
    protected void beforeInsert(T model) {

    }

    @Override
    protected void afterLoad(T model) {
    }

    @Override
    protected void afterInsert(T model) {
    }

    @Override
    protected void afterDelete(T model) {
    }
}
