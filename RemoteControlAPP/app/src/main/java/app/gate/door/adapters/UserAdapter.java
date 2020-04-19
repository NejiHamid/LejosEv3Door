package app.gate.door.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.gate.door.R;
import app.gate.door.models.UserEntity;

public class UserAdapter extends BaseAdapter {
    Context context;
    ArrayList<UserEntity> arrayList;

    public UserAdapter(Context context, ArrayList<UserEntity> arrayList) {
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
        convertView = inflater.inflate(R.layout.user_custom_list_view,null);
        TextView txt_id = (TextView) convertView.findViewById(R.id.txtvIdUser);
        TextView txt_nom = (TextView) convertView.findViewById(R.id.txtvNom);
        TextView txt_prenom= (TextView) convertView.findViewById(R.id.txtvPrenom);
        TextView txt_login = (TextView) convertView.findViewById(R.id.txtvLogin);


        UserEntity userEntity = arrayList.get(position);

        txt_id.setText(String.valueOf(userEntity.getIdUser()));
        txt_nom.setText(userEntity.getNom());
        txt_prenom.setText(userEntity.getPrenom());
        txt_login.setText(userEntity.getLogin());


        return convertView;
    }
}
