package com.example.administrator.internetpassport;

class LoginInfo {
    private static final LoginInfo ourInstance = new LoginInfo();
    public Boolean m_logined = false;

    static LoginInfo getInstance() {
        return ourInstance;
    }

    private LoginInfo() {

    }
}
