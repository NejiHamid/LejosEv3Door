package app.gate.door.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Table User
    public static  final String USER_TABLE = " users";
    public static  final String U_ID = "ID";
    public static  final String U_NOM = "nom";
    public static  final String U_PRENOM = "prenom";
    public static  final String U_LOGIN = "login";
    public static  final String U_PASSWORD = "password";
    public static  final String U_ROLE = "role"; //1 = admin et 0 = user


    public static  final String USER_TABLE_CREATE = "CREATE TABLE " + USER_TABLE +
            "("+
            U_ID+" integer primary key autoincrement,"+
            U_NOM+" text not null,"+
            U_PRENOM+" text not null,"+
            U_LOGIN+" text not null,"+
            U_PASSWORD+" text not null,"+
            U_ROLE+" integer not null"+
            ")" ;

    public static  final String USER_TABLE_DROP = "DROP TABLE IF EXISTS "+USER_TABLE+";";

    //Ajout d'un admin par defaut
    public static final String USER_TABLE_INSERT = "INSERT INTO " + USER_TABLE +
            " (nom, prenom, login, password, role) " +
            " VALUES('BAH', 'Amadou','admin', '1234', 1)";

    //table télécommande
    public static  final String TELECOMMANDE_TABLE= " telecommande";
    public static  final String T_ID = "ID";
    public static  final String T_LIBELLE = "libelle";
    public static  final String T_MAC = "adrMAC";
    public static  final String T_PROPRIETAIRE= "proprietaire"; //id du propriétaire

    public static  final String TELECOMMANDE_TABLE_CREATE = "CREATE TABLE " + TELECOMMANDE_TABLE+
            "("+
            T_ID+" integer primary key autoincrement,"+
            T_LIBELLE+" text not null,"+
            T_MAC+" text not null ,"+
            T_PROPRIETAIRE+ " integer not null"+
            ")" ;


    public static  final String TELECOMMANDE_TABLE_DROP = "DROP TABLE IF EXISTS "+TELECOMMANDE_TABLE+";";

    public DatabaseHelper(@Nullable Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(USER_TABLE_INSERT);
        db.execSQL(TELECOMMANDE_TABLE_CREATE);
        Log.i("DATABASE","onCreate invoked");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL( USER_TABLE_DROP );
        db.execSQL( TELECOMMANDE_TABLE_DROP );
        this.onCreate( db );
        Log.i( "DATABASE", "onUpgrade invoked" );

    }




}
