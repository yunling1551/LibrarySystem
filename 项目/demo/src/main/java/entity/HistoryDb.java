package entity;

import java.util.Date;

/**
 * 借阅历史记录
 */
public class HistoryDb {
    private Integer hid;//历史记录ID
    private Integer uid;//用户ID
    private String name;//用户名
    private String account;//用户账号
    private Integer bid;//图书ID
    private String bookName;//图书名
    private Date beginTime;//开始时间
    private Date endTime;//还书时间
    private Integer status;//图书状态

    public Integer getHid() {
        return hid;
    }

    public void setHid(Integer hid) {
        this.hid = hid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HistoryDb{" +
                "hid=" + hid +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", bid=" + bid +
                ", bookName='" + bookName + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }
}
