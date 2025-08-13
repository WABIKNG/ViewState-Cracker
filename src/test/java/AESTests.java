import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class AESTests {
    final Logger logger = Logger.getLogger(AESTests.class.getName());

    @Test
    public void testAES() throws Exception {
        String val = Base64.getEncoder().encodeToString(verifyAndDecryptViewState(
                "Kl+uJ17DKHmUs//6Zx7lUa9ZbRI4DQlWspWzruHhxeBPt6hnZMyzp/jG/39MtmJ9uKkq7rWW513CDYmGlLNFBNJOnlS/y5W6HpkVTMFFZ+bNhiaIfZNnqKzdyzu0WatXtxGbHw==",
                "319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211",
                "AES",
                "280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87",
                "3DES",
                "5A108D17", false));
        if (!val.equals("/wEPDwUKMTY3NzE5MjIyMGRk"))
            throw new Exception(val);
        else {
            logger.info("AES ok!");
        }
    }

    @Test
    public void testAESWithEncrypted() throws Exception {
        String val = Base64.getEncoder().encodeToString(verifyAndDecryptViewState(
                "n0pNOXiq4gDjfjMWVC21PVJ9cbYSXh/fCSSEKuhmi5usnMdiydtx1sjUxovQ9B0vmyUZA7HuIYRNxijcD2d0qogbAoq3r/8/Ld2mF9xZkk38DBNSafV13yZetCD/mY3ECnMl3cjRwnPDukjUIqj0Gg==",
                "319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211",
                "HMACSHA512",
                "280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87",
                "AES",
                "5A108D17",
                true));
        if (!val.equals("/wEPDwUKMTY3NzE5MjIyMGRk"))
            throw new Exception(val);
        else {
            logger.info("3DESWithEncrypted ok!");
        }
    }
}
