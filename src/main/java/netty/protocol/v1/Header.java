package netty.protocol.v1;

// 消息的头部
public class Header {
    //协议版本
    private int version;
    //消息内容长度
    private int contentLength;

    //服务名称
    private String serviceName;

    public Header(int version, int contentLength, String serviceName) {
        this.version = version;
        this.contentLength = contentLength;
        this.serviceName = serviceName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
