package com.cn.service;

import com.cn.VPO.*;
import org.hyperic.sigar.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SigarConfigServiceImpl implements ISigarConfigService{
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

        }
    }

    /**
     * 以太网信息
     * @throws SigarException
     */
    private void ethernet(SystemDTO systemDTO) throws SigarException{
        Sigar sigar = new Sigar();
        String[] ifaces = sigar.getNetInterfaceList();
        List<EthernetPO> ethernetPOList = new ArrayList<>();
        for (int i = 0; i < ifaces.length; i++){
            NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
            if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
                    || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
                    || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())){
                continue;
            }
            EthernetPO ethernetPO = new EthernetPO();
            ethernetPO.setIpAddress(cfg.getAddress()); // IP地址
            ethernetPO.setBroadcast(cfg.getBroadcast()); // 网关广播地址
            ethernetPO.setHwaddr(cfg.getHwaddr()); // 网卡MAC地址
            ethernetPO.setNetmask(cfg.getNetmask()); // 子网掩码
            ethernetPO.setDescription(cfg.getDescription()); // 网卡描述信息
            ethernetPO.setType(cfg.getType()); // 网卡类型
            ethernetPOList.add(ethernetPO);
        }
        systemDTO.setEthernetPOS(ethernetPOList);
    }

    /**
     * 网络信息
     * @throws Exception
     */
    private void net(SystemDTO systemDTO) throws Exception{
        Sigar sigar = new Sigar();
        String ifNames[] = sigar.getNetInterfaceList();
        List<NetWorkPO> netWorkPOS = new ArrayList<>();
        for (int i = 0; i < ifNames.length; i++){
            String name = ifNames[i];
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            NetWorkPO netWorkPO = new NetWorkPO();
            netWorkPO.setNetName(name); // 网络设备名
            netWorkPO.setIpAddress(ifconfig.getAddress()); // IP地址
            netWorkPO.setNetmask(ifconfig.getNetmask()); // 子网掩码
            if ((ifconfig.getFlags() & 1L) <= 0L){
                continue;
            }
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
            netWorkPO.setRxPackets(ifstat.getRxPackets()); // 接收的总包裹数
            netWorkPO.setTxPackets(ifstat.getTxPackets()); // 发送的总包裹数
            netWorkPO.setRxBytes(ifstat.getRxBytes()); // 接收到的总字节数
            netWorkPO.setRxErrors(ifstat.getRxErrors()); // 接收到的错误包数
            netWorkPO.setTxErrors(ifstat.getTxErrors()); // 发送数据包时的错误数
            netWorkPO.setRxDropped(ifstat.getRxDropped()); // 接收时丢弃的包数
            netWorkPO.setTxDropped(ifstat.getTxDropped()); // 发送时丢弃的包数
            netWorkPOS.add(netWorkPO);
        }
        systemDTO.setNetWorkPOS(netWorkPOS);
    }

    /**
     * 盘符信息
     * @throws Exception
     */
    private void file(SystemDTO systemDTO) throws Exception{
        Sigar sigar = new Sigar();
        FileSystem fslist[] = sigar.getFileSystemList();
        List<FileSystemPO> fileSystemPOS = new ArrayList<>();
        for (int i = 0; i < fslist.length; i++){
            FileSystemPO fileSystemPO = new FileSystemPO();
            FileSystem fs = fslist[i];
            fileSystemPO.setDevName(fs.getDevName().split(":")[0]); // 盘符名称
            fileSystemPO.setDirName(fs.getDirName()); // 盘符路径
            fileSystemPO.setFlags(fs.getFlags()); // 盘符标志
            fileSystemPO.setSysTypeName(fs.getSysTypeName()); // 盘符类型
            fileSystemPO.setTypeName(fs.getTypeName()); // 盘符类型名
            fileSystemPO.setFileSystemType(fs.getType()); // 盘符文件系统类型
            FileSystemUsage usage = null;
            usage = sigar.getFileSystemUsage(fs.getDirName());
            switch (fs.getType()){
                case 0: // TYPE_UNKNOWN ：未知
                    break;
                case 1: // TYPE_NONE
                    break;
                case 2: // TYPE_LOCAL_DISK : 本地硬盘
                    fileSystemPO.setDevNameTotal(usage.getTotal()); // 总大小KB
                    fileSystemPO.setDevNameFree(usage.getFree()); // 剩余大小KB
                    fileSystemPO.setDevNameAvail(usage.getAvail()); // 可用大小KB
                    fileSystemPO.setDevNameUsed(usage.getUsed()); // 已经使用量KB
                    double usePercent = usage.getUsePercent() * 100D;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(String.valueOf(usePercent));
                    stringBuilder.append("%");
                    fileSystemPO.setDevNamePercent(stringBuilder.toString()); // 资源的利用率
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
            fileSystemPO.setDevNameDiskReads(usage.getDiskReads()); // 读出
            fileSystemPO.setDevNameDiskWrites(usage.getDiskWrites()); // 写入
            fileSystemPOS.add(fileSystemPO);
        }
        systemDTO.setFileSystemPOS(fileSystemPOS);
        return;
    }

    /**
     * 获取操作用户信息
     * @throws SigarException
     */
    private void userInfo(SystemDTO systemDTO) throws SigarException{
        Sigar sigar = new Sigar();
        Who whos[] = sigar.getWhoList();
        List<WhoPO> whoPOS = new ArrayList<>();
        if (whos != null && whos.length > 0){
            for (int i = 0; i < whos.length; i++){
                Who who = whos[i];
                WhoPO po = new WhoPO();
                po.setUserdevice(who.getDevice()); // 用户控制台
                po.setUserHost(who.getHost()); // 用户host
                po.setUser(who.getUser()); // 当前系统进程表中的用户名
                whoPOS.add(po);
            }
            systemDTO.setWhoPOs(whoPOS);
        }
    }

    /**
     * 操作系统
     */
    private void os(SystemDTO systemDTO){
        OperatingSystem OS = OperatingSystem.getInstance();
        SystemPO systemPO = new SystemPO();
        systemPO.setArch(OS.getArch()); // 操作系统
        systemPO.setCpuEndian(OS.getCpuEndian()); // 操作系统CpuEndian
        systemPO.setDataModel(OS.getDataModel()); // 操作系统DataModel
        systemPO.setDescription(OS.getDescription()); // 操作系统的描述
        systemPO.setVendor(OS.getVendor()); // 操作系统的卖主
        systemPO.setVendorCodeName(OS.getVendorCodeName()); // 操作系统的卖主名
        systemPO.setVendorName(OS.getVendorName()); // 操作系统名称
        systemPO.setVendorVersion(OS.getVendorVersion()); // 操作系统卖主类型
        systemPO.setVersion(OS.getVersion()); // 操作系统的版本号
        systemDTO.setSystemPO(systemPO);
    }

    /**
     * 内存使用情况
     * @throws SigarException
     */
    private void memory(SystemDTO systemDTO) throws SigarException {
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        MemoryPO memoryPO = new MemoryPO();
        // 内存总量
        memoryPO.setMemoryTotal(mem.getTotal()/ 1024L); // 内存总量
        memoryPO.setMemoryUsed(mem.getUsed()/ 1024L); // 当前内存使用量
        memoryPO.setMemoryFree(mem.getFree()/ 1024L); // 当前内存剩余量

        BigDecimal bigDecimal1 = new BigDecimal(memoryPO.getMemoryUsed());
        BigDecimal bigDecimal2 = new BigDecimal(memoryPO.getMemoryTotal());
        BigDecimal bigDecimal = new BigDecimal(0);
        bigDecimal1 = bigDecimal1.multiply(new BigDecimal(100));
        bigDecimal = bigDecimal1.divide(bigDecimal2,2,BigDecimal.ROUND_HALF_UP);
        String user = bigDecimal.toString()+"%";
        memoryPO.setMemoryUserd(user); // 写入%号

        Swap swap = sigar.getSwap();
        memoryPO.setSwapTotal(swap.getTotal() / 1024L); // 交换区总量
        memoryPO.setSwapUsed(swap.getUsed() / 1024L); // 当前交换区使用量
        memoryPO.setSwapFree(swap.getFree() / 1024L); // 当前交换区剩余量
        systemDTO.setMemoryPO(memoryPO);
    }

    /**
     * cpu使用情况
     * @throws SigarException
     */
    private void cpu(SystemDTO systemDTO) throws SigarException {
        Sigar sigar = new Sigar();
        CpuInfo infos[] = sigar.getCpuInfoList();
        CpuPerc cpuList[] = null;
        cpuList = sigar.getCpuPercList();
        List<CpuPO> cpulist = new ArrayList<>();
        for (int i = 0; i < infos.length; i++){
            CpuPO cpuPO = new CpuPO();
            CpuInfo info = infos[i];
            cpuPO.setCpuNum(i+1); // 第i块CPU信息
            cpuPO.setCpuMhz(info.getMhz()); // CPU的总量MHz
            cpuPO.setCpuVendor(info.getVendor()); // CPU生产商
            cpuPO.setCpuModel(info.getModel()); // CPU类别
            cpuPO.setCpuCacheSize(info.getCacheSize()); // CPU缓存数量
            cpuPO.setCpuUser(CpuPerc.format(cpuList[i].getUser())); // CPU用户使用率
            cpuPO.setCpuSys(CpuPerc.format(cpuList[i].getSys())); // CPU系统使用率
            cpuPO.setCpuWait(CpuPerc.format(cpuList[i].getWait())); // CPU当前等待率
            cpuPO.setCpuIdle(CpuPerc.format(cpuList[i].getIdle())); // CPU当前空闲率
            cpuPO.setCpuCombined(CpuPerc.format(cpuList[i].getCombined())); // CPU总的使用率
            cpulist.add(cpuPO);
        }
        systemDTO.setCpuPOS(cpulist);
    }

    /**
     * 查询内存信息，显卡情况
     * @return
     */
    @Override
    public SystemDTO qureySystemInfo() throws Exception{
        SystemDTO systemDTO = new SystemDTO();
        synchronized (this){
            cpu(systemDTO);
            memory(systemDTO);
            os(systemDTO);
            userInfo(systemDTO);
            file(systemDTO);
            ethernet(systemDTO);
            net(systemDTO);
        }
        return systemDTO;
    }
}
