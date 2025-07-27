package fpoly.hailxph49396.jpmart_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.DTO.MenuDTO;
import fpoly.hailxph49396.jpmart_app.R;

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MenuDTO> list;

    public MenuAdapter(Context context, ArrayList<MenuDTO> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MenuDTO getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuDTO item = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
        }
        TextView tvTitle = convertView.findViewById(R.id.tvMenuTitle);
        ImageView imgIcon = convertView.findViewById(R.id.imgMenuIcon);

        tvTitle.setText(item.getTitle());
        imgIcon.setImageResource(item.getIcon());

        return convertView;
    }
}
