package www.manager.leke.com.lekemanager.bean;

/**
 * 功能：根据图书id 获取的oss信息
 * 作者: YUAN_YE
 * 日期: 2019/7/15
 * 时间: 10:07
 */
public class BookOssBean {

    private DataPackUrlInfo dataPackUrlInfo;
    private AudioDataUrlInfo audioDataUrlInfo;
    private AudioConfigUrlInfo audioConfigUrlInfo;

    public class BaseOssInfo {
        protected String deviceModelCode;
        protected String expiration;
        protected String accessKeyId;
        protected String accessKeySecret;
        protected String securityToken;
        protected String atchRemotePath;
        protected String atchBucket;
        protected String atchMd5;
        private  String region;

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getDeviceModelCode() {
            return deviceModelCode;
        }

        public void setDeviceModelCode(String deviceModelCode) {
            this.deviceModelCode = deviceModelCode;
        }

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
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

        public String getAtchRemotePath() {

            return atchRemotePath;
        }

        public void setAtchRemotePath(String atchRemotePath) {
            this.atchRemotePath = atchRemotePath;
        }

        public String getAtchBucket() {
            return atchBucket;
        }

        public void setAtchBucket(String atchBucket) {
            this.atchBucket = atchBucket;
        }

        public String getAtchMd5() {
            return atchMd5;
        }

        public void setAtchMd5(String atchMd5) {
            this.atchMd5 = atchMd5;
        }

        @Override
        public String toString() {
            return "BaseOssInfo{" +
                    "deviceModelCode='" + deviceModelCode + '\'' +
                    ", expiration='" + expiration + '\'' +
                    ", accessKeyId='" + accessKeyId + '\'' +
                    ", accessKeySecret='" + accessKeySecret + '\'' +
                    ", securityToken='" + securityToken + '\'' +
                    ", atchRemotePath='" + atchRemotePath + '\'' +
                    ", atchBucket='" + atchBucket + '\'' +
                    ", atchMd5='" + atchMd5 + '\'' +
                    '}';
        }
    }

    public class DataPackUrlInfo extends BaseOssInfo {
        //图书的密码
        private String atchEncryptkey;

        public String getAtchEncryptkey() {
            return atchEncryptkey;
        }

        public void setAtchEncryptkey(String atchEncryptkey) {
            this.atchEncryptkey = atchEncryptkey;
        }

        @Override
        public String toString() {
            return "DataPackUrlInfo{" +
                    "deviceModelCode='" + deviceModelCode + '\'' +
                    ", expiration='" + expiration + '\'' +
                    ", accessKeyId='" + accessKeyId + '\'' +
                    ", accessKeySecret='" + accessKeySecret + '\'' +
                    ", securityToken='" + securityToken + '\'' +
                    ", atchRemotePath='" + atchRemotePath + '\'' +
                    ", atchBucket='" + atchBucket + '\'' +
                    ", atchMd5='" + atchMd5 + '\'' +
                    ", atchEncryptKey='" + atchEncryptkey + '\'' +
                    '}';
        }
    }

    public class AudioDataUrlInfo extends BaseOssInfo {

    }

    public class AudioConfigUrlInfo extends BaseOssInfo {

    }

    public DataPackUrlInfo getDataPackUrlInfo() {
        return dataPackUrlInfo;
    }

    public AudioDataUrlInfo getAudioDataUrlInfo() {
        return audioDataUrlInfo;
    }

    public AudioConfigUrlInfo getAudioConfigUrlInfo() {
        return audioConfigUrlInfo;
    }
}
