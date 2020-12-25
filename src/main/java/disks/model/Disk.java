package disks.model;

import disks.dao.PersistentObject;

public class Disk implements PersistentObject {
    private Integer id;
    private DiskType diskType;
    private String title;
    private String description;
    private InformationType informationType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DiskType getDiskType() {
        return diskType;
    }

    public void setDiskType(DiskType diskType) {
        this.diskType = diskType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InformationType getInformationType() {
        return informationType;
    }

    public void setInformationType(InformationType informationType) {
        this.informationType = informationType;
    }
}
