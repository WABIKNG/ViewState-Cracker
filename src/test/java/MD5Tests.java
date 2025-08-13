import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class MD5Tests {
    final Logger logger = Logger.getLogger(MD5Tests.class.getName());

    @Test
    public void testMD5() throws Exception {
        String val = Base64.getEncoder().encodeToString(verifyAndDecryptViewState(
                "/wEPDwUKMTY3NzE5MjIyMGRk7ruGo7JV6grt1PamLlfK5w==",
                "319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211",
                "MD5",
                "280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87",
                "3DES",
                "5A108D17", false));
        if (!val.equals("/wEPDwUKMTY3NzE5MjIyMGRk"))
            throw new Exception(val);
        else {
            logger.info("MD5 ok!");
        }
    }
    @Test
    public void testMD5WithEncrypted() throws Exception {
        String val = Base64.getEncoder().encodeToString(verifyAndDecryptViewState(
                "LS2g838egjRUOb4I0LaMAaynQRONKQI4w6gKrDImp17Oh8aGWtzmQm+I0HL5ZKGrA/iSae1GMkyle29P22QtQQ==",
                "319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211",
                "MD5",
                "280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87",
                "AES",
                "5A108D17", true));
        if (!val.equals("/wEPDwUKMTY3NzE5MjIyMGRk"))
            throw new Exception(val);
        else {
            logger.info("MD5WithEncrypted ok!");
        }
    }
}
