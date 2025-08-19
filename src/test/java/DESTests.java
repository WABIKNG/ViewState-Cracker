import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class DESTests {
    final Logger logger = Logger.getLogger(DESTests.class.getName());

    @Test
    public void testDESWithEncrypted() throws Exception {
        String val = Base64.getEncoder().encodeToString(verifyAndDecryptViewState(
                "Z+rfkSyLKkDpwMiL7PrIvjZHqJ7uqXJHTKJlQgtRiLqkIh0Om2vBNriltBSoGguSvLQe4xgQHA+ph7BuaEbcCwsNWFdN0V07ivvk0kJ324YyJ3W35It8uk1loJ4/4cAL",
                "319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211",
                "HMACSHA512",
                "280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87",
                "DES",
                "5A108D17",
                true, ""));
        if (!val.equals("/wEPDwUKMTY3NzE5MjIyMGRk"))
            throw new Exception(val);
        else {
            logger.info("DESWithEncrypted ok!");
        }
    }
}
