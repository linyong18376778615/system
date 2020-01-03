package com.cn.VPO;

public class CpuPO {
    // 第i块CPU
    private long cpuNum;

    // CPU的总量MHz
    private Integer cpuMhz;

    // CPU生产商
    private String cpuVendor;

    // CPU类别
    private String cpuModel;

    // CPU缓存数量
    private Long cpuCacheSize;

    // CPU用户使用率
    private String cpuUser;

    // CPU系统使用率
    private String cpuSys;

    // CPU当前等待率
    private String cpuWait;

    // CPU当前空闲率
    private String cpuIdle;

    // CPU总的使用率
    private String cpuCombined;

    public long getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(long cpuNum) {
        this.cpuNum = cpuNum;
    }

    public Integer getCpuMhz() {
        return cpuMhz;
    }

    public void setCpuMhz(Integer cpuMhz) {
        this.cpuMhz = cpuMhz;
    }

    public String getCpuVendor() {
        return cpuVendor;
    }

    public void setCpuVendor(String cpuVendor) {
        this.cpuVendor = cpuVendor;
    }

    public String getCpuModel() {
        return cpuModel;
    }

    public void setCpuModel(String cpuModel) {
        this.cpuModel = cpuModel;
    }

    public Long getCpuCacheSize() {
        return cpuCacheSize;
    }

    public void setCpuCacheSize(Long cpuCacheSize) {
        this.cpuCacheSize = cpuCacheSize;
    }

    public String getCpuUser() {
        return cpuUser;
    }

    public void setCpuUser(String cpuUser) {
        this.cpuUser = cpuUser;
    }

    public String getCpuSys() {
        return cpuSys;
    }

    public void setCpuSys(String cpuSys) {
        this.cpuSys = cpuSys;
    }

    public String getCpuWait() {
        return cpuWait;
    }

    public void setCpuWait(String cpuWait) {
        this.cpuWait = cpuWait;
    }

    public String getCpuIdle() {
        return cpuIdle;
    }

    public void setCpuIdle(String cpuIdle) {
        this.cpuIdle = cpuIdle;
    }

    public String getCpuCombined() {
        return cpuCombined;
    }

    public void setCpuCombined(String cpuCombined) {
        this.cpuCombined = cpuCombined;
    }
}
