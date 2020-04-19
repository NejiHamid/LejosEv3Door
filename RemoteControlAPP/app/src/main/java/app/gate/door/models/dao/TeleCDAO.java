package app.gate.door.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import app.gate.door.models.DAOBase;
import app.gate.door.models.DatabaseHelper;
import app.gate.door.models.TeleCEntity;

public class TeleCDAO extends DAOBase {
    public TeleCDAO(Context pContext) {
        super(pContext);
        open();
    }

    /**
     * @param teleC le TeleCEntity à ajouter à la base
     */
    public long ajouter(TeleCEntity teleC) {
        ContentValues value = new ContentValues();
        value.put(DatabaseHelper.T_LIBELLE, teleC.getLibelle());
        value.put(DatabaseHelper.T_MAC, teleC.getAdrMAC());
        value.put(DatabaseHelper.T_PROPRIETAIRE, teleC.getProprietaire());
        long id =  mDb.insert(DatabaseHelper.TELECOMMANDE_TABLE, null, value);
        Log.i( "TeleCDAO", "Ajouter invoked. id created = "+id );

        return  id;
    }

    /**
     * @param id l'identifiant du TeleCEntity à supprimer
     */
    public int supprimer(int id) {
        int res= mDb.delete(DatabaseHelper.TELECOMMANDE_TABLE, DatabaseHelper.T_ID + " = ?", new String[] {String.valueOf(id)});
        Log.i( "TeleCDAO", "Supprimer invoked. affected row = "+res );

        return  res;
    }

    /**
     * @param teleC le TeleCEntity modifié
     */
    public int modifier(TeleCEntity teleC) {
        ContentValues value = new ContentValues();
        value.put(DatabaseHelper.T_LIBELLE, teleC.getLibelle());
        value.put(DatabaseHelper.T_MAC, teleC.getAdrMAC());
        value.put(DatabaseHelper.T_PROPRIETAIRE, teleC.getProprietaire());
        int res = mDb.update(DatabaseHelper.TELECOMMANDE_TABLE, value, DatabaseHelper.T_ID  + " = ?", new String[] {String.valueOf(teleC.getIdTC())});
        Log.i( "TeleCDAO", "Modifier invoked. affected row = "+res );

        return  res;
    }

    /**
     * @param id l'identifiant du TeleCEntity à récupérer
     */
    public TeleCEntity getTCById(int id) {
        Cursor teleC = mDb.rawQuery("select * from " + DatabaseHelper.TELECOMMANDE_TABLE + " where " + DatabaseHelper.T_ID +" = ?", new String[]{String.valueOf(id)});
        while (teleC.moveToNext()) {
            int idTC = teleC.getInt(0);
            String libelle = teleC.getString(1);
            String adrMAC = teleC.getString(2);
            int proprietaire = teleC.getInt(3);
            TeleCEntity teleCEntity = new TeleCEntity(idTC, libelle, adrMAC,proprietaire);
            Log.i( "TeleCDAO", "getTCById invoked. 1 row affected TC = "+teleCEntity.toString());
            return teleCEntity;
        }
        teleC.close();
        Log.i( "TeleCDAO", "getTCById invoked. 0 row affected ");
        return null;

    }
    /**
     * Retourne le teleC par user
     * @param proprietaire   l'id du proprio du TeleCEntity à récupérer
     */
    public TeleCEntity getLoggedUserTC(int proprietaire) {
        Cursor teleC = mDb.rawQuery("select * from " + DatabaseHelper.TELECOMMANDE_TABLE +
                        " where " + DatabaseHelper.T_PROPRIETAIRE +" = ?"
                         , new String[]{String.valueOf(proprietaire)});
        while (teleC.moveToNext()) {
            int idTC = teleC.getInt(0);
            String libelle = teleC.getString(1);
            String adrMAC = teleC.getString(2);
            TeleCEntity teleCEntity = new TeleCEntity(idTC, libelle, adrMAC, proprietaire);
            Log.i( "TeleCDAO", "getLoggedUserTC invoked. affected login = "+teleCEntity.getIdTC()+" pwd = "+teleCEntity.getAdrMAC() );
            return teleCEntity;
        }
        teleC.close();
        Log.i( "TeleCDAO", "getLoggedUserTC invoked. 0 row affected ");
        return null;

    }

    /**
     * Retourne toutes les Telecommandes
     */
    public ArrayList<TeleCEntity> getAllTC() {
        ArrayList<TeleCEntity> teleCEntities = new ArrayList<>();

        Cursor teleC = mDb.query( DatabaseHelper.TELECOMMANDE_TABLE ,
                new String[] { DatabaseHelper.T_ID,DatabaseHelper.T_LIBELLE, DatabaseHelper.T_MAC,
                        DatabaseHelper.T_PROPRIETAIRE},
                null, null, null, null, DatabaseHelper.T_LIBELLE, "10" );
        teleC.moveToFirst();
        while( ! teleC.isAfterLast() ) {
            int idTC = teleC.getInt(0);
            String libelle = teleC.getString(1);
            String adrMAC = teleC.getString(2);
            int proprietaire = teleC.getInt(3);

            TeleCEntity teleCEntity = new TeleCEntity(idTC, libelle, adrMAC,proprietaire);
            teleCEntities.add( teleCEntity );
            teleC.moveToNext();
        }
        teleC.close();

        return teleCEntities;
    }
}
