package fpoly.hailxph49396.jpmart_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.DTO.SanPhamDTO;
import fpoly.hailxph49396.jpmart_app.R;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<SanPhamDTO> list;

    public interface OnSanPhamClickListener {
        void onAddToCart(SanPhamDTO sanPham);
        void onEdit(SanPhamDTO sanPham);
        void onDelete(SanPhamDTO sanPham);
    }

    private final OnSanPhamClickListener listener;

    public SanPhamAdapter(Context context, ArrayList<SanPhamDTO> list, OnSanPhamClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public void setData(ArrayList<SanPhamDTO> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_san_pham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.ViewHolder holder, int position) {
        SanPhamDTO sp = list.get(position);
        holder.tvTenSP.setText(sp.getTenSanPham());
        holder.tvGia.setText(String.valueOf(sp.getGia()) + " đ");
        holder.tvSoLuong.setText("Số lượng: " + sp.getSoLuong());


        holder.btnAddCart.setOnClickListener(v -> {
            if (listener != null) listener.onAddToCart(sp);
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEdit(sp);
        });
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(sp);
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSP, tvGia, tvSoLuong;
        ImageButton btnAddCart, btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGia = itemView.findViewById(R.id.tvGiaSP);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            btnAddCart = itemView.findViewById(R.id.btnAddCart);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
