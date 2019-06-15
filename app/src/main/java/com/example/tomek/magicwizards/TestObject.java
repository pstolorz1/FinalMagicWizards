package com.example.tomek.magicwizards;

public class TestObject
{

    public int getVal() {
        return val;
    }
    public TestObject()
    {

    }
    public TestObject(String uuid, int val)
    {
        this.uuid = uuid;
        this.val = val;
    }
    public void setVal(int val) {
        this.val = val;
    }

    int val;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    String uuid;
}
