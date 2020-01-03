package com.cn.VPO;

// 网络信息
public class NetWorkPO {

    // 网络设备名
    private String netName;

    // IP地址
    private String ipAddress;

    // 子网掩码
    private String netmask;

    // 接收的总包裹数
    private Long rxPackets;

    // 发送的总包裹数
    private Long txPackets;

    // 接收到的总字节数
    private Long rxBytes;

    // 接收到的错误包数
    private Long rxErrors;

    // 发送数据包时的错误数
    private Long txErrors;

    // 接收时丢弃的包数
    private Long rxDropped;

    // 发送时丢弃的包数
    private Long txDropped;

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public Long getRxPackets() {
        return rxPackets;
    }

    public void setRxPackets(Long rxPackets) {
        this.rxPackets = rxPackets;
    }

    public Long getTxPackets() {
        return txPackets;
    }

    public void setTxPackets(Long txPackets) {
        this.txPackets = txPackets;
    }

    public Long getRxBytes() {
        return rxBytes;
    }

    public void setRxBytes(Long rxBytes) {
        this.rxBytes = rxBytes;
    }

    public Long getRxErrors() {
        return rxErrors;
    }

    public void setRxErrors(Long rxErrors) {
        this.rxErrors = rxErrors;
    }

    public Long getTxErrors() {
        return txErrors;
    }

    public void setTxErrors(Long txErrors) {
        this.txErrors = txErrors;
    }

    public Long getRxDropped() {
        return rxDropped;
    }

    public void setRxDropped(Long rxDropped) {
        this.rxDropped = rxDropped;
    }

    public Long getTxDropped() {
        return txDropped;
    }

    public void setTxDropped(Long txDropped) {
        this.txDropped = txDropped;
    }
}
