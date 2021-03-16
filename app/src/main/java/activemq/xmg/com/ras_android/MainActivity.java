package activemq.xmg.com.ras_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;
import java.security.PublicKey;

import activemq.xmg.com.ras_android.encrypt.Base64Utils;
import activemq.xmg.com.ras_android.encrypt.RSAUtils;
import activemq.xmg.com.ras_android.sign.RSA;
import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    /**
     * 文本
     */
    @Bind(R.id.ed_text)
    EditText edText;
    /**
     * 加密
     */
    @Bind(R.id.rsa_btn_jiami)
    Button rsaBtnJiami;
    @Bind(R.id.tv_text_ras_jiami)
    TextView tvTextRasJiami;
    @Bind(R.id.rsa_btn_jiemi)
    Button rsaBtnJiemi;
    @Bind(R.id.tv_text_ras_jiemi)
    TextView tvTextRasJiemi;
    /**
     * 解密
     */
    @Bind(R.id.base64_btn_jiami)
    Button base64BtnJiami;
    @Bind(R.id.tv_text_base64_jiami)
    TextView tvTextBase64Jiami;
    @Bind(R.id.base64_btn_jiemi)
    Button base64BtnJiemi;
    @Bind(R.id.tv_text_base64_jiemi)
    TextView tvTextBase64Jiemi;

    /**
     * 签名 与 验证
     */
    @Bind(R.id.rsa_sign_btn_jiami)
    Button rsaSignBtnJiami;
    @Bind(R.id.tv_text_ras_sign_jiami)
    TextView tvTextRasSignJiami;
    @Bind(R.id.rsa_sign_btn_jiemi)
    Button rsaSignBtnJiemi;
    @Bind(R.id.tv_text_ras_sign_jiemi)
    TextView tvTextRasSignJiemi;

    private static String content = null;
    /*第二组 */

    /*第三组*/
    private static String PUCLIC_KEY =
//            "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqSszNNW6+0btURKZBX8C\n" +
                    "1xQKl11OYyit6WBOzrXj/S3fgK7du4LrtKiOwNzyh7lc+pYDGOSUfXcPIMzrZ/S1\n" +
                    "TdDkdYo8jKvwVUHHH5H+vU/i7nCQCCMvfHnWIk1i9D07RVdi6vBvZTH4T0/jhS7L\n" +
                    "hPR32iqDX0VsoavsWOTQ+GcdHaxleMcyV43moTx1eXeiKT+wJTBRT+okZEZPeL8l\n" +
                    "xyfnubRCDvAHph/uoCepE8bu9IMtwyJN7lXRPTZnpuJkUVNNSFOIzN9TgfzSDnoz\n" +
                    "ipE6rC0ywNlVHmoAfP3FLBDR/YRNuTtlvGBj26bL+9U91EDeqO4xiAhv4yIBRO9F\n" +
                    "NQIDAQAB\n"
//                    + "-----END PUBLIC KEY-----"
            ;
    private static String PRIVATE_KEY =
//            "-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCpKzM01br7Ru1R\n" +
                    "EpkFfwLXFAqXXU5jKK3pYE7OteP9Ld+Art27guu0qI7A3PKHuVz6lgMY5JR9dw8g\n" +
                    "zOtn9LVN0OR1ijyMq/BVQccfkf69T+LucJAIIy98edYiTWL0PTtFV2Lq8G9lMfhP\n" +
                    "T+OFLsuE9HfaKoNfRWyhq+xY5ND4Zx0drGV4xzJXjeahPHV5d6IpP7AlMFFP6iRk\n" +
                    "Rk94vyXHJ+e5tEIO8AemH+6gJ6kTxu70gy3DIk3uVdE9Nmem4mRRU01IU4jM31OB\n" +
                    "/NIOejOKkTqsLTLA2VUeagB8/cUsENH9hE25O2W8YGPbpsv71T3UQN6o7jGICG/j\n" +
                    "IgFE70U1AgMBAAECggEAG9Qkd1OVj9KKJaUWkSXktLbySxYpV/yQJ20VacHYQIT0\n" +
                    "WHnrtfYCilSULzvpldQzbHGTDtxDZSRZ076CFzbVQHjVrFRSQ53hSlUoyZO9wSzM\n" +
                    "GwdfWkdXf3WXeUsbtSwBfRW0CwwEdzQyMfHlbrpU9ok00ineNKT6Ctp6ZeE2UzZ2\n" +
                    "simA4fkOki0supztEZlcFpM6/6yJvP2OQFuggccSoMQ2Y2JTVLCCQe3mHQMQxOh0\n" +
                    "Dj95aSj4vh9A0qXKPsyvu3eImTZHFZa1JWsk6BVoMCK6kzPyVBX0zYE2rKM7xE7X\n" +
                    "8sFOAc7rDBWD6t6WA7NrI3QrQiDYKYYp6gA6ZycNYQKBgQDadYUDCTMa1vVjAWAY\n" +
                    "pKaRWv9YDzX3esAdj+qGJkzQ8qWSZ0lxlPsxPcJsI2rAR2f4EnIDEQAyVSUQhhNF\n" +
                    "VvGSgCY3HnIAEJCE0DVO9NvtOL7XDJSc3tPv9oiITLXepA+bBZDg0r4wD3Dg1QF1\n" +
                    "s2C1iAHU5bH5ZclvKdht2UIArQKBgQDGPUthsD0ljdoNze9p/2HTVIZCydZRuRD4\n" +
                    "Ljzxui1FFzbFgVJEr7hPwqplhReTJiW3A8v7BgppgU12XuJuHhm+giUVMjHhvJ8Y\n" +
                    "31D3iuByZu78Tve+pzJfKTXJEbj9mVkjXURO4iU8cWRvPKGQc+Aabjf+GLdteypJ\n" +
                    "T07WeAN/qQKBgQC0alHLTBG0nMpTVZNzRqd5a9ltdEJxfCKlR3G8EG1oMryiyEJT\n" +
                    "V2KMLCEhBa7n1RD8Qt99wNCUwNS3bMMFhORExSjKZI5pl0SDKyCbcIUgyLhzCaTC\n" +
                    "WRRXHRds2U1p7bwlntcjlWgUcOwcAk4OUnIP+Z7poDj3aySMddaUAdUMkQKBgQC7\n" +
                    "YuMnk00pe338AIbn3kSdW8+RwHJ7S4GT5zkqkf8KyRe3+DdIwAVsN6mUTwtqx+ts\n" +
                    "pQUi4qHi+xu64ZQN3RwerazZ7VmjdQW6oBVL+RWSbmRHUWnz6N2/gBfcWYlRBqxw\n" +
                    "tk/EdRYXnWP/lgNupWnX8gmxgbGZpovhQjtWlZlRCQKBgQDZaXluQjhIiSezTnBk\n" +
                    "zsZsMV19m9emavrUYI5IxNVRJfgSwF4mTrij1S0ymMuou3sxTIfzMRFKuzK5sp3w\n" +
                    "bLaRRklR7rtKbhxd4bcDK4XaxaryDAHJi5GsfmcMsCnxpIN3zUIN67VbM7LwvfqF\n" +
                    "9VVTzqQ3lnWEu5LR4pOVDKkfkw==\n"
//                  +  "-----END PRIVATE KEY-----"
            ;
    String jiamiS = "BAK/wm0vqmMssxzroaBoyhiBym/bi0jy2oiW4CBksyPbRIHVXdo+b0nLDDneVBTewmzvAaCdGFhMOeaDa30E0nMa7Uzlky+i65wDpzjCoUqcx3gLnlIxSExW4TtpZhB/ggdbHtjCgfQYQOw4JJQisRZQASjfxvJkBJYDi2IDzB5AhruX/xPoMBu9NqQclQ+CSHjlEVtvmCegf2Zov9hJKHwIixoDz+QrNDOFxu1YWDlhLoQvj+Jkq+F10q47HVBAq+hBxJ2vfuG3MdMXDmPkmverPVqmTgy3yH0e5HU+OndYIvgabABW9X3AtneAwOrNK94E44ZY3B0dOI7TuRniOw==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setListener();
    }

    /**
     * 或者edText输入的内容
     */
    private void getContent() {
        content = edText.getText().toString().trim();
    }


    private void setListener() {
        //==========================================================================================
        /**
         * 签名
         */
        rsaSignBtnJiami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                String sign = RSA.sign(content, PRIVATE_KEY, "utf-8");
                tvTextRasSignJiami.setText(sign);
            }
        });

        /**
         * 验证
         */
        rsaSignBtnJiemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                String sign = tvTextRasSignJiami.getText().toString().trim();
//                content = "143SDFFSFSDFS423";
                boolean verify = RSA.verify(content, sign, PUCLIC_KEY, "utf-8");
                tvTextRasSignJiemi.setText(verify + "");
            }
        });

        //==========================================================================================

        /**
         * RAS加密
         */
        rsaBtnJiami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                Toast.makeText(MainActivity.this, "RSA加密", Toast.LENGTH_SHORT).show();
                /**加密内容*/
                try {
//                    PublicKey publicKey = RSAUtils.loadPublicKey(PUCLIC_KEY);
//                    /**公钥加密*/
//                    byte[] encryptByte = RSAUtils.encryptData(content.getBytes(), publicKey);
//                    String afterencrypt = Base64Utils.encode(encryptByte);


//                    String afterencrypt = RSAUtils.encrypt(PUCLIC_KEY, content);
                    String afterencrypt = RSAUtils.encrypt(PRIVATE_KEY, content);
                    if (jiamiS.equals(afterencrypt)) {
                        Toast.makeText(MainActivity.this, "匹配。。", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("TAG", "afterencrypt=" + afterencrypt);
                    tvTextRasJiami.setText(afterencrypt);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        /**
         * RAS解密
         */
        rsaBtnJiemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                Toast.makeText(MainActivity.this, "RSA解密", Toast.LENGTH_SHORT).show();
                String jiami = tvTextRasJiami.getText().toString().trim();
                Log.d("TAG", "jiami=" + jiami);
                /**服务端使用私钥解密*/
                try {
//                    PrivateKey privateKey = RSAUtils.loadPrivateKey(PRIVATE_KEY);
//                    /**私钥解密*/
//                    byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(jiami), privateKey);
//                    String decryptStr = new String(decryptByte);
//                    String decryptStr = RSAUtils.decrypt(PRIVATE_KEY, jiami);
                    String decryptStr = RSAUtils.decrypt(PUCLIC_KEY, jiami);
//                    Log.d("TAG", "decryptStr=" + decryptStr);
                    tvTextRasJiemi.setText(decryptStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //==========================================================================================
        /**
         * Base64加密
         */
        base64BtnJiami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                Toast.makeText(MainActivity.this, "Base64加密", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "content=" + content);
                String encode = Base64Utils.encode(content.getBytes());
                Log.d("TAG", "encode=" + encode);
                tvTextBase64Jiami.setText(encode);
            }
        });

        /**
         * Base64解密
         */
        base64BtnJiemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContent();
                Toast.makeText(MainActivity.this, "Base64解密", Toast.LENGTH_SHORT).show();
                String str = tvTextBase64Jiami.getText().toString().trim();
                Log.d("TAG", "str=" + str);
                byte[] decode = Base64Utils.decode(str);
                String decryptStr = new String(decode);
                Log.d("TAG", "decryptStr=" + decryptStr);
                tvTextBase64Jiemi.setText(decryptStr);
            }
        });

    }


}
