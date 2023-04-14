package local.yamato.android.prefmemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class DataAccess {
    public static Memo findByPK(SQLiteDatabase db, int id) {
        String sql = "SELECT * FROM memos WHERE _id = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        Memo memo = null;
        if(cursor.moveToFirst()) {
            memo = new Memo();
            int idxName = cursor.getColumnIndex("name");
            String name = cursor.getString(idxName);
            int idxContent = cursor.getColumnIndex("content");
            String content = cursor.getString(idxContent);
            memo.setId(id);
            memo.setName(name);
            memo.setContent(content);
        }
        return memo;
    }
    public static boolean findRowByPK(SQLiteDatabase db, int id) {
        String sql = "SELECT COUNT(*) AS count FROM memos WHERE _id = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        boolean result = false;
        if(cursor.moveToFirst()) {
            int idxCount = cursor.getColumnIndex("count");
            int count = cursor.getInt(idxCount);
            if(count >= 1) {
                result = true;
            }

        }
        return result;
    }
    public static int update(SQLiteDatabase db, long id, String name, String content) {
        String sql = "UPDATE memos SET name = ?, content = ? WHERE _id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, name);
        stmt.bindString(2, content);
        stmt.bindLong(3, id);
        int result = stmt.executeUpdateDelete();
        return result;
    }
    public static long insert(SQLiteDatabase db, long id, String name, String content) {
        String sql = "INSERT INTO memos (_id, name, content) VALUES (?, ?, ?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindLong(1, id);
        stmt.bindString(2, name);
        stmt.bindString(3, content);
        long insertedId = stmt.executeInsert();
        return insertedId;
    }
}
