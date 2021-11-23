package configurationAndBootstrap.messageDigest;

import java.security.MessageDigest;

public class MessageDigestFactory { //вспомогательный класс для получения конкретной реализации для класса с фабричным методом
    private String algorithmName = "MD5";

    public MessageDigest createInstance() throws Exception {
       return MessageDigest.getInstance(algorithmName);
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }
}
