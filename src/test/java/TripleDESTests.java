import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class TripleDESTests {
    final Logger logger = Logger.getLogger(TripleDESTests.class.getName());

    @Test
    public void testTripleDES() throws Exception {
        String val = Base64.getEncoder().encodeToString(verifyAndDecryptViewState(
                "uO0vXXsFceSfzsLXTn9OnuENleqLbbNRttIRy7vG/yjJdomuRQi2PIHXnOW99U+8KZQORvCTIC3KaLf3BIWFCzBNXnyyKBhuFi7khGHieTfss/lpOdHZIhcxjnY=",
                "319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211",
                "3DES",
                "280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87",
                "3DES",
                "5A108D17",
                false));
        if (!val.equals("/wEPDwUKMTY3NzE5MjIyMGRk"))
            throw new Exception(val);
        else {
            logger.info("3DES ok!");
        }
    }

    @Test
    public void testTripleDESWithEncrypted() throws Exception {
        String val = Base64.getEncoder().encodeToString(verifyAndDecryptViewState(
                "lgfdSOVtlURaSx2WWxY9nHzrIZohaG7Jq6mRX90OMc6OgcaF4aUlM5Opyil7u96lghmgSRprfGcHouA5OqtAWj17m6i3BmVYDPZPljOFV3z8WFtg4ZBaPOn7s6QQMN3oj8wh3rWzaM90roYmYJxs+A==",
                "319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211",
                "HMACSHA512",
                "280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87",
                "3DES",
                "5A108D17",
                true));
        if (!val.equals("/wEPDwUKMTY3NzE5MjIyMGRk"))
            throw new Exception(val);
        else {
            logger.info("3DESWithEncrypted ok!");
        }
    }
}
