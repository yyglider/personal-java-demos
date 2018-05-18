package socket.client.processor;

import static socket.Commons.findSendableClassByOrder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import socket.SocketWrapper;
import socket.client.exceptions.NoOptionException;
import socket.client.sender.Sendable;

/***
 * 负责解析输入的每行命令
 */
public class LineProcessor {

    private String[] tokens;

    private Sendable sendable;

    public LineProcessor(String line) throws Exception {
        line = preLine(line).trim();

        //没有任何操作
        if (line.trim().length() == 0) {
            throw new NoOptionException();
        }

        //这个token标志了发送类型，通过发送类型找到对应的处理类
        tokens = line.trim().split("\\s+");
        String firstToken = tokens[0];
        Class<?> clazz = findSendableClassByOrder(firstToken);
        try {
            sendable = (Sendable) clazz.getConstructor(String[].class)
                    .newInstance(new Object[]{tokens});
        } catch (InvocationTargetException e) {
            throw (Exception) e.getCause();
        }
    }

    public void sendContentBySocket(SocketWrapper socketWrapper) throws IOException {
        if (sendable != null && sendable.getSendType() > 0) {
            //发送类型
            socketWrapper.write(sendable.getSendType());
            sendable.sendContent(socketWrapper);
        }
    }

    private String preLine(String line) {
        if (line == null) {
            return "";
        }
        if (line.startsWith(">")) {
            return line.substring(1);
        }
        return line;
    }
}
