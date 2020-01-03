package com.cn.service;

import org.hyperic.sigar.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;

@Configuration
public class SigarConfig {
    static {
        try {
            initSigar();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void initSigar() throws IOException {
        SigarLoader loader = new SigarLoader(Sigar.class);
        String lib = null;
        try {
            lib = loader.getLibName();
            System.out.println("so文件"+lib);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/sigar.so/" + lib);
        if (resource.exists()){
            InputStream is = resource.getInputStream();
            File tempDir = new File("D:\\var\\log");
            if (!tempDir.exists()){
                tempDir.mkdirs();
            }
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(tempDir, lib), false));
            int lentgh = 0;
            while ((lentgh = is.read()) != -1){
                os.write(lentgh);
            }
            is.close();
            os.close();
            System.setProperty("org.hyperic.sigar.path", tempDir.getCanonicalPath());


        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("-----开始-------");
            System.out.println("-----cpu信息--------");
            cpu();
            System.out.println("-----内存信息-------");
            memory();
            System.out.println("-----操作系统信息-------");
            os();
            System.out.println("-----用户信息-------");
            userInfo();
            System.out.println("-----文件系统信息-------");
            file();
            System.out.println("-----网络信息-------");
            net();
            System.out.println("-----以太网信息-------");
            ethernet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ethernet() throws SigarException{
        Sigar sigar = new Sigar();
        String[] ifaces = sigar.getNetInterfaceList();
        for (int i = 0; i < ifaces.length; i++){
            NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
            if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
                    || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
                    || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())){
                continue;
            }
            System.out.println(cfg.getName() + "IP地址:" + cfg.getAddress());
            System.out.println(cfg.getName() + "网关广播地址:" + cfg.getBroadcast());
            System.out.println(cfg.getName() + "网卡MAC地址:" + cfg.getHwaddr());
            System.out.println(cfg.getName() + "子网掩码:" + cfg.getNetmask());
            System.out.println(cfg.getName() + "网卡描述信息:" + cfg.getDescription());
            System.out.println(cfg.getName() + "网卡类型" + cfg.getType());

        }
    }

    private static void net() throws Exception{
        Sigar sigar = new Sigar();
        String ifNames[] = sigar.getNetInterfaceList();
        for (int i = 0; i < ifNames.length; i++){
            String name = ifNames[i];
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            System.out.println("网络设备名:    " + name);
            System.out.println("IP地址:    " + ifconfig.getAddress());
            System.out.println("子网掩码:    " + ifconfig.getNetmask());
            if ((ifconfig.getFlags() & 1L) <= 0L){
                System.out.println("!IFF_UP...skipping getNetInterfaceStat");
                continue;
            }
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
            System.out.println(name + "接收的总包裹数:" + ifstat.getRxPackets());
            System.out.println(name + "发送的总包裹数:" + ifstat.getTxPackets());
            System.out.println(name + "接收到的总字节数:" + ifstat.getRxBytes());
            System.out.println(name + "接收到的错误包数:" + ifstat.getRxErrors());
            System.out.println(name + "发送数据包时的错误数:" + ifstat.getTxErrors());
            System.out.println(name + "接收时丢弃的包数:" + ifstat.getRxDropped());
            System.out.println(name + "发送时丢弃的包数:" + ifstat.getTxDropped());

        }
    }

    private static void file() throws Exception{
        Sigar sigar = new Sigar();
        FileSystem fslist[] = sigar.getFileSystemList();
        for (int i = 0; i < fslist.length; i++){
            System.out.println("分区的盘符名称" + i);
            FileSystem fs = fslist[i];
            System.out.println("盘符名称:    " + fs.getDevName());
            System.out.println("盘符路径:    " + fs.getDirName());
            System.out.println("盘符标志:    " + fs.getFlags());
            System.out.println("盘符类型:    " + fs.getSysTypeName());
            System.out.println("盘符类型名:    " + fs.getTypeName());
            System.out.println("盘符文件系统类型:    " + fs.getType());
            FileSystemUsage usage = null;
            usage = sigar.getFileSystemUsage(fs.getDirName());
            switch (fs.getType()){
                case 0: // TYPE_UNKNOWN ：未知
                    break;
                case 1: // TYPE_NONE
                    break;
                case 2: // TYPE_LOCAL_DISK : 本地硬盘
                    System.out.println(fs.getDevName() + "总大小:    " + usage.getTotal() + "KB");
                    System.out.println(fs.getDevName() + "剩余大小:    " + usage.getFree() + "KB");
                    System.out.println(fs.getDevName() + "可用大小:    " + usage.getAvail() + "KB");
                    System.out.println(fs.getDevName() + "已经使用量:    " + usage.getUsed() + "KB");
                    double usePercent = usage.getUsePercent() * 100D;
                    System.out.println(fs.getDevName() + "资源的利用率:    " + usePercent + "%");
                    break;
                case 3: // TYPE_NETWORK ：网络
                    break;
                case 4:// TYPE_RAM_DISK ：闪存
                    break;
                case 5:// TYPE_CDROM ：光驱
                    break;
                case 6:// TYPE_SWAP ：页面交换
                    break;
            }
            System.out.println(fs.getDevName() + "读出：    " + usage.getDiskReads());
            System.out.println(fs.getDevName() + "写入：    " + usage.getDiskWrites());
        }
        return;
    }

    private static void userInfo() throws SigarException{
        Sigar sigar = new Sigar();
        Who who[] = sigar.getWhoList();
        if (who != null && who.length > 0){
            for (int i = 0; i < who.length; i++){
                Who _who = who[i];
                System.out.println("用户控制台:    " + _who.getDevice());
                System.out.println("用户host:    " + _who.getHost());
                System.out.println("当前系统进程表中的用户名:    " + _who.getUser());

            }
        }
    }


    private static void os(){
        OperatingSystem OS = OperatingSystem.getInstance();
        System.out.println("操作系统:    " + OS.getArch());
        System.out.println("操作系统CpuEndian():    " + OS.getCpuEndian());
        System.out.println("操作系统DataModel():    " + OS.getDataModel());
        System.out.println("操作系统的描述:    " + OS.getDescription());
        System.out.println("操作系统的卖主:    " + OS.getVendor());
        System.out.println("操作系统的卖主名:    " + OS.getVendorCodeName());
        System.out.println("操作系统名称:    " + OS.getVendorName());
        System.out.println("操作系统卖主类型:    " + OS.getVendorVersion());
        System.out.println("操作系统的版本号:    " + OS.getVersion());
    }

    private static void memory() throws SigarException {
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        // 内存总量
        System.out.println("内存总量:    " + mem.getTotal() / 1024L + "K av");
        System.out.println("当前内存使用量:    " + mem.getUsed() / 1024L + "K used");
        System.out.println("当前内存剩余量:    " + mem.getFree() / 1024L + "K free");
        Swap swap = sigar.getSwap();
        System.out.println("交换区总量:    " + swap.getTotal() / 1024L + "K av");
        System.out.println("当前交换区使用量:    " + swap.getUsed() / 1024L + "K used");
        System.out.println("当前交换区剩余量:    " + swap.getFree() / 1024L + "K free");
    }

    private static void cpu() throws SigarException {
        Sigar sigar = new Sigar();
        CpuInfo infos[] = sigar.getCpuInfoList();
        CpuPerc cpuList[] = null;
        cpuList = sigar.getCpuPercList();
        for (int i = 0; i < infos.length; i++){
            CpuInfo info = infos[i];
            System.out.println("第" + (i + 1) + "块CPU信息");
            System.out.println("CPU的总量MHz:    " + info.getMhz());
            System.out.println("CPU生产商:    " + info.getVendor());
            System.out.println("CPU类别:    " + info.getModel());
            System.out.println("CPU缓存数量:    " + info.getCacheSize());
            System.out.println("CPU用户使用率:    " + CpuPerc.format(cpuList[i].getUser()));
            System.out.println("CPU系统使用率:    " + CpuPerc.format(cpuList[i].getSys()));
            System.out.println("CPU当前等待率:    " + CpuPerc.format(cpuList[i].getWait()));
            System.out.println("CPU当前空闲率:    " + CpuPerc.format(cpuList[i].getIdle()));
            System.out.println("CPU总的使用率:    " + CpuPerc.format(cpuList[i].getCombined()));
        }
    }
}
