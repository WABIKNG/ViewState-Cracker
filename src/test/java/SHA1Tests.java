import cn.wanghw.models.BruteResult;
import cn.wanghw.models.ViewStateData;
import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class SHA1Tests {
    final Logger logger = Logger.getLogger(SHA1Tests.class.getName());

    @Test
    public void testSHA1() throws Exception {
        ViewStateData viewStateData = new ViewStateData();
        viewStateData.setViewState("/wEPDwUKMTY3NzE5MjIyMGRkNQhQVT0DTtqYGat6J8BtJS8eTWs=");
        viewStateData.setViewStateGenerator("5A108D17");
        viewStateData.setViewStateEncrypted(false);
        BruteResult bruteResult = new BruteResult();
        bruteResult.setValidationAlgorithm("SHA1");
        bruteResult.setValidationKeyHex("319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211");
        bruteResult.setDecryptionAlgorithm("3DES");
        bruteResult.setDecryptionKeyHex("280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87");
        verifyAndDecryptViewState(viewStateData, bruteResult);
        if (!bruteResult.isSuccess())
            throw new Exception(bruteResult.getLosFormatterPayload());
        else {
            logger.info("SHA1 ok!");
        }
    }
    @Test
    public void testSHA1WithEncrypted() throws Exception {
        ViewStateData viewStateData = new ViewStateData();
        viewStateData.setViewState("TD4kl1VHQIXmq/4T8uzMiriKBHSATfsc3klvleWKS4RCuX1SJYqpAp9bRN6zt+WjkMzwYQj9KuQU3OwZnIGiD3OVPJo=");
        viewStateData.setViewStateGenerator("5A108D17");
        viewStateData.setViewStateEncrypted(true);
        BruteResult bruteResult = new BruteResult();
        bruteResult.setValidationAlgorithm("SHA1");
        bruteResult.setValidationKeyHex("319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211");
        bruteResult.setDecryptionAlgorithm("AES");
        bruteResult.setDecryptionKeyHex("280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87");
        verifyAndDecryptViewState(viewStateData, bruteResult);
        if (!bruteResult.isSuccess())
            throw new Exception(bruteResult.getLosFormatterPayload());
        else {
            logger.info("SHA1WithEncrypted ok!");
        }
    }
}
