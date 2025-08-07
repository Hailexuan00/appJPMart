package fpoly.hailxph49396.jpmart_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.DTO.NhanVienDTO;
import fpoly.hailxph49396.jpmart_app.R;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<NhanVienDTO> list;

    public interface OnNhanVienListener {
        void onEdit(NhanVienDTO nv);
        void onDelete(NhanVienDTO nv);
    }

    private final OnNhanVienListener listener;

    public NhanVienAdapter(Context context, ArrayList<NhanVienDTO> list, OnNhanVienListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NhanVienAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nhanvien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienAdapter.ViewHolder holder, int position) {
        NhanVienDTO nv = list.get(position);
        DecimalFormat df = new DecimalFormat("#,###");

        holder.tvMaTen.setText(nv.getMaNhanVien() + " - " + nv.getTenNhanVien());
        holder.tvDiaChi.setText(nv.getDiaChi());
        holder.tvLuong.setText(df.format(nv.getLuong()) + " đ");
        holder.tvChucVu.setText(nv.getChucVu() == 1 ? "Quản lý" : "Nhân viên");

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(nv));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(nv));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaTen, tvDiaChi, tvLuong, tvChucVu;
        ImageView btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaTen = itemView.findViewById(R.id.tvMaTen);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            tvLuong = itemView.findViewById(R.id.tvLuong);
            tvChucVu = itemView.findViewById(R.id.tvChucVu);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
