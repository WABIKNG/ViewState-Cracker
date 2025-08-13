package cn.wanghw.utils;

import cn.wanghw.models.BruteResult;
import cn.wanghw.models.KeyPair;
import cn.wanghw.models.ViewStateData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ViewStateUtil {
    public static final String[] ValidationAlgorithms = new String[]{"SHA1", "HMACSHA256", "HMACSHA384", "HMACSHA512", "MD5", "3DES", "AES"};
    public static final String[] DecryptionAlgorithms = new String[]{"DES", "3DES", "AES"};
    private static final Map<String, Integer> ValidationHashSizeMap = new HashMap<>();
    private static final Map<String, String> DecryptionAlgoMap = new HashMap<>();
    private static final Map<String, String> ValidationAlgoMap = new HashMap<>();
    private static KeyPair[] machineKeys;

    static {
        ValidationHashSizeMap.put("3DES", 20);
        ValidationHashSizeMap.put("AES", 20);
        ValidationHashSizeMap.put("HMACSHA256", 32);
        ValidationHashSizeMap.put("HMACSHA384", 48);
        ValidationHashSizeMap.put("HMACSHA512", 64);
        ValidationHashSizeMap.put("MD5", 16);
        ValidationHashSizeMap.put("SHA1", 20);

        DecryptionAlgoMap.put("AES", "AES");
        DecryptionAlgoMap.put("DES", "DES");
        DecryptionAlgoMap.put("3DES", "DESede");
        DecryptionAlgoMap.put("TripleDES".toUpperCase(), "DESede");

        ValidationAlgoMap.put("3DES", "HmacSHA1");
        ValidationAlgoMap.put("TripleDES".toUpperCase(), "HmacSHA1");
        ValidationAlgoMap.put("AES", "HmacSHA1");
        ValidationAlgoMap.put("SHA1", "HmacSHA1");
        ValidationAlgoMap.put("HMACSHA256", "HmacSHA256");
        ValidationAlgoMap.put("HMACSHA384", "HmacSHA384");
        ValidationAlgoMap.put("HMACSHA512", "HmacSHA512");
    }

    static final String keyRegex = "^(\\w*),(\\w*)$";
    static final Pattern keyPattern = Pattern.compile(keyRegex, Pattern.MULTILINE);

    public static void loadKeys() {
        try {
            String[] keyList = CommonUtil.readLines(CommonUtil.getExtensionFileParentDirectory() + File.separator + "machineKeys.txt");
            List<KeyPair> keys = new ArrayList<>();
            for (String key : keyList) {
                if (keyPattern.matcher(key).matches() && !keys.contains(key)) {
                    String[] values = key.split(",");
                    String strValidationKey = values[0];
                    String strDecryptionKey = values[1];
                    keys.add(new KeyPair(strValidationKey, strDecryptionKey));
                }
            }
            machineKeys = keys.toArray(new KeyPair[0]);
            CommonUtil.getLogger().logToOutput(String.format("Load MachineKeys count: %s.", machineKeys.length));
        } catch (Exception e) {
            CommonUtil.getLogger().logToError("Load MachineKeys failed.", e);
        }
    }

    private static byte[] reverse(byte[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            byte temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
    }

    public static boolean isSymAlgo(String validationAlgorithm) {
        return validationAlgorithm.equalsIgnoreCase("AES") || validationAlgorithm.equalsIgnoreCase("TripleDES") || validationAlgorithm.equalsIgnoreCase("3DES");
    }

    public static byte[] verifyAndDecryptViewState(
            String viewState,
            String validationKeyHex,
            String validationAlgorithm,
            String decryptionKeyHex,
            String decryptionAlgorithm,
            String modifier,
            boolean isEncrypted
    ) throws Exception {
        byte[] viewStateBytes = Base64.decode(viewState);
        int macLength = ValidationHashSizeMap.get(String.valueOf(validationAlgorithm).toUpperCase());
        boolean isSymAlgo = isSymAlgo(validationAlgorithm);
        if (viewStateBytes.length < macLength) {
            throw new Exception("Invalid ViewState: too short for MAC");
        }
        byte[] providedMac = Arrays.copyOfRange(viewStateBytes, viewStateBytes.length - macLength, viewStateBytes.length);
        byte[] dataForMac = Arrays.copyOfRange(viewStateBytes, 0, viewStateBytes.length - macLength);
        byte[] validationKeyBytes = hexStringToByteArray(validationKeyHex);
        byte[] decryptionKeyBytes = hexStringToByteArray(decryptionKeyHex);
        byte[] modifierBytes = reverse(hexStringToByteArray(modifier));
        byte[] computedMac;
        if (isSymAlgo) {
            byte[] data1 = decrypt(dataForMac, decryptionKeyBytes, DecryptionAlgoMap.get(validationAlgorithm));
            dataForMac = Arrays.copyOfRange(data1, 0, data1.length - macLength);
            providedMac = Arrays.copyOfRange(data1, data1.length - macLength, data1.length);
        }
        String javaMacAlgorithm = ValidationAlgoMap.getOrDefault(validationAlgorithm.toUpperCase(), validationAlgorithm);
        if (javaMacAlgorithm.equalsIgnoreCase("MD5")) {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(comboBytes(
                    dataForMac,
                    validationKeyBytes,
                    isEncrypted ? new byte[0] : new byte[]{0, 0, 0, 0}
            ));
            computedMac = md5.digest();
        } else {
            byte[] payload = isEncrypted ? dataForMac : comboBytes(dataForMac, modifierBytes);
            Mac mac = Mac.getInstance(javaMacAlgorithm);
            mac.init(new SecretKeySpec(validationKeyBytes, javaMacAlgorithm));
            computedMac = mac.doFinal(payload);
        }
        if (!Arrays.equals(computedMac, providedMac)) {
            throw new Exception("MAC verification failed");
        }
        if (isEncrypted) {
            dataForMac = decrypt(dataForMac, decryptionKeyBytes, DecryptionAlgoMap.get(decryptionAlgorithm));
        }
        return dataForMac;
    }

    private static byte[] comboBytes(byte[]... bytes) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (byte[] b : bytes) {
            bos.writeBytes(b);
        }
        return bos.toByteArray();
    }

    private static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
        int blockSize = cipherAlgorithm.equals("AES") ? 16 : 8;
        int paddedLength = ((data.length + blockSize - 1) / blockSize) * blockSize;
        byte[] paddedData = new byte[paddedLength];
        System.arraycopy(data, 0, paddedData, 0, data.length);
        byte[] iv = new byte[blockSize];
        Cipher cipher = Cipher.getInstance(cipherAlgorithm + "/CBC/PKCS5Padding");
        if (cipherAlgorithm.equals("DES"))
            key = Arrays.copyOfRange(key, 0, 8);
        SecretKeySpec keySpec = new SecretKeySpec(key, cipherAlgorithm);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decrypted = cipher.doFinal(paddedData);
        int hashSize = RoundupNumBitsToNumBytes(key.length * 8);
        byte[] body = new byte[decrypted.length - hashSize - 4];
        // 因为ViewState数据均来自于页面内容和请求Body，所以这里就跳过modifier检查。
        System.arraycopy(decrypted, hashSize, body, 0, body.length);
        return body;
    }

    private static int RoundupNumBitsToNumBytes(int numBits) {
        return numBits < 0 ? 0 : numBits / 8 + ((numBits & 7) != 0 ? 1 : 0);
    }

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static BruteResult bruteViewStateKey(ViewStateData viewStateData) {
        BruteResult bruteResult = new BruteResult();
        bruteResult.setViewStateData(viewStateData);
        for (String valAlgo : ValidationAlgorithms) {
            if (viewStateData.isViewStateEncrypted()) {
                for (String decAlgo : DecryptionAlgorithms) {
                    bruteWithAlgorithm(viewStateData, bruteResult, valAlgo, decAlgo);
                    if (bruteResult.isSuccess())
                        return bruteResult;
                }
            } else {
                bruteWithAlgorithm(viewStateData, bruteResult, valAlgo, null);
                if (bruteResult.isSuccess())
                    return bruteResult;
            }
        }
        return bruteResult;
    }

    private static void bruteWithAlgorithm(ViewStateData viewStateData, BruteResult bruteResult, String valAlgo, String decAlgo) {
        for (KeyPair mk : machineKeys) {
            try {
                byte[] payload = verifyAndDecryptViewState(viewStateData.getViewState(), mk.getValidationKey(), valAlgo, mk.getDecryptionKey(), decAlgo, viewStateData.getViewStateGenerator(), viewStateData.isViewStateEncrypted());
                bruteResult.setLosFormatterPayload(Base64.encode(payload));
                bruteResult.setValidationKeyHex(mk.getValidationKey());
                bruteResult.setDecryptionKeyHex(mk.getDecryptionKey());
                bruteResult.setValidationAlgorithm(valAlgo);
                bruteResult.setDecryptionAlgorithm(decAlgo);
                bruteResult.setSuccess(true);
                return;
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
