package www.manager.leke.com.lekemanager.bean;


import www.manager.leke.com.lekemanager.configuration.HtppConfiguration;

/**
 * 功能：阿里云OSS
 * 作者: YUAN_YE
 * 日期: 2019/7/1
 * 时间: 9:07
 */
public class AliOssInfo {
    //bean.getAccessKeyId()
    private String accessKeyId;
    //bean.getAccessKeySecret()
    private String accessKeySecret;
    //bean.getSecurityToken()
    private String securityToken;
    //bean.getExpiration()
    private String expiration;
    private String endpoint = HtppConfiguration.ENDPOINT;
    //ean.getAtchBucket()
    private String bucketName;
    //bean.getAtchRemotePath()
    private String objectKey;

    /*
        扩展
     */

    private String savePath;
    private String fileType;

    public String getEndpoint() {
        return endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }



    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "AliOssInfo{" +
                "savePath='" + savePath + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
