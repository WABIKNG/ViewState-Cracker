import cn.wanghw.models.BruteResult;
import cn.wanghw.models.ViewStateData;
import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class HMACSHA384Tests {
    final Logger logger = Logger.getLogger(HMACSHA384Tests.class.getName());

    @Test
    public void testHMACSHA384() throws Exception {
        ViewStateData viewStateData = new ViewStateData();
        viewStateData.setViewState("/wEPDwUKMTY3NzE5MjIyMGRk/J5s6usDCYYRq7wzcjPM1FGlYFI55GDIvlIpf4bxFFL8lOKqEyngjn+CwSnAvnpH");
        viewStateData.setViewStateGenerator("5A108D17");
        viewStateData.setViewStateEncrypted(false);
        BruteResult bruteResult = new BruteResult();
        bruteResult.setValidationAlgorithm("HMACSHA384");
        bruteResult.setValidationKeyHex("319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211");
        bruteResult.setDecryptionAlgorithm("3DES");
        bruteResult.setDecryptionKeyHex("280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87");
        verifyAndDecryptViewState(viewStateData, bruteResult);
        if (!bruteResult.isSuccess())
            throw new Exception(bruteResult.getLosFormatterPayload());
        else {
            logger.info("HMACSHA384 ok!");
        }
    }
}
