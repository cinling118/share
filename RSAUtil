import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;//这两条指令的执行需引入外部包，外部包分别为commons-codec和commons-io
import org.apache.commons.io.IOUtils;

/**
 * RSA加密与解密(JAVA)
 *
 */
public class RSAUtil {

	public static final String CHARSET = "UTF-8"; // 设置以UTF-8编码
	public static final String RSA_ALGORITHM = "RSA"; // 采用RSA加解密算法
	public static final String PUBLIC_KEY = "publicKey"; //公钥
	public static final String PRIVATE_KEY = "privateKey"; //私钥
	
	/*
	 * 公钥加密
	 */
	public static String publicEncrypt(String data, String publicKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		X509EncodedKeySpec x509Key = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
		RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(x509Key);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
//		return Base64.encodeBase64String(cipher.doFinal(data.getBytes(CHARSET)));
		return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),pubKey.getModulus().bitLength()));
	}

	/*
	 * 私钥解密
	 */
	public static String privateDecrypt(String data, String privateKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		PKCS8EncodedKeySpec pkcs8Key = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
		RSAPrivateKey priKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8Key);
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, priKey);
//		return new String(cipher.doFinal(Base64.decodeBase64(data)));
		return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),priKey.getModulus().bitLength()), CHARSET);
	}
	
	/*
	 * 私钥加密
	 */
	public static String privateEncrypt(String data, String privateKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		PKCS8EncodedKeySpec pkcs8Key = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
		RSAPrivateKey priKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8Key);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, priKey);
//		return Base64.encodeBase64String(cipher.doFinal(data.getBytes(CHARSET)));
		return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),priKey.getModulus().bitLength()));
	}

	/*
	 * 公钥解密
	 */
	public static String publicDecrypt(String data, String publicKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		X509EncodedKeySpec x509Key = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
		RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(x509Key);
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, pubKey);
//		return new String(cipher.doFinal(Base64.decodeBase64(data)));
		return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),pubKey.getModulus().bitLength()), CHARSET);
	}

	public static Map<String, String> createKeys(int keySize) {
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("No such algorithm: " + RSA_ALGORITHM + "!");

		}
		// 初始化KeyPairGenerator对象，密钥长度
		kpg.initialize(keySize);
		// 生成密钥对
		KeyPair keyPair = kpg.generateKeyPair();
		// 得到密钥对的公钥
		Key publicKey = keyPair.getPublic();
		//JS使用公钥加密时，不能用encodeBase64URLSafeString(原因：加密后的公钥带“_”，加密会返回false)
//		String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
		String publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
		// 得到密钥对的私钥
		Key privateKey = keyPair.getPrivate();
		String privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
		// 将公钥私钥加入到Map中，方便后面的取出
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put(PUBLIC_KEY, publicKeyStr);
		keyMap.put(PRIVATE_KEY, privateKeyStr);
		return keyMap;
	}

	/*
	 * RSA加密算法对于加密的长度是有要求的。一般来说，加密时，明文长度大于加密钥长度-11时，明文就要进行分段；解密时，密文大于解密钥长度时，
	 * 密文就要进行分段（以字节为单位）
	 */
	private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
		int maxBlock = 0;
		if (opmode == Cipher.DECRYPT_MODE) {
			maxBlock = keySize / 8;
		} else {
			maxBlock = keySize / 8 - 11;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] buff;
		int i = 0;
		try {
			while (datas.length > offSet) {
				if (datas.length - offSet > maxBlock) {
					buff = cipher.doFinal(datas, offSet, maxBlock);
				} else {
					buff = cipher.doFinal(datas, offSet, datas.length - offSet);
				}
				out.write(buff, 0, buff.length);
				i++;
				offSet = i * maxBlock;
			}
		} catch (Exception e) {
			throw new RuntimeException("加解密时异常");
		}
		byte[] resultDatas = out.toByteArray();
		IOUtils.closeQuietly(out);
		return resultDatas;
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> keyMap = RSAUtil.createKeys(1024);
		String publicKey = keyMap.get(PUBLIC_KEY);
		String privateKey = keyMap.get(PRIVATE_KEY);
		System.out.println("公钥: \n" + publicKey);
		System.out.println("私钥：\n" + privateKey);
		System.out.println("\n公钥加密——私钥解密");
		String str = "对称密码，非对称密码，数字签名，Hash函数";
		System.out.println("明文：\n" + str);
		String encodedData = RSAUtil.publicEncrypt(str, publicKey);
		System.out.println("密文：\n" + encodedData);
		String decodedData = RSAUtil.privateDecrypt(encodedData, privateKey);
		System.out.println("解密后文字: \n" + decodedData);
		System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~");

		Map<String, String> keyMap2 = RSAUtil.createKeys(1024);
		String publicKey2 = keyMap2.get(PUBLIC_KEY);
		String privateKey2 = keyMap2.get(PRIVATE_KEY);
		System.out.println("公钥2: \n" + publicKey2);
		System.out.println("私钥2：\n" + privateKey2);
		System.out.println("私钥加密——公钥解密");
		String str2 = "对称密码，非对称密码，数字签名，Hash函数~~~~~~";
		System.out.println("明文：\n" + str2);
		String encodedData2 = RSAUtil.privateEncrypt(str2, privateKey2);
		System.out.println("密文：\n" + encodedData2);
		String decodedData2 = RSAUtil.publicDecrypt(encodedData2, publicKey2);
		System.out.println("解密后文字: \n" + decodedData2);
		
		System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("\n公钥1加密——私钥2加密--公钥2解密--私钥1解密");
		String str3 = "公钥1加密——私钥2加密--公钥2解密--私钥1解密";
		System.out.println("明文：\n" + str3);
		String encodedData3_1 = RSAUtil.publicEncrypt(str3, publicKey);
		System.out.println("密文1：\n" + encodedData3_1);
		String encodedData3_2 = RSAUtil.privateEncrypt(encodedData3_1, privateKey2);
		System.out.println("密文2：\n" + encodedData3_2);
		String decodedData3_3 = RSAUtil.publicDecrypt(encodedData3_2, publicKey2);
		System.out.println("解密后文字1: \n" + decodedData3_3);
		String decodedData3_4 = RSAUtil.privateDecrypt(decodedData3_3, privateKey);
		System.out.println("解密后文字2: \n" + decodedData3_4);

	}

}
