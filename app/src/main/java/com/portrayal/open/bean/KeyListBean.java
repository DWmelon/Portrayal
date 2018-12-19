package com.portrayal.open.bean;

import java.util.ArrayList;
import java.util.List;

public class KeyListBean {

    private List<String> list = new ArrayList<>();
    private String type;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
