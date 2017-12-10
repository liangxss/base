package com.yhm.wst.database.db;

import com.yhm.wst.bean.BaseBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by liang_xs on 2017/11/28.
 */
@Entity
public class StockProductSave extends BaseBean {
    @Id(autoincrement = true)
    private Long _id;//主键  自增长

    private String productid;

    private String name;

    private String barcode;

    private String shortcode;

    private String units;

    private String spec;

    private String alianame;

    private String stockproperty;

    private String storecode;

    private String makedate;

    private String maker;

    private String editmark;

    private String edittime;

    private String goodstypecode;

    private String brandid;

    private String isbulk;

    private String realquantity;

    private String crno;

    private String local; // 1是本地数据

    @Generated(hash = 376890832)
    public StockProductSave(Long _id, String productid, String name, String barcode,
            String shortcode, String units, String spec, String alianame,
            String stockproperty, String storecode, String makedate, String maker,
            String editmark, String edittime, String goodstypecode, String brandid,
            String isbulk, String realquantity, String crno, String local) {
        this._id = _id;
        this.productid = productid;
        this.name = name;
        this.barcode = barcode;
        this.shortcode = shortcode;
        this.units = units;
        this.spec = spec;
        this.alianame = alianame;
        this.stockproperty = stockproperty;
        this.storecode = storecode;
        this.makedate = makedate;
        this.maker = maker;
        this.editmark = editmark;
        this.edittime = edittime;
        this.goodstypecode = goodstypecode;
        this.brandid = brandid;
        this.isbulk = isbulk;
        this.realquantity = realquantity;
        this.crno = crno;
        this.local = local;
    }

    @Generated(hash = 2065841640)
    public StockProductSave() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getShortcode() {
        return this.shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String getUnits() {
        return this.units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getSpec() {
        return this.spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getAlianame() {
        return this.alianame;
    }

    public void setAlianame(String alianame) {
        this.alianame = alianame;
    }

    public String getStockproperty() {
        return this.stockproperty;
    }

    public void setStockproperty(String stockproperty) {
        this.stockproperty = stockproperty;
    }

    public String getStorecode() {
        return this.storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public String getMakedate() {
        return this.makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getMaker() {
        return this.maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getEditmark() {
        return this.editmark;
    }

    public void setEditmark(String editmark) {
        this.editmark = editmark;
    }

    public String getEdittime() {
        return this.edittime;
    }

    public void setEdittime(String edittime) {
        this.edittime = edittime;
    }

    public String getGoodstypecode() {
        return this.goodstypecode;
    }

    public void setGoodstypecode(String goodstypecode) {
        this.goodstypecode = goodstypecode;
    }

    public String getBrandid() {
        return this.brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public String getIsbulk() {
        return this.isbulk;
    }

    public void setIsbulk(String isbulk) {
        this.isbulk = isbulk;
    }

    public String getRealquantity() {
        return this.realquantity;
    }

    public void setRealquantity(String realquantity) {
        this.realquantity = realquantity;
    }

    public String getCrno() {
        return this.crno;
    }

    public void setCrno(String crno) {
        this.crno = crno;
    }

    public String getLocal() {
        return this.local;
    }

    public void setLocal(String local) {
        this.local = local;
    }


}
