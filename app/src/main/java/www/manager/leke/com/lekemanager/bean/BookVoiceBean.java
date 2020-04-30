package www.manager.leke.com.lekemanager.bean;

import android.support.annotation.NonNull;

import java.util.List;

/***
 * 点读
 */
public class BookVoiceBean {

    private int page;
    private List<VoiceBean> voiceInfos;

    public static class VoiceBean implements Comparable<VoiceBean> {
        private int postion;
        public String url;
        public int start;
        public int end;
        private int viewLeft;
        private int viewTop;

        private int lacalLet;
        private int localTop;

        public int getLacalLet() {
            return lacalLet;
        }

        public void setLacalLet(int lacalLet) {
            this.lacalLet = lacalLet;
        }

        public int getLocalTop() {
            return localTop;
        }

        public void setLocalTop(int localTop) {
            this.localTop = localTop;
        }

        public int getPostion() {
            return postion;
        }

        public String getUrl() {
            return url;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public int getViewLeft() {
            return viewLeft;
        }

        public int getViewTop() {
            return viewTop;
        }

        @Override
        public int compareTo(@NonNull VoiceBean voiceBean) {
            return this.postion - voiceBean.postion;
        }

        @Override
        public String toString() {
            return
                    "postion:" + postion + "\n" +
                            "url:" + url + "\n" +
                            "start:" + start + "\n" +
                            "end:" + end + "\n" +
                            "viewLeft:" + viewLeft + "\n" +
                            "viewTop:" + viewTop + "\n" +
                            "当前坐标 viewLeft：" + lacalLet + "\n" +
                            "当前坐标 viewTop" + localTop;


        }
    }

    public int getPage() {
        return page;
    }

    public List<VoiceBean> getVoiceInfos() {
        return voiceInfos;
    }
}
