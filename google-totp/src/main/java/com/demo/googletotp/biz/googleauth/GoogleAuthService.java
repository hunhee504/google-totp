package com.demo.googletotp.biz.googleauth;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.security.SecureRandom;

@Service
public class GoogleAuthService {


    // 최초 개인키 생성
    public String getGoogleSecretKey() {
        SecureRandom rndm = new SecureRandom();

        byte[] bytes = new byte[20];
        rndm.nextBytes(bytes);

        Base32 base32 = new Base32();

        return base32.encodeToString(bytes);
    }

    // OTP검증 요청시 개인키로 OTP 생성
    public String getTOTPCode(String secretKey) {
        Base32 base = new Base32();
        byte[] bytes = base.decode(secretKey);

        return TOTP.getOTP(Hex.encodeHexString(bytes));
    }

    // 구글 OTP 인증용 링크 생성
    public String getGoogleAuthBarcode(String secretKey, String issuer, String userName) throws Exception {
        return    "otpauth://totp/"
                + URLEncoder.encode(issuer + ":" + userName, "UTF-8").replace("+", "%20")
                + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
    }

    // QR코드 이미지 생성
    public byte[] getQRCodeImg(String url, int width, int height) throws Exception {
        BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "png", bos);

        return bos.toByteArray();
    }
}
