package com.cn.VPO;

// ethernet 以太网
public class EthernetPO {

    // ip地址
    private String ipAddress;

    // 网关广播地址
    private String broadcast;

    // 网卡MAC地址
    private String hwaddr;

    // 子网掩码
    private String netmask;

    // 网卡描述信息
    private String description;

    // 网卡类型
    private String type;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public String getHwaddr() {
        return hwaddr;
    }

    public void setHwaddr(String hwaddr) {
        this.hwaddr = hwaddr;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
