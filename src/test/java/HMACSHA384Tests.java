import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class HMACSHA384Tests {
    final Logger logger = Logger.getLogger(HMACSHA384Tests.class.getName());

    @Test
    public void testHMACSHA384() throws Exception {
        String val = Base64.getEncoder().encodeToString(verifyAndDecryptViewState(
                "/wEPDwUKMTY3NzE5MjIyMGRk/J5s6usDCYYRq7wzcjPM1FGlYFI55GDIvlIpf4bxFFL8lOKqEyngjn+CwSnAvnpH",
                "319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211",
                "HMACSHA384",
                "280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87",
                "3DES",
                "5A108D17", false));
        if (!val.equals("/wEPDwUKMTY3NzE5MjIyMGRk"))
            throw new Exception(val);
        else {
            logger.info("HMACSHA384 ok!");
        }
    }
}
