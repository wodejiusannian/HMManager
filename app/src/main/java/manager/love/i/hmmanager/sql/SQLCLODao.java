package manager.love.i.hmmanager.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import manager.love.i.hmmanager.bean.ShopTag;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class SQLCLODao {
    private static final String TAG = "SQLCLODao";
    private SQLHelper helper;

    public SQLCLODao(Context context) {
        helper = new SQLHelper(context);
    }

    public void addCloTAG(ShopTag.BodyBean bean) {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        String sqlStr = "insert into clo(type,yiji_name,yiji_code,erji_name,erji_code,erji_jc)values(?,?,?,?,?,?)";
        sqLiteDatabase.execSQL(sqlStr, new Object[]{bean.getType(), bean.getYiji_name(), bean.getYiji_code(), bean.getErji_name(), bean.getErji_code(), bean.getErji_jc()});
        sqLiteDatabase.close();
    }

    public List<ShopTag.BodyBean> selectCloTAG(String type) {
        List<ShopTag.BodyBean> mData = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sqlStr = "checkbox_style_select * from clo  where type = " + type;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sqlStr, null);
            while (cursor.moveToNext()) {
                ShopTag.BodyBean bean = new ShopTag.BodyBean();
                bean.setType(cursor.getString(0));
                bean.setYiji_name(cursor.getString(1));
                bean.setYiji_code(cursor.getString(2));
                bean.setErji_name(cursor.getString(3));
                bean.setErji_code(cursor.getString(4));
                bean.setErji_jc(cursor.getString(5));
                mData.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //关闭相应的资源
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return mData;
    }


    public List<ShopTag.BodyBean> selectSort() {
        List<ShopTag.BodyBean> mData = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sqlStr = "checkbox_style_select * from clo where type = '1' GROUP BY yiji_code";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sqlStr, null);
            while (cursor.moveToNext()) {
                ShopTag.BodyBean bean = new ShopTag.BodyBean();
                bean.setType(cursor.getString(0));
                bean.setYiji_name(cursor.getString(1));
                bean.setYiji_code(cursor.getString(2));
                bean.setErji_name(cursor.getString(1));
                bean.setErji_code(cursor.getString(2));
                bean.setErji_jc(cursor.getString(1));
                mData.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //关闭相应的资源
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return mData;
    }


    public List<ShopTag.BodyBean> selectStyle(String yji_name) {
        List<ShopTag.BodyBean> mData = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sqlStr = "checkbox_style_select * from clo where yiji_name = " + "'" + yji_name + "'";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sqlStr, null);
            while (cursor.moveToNext()) {
                ShopTag.BodyBean bean = new ShopTag.BodyBean();
                bean.setType(cursor.getString(0));
                bean.setYiji_name(cursor.getString(1));
                bean.setYiji_code(cursor.getString(2));
                bean.setErji_name(cursor.getString(3));
                bean.setErji_code(cursor.getString(4));
                bean.setErji_jc(cursor.getString(3));
                mData.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //关闭相应的资源
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return mData;
    }
}
