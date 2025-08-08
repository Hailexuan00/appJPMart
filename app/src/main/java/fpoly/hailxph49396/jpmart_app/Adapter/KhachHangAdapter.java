package fpoly.hailxph49396.jpmart_app.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.ChucNang.KhachHangActivity;
import fpoly.hailxph49396.jpmart_app.DAO.KhachHangDAO;
import fpoly.hailxph49396.jpmart_app.DTO.KhachHangDTO;

import fpoly.hailxph49396.jpmart_app.R;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder> {
    Context context;
    ArrayList<KhachHangDTO> list;
    KhachHangDAO dao;

    public KhachHangAdapter(Context context, ArrayList<KhachHangDTO> list) {
        this.context = context;
        this.list = list;
        dao = new KhachHangDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_khachhang, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        KhachHangDTO kh = list.get(position);
        h.tvMaKH.setText("Mã KH: " + kh.getMaKhachHang());
        h.tvTenKH.setText("Tên KH: " + kh.getTenKhachHang());
        h.tvDiaChi.setText("Địa chỉ: " + kh.getDiaChi());
        h.tvSdt.setText("SĐT: " + kh.getSoDienThoai());
        h.tvEmail.setText("Email: " + kh.getEmail());

        h.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa khách hàng")
                    .setMessage("Bạn có chắc muốn xóa?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        if (dao.delete(kh.getMaKhachHang())) {
                            list.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

        h.btnEdit.setOnClickListener(v -> {
            if (context instanceof KhachHangActivity) {
                ((KhachHangActivity) context).openDialog(kh);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaKH, tvTenKH, tvDiaChi, tvSdt, tvEmail;
        ImageView btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaKH = itemView.findViewById(R.id.tvMaKH);
            tvTenKH = itemView.findViewById(R.id.tvTenKH);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            tvSdt = itemView.findViewById(R.id.tvSdt);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
