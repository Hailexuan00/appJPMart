package fpoly.hailxph49396.jpmart_app.DTO;

public class HoaDonDTO {
    private String maHoaDon;
    private String tenNhanVien;
    private String tenKhachHang;
    private String ngayLap;
    private int tongTien;

    public HoaDonDTO(String maHoaDon, String tenNhanVien, String tenKhachHang, String ngayLap, int tongTien) {
        this.maHoaDon = maHoaDon;
        this.tenNhanVien = tenNhanVien;
        this.tenKhachHang = tenKhachHang;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
    }

    public String getMaHoaDon() { return maHoaDon; }
    public String getTenNhanVien() { return tenNhanVien; }
    public String getTenKhachHang() { return tenKhachHang; }
    public String getNgayLap() { return ngayLap; }
    public int getTongTien() { return tongTien; }
}
