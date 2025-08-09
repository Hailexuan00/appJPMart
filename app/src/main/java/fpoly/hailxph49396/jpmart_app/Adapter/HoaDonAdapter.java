package fpoly.hailxph49396.jpmart_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.DAO.HoaDonDAO;
import fpoly.hailxph49396.jpmart_app.DTO.HoaDonDTO;
import fpoly.hailxph49396.jpmart_app.R;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HoaDonDTO> list;
    private HoaDonDAO hoaDonDAO;

    public HoaDonAdapter(Context context, ArrayList<HoaDonDTO> list) {
        this.context = context;
        this.list = list;
        hoaDonDAO = new HoaDonDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hoa_don, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDonDTO hd = list.get(position);
        holder.tvMaHD.setText("Mã hóa đơn: " + hd.getMaHoaDon());
        holder.tvTenNV.setText("Tên nhân viên: " + hd.getTenNhanVien());
        holder.tvTenKH.setText("Tên khách hàng: " + hd.getTenKhachHang());
        holder.tvNgayLap.setText("Ngày lập: " + hd.getNgayLap());
        holder.tvTongTien.setText("Tổng tiền: " + hd.getTongTien());

        holder.btnDelete.setOnClickListener(v -> {
            hoaDonDAO.deleteHoaDon(hd.getMaHoaDon());
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, list.size());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaHD, tvTenNV, tvTenKH, tvNgayLap, tvTongTien;
        ImageView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaHD = itemView.findViewById(R.id.tvMaHD);
            tvTenNV = itemView.findViewById(R.id.tvTenNV);
            tvTenKH = itemView.findViewById(R.id.tvTenKH);
            tvNgayLap = itemView.findViewById(R.id.tvNgayLap);
            tvTongTien = itemView.findViewById(R.id.tvTongTien);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
