import cn.wanghw.models.BruteResult;
import cn.wanghw.models.ViewStateData;
import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class HMACSHA512Tests {
    final Logger logger = Logger.getLogger(HMACSHA512Tests.class.getName());

    @Test
    public void testHMACSHA512() throws Exception {
        ViewStateData viewStateData = new ViewStateData();
        viewStateData.setViewState("/wEPDwUKMTY3NzE5MjIyMGRkmBoKZF3pj71/SJtZIPD79tjLZrMVXHU+FUSASPOVLknrW3lTE8KfLEo82rxUBGaOvhLsAN2eqGEdtSRUnLnDaQ==");
        viewStateData.setViewStateGenerator("5A108D17");
        viewStateData.setViewStateEncrypted(false);
        BruteResult bruteResult = new BruteResult();
        bruteResult.setValidationAlgorithm("HMACSHA512");
        bruteResult.setValidationKeyHex("319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211");
        bruteResult.setDecryptionAlgorithm("3DES");
        bruteResult.setDecryptionKeyHex("280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87");
        verifyAndDecryptViewState(viewStateData, bruteResult);
        if (!bruteResult.isSuccess())
            throw new Exception(bruteResult.getLosFormatterPayload());
        else {
            logger.info("HMACSHA512 ok!");
        }
    }
}
