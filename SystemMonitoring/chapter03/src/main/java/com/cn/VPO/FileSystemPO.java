package com.cn.VPO;

// 分区的盘符
public class FileSystemPO {

    // 盘符名称
    private String devName;

    // 盘符路径
    private String dirName;

    // 盘符标志
    private Long flags;

    // 盘符类型
    private String sysTypeName;

    // 盘符类型名
    private String typeName;

    // 盘符文件系统类型
    private Integer fileSystemType;

    // 总大小
    private Long devNameTotal;

    // 剩余大小
    private Long devNameFree;

    // 可用大小
    private Long devNameAvail;

    // 已经使用量
    private Long devNameUsed;

    // 资源的利用率
    private String devNamePercent;

    // 读出
    private Long devNameDiskReads;

    // 写入
    private Long devNameDiskWrites;

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public Long getFlags() {
        return flags;
    }

    public void setFlags(Long flags) {
        this.flags = flags;
    }

    public String getSysTypeName() {
        return sysTypeName;
    }

    public void setSysTypeName(String sysTypeName) {
        this.sysTypeName = sysTypeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getFileSystemType() {
        return fileSystemType;
    }

    public void setFileSystemType(Integer fileSystemType) {
        this.fileSystemType = fileSystemType;
    }

    public Long getDevNameTotal() {
        return devNameTotal;
    }

    public void setDevNameTotal(Long devNameTotal) {
        this.devNameTotal = devNameTotal;
    }

    public Long getDevNameFree() {
        return devNameFree;
    }

    public void setDevNameFree(Long devNameFree) {
        this.devNameFree = devNameFree;
    }

    public Long getDevNameAvail() {
        return devNameAvail;
    }

    public void setDevNameAvail(Long devNameAvail) {
        this.devNameAvail = devNameAvail;
    }

    public Long getDevNameUsed() {
        return devNameUsed;
    }

    public void setDevNameUsed(Long devNameUsed) {
        this.devNameUsed = devNameUsed;
    }

    public String getDevNamePercent() {
        return devNamePercent;
    }

    public void setDevNamePercent(String devNamePercent) {
        this.devNamePercent = devNamePercent;
    }

    public Long getDevNameDiskReads() {
        return devNameDiskReads;
    }

    public void setDevNameDiskReads(Long devNameDiskReads) {
        this.devNameDiskReads = devNameDiskReads;
    }

    public Long getDevNameDiskWrites() {
        return devNameDiskWrites;
    }

    public void setDevNameDiskWrites(Long devNameDiskWrites) {
        this.devNameDiskWrites = devNameDiskWrites;
    }
}
