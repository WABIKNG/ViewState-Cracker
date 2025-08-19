import cn.wanghw.models.BruteResult;
import cn.wanghw.models.ViewStateData;
import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class TripleDESTests {
    final Logger logger = Logger.getLogger(TripleDESTests.class.getName());

    @Test
    public void testTripleDES() throws Exception {
        ViewStateData viewStateData = new ViewStateData();
        viewStateData.setViewState("uO0vXXsFceSfzsLXTn9OnuENleqLbbNRttIRy7vG/yjJdomuRQi2PIHXnOW99U+8KZQORvCTIC3KaLf3BIWFCzBNXnyyKBhuFi7khGHieTfss/lpOdHZIhcxjnY=");
        viewStateData.setViewStateGenerator("5A108D17");
        viewStateData.setViewStateEncrypted(false);
        BruteResult bruteResult = new BruteResult();
        bruteResult.setValidationAlgorithm("3DES");
        bruteResult.setValidationKeyHex("319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211");
        bruteResult.setDecryptionAlgorithm("3DES");
        bruteResult.setDecryptionKeyHex("280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87");
        verifyAndDecryptViewState(viewStateData, bruteResult);
        if (!bruteResult.isSuccess())
            throw new Exception(bruteResult.getLosFormatterPayload());
        else {
            logger.info("3DES ok!");
        }
    }

    @Test
    public void testTripleDESWithEncrypted() throws Exception {
        ViewStateData viewStateData = new ViewStateData();
        viewStateData.setViewState("lgfdSOVtlURaSx2WWxY9nHzrIZohaG7Jq6mRX90OMc6OgcaF4aUlM5Opyil7u96lghmgSRprfGcHouA5OqtAWj17m6i3BmVYDPZPljOFV3z8WFtg4ZBaPOn7s6QQMN3oj8wh3rWzaM90roYmYJxs+A==");
        viewStateData.setViewStateGenerator("5A108D17");
        viewStateData.setViewStateEncrypted(true);
        BruteResult bruteResult = new BruteResult();
        bruteResult.setValidationAlgorithm("HMACSHA512");
        bruteResult.setValidationKeyHex("319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211");
        bruteResult.setDecryptionAlgorithm("3DES");
        bruteResult.setDecryptionKeyHex("280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87");
        verifyAndDecryptViewState(viewStateData, bruteResult);
        if (!bruteResult.isSuccess())
            throw new Exception(bruteResult.getLosFormatterPayload());
        else {
            logger.info("3DESWithEncrypted ok!");
        }
    }
}
