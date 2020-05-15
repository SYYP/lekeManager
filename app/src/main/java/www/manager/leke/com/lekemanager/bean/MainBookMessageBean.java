package www.manager.leke.com.lekemanager.bean;

/**
 * Created by ypu
 * on 2020/5/14 0014
 * 图书列表bean/点读列表bean/题库列表
 */
public class MainBookMessageBean {
    /**
     * bookId : 207020005
     * bookTitle : 七年级数学辅导书
     * bookDisplayTitle : 测试_七年级人教版数学下册辅导书
     * publishVerId : 101
     * publishVerName : 教材版本1
     * bookVolCode : BC02
     * bookVolName : 下册
     * fitGradeU : SE03
     * fitGradeL : SE01
     */
    private int bookId;
    private String bookTitle;
    private String bookDisplayTitle;
    private String fitGradeU;
    private String fitGradeL;
    private String bookSubjectCode;
    private String bookSubjectName;
    private int publishVerId;
    private String publishVerName;

    /**
     *
     * bookTypeCode : BB01
     * bookTypeName : 课本
     * bookSubjectCode : SH01
     * bookSubjectName : 语文
     * extBookTypeCode : BD01
     * extBookTypeName : 文学
     * bookStatus : BA11
     * bookPublisherId : 1
     * bookPublisherName : 出版社1
     * bookEditionTime : 2018-02-01
     * bookEditionNum : 2
     * bookReprintTime : 2018-02-01
     * bookReprintNum : 1
     * bookCreateTime : 2018-07-31 10:53:22
     * bookModifierName : user2
     * bookModifyTime : 2018-07-31 10:58:13
     * bookInspectorName : user3
     * bookInspTime : 2018-08-01 10:56:56
     * bookApproverName : user4
     * bookApprTime : 2018-08-31 10:56:56
     * bookOnShelfTime : 2018-11-01 10:56:56
     * bookOffShelfTime : 2018-11-02 10:56:56
     * bookArchiveTime : 2018-11-03 10:56:56
     * coverBAtchRemotePath : //bj-global-task.oss-cn-xxx/xxx/DAAN_2019-7-31_14-43-17.jpg
     * coverLAtchRemotePath : //bj-global-task.oss-cn-xxx/xxx/DAAN_2019-7-31_14-43-18.jpg
     */
    private String bookTypeCode;
    private String bookTypeName;
    private String extBookTypeCode;
    private String extBookTypeName;
    private String bookStatus;
    private String bookVolCode;
    private String bookVolName;
    private int bookPublisherId;
    private String bookPublisherName;
    private String bookEditionTime;
    private int bookEditionNum;
    private String bookReprintTime;
    private int bookReprintNum;
    private String bookCreateTime;
    private String bookModifierName;
    private String bookModifyTime;
    private String bookInspectorName;
    private String bookInspTime;
    private String bookApproverName;
    private String bookApprTime;
    private String bookOnShelfTime;
    private String bookOffShelfTime;
    private String bookArchiveTime;
    private String coverBAtchRemotePath;
    private String coverLAtchRemotePath;
    /**
     * audioStatus : BT01
     * audioCreateTime : 2018-07-31 10:53:22
     * audioModifierName : user2
     * audioModifyTime : 2018-07-31 10:58:13
     * audioInspectorName : user3
     * audioInspTime : 2018-08-01 10:56:56
     * audioApproverName : user4
     * audioApprTime : 2018-08-31 10:56:56
     * audioOnShelfTime : 2018-11-01 10:56:56
     * audioOffShelfTime : 2018-11-02 10:56:56
     * audioArchiveTime : 2018-11-03 10:56:56
     * audioDataAtchRemotePath : //bj-global-XXX.oss-cn-xxx/xxx/DAAN_2019-7-31_14-43-17.zip
     * audioConfigAtchRemotePath : //bj-global-XXX.oss-cn-xxx/xxx/DAAN_2019-7-31_14-43-18.zip
     */

    private String audioStatus;
    private String audioCreateTime;
    private String audioModifierName;
    private String audioModifyTime;
    private String audioInspectorName;
    private String audioInspTime;
    private String audioApproverName;
    private String audioApprTime;
    private String audioOnShelfTime;
    private String audioOffShelfTime;
    private String audioArchiveTime;
    private String audioDataAtchRemotePath;
    private String audioConfigAtchRemotePath;
    /**
     * hasInsp : true
     * hasAppr : false
     * isModifier : true
     * isInspector : false
     * isApprover : false
     */

    private boolean hasInsp;
    private boolean hasAppr;
    private boolean isModifier;
    private boolean isInspector;
    private boolean isApprover;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookDisplayTitle() {
        return bookDisplayTitle;
    }

    public void setBookDisplayTitle(String bookDisplayTitle) {
        this.bookDisplayTitle = bookDisplayTitle;
    }

    public String getBookTypeCode() {
        return bookTypeCode;
    }

    public void setBookTypeCode(String bookTypeCode) {
        this.bookTypeCode = bookTypeCode;
    }

    public String getBookTypeName() {
        return bookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public String getFitGradeU() {
        return fitGradeU;
    }

    public void setFitGradeU(String fitGradeU) {
        this.fitGradeU = fitGradeU;
    }

    public String getFitGradeL() {
        return fitGradeL;
    }

    public void setFitGradeL(String fitGradeL) {
        this.fitGradeL = fitGradeL;
    }

    public String getBookSubjectCode() {
        return bookSubjectCode;
    }

    public void setBookSubjectCode(String bookSubjectCode) {
        this.bookSubjectCode = bookSubjectCode;
    }

    public String getBookSubjectName() {
        return bookSubjectName;
    }

    public void setBookSubjectName(String bookSubjectName) {
        this.bookSubjectName = bookSubjectName;
    }

    public int getPublishVerId() {
        return publishVerId;
    }

    public void setPublishVerId(int publishVerId) {
        this.publishVerId = publishVerId;
    }

    public String getPublishVerName() {
        return publishVerName;
    }

    public void setPublishVerName(String publishVerName) {
        this.publishVerName = publishVerName;
    }

    public String getExtBookTypeCode() {
        return extBookTypeCode;
    }

    public void setExtBookTypeCode(String extBookTypeCode) {
        this.extBookTypeCode = extBookTypeCode;
    }

    public String getExtBookTypeName() {
        return extBookTypeName;
    }

    public void setExtBookTypeName(String extBookTypeName) {
        this.extBookTypeName = extBookTypeName;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getBookVolCode() {
        return bookVolCode;
    }

    public void setBookVolCode(String bookVolCode) {
        this.bookVolCode = bookVolCode;
    }

    public String getBookVolName() {
        return bookVolName;
    }

    public void setBookVolName(String bookVolName) {
        this.bookVolName = bookVolName;
    }

    public int getBookPublisherId() {
        return bookPublisherId;
    }

    public void setBookPublisherId(int bookPublisherId) {
        this.bookPublisherId = bookPublisherId;
    }

    public String getBookPublisherName() {
        return bookPublisherName;
    }

    public void setBookPublisherName(String bookPublisherName) {
        this.bookPublisherName = bookPublisherName;
    }

    public String getBookEditionTime() {
        return bookEditionTime;
    }

    public void setBookEditionTime(String bookEditionTime) {
        this.bookEditionTime = bookEditionTime;
    }

    public int getBookEditionNum() {
        return bookEditionNum;
    }

    public void setBookEditionNum(int bookEditionNum) {
        this.bookEditionNum = bookEditionNum;
    }

    public String getBookReprintTime() {
        return bookReprintTime;
    }

    public void setBookReprintTime(String bookReprintTime) {
        this.bookReprintTime = bookReprintTime;
    }

    public int getBookReprintNum() {
        return bookReprintNum;
    }

    public void setBookReprintNum(int bookReprintNum) {
        this.bookReprintNum = bookReprintNum;
    }

    public String getBookCreateTime() {
        return bookCreateTime;
    }

    public void setBookCreateTime(String bookCreateTime) {
        this.bookCreateTime = bookCreateTime;
    }

    public String getBookModifierName() {
        return bookModifierName;
    }

    public void setBookModifierName(String bookModifierName) {
        this.bookModifierName = bookModifierName;
    }

    public String getBookModifyTime() {
        return bookModifyTime;
    }

    public void setBookModifyTime(String bookModifyTime) {
        this.bookModifyTime = bookModifyTime;
    }

    public String getBookInspectorName() {
        return bookInspectorName;
    }

    public void setBookInspectorName(String bookInspectorName) {
        this.bookInspectorName = bookInspectorName;
    }

    public String getBookInspTime() {
        return bookInspTime;
    }

    public void setBookInspTime(String bookInspTime) {
        this.bookInspTime = bookInspTime;
    }

    public String getBookApproverName() {
        return bookApproverName;
    }

    public void setBookApproverName(String bookApproverName) {
        this.bookApproverName = bookApproverName;
    }

    public String getBookApprTime() {
        return bookApprTime;
    }

    public void setBookApprTime(String bookApprTime) {
        this.bookApprTime = bookApprTime;
    }

    public String getBookOnShelfTime() {
        return bookOnShelfTime;
    }

    public void setBookOnShelfTime(String bookOnShelfTime) {
        this.bookOnShelfTime = bookOnShelfTime;
    }

    public String getBookOffShelfTime() {
        return bookOffShelfTime;
    }

    public void setBookOffShelfTime(String bookOffShelfTime) {
        this.bookOffShelfTime = bookOffShelfTime;
    }

    public String getBookArchiveTime() {
        return bookArchiveTime;
    }

    public void setBookArchiveTime(String bookArchiveTime) {
        this.bookArchiveTime = bookArchiveTime;
    }

    public String getCoverBAtchRemotePath() {
        return coverBAtchRemotePath;
    }

    public void setCoverBAtchRemotePath(String coverBAtchRemotePath) {
        this.coverBAtchRemotePath = coverBAtchRemotePath;
    }

    public String getCoverLAtchRemotePath() {
        return coverLAtchRemotePath;
    }

    public void setCoverLAtchRemotePath(String coverLAtchRemotePath) {
        this.coverLAtchRemotePath = coverLAtchRemotePath;
    }

    public String getAudioStatus() {
        return audioStatus;
    }

    public void setAudioStatus(String audioStatus) {
        this.audioStatus = audioStatus;
    }

    public String getAudioCreateTime() {
        return audioCreateTime;
    }

    public void setAudioCreateTime(String audioCreateTime) {
        this.audioCreateTime = audioCreateTime;
    }

    public String getAudioModifierName() {
        return audioModifierName;
    }

    public void setAudioModifierName(String audioModifierName) {
        this.audioModifierName = audioModifierName;
    }

    public String getAudioModifyTime() {
        return audioModifyTime;
    }

    public void setAudioModifyTime(String audioModifyTime) {
        this.audioModifyTime = audioModifyTime;
    }

    public String getAudioInspectorName() {
        return audioInspectorName;
    }

    public void setAudioInspectorName(String audioInspectorName) {
        this.audioInspectorName = audioInspectorName;
    }

    public String getAudioInspTime() {
        return audioInspTime;
    }

    public void setAudioInspTime(String audioInspTime) {
        this.audioInspTime = audioInspTime;
    }

    public String getAudioApproverName() {
        return audioApproverName;
    }

    public void setAudioApproverName(String audioApproverName) {
        this.audioApproverName = audioApproverName;
    }

    public String getAudioApprTime() {
        return audioApprTime;
    }

    public void setAudioApprTime(String audioApprTime) {
        this.audioApprTime = audioApprTime;
    }

    public String getAudioOnShelfTime() {
        return audioOnShelfTime;
    }

    public void setAudioOnShelfTime(String audioOnShelfTime) {
        this.audioOnShelfTime = audioOnShelfTime;
    }

    public String getAudioOffShelfTime() {
        return audioOffShelfTime;
    }

    public void setAudioOffShelfTime(String audioOffShelfTime) {
        this.audioOffShelfTime = audioOffShelfTime;
    }

    public String getAudioArchiveTime() {
        return audioArchiveTime;
    }

    public void setAudioArchiveTime(String audioArchiveTime) {
        this.audioArchiveTime = audioArchiveTime;
    }

    public String getAudioDataAtchRemotePath() {
        return audioDataAtchRemotePath;
    }

    public void setAudioDataAtchRemotePath(String audioDataAtchRemotePath) {
        this.audioDataAtchRemotePath = audioDataAtchRemotePath;
    }

    public String getAudioConfigAtchRemotePath() {
        return audioConfigAtchRemotePath;
    }

    public void setAudioConfigAtchRemotePath(String audioConfigAtchRemotePath) {
        this.audioConfigAtchRemotePath = audioConfigAtchRemotePath;
    }

    public boolean isHasInsp() {
        return hasInsp;
    }

    public void setHasInsp(boolean hasInsp) {
        this.hasInsp = hasInsp;
    }

    public boolean isHasAppr() {
        return hasAppr;
    }

    public void setHasAppr(boolean hasAppr) {
        this.hasAppr = hasAppr;
    }

    public boolean isIsModifier() {
        return isModifier;
    }

    public void setIsModifier(boolean isModifier) {
        this.isModifier = isModifier;
    }

    public boolean isIsInspector() {
        return isInspector;
    }

    public void setIsInspector(boolean isInspector) {
        this.isInspector = isInspector;
    }

    public boolean isIsApprover() {
        return isApprover;
    }

    public void setIsApprover(boolean isApprover) {
        this.isApprover = isApprover;
    }
}
