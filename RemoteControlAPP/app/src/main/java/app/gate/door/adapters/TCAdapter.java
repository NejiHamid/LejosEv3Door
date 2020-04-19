package app.gate.door.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.gate.door.R;
import app.gate.door.models.TeleCEntity;

public class TCAdapter extends BaseAdapter {
    Context context;
    ArrayList<TeleCEntity> arrayList;

    public TCAdapter(Context context, ArrayList<TeleCEntity> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.tc_custom_list_view,null);
        TextView txt_id = (TextView) convertView.findViewById(R.id.txtvIdTC);
        TextView txt_libelle = (TextView) convertView.findViewById(R.id.txtvLibelle);
        TextView txt_proprietaire= (TextView) convertView.findViewById(R.id.txtvProprietaire);


        TeleCEntity teleCEntity = arrayList.get(position);

        txt_id.setText(String.valueOf(teleCEntity.getIdTC()));
        txt_libelle.setText(teleCEntity.getLibelle());
        txt_proprietaire.setText(String.valueOf(teleCEntity.getProprietaire()));


        return convertView;
    }
}
