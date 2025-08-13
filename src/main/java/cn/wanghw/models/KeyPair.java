package cn.wanghw.models;

import java.util.Objects;

public class KeyPair {
    public KeyPair(String validationKey, String decryptionKey) {
        this.validationKey = validationKey;
        this.decryptionKey = decryptionKey;
    }

    private String validationKey, decryptionKey;

    public String getDecryptionKey() {
        return decryptionKey;
    }

    public String getValidationKey() {
        return validationKey;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof KeyPair keyPair) {
            return Objects.equals(getValidationKey(), keyPair.getValidationKey()) && Objects.equals(getDecryptionKey(), keyPair.getDecryptionKey());
        } else if (o instanceof String) {
            return String.valueOf(o).equalsIgnoreCase(toString());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValidationKey(), getDecryptionKey());
    }

    @Override
    public String toString() {
        return validationKey + "," + decryptionKey;
    }
}
