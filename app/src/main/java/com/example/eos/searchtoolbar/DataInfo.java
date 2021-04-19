package com.example.eos.searchtoolbar;

/**
 * Created by lsy01 on 2015-11-08.
 */
public class DataInfo {//리스트뷰 아이템의 데이터 세팅을 위한 데이터 클래스.
    private String name;//빚쟁이 이름
    private String pnum;//빚쟁이 번호
    private int dept;//빚
    private boolean check_state;//체크여부

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck_state() {
        return check_state;
    }

    public void setCheck_state(boolean check_state) {
        this.check_state = check_state;
    }

    public int getDept() {
        return dept;
    }

    public void setDept(int dept) {
        this.dept = dept;
    }

    public String getPnum() {
        return pnum;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }
}
