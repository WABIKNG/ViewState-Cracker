import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class SHA1Tests {
    final Logger logger = Logger.getLogger(SHA1Tests.class.getName());

    @Test
    public void testSHA1() throws Exception {
        String val = Base64.getEncoder().encodeToString(verifyAndDecryptViewState(
                "/wEPDwUKMTY3NzE5MjIyMGRkNQhQVT0DTtqYGat6J8BtJS8eTWs=",
                "319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211",
                "SHA1",
                "280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87",
                "3DES",
                "5A108D17", false));
        if (!val.equals("/wEPDwUKMTY3NzE5MjIyMGRk"))
            throw new Exception(val);
        else {
            logger.info("SHA1 ok!");
        }
    }
    @Test
    public void testSHA1WithEncrypted() throws Exception {
        String val = Base64.getEncoder().encodeToString(verifyAndDecryptViewState(
                "TD4kl1VHQIXmq/4T8uzMiriKBHSATfsc3klvleWKS4RCuX1SJYqpAp9bRN6zt+WjkMzwYQj9KuQU3OwZnIGiD3OVPJo=",
                "319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211",
                "SHA1",
                "280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87",
                "AES",
                "5A108D17", true));
        if (!val.equals("/wEPDwUKMTY3NzE5MjIyMGRk"))
            throw new Exception(val);
        else {
            logger.info("SHA1WithEncrypted ok!");
        }
    }
}
