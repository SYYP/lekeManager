package www.manager.leke.com.lekemanager.reading;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * 功能：点读
 * 作者: YUAN_YE
 * 日期: 2019/10/31
 * 时间: 9:55
 */
public class ReadingBean {

    private int page;
    private List<VoiceBean> voiceInfos;
    public static class VoiceBean implements Comparable<VoiceBean> {
        //顺序
        private int postion;
        public String url;
        //坐标
        private int viewLeft;
        private int viewTop;

        public int getPostion() {
            return postion;
        }

        public String getUrl() {
            return url;
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
            return "VoiceBean{" +
                    "postion=" + postion +
                    ", url='" + url + '\'' +
                    ", viewLeft=" + viewLeft +
                    ", viewTop=" + viewTop +
                    '}';
        }
    }

    public int getPage() {
        return page;
    }

    public List<VoiceBean> getVoiceInfos() {
        return voiceInfos;
    }
}
