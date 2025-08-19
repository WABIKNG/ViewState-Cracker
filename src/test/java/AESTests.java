import cn.wanghw.models.BruteResult;
import cn.wanghw.models.ViewStateData;
import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class AESTests {
    final Logger logger = Logger.getLogger(AESTests.class.getName());

    @Test
    public void testAES() throws Exception {
        ViewStateData viewStateData = new ViewStateData();
        viewStateData.setViewState("Kl+uJ17DKHmUs//6Zx7lUa9ZbRI4DQlWspWzruHhxeBPt6hnZMyzp/jG/39MtmJ9uKkq7rWW513CDYmGlLNFBNJOnlS/y5W6HpkVTMFFZ+bNhiaIfZNnqKzdyzu0WatXtxGbHw==");
        viewStateData.setViewStateGenerator("5A108D17");
        viewStateData.setViewStateEncrypted(false);
        BruteResult bruteResult = new BruteResult();
        bruteResult.setValidationAlgorithm("AES");
        bruteResult.setValidationKeyHex("319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211");
        bruteResult.setDecryptionAlgorithm("3DES");
        bruteResult.setDecryptionKeyHex("280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87");
        verifyAndDecryptViewState(viewStateData, bruteResult);
        if (!bruteResult.isSuccess())
            throw new Exception(bruteResult.getLosFormatterPayload());
        else {
            logger.info("AES ok!");
        }
    }

    @Test
    public void testAESWithEncrypted() throws Exception {
        ViewStateData viewStateData = new ViewStateData();
        viewStateData.setViewState("n0pNOXiq4gDjfjMWVC21PVJ9cbYSXh/fCSSEKuhmi5usnMdiydtx1sjUxovQ9B0vmyUZA7HuIYRNxijcD2d0qogbAoq3r/8/Ld2mF9xZkk38DBNSafV13yZetCD/mY3ECnMl3cjRwnPDukjUIqj0Gg==");
        viewStateData.setViewStateGenerator("5A108D17");
        viewStateData.setViewStateEncrypted(true);
        BruteResult bruteResult = new BruteResult();
        bruteResult.setValidationAlgorithm("HMACSHA512");
        bruteResult.setValidationKeyHex("319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211");
        bruteResult.setDecryptionAlgorithm("AES");
        bruteResult.setDecryptionKeyHex("280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87");
        verifyAndDecryptViewState(viewStateData, bruteResult);
        if (!bruteResult.isSuccess())
            throw new Exception(bruteResult.getLosFormatterPayload());
        else {
            logger.info("AESWithEncrypted ok!");
        }
    }

    @Test
    public void testAESWithEncryptedFor4Dot5() throws Exception {
        ViewStateData viewStateData = new ViewStateData();
        viewStateData.setViewState("rB1aAtuotmjHRBU71tWz2a1bPPJJuDHuYz16Q7xB680Q7yVw34QZ+6sFu/o8toTQCdL14+rniYCKPCOK1NfVtGL0x3M=");
        viewStateData.setViewStateGenerator("BE5A8042");
        viewStateData.setPath("/demo/Login.aspx");
        viewStateData.setViewStateEncrypted(true);
        BruteResult bruteResult = new BruteResult();
        bruteResult.setValidationAlgorithm("SHA1");
        bruteResult.setValidationKeyHex("32E35872597989D14CC1D5D9F5B1E94238D0EE32CF10AA2D2059533DF6035F4F");
        bruteResult.setDecryptionAlgorithm("AES");
        bruteResult.setDecryptionKeyHex("B179091DBB2389B996A526DE8BCD7ACFDBCAB04EF1D085481C61496F693DF5F4");
        verifyAndDecryptViewState(viewStateData, bruteResult);
        if (!bruteResult.isSuccess())
            throw new Exception(bruteResult.getLosFormatterPayload());
        else {
            logger.info("AESWithEncryptedFor4Dot5 ok!");
        }
    }
}
