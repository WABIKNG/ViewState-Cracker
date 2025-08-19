package cn.wanghw.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Purpose {
    private String[] contextArray;
    private String path;
    private String appPath;

    public Purpose(String[] contextArray, String path, String appPath) {
        this.contextArray = contextArray;
        this.path = path;
        this.appPath = appPath;
    }

    public byte[] getLabel() {
        return label;
    }


    final byte[] label = "WebForms.HiddenFieldPageStatePersister.ClientState".getBytes(StandardCharsets.UTF_8);

    public byte[] getContext() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (String context : contextArray) {
            byte[] data = context.getBytes(StandardCharsets.UTF_8);
            try {
                write7BitEncodedInt(byteArrayOutputStream, data.length);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            byteArrayOutputStream.writeBytes(data);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public void write7BitEncodedInt(OutputStream outputStream, int length) throws IOException {
        int current = length;
        while (current > 0x7F) {
            outputStream.write((byte) ((current & 0x7F) | 0x80));
        }
        outputStream.write((byte) current);
    }

    byte[] context;

    public String getAppPath() {
        return appPath;
    }

    public String getPath() {
        return path;
    }
}
