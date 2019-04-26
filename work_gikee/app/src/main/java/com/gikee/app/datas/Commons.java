package com.gikee.app.datas;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/**
 * Created by THINKPAD on 2018/6/11.
 */

public class Commons{

    public static boolean isDebug=true;
    public static float rate;
    public static float rateUSD_btc;
    public static float rateCNY_btc;
    public static float rateUSD_eth;
    public static float rateCNY_eth;
    public static int lanuage_type=4;
    public static boolean quate_color=true;
    public static Locale type = Locale.SIMPLIFIED_CHINESE;
    public static final String WEBSOCKET_URL = isDebug?"ws://192.168.1.113:60080/bigChange/android":"ws://192.168.1.122:60080/bigChange/android";
    public static final String EXTRA_BOOLEAN="com.gikee.app.EXTRA_BOOLEAN";
    public static final String APP_CACHE_PATH="EmoneyIMSysCache";
    public static final String SHARED_UPGRADE_IGNORE_VERSION="com.gikee.app.SHARED_UPGRADE_IGNORE_VERSION";
    public static final String BROADCAST_MSG_UPGRADE_PROGRESS="com.gikee.app.BROADCAST_MSG_UPGRADE_PROGRESS";
    public static final String EXTRA_UPGRADE_PROGRESS="com.gikee.app.EXTRA_UPGRADE_PROGRESS";

    public static final String BTC="BTC";
    public static final String ETH="ETH";
    public static final String EOS="EOS";

    public static String BASELINE="";

    public static final String ADDRESS="address";
    public static final String HASH="hash";
    public static final String TXHASH="Txhash";

    //自定义接收到推送消息通知
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";

    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static boolean isForeground = true;
    public static final int Remind = 3;
    public static final String history="history_search";

    public static final String history_special="historyspecial_search";

    //数据本地持久化
    public static final String ITEM_DATA = "item_data";
    public static final String BASELINE_DATA = "baseline_data";
    public static final String HOTPROJECT_DATA = "hotproject_data";
    public static final String LEADER_DATA = "leader_data";
    public static final String BTCADDRESS_DATA = "btcaddress_data";
    public static final String BTCADDRESSTRADE_DATA = "btcaddresstrade_data";
    public static final String ETHADDRESS_DATA = "ethaddress_data";
    public static final String ETHADDRESSTRADE_DATA = "ethaddresstrade_data";

    public static String collect_address = "";


    public static List<String> ETH_ADDRESS=new ArrayList<>();



    public static String smybl=null;

    public static String id =null;


    //各个平台推送
    public static final String MIAPP_ID = "2882303761517864637";
    public static final String MIAPP_KEY = "5891786480637";

    public static final String Meizu_APP_ID = "116002";
    public static final String Meizu_APP_KEY = "8e3403f050b04887a8620522c526dd83";


    // Fields from default config.
    /**
     * 后台为每个应用分配的id，用于唯一标识一个应用，在程序代码中用不到
     */
    public static final String oppo_appId = "3703028";
    /**
     * appKey，用于向push服务器进行注册，开发者应当谨慎保存，避免泄漏
     */
    public static final String oppo_appKey = "67ZtaSY1EyjZZoYqqYVT6e3X";
    /**
     * appSecret，用于进行注册和消息加解密，开发者应当谨慎保存，避免泄漏
     */
    public static final String oppo_appSecret = "f41c5c0875954eaf97fbc944c12eb8b1";



    public static final String WX_APP_ID = "wx1c7e028967ecff06"; //5a23175d22cd74c1d290a46829c7a30b

    public static final String WX_APP_Secret = "wx1c7e028967ecff06";

    public static final String TC_APP_ID = "1107712777";

    public static final String TC_APP_key = "19G3G1J8OTrFgym0";

    public static final String WEIBO_APP_ID="2249814196";//2249814196

    public static final String WEIBO_APP_Secret="49520c5b2d3de36a308cd3900d319f34";

    public static final String REDIRECT_URL = "http://sns.whalecloud.com";


    //public static String share_gikee = "https://at.umeng.com/rCCWLD?cid=7030";

    public static String share_gikee = "https://at.umeng.com/CObyWr?cid=9279";

    public static String share_title = "Gikee";

    public static String share_description = "区块链数字资产监测，浏览及可视化工具";

    public static  String version_info;

    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    public static final String price = "price";

    public static final String topPlayer = "topPlayer";

    public static final String allAddr= "totalAccount";//总地址数

    public static final String ownAddr= "ownAccount";//持币地址数

    public static final String top100 = "top100";//TOP100地址资产分布

    public static final String participateAddress = "participateAccount";//参与交易地址

    public static final String tradeValue= "tradeValue";//交易金额

    public static final String avgTrdValue= "avgTrdValue";//"平均每笔交易金额"

    public static final String topDis = "topDis";//持币地址资产分布

    public static final String specialTrade= "specialTrade";//特殊交易监控

    public static final String newAndInactivity = "newAndInactivity";//新增与唤醒地址

    public static final String newAccount = "newAccount";//新增地址

    public static final String wakeCount = "awakenAccount";//唤醒地址

    public static final String inactiveCount = "inactiveAccount";//休眠地址

    public static final String activeDis= "activeAccount";//活跃账户

    public static final String tradevolume = "tradeVolume";//交易量

    public static final String tradeCount = "tradeCount";//交易笔数

    public static final String avgTrdVol = "avgTrdVolume";//'平均每笔交易量'

    public static final String topFreqAddr = "tradeCountRanking";//地址交易频次排名

    public static final String tradeCountDis = "tradeCountDis";//交易笔数分布

    public static final String tradeVolDis = "tradeVolDis";//交易量分布

    public static final String bigTradeCountDis = "bigTradeCountDis";//大额转账

    public static final String newAndInactivityy = "newAndInactivityy";//新增与唤醒地址

    public static final String btcHashRate = "btcHashRate";//BTC算力

    public static final String bchHashRate = "bchHashRate";//BCH算力

    public static final String ltcHashRate = "ltcHashRate";//LTC算力

    public static final String ethHashRate = "ethHashRate";//ETH算力

    public static final String btcPrice = "btcPrice";//BTC价格

    public static final String bchPrice = "bchPrice";//BCH价格

    public static final String ltcPrice = "ltcPrice";//BCH价格

    public static final String ethPrice = "ethPrice";//BCH价格

    public static final String diffPower = "diffPower";//挖矿难度、算力

    public static final String trade = "trade";//交易热度

    public static final String allGas = "allGas";//手续费总消耗

    public static final String avgGas = "avgGas";//平均每笔手续费

    public static final String marketValue ="marketValue";//流通市值情况

    public static int distanceDayMinute=1;

    public static int distanceDayHour=3;

    public static int distanceDayDay=30;

    public static int distanceDayWeek=183;

    public static int distanceDayMouth=365;

    public static String  info_totalmarket="TotalMarketValue";//每日全球总市值消息

    public static String info_price = "price";//每日头部币价格信息

    public static String info_marketing = "marketing";//运营手推消息

    public static String info_bigchange = "bigchange";//大额异动消息

    public static String info_pricechange = "pricechange";//大额异动消息

    public static String assest_in = "assest_in";//资产转入

    public static String assest_out = "assest_out";//资产转出



    public static String totalMessageSize="totalMessageSize";

    public static String currectLine="currectLine";


    public static String baseLine[] = {"eos","bitcoin", "ethereum", "bitcoin-cash-sv", "tether", "bitcoin-cash", "litecoin", "monero",
            "dash", "ethereum-classic", "neo", "zcash", "dogecoin", "digibyte", "bytom", "ravencoin", "reddcoin",
            "zcoin", "monacoin", "zencash", "nebulas", "groestlcoin", "peercoin", "vertcoin", "namecoin",
            "zclassic", "feathercoin"};

    public static String baseLine2[] = { "bitcoin-cash-sv", "tether", "bitcoin-cash", "litecoin", "monero",
            "dash", "ethereum-classic", "neo", "zcash", "dogecoin", "digibyte", "bytom", "ravencoin", "reddcoin",
            "zcoin", "monacoin", "zencash", "nebulas", "groestlcoin", "peercoin", "vertcoin", "namecoin",
            "zclassic", "feathercoin"};

}
