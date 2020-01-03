package com.cn.VPO;

import java.util.List;

public class SystemDTO {

    private List<CpuPO> cpuPOS;

    private List<WhoPO> whoPOs;

    private SystemPO systemPO;

    private MemoryPO memoryPO;

    private List<FileSystemPO> fileSystemPOS;

    private List<NetWorkPO> netWorkPOS;

    private List<EthernetPO> ethernetPOS;

    public List<EthernetPO> getEthernetPOS() {
        return ethernetPOS;
    }

    public void setEthernetPOS(List<EthernetPO> ethernetPOS) {
        this.ethernetPOS = ethernetPOS;
    }

    public List<CpuPO> getCpuPOS() {
        return cpuPOS;
    }

    public void setCpuPOS(List<CpuPO> cpuPOS) {
        this.cpuPOS = cpuPOS;
    }

    public List<WhoPO> getWhoPOs() {
        return whoPOs;
    }

    public void setWhoPOs(List<WhoPO> whoPOs) {
        this.whoPOs = whoPOs;
    }

    public SystemPO getSystemPO() {
        return systemPO;
    }

    public void setSystemPO(SystemPO systemPO) {
        this.systemPO = systemPO;
    }

    public MemoryPO getMemoryPO() {
        return memoryPO;
    }

    public void setMemoryPO(MemoryPO memoryPO) {
        this.memoryPO = memoryPO;
    }

    public List<FileSystemPO> getFileSystemPOS() {
        return fileSystemPOS;
    }

    public void setFileSystemPOS(List<FileSystemPO> fileSystemPOS) {
        this.fileSystemPOS = fileSystemPOS;
    }

    public List<NetWorkPO> getNetWorkPOS() {
        return netWorkPOS;
    }

    public void setNetWorkPOS(List<NetWorkPO> netWorkPOS) {
        this.netWorkPOS = netWorkPOS;
    }
}
