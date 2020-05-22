package www.manager.leke.com.lekemanager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ypu
 * on 2020/5/18 0018
 */
public class BookMessageDetail implements Serializable {
    /**
     * bookId : 207020005
     * bookTitle : 七年级数学辅导书
     * bookDisplayTitle : 测试_七年级人教版数学下册辅导书
     * bookTypeCode : BB01
     * bookTypeName : 课本
     * fitGradeU : SE03
     * fitGradeL : SE01
     * bookSubjectCode : SH01
     * bookSubjectName : 语文
     * publishVerId : 101
     * publishVerName : 教材版本1
     * extBookTypeCode : BD01
     * extBookTypeName : 文学
     * bookStatus : BA11
     * bookContents : {"nodes":[{"id":1,"level":1,"name":"第五章 相交线与平行线","nodes":[{"id":2,"level":2,"name":"测试1 相交线"}]}],"ver":0.1}
     * bookExtraInfo : {"bookAuthor":"李二林 白建明","bookVolCode":"BC02","bookVolName":"下册","bookIsbn":"9787116065185","bookPublisherId":1,"bookPublisherName":"出版社1","bookEditionTime":"2018-02-01","bookEditionNum":2,"bookReprintTime":"2018-02-01","bookReprintNum":1,"bookOriginPrice":2.2,"bookAwards":"","bookKeyword":"","bookSummary":""}
     * coverBInfos : [{"coverBAtchRemotePath":"//bj-global-task.oss-cn-xxx/xxx/DAAN_2019-7-31_14-43-17.jpg","deviceModelCode":"BN01"}]
     * coverLInfos : [{"coverLAtchRemotePath":"//bj-global-task.oss-cn-xxx/xxx/DAAN_2019-7-31_14-43-18.jpg","deviceModel":"BN01"}]
     * previewInfos : [{"previewAtchRemotePath":"//espo.oss-cn-shanghai.aliyuncs.com/leke/book/preview/sZQhCjQWrMZ4AP7sW6PfSebTZBJ3jipB.pdf","previewAtchSize":17706,"deviceModelCode":"BN01"}]
     * dataPackInfos : [{"dataPackAtchRemotePath":"//espo.oss-cn-XXX.aliyuncs.com/XXX/sZQhCjQWrMZ4AP7sW6PfSebTZBJ3jipB.pdf","atchBucket":"bj-xxx","atchName":"附件名称","atchRemotePath":"38113/1000012883/xxx/251_0_0.pdf","atchSize":1024,"atchEncryptkey":"xxx","atchMd5":"xxx","deviceModelCode":"BN01"}]
     * hasOriginDataPack : true
     */

    private int bookId;
    private String bookTitle;
    private String bookDisplayTitle;
    private String bookTypeCode;
    private String bookTypeName;
    private String fitGradeU;
    private String fitGradeL;
    private String bookSubjectCode;
    private String bookSubjectName;
    private int publishVerId;
    private String publishVerName;
    private String extBookTypeCode;
    private String extBookTypeName;
    private String bookStatus;
    private BookContentsBean bookContents;
    private BookExtraInfoBean bookExtraInfo;
    private boolean hasOriginDataPack;
    private List<CoverBInfosBean> coverBInfos;
    private List<CoverLInfosBean> coverLInfos;
    private List<PreviewInfosBean> previewInfos;
    private List<DataPackInfosBean> dataPackInfos;

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

    public BookContentsBean getBookContents() {
        return bookContents;
    }

    public void setBookContents(BookContentsBean bookContents) {
        this.bookContents = bookContents;
    }

    public BookExtraInfoBean getBookExtraInfo() {
        return bookExtraInfo;
    }

    public void setBookExtraInfo(BookExtraInfoBean bookExtraInfo) {
        this.bookExtraInfo = bookExtraInfo;
    }

    public boolean isHasOriginDataPack() {
        return hasOriginDataPack;
    }

    public void setHasOriginDataPack(boolean hasOriginDataPack) {
        this.hasOriginDataPack = hasOriginDataPack;
    }

    public List<CoverBInfosBean> getCoverBInfos() {
        return coverBInfos;
    }

    public void setCoverBInfos(List<CoverBInfosBean> coverBInfos) {
        this.coverBInfos = coverBInfos;
    }

    public List<CoverLInfosBean> getCoverLInfos() {
        return coverLInfos;
    }

    public void setCoverLInfos(List<CoverLInfosBean> coverLInfos) {
        this.coverLInfos = coverLInfos;
    }

    public List<PreviewInfosBean> getPreviewInfos() {
        return previewInfos;
    }

    public void setPreviewInfos(List<PreviewInfosBean> previewInfos) {
        this.previewInfos = previewInfos;
    }

    public List<DataPackInfosBean> getDataPackInfos() {
        return dataPackInfos;
    }

    public void setDataPackInfos(List<DataPackInfosBean> dataPackInfos) {
        this.dataPackInfos = dataPackInfos;
    }

    public static class BookContentsBean {
        /**
         * nodes : [{"id":1,"level":1,"name":"第五章 相交线与平行线","nodes":[{"id":2,"level":2,"name":"测试1 相交线"}]}]
         * ver : 0.1
         */

        private double ver;
        private List<NodesBeanX> nodes;

        public double getVer() {
            return ver;
        }

        public void setVer(double ver) {
            this.ver = ver;
        }

        public List<NodesBeanX> getNodes() {
            return nodes;
        }

        public void setNodes(List<NodesBeanX> nodes) {
            this.nodes = nodes;
        }

        public static class NodesBeanX {
            /**
             * id : 1
             * level : 1
             * name : 第五章 相交线与平行线
             * nodes : [{"id":2,"level":2,"name":"测试1 相交线"}]
             */

            private int id;
            private int level;
            private String name;
            private List<NodesBean> nodes;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<NodesBean> getNodes() {
                return nodes;
            }

            public void setNodes(List<NodesBean> nodes) {
                this.nodes = nodes;
            }

            public static class NodesBean {
                /**
                 * id : 2
                 * level : 2
                 * name : 测试1 相交线
                 */

                private int id;
                private int level;
                private String name;
                private List<NodesBean> nodes;

                public List<NodesBean> getNodes() {
                    return nodes;
                }

                public void setNodes(List<NodesBean> nodes) {
                    this.nodes = nodes;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }

    public static class BookExtraInfoBean {
        /**
         * bookAuthor : 李二林 白建明
         * bookVolCode : BC02
         * bookVolName : 下册
         * bookIsbn : 9787116065185
         * bookPublisherId : 1
         * bookPublisherName : 出版社1
         * bookEditionTime : 2018-02-01
         * bookEditionNum : 2
         * bookReprintTime : 2018-02-01
         * bookReprintNum : 1
         * bookOriginPrice : 2.2
         * bookAwards :
         * bookKeyword :
         * bookSummary :
         */

        private String bookAuthor;
        private String bookVolCode;
        private String bookVolName;
        private String bookIsbn;
        private int bookPublisherId;
        private String bookPublisherName;
        private String bookEditionTime;
        private int bookEditionNum;
        private String bookReprintTime;
        private int bookReprintNum;
        private double bookOriginPrice;
        private String bookAwards;
        private String bookKeyword;
        private String bookSummary;

        public String getBookAuthor() {
            return bookAuthor;
        }

        public void setBookAuthor(String bookAuthor) {
            this.bookAuthor = bookAuthor;
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

        public String getBookIsbn() {
            return bookIsbn;
        }

        public void setBookIsbn(String bookIsbn) {
            this.bookIsbn = bookIsbn;
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

        public double getBookOriginPrice() {
            return bookOriginPrice;
        }

        public void setBookOriginPrice(double bookOriginPrice) {
            this.bookOriginPrice = bookOriginPrice;
        }

        public String getBookAwards() {
            return bookAwards;
        }

        public void setBookAwards(String bookAwards) {
            this.bookAwards = bookAwards;
        }

        public String getBookKeyword() {
            return bookKeyword;
        }

        public void setBookKeyword(String bookKeyword) {
            this.bookKeyword = bookKeyword;
        }

        public String getBookSummary() {
            return bookSummary;
        }

        public void setBookSummary(String bookSummary) {
            this.bookSummary = bookSummary;
        }
    }

    public static class CoverBInfosBean {
        /**
         * coverBAtchRemotePath : //bj-global-task.oss-cn-xxx/xxx/DAAN_2019-7-31_14-43-17.jpg
         * deviceModelCode : BN01
         */

        private String coverBAtchRemotePath;
        private String deviceModelCode;

        public String getCoverBAtchRemotePath() {
            return coverBAtchRemotePath;
        }

        public void setCoverBAtchRemotePath(String coverBAtchRemotePath) {
            this.coverBAtchRemotePath = coverBAtchRemotePath;
        }

        public String getDeviceModelCode() {
            return deviceModelCode;
        }

        public void setDeviceModelCode(String deviceModelCode) {
            this.deviceModelCode = deviceModelCode;
        }
    }

    public static class CoverLInfosBean {
        /**
         * coverLAtchRemotePath : //bj-global-task.oss-cn-xxx/xxx/DAAN_2019-7-31_14-43-18.jpg
         * deviceModel : BN01
         */

        private String coverLAtchRemotePath;
        private String deviceModel;

        public String getCoverLAtchRemotePath() {
            return coverLAtchRemotePath;
        }

        public void setCoverLAtchRemotePath(String coverLAtchRemotePath) {
            this.coverLAtchRemotePath = coverLAtchRemotePath;
        }

        public String getDeviceModel() {
            return deviceModel;
        }

        public void setDeviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
        }
    }

    public static class PreviewInfosBean {
        /**
         * previewAtchRemotePath : //espo.oss-cn-shanghai.aliyuncs.com/leke/book/preview/sZQhCjQWrMZ4AP7sW6PfSebTZBJ3jipB.pdf
         * previewAtchSize : 17706
         * deviceModelCode : BN01
         */

        private String previewAtchRemotePath;
        private int previewAtchSize;
        private String deviceModelCode;

        public String getPreviewAtchRemotePath() {
            return previewAtchRemotePath;
        }

        public void setPreviewAtchRemotePath(String previewAtchRemotePath) {
            this.previewAtchRemotePath = previewAtchRemotePath;
        }

        public int getPreviewAtchSize() {
            return previewAtchSize;
        }

        public void setPreviewAtchSize(int previewAtchSize) {
            this.previewAtchSize = previewAtchSize;
        }

        public String getDeviceModelCode() {
            return deviceModelCode;
        }

        public void setDeviceModelCode(String deviceModelCode) {
            this.deviceModelCode = deviceModelCode;
        }
    }

    public static class DataPackInfosBean {
        /**
         * dataPackAtchRemotePath : //espo.oss-cn-XXX.aliyuncs.com/XXX/sZQhCjQWrMZ4AP7sW6PfSebTZBJ3jipB.pdf
         * atchBucket : bj-xxx
         * atchName : 附件名称
         * atchRemotePath : 38113/1000012883/xxx/251_0_0.pdf
         * atchSize : 1024
         * atchEncryptkey : xxx
         * atchMd5 : xxx
         * deviceModelCode : BN01
         */

        private String dataPackAtchRemotePath;
        private String atchBucket;
        private String atchName;
        private String atchRemotePath;
        private int atchSize;
        private String atchEncryptkey;
        private String atchMd5;
        private String deviceModelCode;

        public String getDataPackAtchRemotePath() {
            return dataPackAtchRemotePath;
        }

        public void setDataPackAtchRemotePath(String dataPackAtchRemotePath) {
            this.dataPackAtchRemotePath = dataPackAtchRemotePath;
        }

        public String getAtchBucket() {
            return atchBucket;
        }

        public void setAtchBucket(String atchBucket) {
            this.atchBucket = atchBucket;
        }

        public String getAtchName() {
            return atchName;
        }

        public void setAtchName(String atchName) {
            this.atchName = atchName;
        }

        public String getAtchRemotePath() {
            return atchRemotePath;
        }

        public void setAtchRemotePath(String atchRemotePath) {
            this.atchRemotePath = atchRemotePath;
        }

        public int getAtchSize() {
            return atchSize;
        }

        public void setAtchSize(int atchSize) {
            this.atchSize = atchSize;
        }

        public String getAtchEncryptkey() {
            return atchEncryptkey;
        }

        public void setAtchEncryptkey(String atchEncryptkey) {
            this.atchEncryptkey = atchEncryptkey;
        }

        public String getAtchMd5() {
            return atchMd5;
        }

        public void setAtchMd5(String atchMd5) {
            this.atchMd5 = atchMd5;
        }

        public String getDeviceModelCode() {
            return deviceModelCode;
        }

        public void setDeviceModelCode(String deviceModelCode) {
            this.deviceModelCode = deviceModelCode;
        }
    }
}
