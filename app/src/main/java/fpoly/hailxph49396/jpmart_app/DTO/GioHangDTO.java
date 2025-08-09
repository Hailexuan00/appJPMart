package fpoly.hailxph49396.jpmart_app.DTO;

public class GioHangDTO {
    private String maSanPham;
    private String tenSanPham;
    private int soLuong;
    private int gia;

    public GioHangDTO() {
    }

    public GioHangDTO(String maSanPham, String tenSanPham, int soLuong, int gia) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getThanhTien() {
        return gia * soLuong;
    }
}
