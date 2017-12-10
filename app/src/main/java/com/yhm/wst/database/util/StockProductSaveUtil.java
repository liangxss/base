package com.yhm.wst.database.util;

import android.content.Context;
import android.text.TextUtils;

import com.yhm.wst.database.DBManager;
import com.yhm.wst.database.db.StockProductSave;
import com.yhm.wst.greendao.gen.StockProductSaveDao;
import com.yhm.wst.util.ArrayUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liang_xs on 2017/11/28.
 */
public class StockProductSaveUtil {
    private static final String TAG = StockProductSaveUtil.class.getSimpleName();
    private DBManager mManager;

    public StockProductSaveUtil(Context context) {
        mManager = DBManager.getInstance(context);
    }

    /**
     * 完成bean记录的插入，如果表未创建，先创建bean表
     *
     * @param bean
     * @return
     */
    public boolean insert(StockProductSave bean) {
        boolean flag = mManager.getDaoSession().getStockProductSaveDao().insert(bean) == -1 ? false : true;
        return flag;
    }

    /**
     * 修改一条数据
     *
     * @param bean
     * @return
     */
    public boolean update(StockProductSave bean) {
        boolean flag = false;
        try {
            ArrayList<StockProductSave> list = (ArrayList<StockProductSave>) queryBuilderByCrnoAndId(bean.getCrno(), bean.getProductid());
            if(ArrayUtil.isEmpty(list)) {
                insert(bean);
            } else {
                mManager.getDaoSession().update(bean);
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 以edittime最新为条件，修改一条数据
     *
     * @param bean
     * @return
     */
    public boolean updateWithEditTime(StockProductSave bean) {
        boolean flag = false;
        try {
            ArrayList<StockProductSave> list = (ArrayList<StockProductSave>) queryBuilderByCrnoAndId(bean.getCrno(), bean.getProductid());
            if(ArrayUtil.isEmpty(list)) {
                insert(bean);
            } else {
                StockProductSave local = list.get(0);
                Long editTime = 0L;
                if(!TextUtils.isEmpty(local.getEdittime())) {
                    editTime = Long.valueOf(local.getEdittime());
                }
                Long makeDate = 0L;
                if(!TextUtils.isEmpty(bean.getEdittime())) {
                    makeDate = Long.valueOf(bean.getEdittime());
                }
                if(editTime > makeDate) {
                    mManager.getDaoSession().update(local);
                } else {
                    bean.set_id(local.get_id());
                    mManager.getDaoSession().update(bean);
                }
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除某盘点单所有记录
     *
     * @return
     */
    public boolean deleteAllByCrno(String crno) {
        boolean flag = false;
        try {
            ArrayList<StockProductSave> list = (ArrayList<StockProductSave>) queryBuilderByCrno(crno);
            if(!ArrayUtil.isEmpty(list)) {
                mManager.getDaoSession().getStockProductSaveDao().deleteInTx(list);
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 删除所有记录
     *
     * @return
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(StockProductSave.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 删除某盘点单某商品
     *
     * @return
     */
    public boolean deleteByCrnoAndProId(String crno,String productId) {
        boolean flag = false;
        try {
            ArrayList<StockProductSave> list = (ArrayList<StockProductSave>) queryBuilderByCrnoAndId(crno, productId);
            if(!ArrayUtil.isEmpty(list)) {
                for (StockProductSave bean : list) {
                    mManager.getDaoSession().delete(bean);
                }
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 根据crno为条件，edittime倒序排列，editmark不为3，查询列表
     *
     * @param crno
     * @return
     */
    public List<StockProductSave> queryByCrnoOrderEdittimeNotEq(String crno) {
        QueryBuilder<StockProductSave> queryBuilder = mManager.getDaoSession().queryBuilder(StockProductSave.class);
        return queryBuilder.orderDesc(StockProductSaveDao.Properties.Edittime)
                .where(StockProductSaveDao.Properties.Crno.eq(crno))
                .where(StockProductSaveDao.Properties.Editmark.notEq("3")).list();
    }

    /**
     * 根据crno和productId查询列表
     *
     * @return
     */
    public List<StockProductSave> queryBuilderByCrnoAndId(String crno, String productId) {
        QueryBuilder<StockProductSave> queryBuilder = mManager.getDaoSession().queryBuilder(StockProductSave.class);
        return queryBuilder.where(StockProductSaveDao.Properties.Productid.eq(productId)).where(StockProductSaveDao.Properties.Crno.eq(crno)).list();
    }


    /**
     * 根据crno查询列表
     *
     * @return
     */
    public List<StockProductSave> queryBuilderByCrno(String crno) {
        QueryBuilder<StockProductSave> queryBuilder = mManager.getDaoSession().queryBuilder(StockProductSave.class);
        return queryBuilder.where(StockProductSaveDao.Properties.Crno.eq(crno)).list();
    }

    /**
     * 根据crno并且edittime不为""为条件查询列表
     *
     * @return
     */
    public List<StockProductSave> queryBuilderByCrnoAndNotUpload(String crno) {
        QueryBuilder<StockProductSave> queryBuilder = mManager.getDaoSession().queryBuilder(StockProductSave.class);
        return queryBuilder.where(StockProductSaveDao.Properties.Crno.eq(crno)).where(StockProductSaveDao.Properties.Editmark.notEq("")).list();
    }
    /**
     * 根据crno并且edittime不为""，并且local为"1"为条件查询列表
     *
     * @return
     */
    public List<StockProductSave> queryBuilderByCrnoAndNeedUpload(String crno) {
        QueryBuilder<StockProductSave> queryBuilder = mManager.getDaoSession().queryBuilder(StockProductSave.class);
        return queryBuilder.where(StockProductSaveDao.Properties.Crno.eq(crno))
                .where(StockProductSaveDao.Properties.Editmark.notEq(""))
                .where(StockProductSaveDao.Properties.Local.eq("1"))
                .list();
    }


    /**
     * 生成按Edittime倒排序的列表
     *
     * @return 倒排数据
     */
    public List<StockProductSave> queryAllOrderDescByEditTime() {
        QueryBuilder<StockProductSave> queryBuilder = mManager.getDaoSession().queryBuilder(StockProductSave.class);
        return queryBuilder.orderDesc(StockProductSaveDao.Properties.Edittime).list();
    }
}
