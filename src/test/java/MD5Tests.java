import cn.wanghw.models.BruteResult;
import cn.wanghw.models.ViewStateData;
import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class MD5Tests {
    final Logger logger = Logger.getLogger(MD5Tests.class.getName());

    @Test
    public void testMD5() throws Exception {
        ViewStateData viewStateData = new ViewStateData();
        viewStateData.setViewState("/wEPDwUKMTY3NzE5MjIyMGRk7ruGo7JV6grt1PamLlfK5w==");
        viewStateData.setViewStateGenerator("5A108D17");
        viewStateData.setViewStateEncrypted(false);
        BruteResult bruteResult = new BruteResult();
        bruteResult.setValidationAlgorithm("MD5");
        bruteResult.setValidationKeyHex("319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211");
        bruteResult.setDecryptionAlgorithm("3DES");
        bruteResult.setDecryptionKeyHex("280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87");
        verifyAndDecryptViewState(viewStateData, bruteResult);
        if (!bruteResult.isSuccess())
            throw new Exception(bruteResult.getLosFormatterPayload());
        else {
            logger.info("MD5 ok!");
        }
    }

    @Test
    public void testMD5WithEncrypted() throws Exception {
        ViewStateData viewStateData = new ViewStateData();
        viewStateData.setViewState("LS2g838egjRUOb4I0LaMAaynQRONKQI4w6gKrDImp17Oh8aGWtzmQm+I0HL5ZKGrA/iSae1GMkyle29P22QtQQ==");
        viewStateData.setViewStateGenerator("5A108D17");
        viewStateData.setViewStateEncrypted(true);
        BruteResult bruteResult = new BruteResult();
        bruteResult.setValidationAlgorithm("MD5");
        bruteResult.setValidationKeyHex("319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211");
        bruteResult.setDecryptionAlgorithm("AES");
        bruteResult.setDecryptionKeyHex("280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87");
        verifyAndDecryptViewState(viewStateData, bruteResult);
        if (!bruteResult.isSuccess())
            throw new Exception(bruteResult.getLosFormatterPayload());
        else {
            logger.info("MD5WithEncrypted ok!");
        }
    }
}
