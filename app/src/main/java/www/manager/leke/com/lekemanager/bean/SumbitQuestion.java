package www.manager.leke.com.lekemanager.bean;

/**
 * Created by ypu
 * on 2020/5/18 0018
 * 提交题库的校对/审核
 */
public class SumbitQuestion {

     private Integer qBankBookId ;
     private  Integer insp; //0:校对不通过，1：校对通过，必传
        private Integer appr ;


    public SumbitQuestion(Integer qBankBookId, Integer insp, Integer appr) {
        this.qBankBookId = qBankBookId;
        this.insp = insp;
        this.appr = appr;
    }

    @Override
    public String toString() {
        return "SumbitQuestion{" +
                "qBankBookId=" + qBankBookId +
                ", insp=" + insp +
                '}';
    }
}
