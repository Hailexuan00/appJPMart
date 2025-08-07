package fpoly.hailxph49396.jpmart_app.DTO;

public class NhanVienDTO {
    private String maNhanVien;
    private String tenNhanVien;
    private String diaChi;
    private int chucVu; // 0: Nhân viên, 1: Quản lý
    private double luong;
    private String matKhau;

    public NhanVienDTO() {}

    public NhanVienDTO(String maNhanVien, String tenNhanVien, String diaChi, int chucVu, double luong, String matKhau) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.diaChi = diaChi;
        this.chucVu = chucVu;
        this.luong = luong;
        this.matKhau = matKhau;
    }

    // Getters & Setters

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getChucVu() {
        return chucVu;
    }

    public void setChucVu(int chucVu) {
        this.chucVu = chucVu;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
