package fpoly.hailxph49396.jpmart_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.ChucNang.SanPhamActivity;
import fpoly.hailxph49396.jpmart_app.DAO.SanPhamDAO;
import fpoly.hailxph49396.jpmart_app.DTO.SanPhamDTO;
import fpoly.hailxph49396.jpmart_app.R;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SanPhamDTO> list;
    private SanPhamDAO dao;

    public SanPhamAdapter(Context context, ArrayList<SanPhamDTO> list, SanPhamDAO dao) {
        this.context = context;
        this.list = list;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_san_pham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        SanPhamDTO sp = list.get(position);
        h.tvTen.setText(sp.getTenSanPham());
        h.tvGia.setText(String.format("%,d đ", sp.getGia()));
        h.tvTon.setText("Tồn kho: " + sp.getSoLuong());

        h.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa sản phẩm")
                    .setMessage("Bạn có chắc muốn xóa?")
                    .setPositiveButton("Xóa", (d, i) -> {
                        dao.delete(sp.getMaSanPham());
                        list.remove(position);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

//        h.btnEdit.setOnClickListener(v -> {
//            if (context instanceof SanPhamActivity) {
//                ((SanPhamActivity) context).openDialog(sp);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen, tvGia, tvTon;
        ImageView btnEdit, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTenSP);
            tvGia = itemView.findViewById(R.id.tvGiaSP);
            tvTon = itemView.findViewById(R.id.tvTonKho);
            btnEdit = itemView.findViewById(R.id.btnEditSP);
            btnDelete = itemView.findViewById(R.id.btnDeleteSP);
        }
    }
}

