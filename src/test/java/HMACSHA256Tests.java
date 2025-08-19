import cn.wanghw.models.BruteResult;
import cn.wanghw.models.ViewStateData;
import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class HMACSHA256Tests {
    final Logger logger = Logger.getLogger(HMACSHA256Tests.class.getName());

    @Test
    public void testHMACSHA256() throws Exception {
        ViewStateData viewStateData = new ViewStateData();
        viewStateData.setViewState("/wEPDwUKMTY3NzE5MjIyMGRkXl9wJkYyCD2+tHuvpJfbBQVJfo17d29eq8F4wyb3cGs=");
        viewStateData.setViewStateGenerator("5A108D17");
        viewStateData.setViewStateEncrypted(false);
        BruteResult bruteResult = new BruteResult();
        bruteResult.setValidationAlgorithm("HMACSHA256");
        bruteResult.setValidationKeyHex("319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211");
        bruteResult.setDecryptionAlgorithm("3DES");
        bruteResult.setDecryptionKeyHex("280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87");
        verifyAndDecryptViewState(viewStateData, bruteResult);
        if (!bruteResult.isSuccess())
            throw new Exception(bruteResult.getLosFormatterPayload());
        else {
            logger.info("HMACSHA256 ok!");
        }
    }
}
