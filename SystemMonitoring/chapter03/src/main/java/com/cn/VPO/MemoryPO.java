package com.cn.VPO;

// 内存信息
public class MemoryPO {

    // 内存总量
    private long memoryTotal;

    // 当前内存使用量
    private long memoryUsed;

    // 当前内存剩余量
    private long memoryFree;

    private String memoryUserd;

    public String getMemoryUserd() {
        return memoryUserd;
    }

    public void setMemoryUserd(String memoryUserd) {
        this.memoryUserd = memoryUserd;
    }

    // 交换区总量
    private long swapTotal;

    // 当前交换区使用量
    private long swapUsed;

    // 当前交换区剩余量
    private long swapFree;

    public long getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(long memoryTotal) {
        this.memoryTotal = memoryTotal;
    }

    public long getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(long memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public long getMemoryFree() {
        return memoryFree;
    }

    public void setMemoryFree(long memoryFree) {
        this.memoryFree = memoryFree;
    }

    public long getSwapTotal() {
        return swapTotal;
    }

    public void setSwapTotal(long swapTotal) {
        this.swapTotal = swapTotal;
    }

    public long getSwapUsed() {
        return swapUsed;
    }

    public void setSwapUsed(long swapUsed) {
        this.swapUsed = swapUsed;
    }

    public long getSwapFree() {
        return swapFree;
    }

    public void setSwapFree(long swapFree) {
        this.swapFree = swapFree;
    }
}
