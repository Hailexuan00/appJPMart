package fpoly.hailxph49396.jpmart_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.DAO.GioHangDAO;
import fpoly.hailxph49396.jpmart_app.DTO.GioHangDTO;
import fpoly.hailxph49396.jpmart_app.R;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GioHangDTO> list;
    private GioHangDAO gioHangDAO;
    private OnCartChangeListener listener;

    public interface OnCartChangeListener {
        void onCartUpdated();
    }

    public GioHangAdapter(Context context, ArrayList<GioHangDTO> list, GioHangDAO gioHangDAO, OnCartChangeListener listener) {
        this.context = context;
        this.list = (list != null) ? list : new ArrayList<>();
        this.listener = listener;
        this.gioHangDAO = new GioHangDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_gio_hang, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHangDTO item = list.get(position);
        DecimalFormat df = new DecimalFormat("#,###");

        holder.tvTenSP.setText(item.getTenSanPham());
        holder.tvGia.setText(df.format(item.getGia()) + " đ");
        holder.tvSoLuong.setText(String.valueOf(item.getSoLuong()));

        holder.btnTang.setOnClickListener(v -> {
            int soLuongMoi = item.getSoLuong() + 1;
            item.setSoLuong(soLuongMoi);
            gioHangDAO.updateSoLuong(item.getId(), soLuongMoi);
            notifyItemChanged(position);
            if (listener != null) listener.onCartUpdated();
        });

        holder.btnGiam.setOnClickListener(v -> {
            if (item.getSoLuong() > 1) {
                int soLuongMoi = item.getSoLuong() - 1;
                item.setSoLuong(soLuongMoi);
                gioHangDAO.updateSoLuong(item.getId(), soLuongMoi);
                notifyItemChanged(position);
                if (listener != null) listener.onCartUpdated();
            }
        });

        holder.btnXoa.setOnClickListener(v -> {
            gioHangDAO.delete(item.getId());
            list.remove(position);
            notifyItemRemoved(position);
            if (listener != null) listener.onCartUpdated();
        });
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSP, tvGia, tvSoLuong;
        ImageButton btnTang, btnGiam, btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            btnTang = itemView.findViewById(R.id.btnTang);
            btnGiam = itemView.findViewById(R.id.btnGiam);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}
