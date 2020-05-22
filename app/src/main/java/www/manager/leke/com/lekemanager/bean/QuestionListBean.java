package www.manager.leke.com.lekemanager.bean;

import java.util.List;

/**
 * Created by ypu
 * on 2020/5/21 0021
 * 问题列表
 */
public class QuestionListBean {


    /**
     * qId : 189
     * qTypeCode : IC03
     * qStatusCode : IB05
     * qCreateTime : 2018-07-31 10:53:22
     * qStemLink : //global-questions.oss-cn-shanghai.aliyuncs.com/2018/203020001/68d30cea-018e-4ab1-a5ad-c183ee2ec4e4/w2.pdf
     * qStemColorLink : //global-questions.oss-cn-shanghai.aliyuncs.com/2018/204010001/dc6cb543-9c21-4aa0-8482-6fdcb809c377/1-2-2.pdf
     * qStemAudioLink : //global-questions.oss-cn-shanghai.aliyuncs.com/2018/204010001/dc6cb543-9c21-4aa0-8482-6fdcb8777/1-2-2.mp3
     * subAMode : 1
     * subQInfos : [{"subQId":1,"subQIdDisplay":"一","subAOptsInfos":["A","B","C","D"],"subAInfos":["A"]}]
     * analysisLink : //global-questions.oss-cn-shanghai.aliyuncs.com/2018/203020001/68d30cea-018e-4ab1-a5ad-c183ee2ec4e4/w2a.pdf
     * qKeywords : ["圆","第一章"]
     */

    private int qId;
    private String qTypeCode;
    private String qStatusCode;
    private String qCreateTime;
    private String qStemLink;
    private String qStemColorLink;
    private String qStemAudioLink;
    private int subAMode;
    private String analysisLink;
    private List<SubQInfosBean> subQInfos;
    private List<String> qKeywords;

    public int getQId() {
        return qId;
    }

    public void setQId(int qId) {
        this.qId = qId;
    }

    public String getQTypeCode() {
        return qTypeCode;
    }

    public void setQTypeCode(String qTypeCode) {
        this.qTypeCode = qTypeCode;
    }

    public String getQStatusCode() {
        return qStatusCode;
    }

    public void setQStatusCode(String qStatusCode) {
        this.qStatusCode = qStatusCode;
    }

    public String getQCreateTime() {
        return qCreateTime;
    }

    public void setQCreateTime(String qCreateTime) {
        this.qCreateTime = qCreateTime;
    }

    public String getQStemLink() {
        return qStemLink;
    }

    public void setQStemLink(String qStemLink) {
        this.qStemLink = qStemLink;
    }

    public String getQStemColorLink() {
        return qStemColorLink;
    }

    public void setQStemColorLink(String qStemColorLink) {
        this.qStemColorLink = qStemColorLink;
    }

    public String getQStemAudioLink() {
        return qStemAudioLink;
    }

    public void setQStemAudioLink(String qStemAudioLink) {
        this.qStemAudioLink = qStemAudioLink;
    }

    public int getSubAMode() {
        return subAMode;
    }

    public void setSubAMode(int subAMode) {
        this.subAMode = subAMode;
    }

    public String getAnalysisLink() {
        return analysisLink;
    }

    public void setAnalysisLink(String analysisLink) {
        this.analysisLink = analysisLink;
    }

    public List<SubQInfosBean> getSubQInfos() {
        return subQInfos;
    }

    public void setSubQInfos(List<SubQInfosBean> subQInfos) {
        this.subQInfos = subQInfos;
    }

    public List<String> getQKeywords() {
        return qKeywords;
    }

    public void setQKeywords(List<String> qKeywords) {
        this.qKeywords = qKeywords;
    }

    public static class SubQInfosBean {
        /**
         * subQId : 1
         * subQIdDisplay : 一
         * subAOptsInfos : ["A","B","C","D"]
         * subAInfos : ["A"]
         */

        private int subQId;
        private String subQIdDisplay;
        private List<String> subAOptsInfos;
        private List<String> subAInfos;

        public int getSubQId() {
            return subQId;
        }

        public void setSubQId(int subQId) {
            this.subQId = subQId;
        }

        public String getSubQIdDisplay() {
            return subQIdDisplay;
        }

        public void setSubQIdDisplay(String subQIdDisplay) {
            this.subQIdDisplay = subQIdDisplay;
        }

        public List<String> getSubAOptsInfos() {
            return subAOptsInfos;
        }

        public void setSubAOptsInfos(List<String> subAOptsInfos) {
            this.subAOptsInfos = subAOptsInfos;
        }

        public List<String> getSubAInfos() {
            return subAInfos;
        }

        public void setSubAInfos(List<String> subAInfos) {
            this.subAInfos = subAInfos;
        }
    }
}
