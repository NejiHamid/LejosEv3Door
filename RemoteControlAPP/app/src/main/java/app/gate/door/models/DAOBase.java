package app.gate.door.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAOBase {
    // Nous sommes à la première version de la base
    // Si je décide de la mettre à jour, il faudra changer cet attribut
    protected final static int VERSION = 2;

    // Le nom du fichier qui représente ma base
    protected final static String NOM = "telecommande.db";

    protected SQLiteDatabase mDb = null;
    protected DatabaseHelper mHelper = null;

    public DAOBase(Context pContext) {
        this.mHelper = new DatabaseHelper(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la derni�re base puisque getWritableDatabase s'en charge
        mDb = mHelper.getWritableDatabase();
        return mDb;
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }
}

