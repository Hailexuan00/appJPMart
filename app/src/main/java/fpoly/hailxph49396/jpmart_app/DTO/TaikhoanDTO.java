package fpoly.hailxph49396.jpmart_app.DTO;

public class TaikhoanDTO {
    private String username;
    private String password;
    private String ten;
    private String ho_va_ten_dem;
    private String gioi_tinh;
    private String so_dien_thoai;
    private String email;
    private String dia_chi;

    public TaikhoanDTO() {
    }

    public TaikhoanDTO(String username, String password, String ten, String ho_va_ten_dem, String gioi_tinh, String so_dien_thoai, String email, String dia_chi) {
        this.username = username;
        this.password = password;
        this.ten = ten;
        this.ho_va_ten_dem = ho_va_ten_dem;
        this.gioi_tinh = gioi_tinh;
        this.so_dien_thoai = so_dien_thoai;
        this.email = email;
        this.dia_chi = dia_chi;
    }

    // Getters & Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHo_va_ten_dem() {
        return ho_va_ten_dem;
    }

    public void setHo_va_ten_dem(String ho_va_ten_dem) {
        this.ho_va_ten_dem = ho_va_ten_dem;
    }

    public String getGioi_tinh() {
        return gioi_tinh;
    }

    public void setGioi_tinh(String gioi_tinh) {
        this.gioi_tinh = gioi_tinh;
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }
}
