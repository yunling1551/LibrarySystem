package entity;

/**
 * 用户实体类
 */
public class UserDb {
    private Integer uid;//id
    private String account;//账号
    private String password;//密码
    private String name;//姓名
    private String phone;//手机
    private Integer times;//借阅量
    private Integer lendNum;//借阅天数
    private Integer maxNum;//可借阅数量
    private Integer role;//用户权限

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getLendNum() {
        return lendNum;
    }

    public void setLendNum(Integer lendNum) {
        this.lendNum = lendNum;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDb{" +
                "uid=" + uid +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", times=" + times +
                ", lendNum=" + lendNum +
                ", maxNum=" + maxNum +
                ", role=" + role +
                '}';
    }
}
