package cn.wanghw.utils;

import cn.wanghw.models.Purpose;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SP800_108 {

    public static byte[] deriveKey(byte[] keyDerivationKey, Purpose purpose) throws Exception {
        Mac hmac = Mac.getInstance("HmacSHA512");
        SecretKey secretKey = new SecretKeySpec(keyDerivationKey, "HmacSHA512");
        hmac.init(secretKey);

        byte[] label = purpose.getLabel();
        byte[] context = purpose.getContext();

        int keyLengthInBits = secretKey.getEncoded().length * 8;

        return deriveKeyImpl(hmac, label, context, keyLengthInBits);
    }

    private static byte[] deriveKeyImpl(
            Mac hmac,
            byte[] label,
            byte[] context,
            int keyLengthInBits
    ) {
        int labelLen = label != null ? label.length : 0;
        int contextLen = context != null ? context.length : 0;
        byte[] data = new byte[4 + labelLen + 1 + contextLen + 4];
        int pos = 4;
        if (label != null) {
            System.arraycopy(label, 0, data, pos, labelLen);
            pos += labelLen;
        }
        data[pos++] = 0;
        if (context != null) {
            System.arraycopy(context, 0, data, pos, contextLen);
            pos += contextLen;
        }
        writeUInt32BigEndian(keyLengthInBits, data, pos);
        int keyLengthInBytes = keyLengthInBits / 8;
        byte[] derivedKey = new byte[keyLengthInBytes];
        int destOffset = 0;
        int remaining = keyLengthInBytes;
        int counter = 1;

        while (remaining > 0) {
            writeUInt32BigEndian(counter, data, 0);
            byte[] hash = hmac.doFinal(data);
            int copyLength = Math.min(remaining, hash.length);
            System.arraycopy(hash, 0, derivedKey, destOffset, copyLength);
            destOffset += copyLength;
            remaining -= copyLength;
            counter++;
        }

        return derivedKey;
    }

    private static void writeUInt32BigEndian(int value, byte[] buffer, int offset) {
        buffer[offset] = (byte) (value >>> 24);
        buffer[offset + 1] = (byte) (value >>> 16);
        buffer[offset + 2] = (byte) (value >>> 8);
        buffer[offset + 3] = (byte) value;
    }
}