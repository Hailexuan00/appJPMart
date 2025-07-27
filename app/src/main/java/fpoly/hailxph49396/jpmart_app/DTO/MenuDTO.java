package fpoly.hailxph49396.jpmart_app.DTO;

public class MenuDTO {
    private String title;
    private int icon;

    public MenuDTO(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }
}
