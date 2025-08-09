package fpoly.hailxph49396.jpmart_app.DTO;

public class GioHangDTO {
    private int id;
    private String maSanPham;
    private String tenSanPham;
    private int soLuong;
    private int gia;
    private String donViTinh;
    private String ngayNhap;

    public GioHangDTO() {
    }

    public GioHangDTO(int id, String maSanPham, String tenSanPham, int soLuong, int gia, String donViTinh, String ngayNhap) {
        this.id = id;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.gia = gia;
        this.donViTinh = donViTinh;
        this.ngayNhap = ngayNhap;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }

    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public int getGia() { return gia; }
    public void setGia(int gia) { this.gia = gia; }

    public String getDonViTinh() { return donViTinh; }
    public void setDonViTinh(String donViTinh) { this.donViTinh = donViTinh; }

    public String getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(String ngayNhap) { this.ngayNhap = ngayNhap; }
}
