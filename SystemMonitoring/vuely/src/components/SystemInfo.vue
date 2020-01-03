<template>
<el-collapse>
    <el-form>
        <el-collapse-item title="CPU" name="1">
            <el-form-item>
            <!-- <h3>CPU</h3> -->
                <el-table :data="cpuPOS" style="width: 100%">
                <el-table-column prop="cpuNum" label="第N块CPU">
                </el-table-column>
                <el-table-column prop="cpuMhz" label="CPU的总量MHz">
                </el-table-column>
                <el-table-column prop="cpuVendor" label="CPU生产商">
                </el-table-column>
                <el-table-column prop="cpuModel" label="CPU类别">
                </el-table-column>
                <el-table-column prop="cpuUser" label="CPU使用率">
                </el-table-column>
                <el-table-column prop="cpuSys" label="CPU系统使用率">
                </el-table-column>
                <el-table-column prop="cpuWait" label="CPU当前等待率">
                </el-table-column>
                <el-table-column prop="cpuIdle" label="CPU当前空闲率">
                </el-table-column>
                <el-table-column prop="cpuCombined" label="CPU总的使用率">
                </el-table-column>
                </el-table>
            </el-form-item>
        </el-collapse-item>
        <el-collapse-item title="内存" name="2">
            <el-form-item>
            <!-- <h3>内存</h3> -->
                <el-table :data="memoryPO" style="width: 100%">
                <el-table-column prop="memoryTotal" label="内存总量KB">
                </el-table-column>
                <el-table-column prop="memoryUserd" label="当前使用率">
                </el-table-column>
                <el-table-column prop="memoryUsed" label="当前内存使用量KB">
                </el-table-column>
                <el-table-column prop="memoryFree" label="当前内存剩余量KB">
                </el-table-column>
                <el-table-column prop="swapTotal" label="交换区总量KB">
                </el-table-column>
                <el-table-column prop="swapUsed" label="当前交换区使用量KB">
                </el-table-column>
                <el-table-column prop="swapFree" label="当前交换区剩余量KB">
                </el-table-column>
                </el-table>
            </el-form-item>
        </el-collapse-item>
        <el-collapse-item title="系统盘" name="3">
            <el-form-item>
            <!-- <h3>系统盘</h3> -->
                <el-table :data="fileSystemPOS" style="width: 100%">
                <el-table-column prop="devName" label="盘符名称">
                </el-table-column>
                <!-- <el-table-column prop="dirName" label="盘符路径">
                </el-table-column> -->
                <el-table-column prop="devNameTotal" label="盘符总大小KB">
                </el-table-column>
                <el-table-column prop="devNameFree" label="盘符剩余大小KB">
                </el-table-column>
                <el-table-column prop="devNameAvail" label="盘符可用大小KB">
                </el-table-column>
                <el-table-column prop="devNameUsed" label="盘符已经使用KB">
                </el-table-column>
                <el-table-column prop="devNamePercent" label="资源的利用率">
                </el-table-column>
                </el-table>
            </el-form-item>
        </el-collapse-item>
        <el-collapse-item title="网络信息" name="4">
            <el-form-item>
            <!-- <h3>网络信息</h3> -->
                <el-table :data="netWorkPOS" style="width: 100%">
                <el-table-column prop="netName" label="网络设备名">
                </el-table-column>
                <el-table-column prop="rxPackets" label="接收的总包裹数">
                </el-table-column>
                <el-table-column prop="txPackets" label="发送的总包裹数">
                </el-table-column>
                <el-table-column prop="rxBytes" label="接收到的总字节数">
                </el-table-column>
                <el-table-column prop="rxErrors" label="接收到的错误包数">
                </el-table-column>
                <el-table-column prop="txErrors" label="发送数据包时的错误数">
                </el-table-column>
                <el-table-column prop="rxDropped" label="接收时丢弃的包数">
                </el-table-column>
                <el-table-column prop="txDropped" label="发送时丢弃的包数">
                </el-table-column>
                </el-table>
            </el-form-item>
        </el-collapse-item>
        <el-collapse-item title="网卡信息" name="5">
            <el-form-item>
            <!-- <h3>网卡信息</h3> -->
                <el-table :data="etherenetPO" style="width: 100%">
                <el-table-column prop="ipAddress" label="ip地址">
                </el-table-column>
                <el-table-column prop="broadcast" label="网关广播地址">
                </el-table-column>
                <el-table-column prop="hwaddr" label="网卡MAC地址">
                </el-table-column>
                <el-table-column prop="netmask" label="子网掩码">
                </el-table-column>
                <el-table-column prop="description" label="网卡描述信息">
                </el-table-column>
                <el-table-column prop="type" label="网卡类型">
                </el-table-column>
                </el-table>
            </el-form-item>
        </el-collapse-item>
    </el-form>
</el-collapse>
</template>

<script>
export default {
    data(){
        return{
            cpuPOS: [],
            memoryPO: [],
            fileSystemPOS: [],
            etherenetPO: [],
            netWorkPOS: []
        }
    },
    created() {
    //   this.getSystemInfo();
      this.initWebSocket();
    },
    destroyed() {      
		this.websock.close() //离开路由之后断开websocket连接
    }, 
    methods: {
        initWebSocket(){ //初始化weosocket
        var websock;
        const wsuri = "ws://localhost:8072/websocket";        
		this.websock = new WebSocket(wsuri);      
		this.websock.onmessage = this.websocketonmessage;   
		this.websock.onopen = this.websocketonopen;        
		this.websock.onerror = this.websocketonerror;       
		this.websock.onclose = this.websocketclose;
        },
        websocketonopen(){ //连接建立之后执行send方法发送数据
            var actions = {"test":"12345"};        
            this.websocketsend(JSON.stringify(actions));
        },
        websocketonerror(){//连接建立失败重连
            this.initWebSocket();
        },
        websocketonmessage(e){ //数据接收

        const redata = JSON.parse(e.data);
        this.cpuPOS = redata.cpuPOS;
        this.memoryPO = [redata.memoryPO];
        this.fileSystemPOS = redata.fileSystemPOS;
        console.log(redata)
        this.etherenetPO = redata.ethernetPOS;
        this.netWorkPOS = redata.netWorkPOS;
        
        },
        websocketsend(Data){//数据发送
            this.websock.send(Data);
        },
        websocketclose(e){  //关闭
            console.log('断开连接',e);
        }
    }
}
</script>

<style>
    .el-collapse-item__header {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: unset;
    -ms-flex-align: center;
    align-items: center;
    height: 100px;
    line-height: 48px;
    background-color: #FFF;
    color: #303145;
    cursor: move;
    border-bottom: 1px solid #EBEEF5;
    font-size: 35px;
    font-weight: 500;
    -webkit-transition: border-bottom-color .3s;
    transition: border-bottom-color .3s;
    outline: 0;
    flex-direction: column;
    }
</style>>
