package com.example.administrator.internetpassport;

class LoginInfo {
    private static final LoginInfo ourInstance = new LoginInfo();
    public Boolean m_logined = false;
    public String m_verify_no = null;

    static LoginInfo getInstance() {
        return ourInstance;
    }

    private LoginInfo() {

    }
}
