package cn.wanghw.models;

import cn.wanghw.utils.ViewStateUtil;

public class BruteResult {
    private boolean success = false;
    private ViewStateData viewStateData;
    private String validationKeyHex;
    private String validationAlgorithm;
    private String decryptionKeyHex;
    private String decryptionAlgorithm;
    private String losFormatterPayload;
    private Purpose purpose;
    private String modifier;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ViewStateData getViewStateData() {
        return viewStateData;
    }

    public void setViewStateData(ViewStateData viewStateData) {
        this.viewStateData = viewStateData;
    }

    public String getValidationKeyHex() {
        return validationKeyHex;
    }

    public void setValidationKeyHex(String validationKeyHex) {
        this.validationKeyHex = validationKeyHex;
    }

    public String getValidationAlgorithm() {
        return validationAlgorithm;
    }

    public void setValidationAlgorithm(String validationAlgorithm) {
        this.validationAlgorithm = validationAlgorithm;
    }

    public String getDecryptionKeyHex() {
        return decryptionKeyHex;
    }

    public void setDecryptionKeyHex(String decryptionKeyHex) {
        this.decryptionKeyHex = decryptionKeyHex;
    }

    public String getDecryptionAlgorithm() {
        return decryptionAlgorithm;
    }

    public void setDecryptionAlgorithm(String decryptionAlgorithm) {
        this.decryptionAlgorithm = decryptionAlgorithm;
    }

    @Override
    public String toString() {
        return "BlastResult{" +
                "success=" + success +
                ", viewStateData=" + viewStateData +
                ", validationKeyHex='" + validationKeyHex + '\'' +
                ", validationAlgorithm='" + validationAlgorithm + '\'' +
                ", decryptionKeyHex='" + decryptionKeyHex + '\'' +
                ", decryptionAlgorithm='" + decryptionAlgorithm + '\'' +
                '}';
    }

    public String getDetail() {
        StringBuilder sb = new StringBuilder();
        if (success) {
            sb.append("Found ViewState MachineKey.<br><br>");
            sb.append(String.format("ValidationKey: <b>%s</b><br>", validationKeyHex));
            sb.append(String.format("ValidationAlgorithm: <b>%s</b><br>", validationAlgorithm));
            if (decryptionAlgorithm != null || purpose != null) {
                sb.append(String.format("DecryptionKey: <b>%s</b><br>", decryptionKeyHex));
                sb.append(String.format("DecryptionAlgorithm: <b>%s</b><br>", decryptionAlgorithm));
            } else if (ViewStateUtil.isSymAlgo(validationAlgorithm)) {
                sb.append(String.format("DecryptionKey: <b>%s</b><br>", decryptionKeyHex));
                sb.append(String.format("DecryptionAlgorithm: <b>%s</b><br>", validationAlgorithm));
            }
            if (modifier != null) {
                sb.append(String.format("Modifier: <b>%s</b><br>", modifier));
            }
            if (purpose != null) {
                sb.append(String.format("Purpose.Path: <b>%s</b><br>", purpose.getPath()));
                sb.append(String.format("Purpose.AppPath: <b>%s</b><br>", purpose.getAppPath()));
            }
            sb.append(String.format("LosFormatterPayload: <b>%s</b><br>", losFormatterPayload));
        }
        return sb.toString();
    }

    public String getLosFormatterPayload() {
        return losFormatterPayload;
    }

    public void setLosFormatterPayload(String losFormatterPayload) {
        this.losFormatterPayload = losFormatterPayload;
    }

    public Purpose getPurpose() {
        return purpose;
    }

    public void setPurpose(Purpose purpose) {
        this.purpose = purpose;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}
