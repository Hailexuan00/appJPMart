package fpoly.hailxph49396.jpmart_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import fpoly.hailxph49396.jpmart_app.R;

public class ThongKeSanPhamAdapter extends RecyclerView.Adapter<ThongKeSanPhamAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HashMap<String, Object>> list;

    public ThongKeSanPhamAdapter(Context context, ArrayList<HashMap<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thong_ke_san_pham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, Object> map = list.get(position);
        holder.tvTenSanPham.setText("Sản phẩm: " + map.get("TenSanPham"));
        holder.tvSoLuong.setText("Số lượng bán: " + map.get("TongSoLuong"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSanPham, tvSoLuong;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSanTopPham);
            tvSoLuong = itemView.findViewById(R.id.tvTopSoLuongBan);
        }
    }
}
