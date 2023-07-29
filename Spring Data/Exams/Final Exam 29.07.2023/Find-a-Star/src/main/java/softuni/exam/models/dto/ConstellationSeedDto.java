package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ConstellationSeedDto {

    @Expose
    @NonNull
    @NotEmpty
    @Size(min = 3, max = 20)
    private String name;

    @Expose
    @NonNull
    @NotEmpty
    @Size(min = 5)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
