package com.Lbins.cpy.module;

import com.Lbins.cpy.dao.dao.DaoSession;
import com.Lbins.cpy.dao.dao.HotWordObjDao;
import de.greenrobot.dao.DaoException;


// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table HOT_WORD_OBJ.
 */
public class HotWordObj {
    /** Not-null value. */
    private String mm_hot_word_title;
    private String mm_hot_word_id;
    private String mm_hot_num;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient HotWordObjDao myDao;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public HotWordObj() {
    }

    public HotWordObj(String mm_hot_word_title) {
        this.mm_hot_word_title = mm_hot_word_title;
    }

    public HotWordObj(String mm_hot_word_title, String mm_hot_word_id, String mm_hot_num) {
        this.mm_hot_word_title = mm_hot_word_title;
        this.mm_hot_word_id = mm_hot_word_id;
        this.mm_hot_num = mm_hot_num;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHotWordObjDao() : null;
    }

    /** Not-null value. */
    public String getMm_hot_word_title() {
        return mm_hot_word_title;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMm_hot_word_title(String mm_hot_word_title) {
        this.mm_hot_word_title = mm_hot_word_title;
    }

    public String getMm_hot_word_id() {
        return mm_hot_word_id;
    }

    public void setMm_hot_word_id(String mm_hot_word_id) {
        this.mm_hot_word_id = mm_hot_word_id;
    }

    public String getMm_hot_num() {
        return mm_hot_num;
    }

    public void setMm_hot_num(String mm_hot_num) {
        this.mm_hot_num = mm_hot_num;
    }

    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
