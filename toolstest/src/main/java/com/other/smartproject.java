package com.other;

/**
 * 作者：jiangxiaolin on 2020/6/30
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class smartproject {
    private String testname;
    private int iamgeurl;

    public smartproject() {
    }

    public smartproject(String testname,int iamgeurl) {
        this.testname = testname;
        this.iamgeurl = iamgeurl;
    }


    public void setIamgeurl(int iamgeurl) {
        this.iamgeurl = iamgeurl;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }

    public int getIamgeurl() {
        return iamgeurl;
    }

    public String getTestname() {
        return testname;
    }
}
