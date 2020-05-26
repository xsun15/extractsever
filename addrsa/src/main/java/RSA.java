import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSA {
	private Map<Integer, String> keyMap = new HashMap<>(); //用于封装随机产生的公钥与私钥

	public RSA() {
		keyMap.put(0, "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAky9mzuNuYkchjmUlAtG0lnSsOvkaVbEdMQacQaSsv4Lv84ehXrOUv6rrBNiY1h6PibisFzHlqI47JXExqA71jXPdQHtZAyHFbOuXQ2U023G8hhXo74iNDZzXj/sfKHeK6XihaGmPWMUD8dQnTUeA/KI+dJWziEgyR1TkrDM0z8/06CIqXfF/Z0WBZImtVg280F13tPTbjOcLUuWFlH5QWqRYlW/mVJtKeXLy4D5i/WO3JsZi+w3dga5Z6eqBb2l1F4wPlkT1YVScUoyq9bGmgsXyKpca4kyGVsPQnP1QMZutQpbd99RUttN1JPqn0X+0HmZ/n8H6ZC1SIKdKKVMjb382MXNmQ8wyXXMnaQGCruu7xcJlK9sVcKeqbUNe4rMH7PAIqLt3GteEc+J5BrQrec8NgbaIQ6w8VsnLigoe5QvefZw71CIHGdXH09vlhg2j4d/JEe6cMsLIwpgqV5HTvaKkm7ANjznXs20qQZt66ZkeFpu5VUkPuguVtUkopNFMHnazX0nFRSI8L4oAf9dJjBpezbDVktuaYhu1If9l7bVL8VlCA+qhJzd3LEjj1RN9jE0dkKhToqsgbBQFgrVdPKEKsVoeMDMfVBWV5HQIicPkykPpfgPcFn9bKkySgzTlRff3Mt7GydPS9Rhc7fp9spVzM5/tsipEW3sPCKC9YbMCAwEAAQ==");
		keyMap.put(1, "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");
	}

	public static void main(String[] args) throws Exception {
		RSA rsa = new RSA();
		//加密字符串
		String message = "df723820";
		System.out.println("随机生成的公钥为:" + rsa.keyMap.get(0));
		System.out.println("随机生成的私钥为:" + rsa.keyMap.get(1));
		String messageEn = encrypt(message,rsa.keyMap.get(0));
		System.out.println(message + "\t加密后的字符串为:" + messageEn);
		String messageDe = decrypt(messageEn,rsa.keyMap.get(1));
		System.out.println("还原后的字符串为:" + messageDe);
		String inputPath = "/app/develop/extractsever/addrsa/src/main/resources/raw";;		//要遍历的路径
		File file = new File(inputPath);		//获取其file对象
		func(file);
	}

	/**
	 * 随机生成密钥对
	 * @throws NoSuchAlgorithmException
	 */
	public static void genKeyPair() throws NoSuchAlgorithmException {
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		// 初始化密钥对生成器，密钥大小为96-1024位
		keyPairGen.initialize(4096,new SecureRandom());
		// 生成一个密钥对，保存在keyPair中
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
		String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
		// 得到私钥字符串
		String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
	}
	/**
	 * RSA公钥加密
	 *
	 * @param str
	 *            加密字符串
	 * @param publicKey
	 *            公钥
	 * @return 密文
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public static String encrypt( String str, String publicKey ) throws Exception{
		//base64编码的公钥
		byte[] decoded = Base64.decodeBase64(publicKey);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		//RSA加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		System.out.println(str);
		String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
		return outStr;
	}

	/**
	 * RSA私钥解密
	 *
	 * @param str
	 *            加密字符串
	 * @param privateKey
	 *            私钥
	 * @return 铭文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public static String decrypt(String str, String privateKey) throws Exception{
		//64位解码加密后的字符串
		byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
		//base64编码的私钥
		byte[] decoded = Base64.decodeBase64(privateKey);
		RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
		//RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String outStr = new String(cipher.doFinal(inputByte));
		return outStr;
	}

	// 扫描需要加密的目录, 将规则加密
	public void addRSA(String dir) {

	}

	private static void func(File file) {
		File[] fs = file.listFiles();
		for (File f : fs) {
			if (f.isDirectory())    //若是目录，则递归打印该目录下的文件
				func(f);
			if (f.isFile()) {     //若是文件，直接打印
				parseFile(f);
			}
		}
	}

	private static void parseFile(File file) {
		String saveName = file.getAbsolutePath().replaceAll("raw", "encrypt");
		System.out.println(saveName);
		RSA rsa = new RSA();
		BufferedReader reader = null;
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
			reader = new BufferedReader(isr);
			String line;
			String result = "";
			while ((line = reader.readLine()) != null) {
				String lineNew = line.replaceAll("\\s+", "");
				if (line.length()>0 && lineNew.substring(0,1).equals("-")) {
					lineNew = lineNew.substring(1).replaceAll("\"", "");
					String messageEn = encrypt(lineNew, rsa.keyMap.get(0));
					result += "      - \"" + messageEn + "\"\n";
				} else  {
					result += line + "\n";
				}
			}
			saveAsFileWriter(saveName, result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private static void saveAsFileWriter(String savepath, String content) {
		FileWriter fwriter = null;
		try {
			// true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
			fwriter = new FileWriter(savepath);
			fwriter.write(content);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fwriter.flush();
				fwriter.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}