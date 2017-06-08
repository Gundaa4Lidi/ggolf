package com.galaxy.ggolf.tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import com.galaxy.ggolf.jdbc.CommonConfig;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;
import com.pingplusplus.model.ChargeRefundCollection;
import com.pingplusplus.model.Refund;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingPPUtil {

	private final static Logger logger = LoggerFactory.getLogger(PingPPUtil.class);

	/**
	 * Pingpp 管理平台对应的 API Key，api_key 获取方式：登录 [Dashboard](https://dashboard.pingxx.com)->点击管理平台右上角公司名称->开发信息-> Secret Key
	 */
	private final static String apiKey = CommonConfig.PingPP_Apikey;

	/**
	 * Pingpp 管理平台对应的应用 ID，app_id 获取方式：登录 [Dashboard](https://dashboard.pingxx.com)->点击你创建的应用->应用首页->应用 ID(App ID)
	 */
	private final static String appId = CommonConfig.PingPP_AppID;




	/**
	 * 设置请求签名密钥，密钥对需要你自己用 openssl 工具生成，如何生成可以参考帮助中心：https://help.pingxx.com/article/123161；
	 * 生成密钥后，需要在代码中设置请求签名的私钥(rsa_private_key.pem)；
	 * 然后登录 [Dashboard](https://dashboard.pingxx.com)->点击右上角公司名称->开发信息->商户公钥（用于商户身份验证）
	 * 将你的公钥复制粘贴进去并且保存->先启用 Test 模式进行测试->测试通过后启用 Live 模式
	 */
	// 你生成的私钥路径
	private final static String privateKeyFilePath = "D:\\workspace\\GGolfz\\ggolf\\res\\my_rsa_private_key.pem";
	
	private final static String publicKeyFilePath = "D:\\workspace\\GGolfz\\ggolf\\res\\my_rsa_public_key.pem";
	
	private final static String pingPPpublicKeyFilePath = "D:\\workspace\\GGolfz\\ggolf\\res\\pingpp_rsa_public_key.pem";

	/**
	 * 创建 Charge
	 *
	 * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create();
	 * map 里面参数的具体说明请参考：https://www.pingxx.com/api#api-c-new
	 * @param orderID 				订单编号
	 * @param price					价钱
	 * @param channel 				支付方式
	 * @param clientIp 				客户端的ip地址
	 * @param subject 				订单标题
	 * @param body					订单详细描述
	 * @param initialMetadata		自定义键值对(非必填,最多10个键值对)
	 * @return
	 */
	public static Charge createCharge(String orderID,
									  String price,
									  String channel,
									  String clientIp,
									  String subject,
									  String body,
									  Map<String,Object> initialMetadata) {
		Charge charge = null;
		Pingpp.apiKey = apiKey;
		Pingpp.privateKeyPath = privateKeyFilePath;
		double orderPrice = Double.parseDouble(price) * 100;
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		chargeParams.put("order_no", orderID);
		chargeParams.put("amount", orderPrice);
		Map<String, String> app = new HashMap<String, String>();
		app.put("id", appId);
		chargeParams.put("app", app);
		chargeParams.put("channel", channel);
		chargeParams.put("currency", "cny");
		chargeParams.put("client_ip", clientIp);
		chargeParams.put("subject", subject);
		chargeParams.put("body", body);
		if(initialMetadata!=null){
			chargeParams.put("metadata", initialMetadata);
		}
		try {
			charge = Charge.create(chargeParams);
		} catch (PingppException e) {
			e.printStackTrace();
		}
		return charge;
	}


	/**
	 * 查询 Charge
	 *
	 * 该接口根据 charge Id 查询对应的 charge 。
	 * 参考文档：https://www.pingxx.com/api#api-c-inquiry
	 *
	 * 该接口可以传递一个 expand ， 返回的 charge 中的 app 会变成 app 对象。
	 * 参考文档： https://www.pingxx.com/api#api-expanding
	 * @param CHARGE_ID
	 */
	public static Charge getCharge(String CHARGE_ID,Map<String,Object> Params){
		Pingpp.apiKey = apiKey;
		Charge charge = null;
		Map<String,Object> params = new HashMap<String,Object>();
		if(Params!=null){
			params = Params;
		}
		try {
			charge = Charge.retrieve(CHARGE_ID,params);
		} catch (PingppException e) {
			e.printStackTrace();
		}
		return charge;
	}

	/**
	 * 分页查询 Charge
	 *
	 * 该接口为批量查询接口，默认一次查询10条。
	 * 用户可以通过添加 limit 参数自行设置查询数目，最多一次不能超过 100 条。
	 *
	 * 该接口同样可以使用 expand 参数。
	 * @param limit		(选填项)每页条数
	 * @param starting	(选填项)当前页的最后一个charge对象的id,下一页
	 * @param ending	(选填项)当前页的最后一个charge对象的id,上一页
	 * @param channel	(选填项)支付方式
	 * @param paid		(选填项)是否已支付
	 * @param refunded	(选填项)是否存在退款信息
	 * @return
	 */
	public static ChargeCollection ChargeList(String limit,
											  String starting,
											  String ending,
											  String channel,
											  String paid,
											  String refunded) {
		Pingpp.apiKey = apiKey;
		ChargeCollection chargeList = null;
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		Map<String, String> app = new HashMap<String, String>();
		app.put("id", appId);
		chargeParams.put("app", app);
		if(limit!=null&&!limit.equals("")){
			chargeParams.put("limit", Integer.parseInt(limit));
		}
		if(starting!=null&&!limit.equals("")&&(ending==null||limit.equals(""))){
			chargeParams.put("starting_after",starting);
		}
		if(ending!=null&&!limit.equals("")&&(starting==null||limit.equals(""))){
			chargeParams.put("ending_before",ending);
		}
		if(channel!=null){
			chargeParams.put("channel",channel);
		}
		if(paid!=null){
			boolean Paid = false;
			if(paid.equals("0")){
				Paid = false;
			}else if(paid.equals("1")){
				Paid = true;
			}
			chargeParams.put("paid",Paid);
		}
		if(refunded!=null){
			boolean Refunded = false;
			if(refunded.equals("0")){
				Refunded = false;
			}else if(refunded.equals("1")){
				Refunded = true;
			}
			chargeParams.put("refunded",Refunded);
		}
		try {
			chargeList = Charge.list(chargeParams);
		} catch (PingppException e) {
			e.printStackTrace();
			logger.error("Error ping++", e);
		}
		return chargeList;
	}

	/**
	 * 创建退款，需要先获得 charge ,然后调用 charge.getRefunds().create();
	 * 参数具体说明参考：https://www.pingxx.com/api#api-r-new
	 *
	 * 可以一次退款，也可以分批退款。
	 * @param ChargeID		(必填)Charge对象编号
	 * @param amount		(非必填)退款金额
	 * @param description	(必填)退款理由
	 * @param metadata		(非必填)元数据
	 * @return
	 */
	public static Refund refund(String ChargeID,
								Integer amount,
								String description,
								Map<String,Object> metadata){
		Pingpp.apiKey = apiKey;
		Refund refund = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("amount",amount);
		params.put("description",description);
		Map<String,Object> initMetadata = new HashMap<String,Object>();
		if(metadata!=null){
			initMetadata = metadata;
		}
		params.put("metadata",initMetadata);
		try {
			Refund.create(ChargeID,params);
		}catch (PingppException e){
			e.printStackTrace();
			logger.error("Error ping++",e);
		}
		return refund;
	}

	/**
	 * 查询退款
	 * 根据 Id 查询退款记录。需要传递 charge。
	 * 参考文档：https://www.pingxx.com/api#api-r-inquiry
	 *
	 * @param ChargeID	(必填)Charge对象编号
	 * @param RefundID	(必填)退款编号
	 */
	public static Refund getRefund(String ChargeID, String RefundID) {
		Pingpp.apiKey = apiKey;
		Refund refund = null;
		try {
			Charge ch = Charge.retrieve(ChargeID);
			refund = ch.getRefunds().retrieve(RefundID);
		} catch (PingppException e) {
			logger.error("Error ping++", e);
			e.printStackTrace();
		}
		return refund;
	}

	/**
	 * 分页查询退款
	 *
	 * 批量查询退款。默认一次 10 条，用户可以通过 limit 自定义查询数目，但是最多不超过 100 条。
	 * 参考文档：https://www.pingxx.com/api#api-r-list
	 * @param ChargeID		(必填)Charge对象编号
	 * @param limit			(选填项)每页条数
	 * @param starting		(选填项)当前页的最后一个charge对象的id,下一页
	 * @param ending		(选填项)当前页的最后一个charge对象的id,上一页
	 * @return
	 */
	public static ChargeRefundCollection RefundList(String ChargeID,String limit,String starting,String ending){
		Pingpp.apiKey = apiKey;
		ChargeRefundCollection refundList = null;
		Map<String,Object> params = new HashMap<String,Object>();
		try {
			params.put("limit",Integer.parseInt(limit));
			if(starting!=null&&ending==null){
				params.put("starting_after",starting);
			}else if(starting==null&&ending!=null){
				params.put("ending_before",ending);
			}
			refundList = Refund.list(ChargeID,params);

		}catch (PingppException e){
			logger.error("Error ping++");
			e.printStackTrace();
		}
		return refundList;
	}
	
	/**
	 * 验证 webhooks 签名
	 * @param eventPath
	 * @param signPath
	 * @return
	 */
	public static boolean verifyWebhooks(String eventData,String Signature){
		boolean result = false;
		try {
			result = verifyData(eventData, Signature, getPubKey());
		} catch (Exception e) {
			logger.error("Error ping++");
			e.printStackTrace();
		}
		return result;
	}

	/**
     * 读取文件, 部署 web 程序的时候, 签名和验签内容需要从 request 中获得
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getStringFromFile(String filePath) throws Exception {
        FileInputStream in = new FileInputStream(filePath);
        InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
        BufferedReader bf = new BufferedReader(inReader);
        StringBuilder sb = new StringBuilder();
        String line;
        do {
            line = bf.readLine();
            if (line != null) {
                if (sb.length() != 0) {
                    sb.append("\n");
                }
                sb.append(line);
            }
        } while (line != null);

        return sb.toString();
    }
	
	/**
     * 获得公钥
     * @return
     * @throws Exception
     */
    public static PublicKey getPubKey() throws Exception {
    	String pubKeyString = getStringFromFile(pingPPpublicKeyFilePath);
//        String pubKeyString = getStringFromFile(publicKeyFilePath);
        pubKeyString = pubKeyString.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
        byte[] keyBytes = Base64.decodeBase64(pubKeyString);

        // generate public key
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);
        return publicKey;
    }
	
	/**
     * 验证签名
     * @param dataString
     * @param signatureString
     * @param publicKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verifyData(String dataString, String signatureString, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        byte[] signatureBytes = Base64.decodeBase64(signatureString);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(dataString.getBytes("UTF-8"));
        return signature.verify(signatureBytes);
    }

}
