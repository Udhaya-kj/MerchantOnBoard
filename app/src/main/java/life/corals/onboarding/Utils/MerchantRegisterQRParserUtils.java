package life.corals.onboarding.Utils;

import android.content.Context;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MerchantRegisterQRParserUtils {

    private static final String TAG = "QR_CODE_PARSER";

    private static final String SAMPLE_QR_DATA = "00020101021226520008com.grab01367fd77304-556e-4992-9273-3a6c7f2af9825204581253037025802SG5924Eden Garden Cafe Pte Ltd6009Singapore62110707010002191480008com.grab0732f367a2bdc91e4938a7906961f2652d3563042EA4";

    private static final String STATIC_NODE_00 = "00";

    private static final String STATIC_NODE_01 = "01";

    // 2 digit number from 25 - 50
    private static final String STATIC_NODE_VARIANTS = "";

    private int STATIC_NODE_VARIANTS_LENGTH = 2;

    private static final String STATIC_NODE_52 = "52";

    private static final String STATIC_NODE_53 = "53";

    private static final String STATIC_NODE_58 = "58";

    private HashMap<String, String> NODE_AND_VALUES = new HashMap<>();

    private boolean isParsingComplete = false;

    private HashMap<String, String> RESULT_HASH_MAP = new HashMap<>();

    private Context mCtx;

    private boolean containsFirstNodes = false;
    private boolean containsStaticNodes = true;

    private CoralsToast coralsToast;

    public MerchantRegisterQRParserUtils(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void extractQrData(String sampleQrData) {

        String qrData = sampleQrData;
        //String qrData = SAMPLE_QR_DATA;

        List<String> NODES = Arrays.asList(STATIC_NODE_00, STATIC_NODE_01, STATIC_NODE_VARIANTS, STATIC_NODE_52, STATIC_NODE_53, STATIC_NODE_58);

        int i = 0;
        try {

            for (String NODE : NODES) {

                int startPosition = 0;
                int endPosition = 0;

                Pattern word = Pattern.compile(NODE);
                Matcher match = word.matcher(qrData);
                if (i != 2) {
                    try {
                        while (match.find()) {

                            startPosition = match.start();
                            endPosition = match.end();

                            if (startPosition == 0) {

                                String first = qrData.substring(startPosition, endPosition);

                                String second = qrData.substring(endPosition);

                                int lengthOfNodeValue = Integer.parseInt(second.substring(0, endPosition));

                                String third = second.substring(endPosition);

                                String value = third.substring(0, lengthOfNodeValue);
                                Log.d(TAG, "parsed: " + first + "   " + value);
                                NODE_AND_VALUES.put(first, value);

                                qrData = third.substring(lengthOfNodeValue);

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        containsFirstNodes = false;
                    }
                } else {
                    try {
                        List<String> VARIANTS = getSTATIC_NODE_VARIANTS();
                        for (String NODE_STATIC_VARIANTS : VARIANTS) {

                            Pattern wordToMatch = Pattern.compile(NODE_STATIC_VARIANTS);
                            Matcher matchPattern = wordToMatch.matcher(qrData);


                            while (matchPattern.find()) {

                                int start = 0;
                                int end = STATIC_NODE_VARIANTS_LENGTH;

                                String first = qrData.substring(start, end);

                                String second = qrData.substring(end);

                                int lengthOfNodeValue = Integer.parseInt(second.substring(0, end));

                                String third = second.substring(end);

                                String value = third.substring(0, lengthOfNodeValue);

                                Log.d(TAG, "parsed: " + first + "   " + value);
                                NODE_AND_VALUES.put(first, value);

                                qrData = third.substring(lengthOfNodeValue);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        containsStaticNodes = false;
                    }
                }

                i++;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String s : NODES) {
            if (!s.equals(STATIC_NODE_VARIANTS)) {
                if (NODE_AND_VALUES.containsKey(s)) {
                    if (NODE_AND_VALUES.get(s) != null) {
                        RESULT_HASH_MAP.put(s, NODE_AND_VALUES.get(s));
                        containsFirstNodes = true;
                        Log.d(TAG, "extractQrData: " + s + "  " + NODE_AND_VALUES.get(s));
                    }
                }
            } else {
                for (String n : getSTATIC_NODE_VARIANTS()) {
                    if (NODE_AND_VALUES.containsKey(n)) {
                        if (NODE_AND_VALUES.get(n) != null) {
                            RESULT_HASH_MAP.put(n, NODE_AND_VALUES.get(n));
                            containsStaticNodes = true;
                            Log.d(TAG, "extractQrData26: " + n + "  " + NODE_AND_VALUES.get(n));
                            isParsingComplete = true;
                        } else {
                            onFailureParser("Invalid QR code");
                            return;
                        }
                    }
                }
            }
        }

        if (RESULT_HASH_MAP.get(STATIC_NODE_00) != null || StringUtils.isNotEmpty(RESULT_HASH_MAP.get(STATIC_NODE_00))) {
            String node00 = RESULT_HASH_MAP.get(STATIC_NODE_00);
            if ("P".equals(node00)
                    || "T".equals(node00)) {
                onFailureParser("Invalid merchant QR code");
                return;
            }
        }

        if (!containsStaticNodes || !containsFirstNodes || RESULT_HASH_MAP.get(STATIC_NODE_58) == null) {
            onFailureParser("Invalid merchant QR code");
            return;
        }

        Log.d(TAG, "extractQrData: parsed");
        parsedData(RESULT_HASH_MAP);
    }


    public abstract void parsedData(HashMap<String, String> hashMap);

    public abstract void onFailureParser(String result);

    private List<String> getSTATIC_NODE_VARIANTS() {
        List<String> stringList = new ArrayList<>();
        for (int i = 25; i <= 50; i++) {
            stringList.add(String.valueOf(i));
        }

        return stringList;
    }


}
