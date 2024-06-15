package entity;

/**
 * 图书类别
 */
public class TypeDb {
    private Integer tid;//类别ID
    private String typeName;//类别名


    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "TypeDb[tid=" + tid + ", typeName=" + typeName + "]";
    }

}
