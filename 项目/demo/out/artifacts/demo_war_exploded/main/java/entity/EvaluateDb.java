package entity;

import java.util.Date;

public class EvaluateDb {
    private Integer pid;
    private Integer uid;
    private String name;
    private String account;
    private String title;
    private String book;
    private String content;
    private Date time;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "EvaluateDb{" +
                "pid=" + pid +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", title='" + title + '\'' +
                ", book='" + book + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}
