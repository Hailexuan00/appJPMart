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

import fpoly.hailxph49396.jpmart_app.DAO.GioHangDAO;
import fpoly.hailxph49396.jpmart_app.DTO.GioHangDTO;
import fpoly.hailxph49396.jpmart_app.R;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<GioHangDTO> list;
    private final GioHangDAO gioHangDAO;

    public GioHangAdapter(Context context, ArrayList<GioHangDTO> list) {
        this.context = context;
        this.list = list;
        this.gioHangDAO = new GioHangDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gio_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHangDTO item = list.get(position);
        holder.tvTenSP.setText(item.getTenSanPham());
        holder.tvSoLuong.setText("SL: " + item.getSoLuong());
        holder.tvGia.setText(item.getGia() + " đ");
        holder.tvThanhTien.setText("Tổng: " + item.getThanhTien() + " đ");

        holder.btnXoa.setOnClickListener(v -> {
            int kq = gioHangDAO.xoaSanPham(item.getMaSanPham());
            if (kq > 0) {
                list.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSP, tvSoLuong, tvGia, tvThanhTien;
        ImageButton btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSP = itemView.findViewById(R.id.tvTenSP_Gio);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong_Gio);
            tvGia = itemView.findViewById(R.id.tvGiaSP_Gio);
            tvThanhTien = itemView.findViewById(R.id.tvThanhTien);
            btnXoa = itemView.findViewById(R.id.btnXoaGio);
        }
    }
}
