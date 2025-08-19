import cn.wanghw.models.BruteResult;
import cn.wanghw.models.ViewStateData;
import org.junit.Test;

import java.util.Base64;
import java.util.logging.Logger;

import static cn.wanghw.utils.ViewStateUtil.verifyAndDecryptViewState;

public class DESTests {
    final Logger logger = Logger.getLogger(DESTests.class.getName());

    @Test
    public void testDESWithEncrypted() throws Exception {
        ViewStateData viewStateData = new ViewStateData();
        viewStateData.setViewState("Z+rfkSyLKkDpwMiL7PrIvjZHqJ7uqXJHTKJlQgtRiLqkIh0Om2vBNriltBSoGguSvLQe4xgQHA+ph7BuaEbcCwsNWFdN0V07ivvk0kJ324YyJ3W35It8uk1loJ4/4cAL");
        viewStateData.setViewStateGenerator("5A108D17");
        viewStateData.setViewStateEncrypted(true);
        BruteResult bruteResult = new BruteResult();
        bruteResult.setValidationAlgorithm("HMACSHA512");
        bruteResult.setValidationKeyHex("319B474B1D2B7A87C996B280450BB36506A95AEDF9B51211");
        bruteResult.setDecryptionAlgorithm("DES");
        bruteResult.setDecryptionKeyHex("280450BB36319B474C996B506A95AEDF9B51211B1D2B7A87");
        verifyAndDecryptViewState(viewStateData, bruteResult);
        if (!bruteResult.isSuccess())
            throw new Exception(bruteResult.getLosFormatterPayload());
        else {
            logger.info("DESWithEncrypted ok!");
        }
    }
}
