package com.example.app2_mycontentprovider;

public class sach {
    private String maSach, tenSach, maTG;

    public sach(String maSach, String tenSach, String tenTG) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.maTG = tenTG;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String tenTG) {
        this.maTG = tenTG;
    }
}
