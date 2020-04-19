package app.gate.door.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import app.gate.door.models.DAOBase;
import app.gate.door.models.DatabaseHelper;
import app.gate.door.models.UserEntity;

public class UserDAO extends DAOBase {
    public UserDAO(Context pContext) {
        super(pContext);
        open();
    }

    /**
     * @param user le UserEntity à ajouter à la base
     */
    public long ajouter(UserEntity user) {
        ContentValues value = new ContentValues();
        value.put(DatabaseHelper.U_NOM, user.getNom());
        value.put(DatabaseHelper.U_PRENOM, user.getPrenom());
        value.put(DatabaseHelper.U_LOGIN, user.getLogin());
        value.put(DatabaseHelper.U_PASSWORD, user.getPassword());
        value.put(DatabaseHelper.U_ROLE, user.getRole());
        long id =  mDb.insert(DatabaseHelper.USER_TABLE, null, value);
        Log.i( "UserDAO", "Ajouter invoked. id created = "+id );

        return  id;
    }

    /**
     * @param id l'identifiant du UserEntity à supprimer
     */
    public int supprimer(int id) {
        int res = mDb.delete(DatabaseHelper.USER_TABLE, DatabaseHelper.U_ID + " = ?", new String[] {String.valueOf(id)});
        Log.i( "UserDAO", "Supprimer invoked. affected row = "+res );

        return  res;
    }

    /**
     * @param user le UserEntity modifié
     */
    public int modifier(UserEntity user) {
        ContentValues value = new ContentValues();
        value.put(DatabaseHelper.U_NOM, user.getNom());
        value.put(DatabaseHelper.U_PRENOM, user.getPrenom());
        value.put(DatabaseHelper.U_LOGIN, user.getLogin());
        value.put(DatabaseHelper.U_PASSWORD, user.getPassword());
        value.put(DatabaseHelper.U_ROLE, user.getRole());
        int res =  mDb.update(DatabaseHelper.USER_TABLE, value, DatabaseHelper.U_ID  + " = ?", new String[] {String.valueOf(user.getIdUser())});
        Log.i( "UserDAO", "Modifier invoked. affected row = "+res );

        return  res;
    }

    /**
     * @param id l'identifiant du UserEntity à récupérer
     */
    public UserEntity selectionner(int id) {
        Cursor user = mDb.rawQuery("select * from " + DatabaseHelper.USER_TABLE + " where " + DatabaseHelper.U_ID +" = ?", new String[]{String.valueOf(id)});
        while (user.moveToNext()) {
            int idUser = user.getInt(0);
            String nom = user.getString(1);
            String prenom = user.getString(2);
            String login = user.getString(3);
            String pwd = user.getString(4);
            int role = user.getInt(5);
            UserEntity userEntity = new UserEntity (idUser, nom, prenom,login,pwd,role);
            Log.i( "UserDAO", "selectionner invoked. 1 row affected user = "+userEntity.toString());
            return userEntity;
        }
        user.close();
        Log.i( "UserDAO", "selectionner invoked. 0 row affected ");
        return null;

    }
    /**
     * Retourne le user connecté
     * @param login   le login du UserEntity à récupérer
     * @param pwd le mot de passe du login à récupérer
     */
    public UserEntity getLoggedUser(String login, String pwd) {
        Cursor user = mDb.rawQuery("select * from " + DatabaseHelper.USER_TABLE +
                " where " + DatabaseHelper.U_LOGIN +" = ?"+
                 "and "+ DatabaseHelper.U_PASSWORD + " = ?"
                , new String[]{login.trim(), pwd.trim()});
        while (user.moveToNext()) {
            int idUser = user.getInt(0);
            String nom = user.getString(1);
            String prenom = user.getString(2);
            String logintmp = user.getString(3);
            String password = user.getString(4);
            int role = user.getInt(5);
            UserEntity userEntity = new UserEntity (idUser, nom, prenom,logintmp,password,role);
            Log.i( "UserDAO", "getLoggedUser invoked. affected login = "+userEntity.getLogin()+" pwd = "+userEntity.getPassword() );
            return userEntity;
        }
        user.close();
        Log.i( "UserDAO", "getLoggedUser invoked. 0 row affected ");
        return null;

    }

    /**
     * Retourne tous les users
     */
    public ArrayList<UserEntity> getAllUsers() {
        ArrayList<UserEntity> userEntities = new ArrayList<>();

        Cursor user = mDb.query( DatabaseHelper.USER_TABLE ,
                new String[] { DatabaseHelper.U_ID,DatabaseHelper.U_NOM, DatabaseHelper.U_PRENOM,
                        DatabaseHelper.U_LOGIN, DatabaseHelper.U_PASSWORD, DatabaseHelper.U_ROLE},
                null, null, null, null, DatabaseHelper.U_NOM, "10" );
        user.moveToFirst();
        while( ! user.isAfterLast() ) {
            int idUser = user.getInt(0);
            String nom = user.getString(1);
            String prenom = user.getString(2);
            String logintmp = user.getString(3);
            String password = user.getString(4);
            int role = user.getInt(5);

            UserEntity userEntity = new UserEntity (idUser, nom, prenom,logintmp,password,role);
            userEntities.add( userEntity );
            user.moveToNext();
        }
        user.close();

        return userEntities;
    }
    /**
     * Retourne tous les users sans l'admin
     */
    public ArrayList<UserEntity> getOrdinaryUsers() {
        ArrayList<UserEntity> userEntities = new ArrayList<>();
        String[] args = { "0" };

        Cursor user = mDb.query( DatabaseHelper.USER_TABLE ,
                new String[] { DatabaseHelper.U_ID,DatabaseHelper.U_NOM, DatabaseHelper.U_PRENOM,
                        DatabaseHelper.U_LOGIN, DatabaseHelper.U_PASSWORD, DatabaseHelper.U_ROLE},
                DatabaseHelper.U_ROLE +" = ?", args, null, null, DatabaseHelper.U_NOM, "10" );
        user.moveToFirst();
        while( ! user.isAfterLast() ) {
            int idUser = user.getInt(0);
            String nom = user.getString(1);
            String prenom = user.getString(2);
            String logintmp = user.getString(3);
            String password = user.getString(4);
            int role = user.getInt(5);

            UserEntity userEntity = new UserEntity (idUser, nom, prenom,logintmp,password,role);
            userEntities.add( userEntity );
            user.moveToNext();
        }
        user.close();

        return userEntities;
    }

}
