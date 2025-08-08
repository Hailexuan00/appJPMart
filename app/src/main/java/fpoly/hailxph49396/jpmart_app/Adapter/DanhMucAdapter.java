package fpoly.hailxph49396.jpmart_app.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.hailxph49396.jpmart_app.DAO.DanhMucDAO;

import fpoly.hailxph49396.jpmart_app.DTO.DanhMucDTO;
import fpoly.hailxph49396.jpmart_app.R;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DanhMucDTO> list;
    private DanhMucDAO dao;

    public DanhMucAdapter(Context context, ArrayList<DanhMucDTO> list, DanhMucDAO dao) {
        this.context = context;
        this.list = list;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_danh_muc, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        DanhMucDTO dm = list.get(position);
        h.tvMaDM.setText("Mã: " + dm.getMaDanhMuc());
        h.tvTenDM.setText(dm.getTenDanhMuc());

        h.btnEdit.setOnClickListener(v -> {
            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_danh_muc, null);
            EditText edMa = dialogView.findViewById(R.id.edMaDM);
            EditText edTen = dialogView.findViewById(R.id.edTenDM);

            edMa.setText(dm.getMaDanhMuc());
            edMa.setEnabled(false);
            edTen.setText(dm.getTenDanhMuc());

            new AlertDialog.Builder(context)
                    .setTitle("Sửa Danh Mục")
                    .setView(dialogView)
                    .setPositiveButton("Lưu", (d, w) -> {
                        dm.setTenDanhMuc(edTen.getText().toString().trim());
                        if (dao.update(dm)) {
                            list.set(position, dm);
                            notifyItemChanged(position);
                            Toast.makeText(context, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

        h.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setMessage("Xóa danh mục này?")
                    .setPositiveButton("Xóa", (d, w) -> {
                        if (dao.delete(dm.getMaDanhMuc())) {
                            list.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaDM, tvTenDM;
        ImageButton btnEdit, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaDM = itemView.findViewById(R.id.tvMaDM);
            tvTenDM = itemView.findViewById(R.id.tvTenDM);
            btnEdit = itemView.findViewById(R.id.btnEditDM);
            btnDelete = itemView.findViewById(R.id.btnDeleteDM);
        }
    }
}
