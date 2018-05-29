package netty.protocol.v1;

//消息的主体
public class Message {
    private Header header;
    private String content;

    public Message(Header header, String content) {
        this.header = header;
        this.content = content;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("[version=%d,contentLength=%d,serviceName=%s,content=%s]",
                header.getVersion(),
                header.getContentLength(),
                header.getServiceName(),
                content);
    }

}
