package com.sport.bean;

import java.util.List;

public class News{

    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2016-05-27 10:38","title":"刘翔成立个人工作室 以公益为先或进军娱乐圈","description":"凤凰体育","picUrl":"http://d.ifengimg.com/w145_h103/p0.ifengimg.com/cmpp/2016/05/27/1270ba2b84e78f318c04fe329958d849_size310_w350_h500.jpg","url":"http://sports.ifeng.com/a/20160527/48859920_0.shtml"},{"ctime":"2016-05-27 09:15","title":"纳达尔迎来大满贯200胜 不解为何有人放弃奥运","description":"凤凰体育","picUrl":"http://d.ifengimg.com/w145_h103/p1.ifengimg.com/a/2016_22/d87eccd1c33fff8_size35_w570_h320.jpg","url":"http://sports.ifeng.com/a/20160527/48859245_0.shtml"},{"ctime":"2016-05-27 09:00","title":"叶江川：尊重侯逸凡维护自身权益 有利于国象发展","description":"凤凰体育","picUrl":"http://d.ifengimg.com/w145_h103/p3.ifengimg.com/a/2016_22/9ded7eff0fffc3e_size95_w550_h438.jpg","url":"http://sports.ifeng.com/a/20160527/48858923_0.shtml"},{"ctime":"2016-05-27 09:00","title":"郎平的心态他了解！陈忠和剖析女排里约奥运前景","description":"凤凰体育","picUrl":"http://d.ifengimg.com/w145_h103/p1.ifengimg.com/cmpp/2016/05/27/37c7f2cf1e558b9c97d5b0e64a7c6378_size281_w500_h350.jpg","url":"http://sports.ifeng.com/a/20160527/48858994_0.shtml"},{"ctime":"2016-05-27 08:41","title":"王蔷看清自己与红土高手差距 盼为国效力出战奥运","description":"凤凰体育","picUrl":"http://d.ifengimg.com/w145_h103/p3.ifengimg.com/cmpp/2016/05/27/ae10c3b1cb4ef119d0b26e5912b985fd_size279_w500_h350.jpg","url":"http://sports.ifeng.com/a/20160527/48859025_0.shtml"},{"ctime":"2016-05-27 08:00","title":"国羽公布奥运选拔方案 将看综合排名+大赛表现","description":"凤凰体育","picUrl":"http://d.ifengimg.com/w145_h103/p2.ifengimg.com/a/2016_22/9b5186ce70ec0c5_size13_w481_h280.jpg","url":"http://sports.ifeng.com/a/20160527/48858024_0.shtml"},{"ctime":"2016-05-27 08:00","title":"奇葩！韩体育会劝朴泰桓弃奥运 承诺里约后解禁","description":"凤凰体育","picUrl":"http://d.ifengimg.com/w145_h103/p2.ifengimg.com/a/2016_22/92186fd6384903e_size26_w528_h322.jpg","url":"http://sports.ifeng.com/a/20160527/48858350_0.shtml"},{"ctime":"2016-05-27 07:40","title":"法网-特松加苦战上演大逆转 携手费雷尔晋级","description":"凤凰体育","picUrl":"http://d.ifengimg.com/w145_h103/p3.ifengimg.com/a/2016_22/feb3bada85ac04b_size10_w243_h171.jpg","url":"http://sports.ifeng.com/a/20160527/48858508_0.shtml"},{"ctime":"2016-05-27 07:00","title":"李世石退出韩国棋士会引震动 或将面临全面禁赛","description":"凤凰体育","picUrl":"http://d.ifengimg.com/w145_h103/p0.ifengimg.com/a/2016_22/3ad5b4281415513_size874_w946_h712.jpg","url":"http://sports.ifeng.com/a/20160527/48854931_0.shtml"},{"ctime":"2016-05-27 07:00","title":"中国天才女棋手退赛抗议赛制 效仿传奇专注男子比赛","description":"凤凰体育","picUrl":"http://d.ifengimg.com/w145_h103/p2.ifengimg.com/a/2016_22/a11b37dff7f7494_size344_w502_h624.jpg","url":"http://sports.ifeng.com/a/20160527/48854983_0.shtml"}]
     */

    public int code;
    public String msg;
    /**
     * ctime : 2016-05-27 10:38
     * title : 刘翔成立个人工作室 以公益为先或进军娱乐圈
     * description : 凤凰体育
     * picUrl : http://d.ifengimg.com/w145_h103/p0.ifengimg.com/cmpp/2016/05/27/1270ba2b84e78f318c04fe329958d849_size310_w350_h500.jpg
     * url : http://sports.ifeng.com/a/20160527/48859920_0.shtml
     */

    public List<NewslistEntity> newslist;

    public static class NewslistEntity {
        public String ctime;
        public String title;
        public String description;
        public String picUrl;
        public String url;
    }
}
