package fpoly.hailxph49396.jpmart_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import fpoly.hailxph49396.jpmart_app.R;

public class ThongKeKhachHangAdapter extends RecyclerView.Adapter<ThongKeKhachHangAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HashMap<String, Object>> list;

    public ThongKeKhachHangAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_thongke_khachhang, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, Object> item = list.get(position);
        holder.txtTenKH.setText(item.get("TenKhachHang").toString());

        int tongChiTieu = Integer.parseInt(item.get("TongChiTieu").toString());
        String tongTienFormatted = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(tongChiTieu);

        holder.txtChiTieu.setText("Tổng chi tiêu: " + tongTienFormatted);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenKH, txtChiTieu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenKH = itemView.findViewById(R.id.txtTenKH);
            txtChiTieu = itemView.findViewById(R.id.txtChiTieu);
        }
    }
}
