package softuni.exam.instagraphlite.models.DTO;

public class ExportPictureDto {

    private Double size;

    private String path;

    public ExportPictureDto(Double size, String path) {
        this.size = size;
        this.path = path;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
