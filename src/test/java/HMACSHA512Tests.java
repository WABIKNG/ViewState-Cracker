import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class HMACSHA512Tests {
    final Logger logger = Logger.getLogger(HMACSHA512Tests.class.getName());

    @Test
    public void testHMACSHA512() throws Exception {
        String val = Base64.getEncoder().encodeToString(verifyAndDecryptViewState(
                "/wEPDwUKMTY3NzE5MjIyMGRkmBoKZF3pj71/SJtZIPD79tjLZrMVXHU+FUSASPOVLknrW3lTE8KfLEo82rxUBGaOvhLsAN2eqGEdtSRUnLnDaQ==",
                "319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211",
                "HMACSHA512",
                "280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87",
                "3DES",
                "5A108D17", false));
        if (!val.equals("/wEPDwUKMTY3NzE5MjIyMGRk"))
            throw new Exception(val);
        else {
            logger.info("HMACSHA512 ok!");
        }
    }
}
