package com.yhm.wst.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.yhm.wst.database.db.StockProductSave;

import com.yhm.wst.greendao.gen.StockProductSaveDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig stockProductSaveDaoConfig;

    private final StockProductSaveDao stockProductSaveDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        stockProductSaveDaoConfig = daoConfigMap.get(StockProductSaveDao.class).clone();
        stockProductSaveDaoConfig.initIdentityScope(type);

        stockProductSaveDao = new StockProductSaveDao(stockProductSaveDaoConfig, this);

        registerDao(StockProductSave.class, stockProductSaveDao);
    }
    
    public void clear() {
        stockProductSaveDaoConfig.clearIdentityScope();
    }

    public StockProductSaveDao getStockProductSaveDao() {
        return stockProductSaveDao;
    }

}