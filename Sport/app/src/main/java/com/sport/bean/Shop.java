package com.sport.bean;

import java.io.Serializable;
import java.util.List;


public class Shop implements Serializable{

    /**
     * status : 0
     * message : ok
     * total : 400
     * results : [{"name":"绍发科技体育用品","location":{"lat":39.109094,"lng":117.22971},"address":"大沽南路494号","street_id":"befdb29270787ff887154125","telephone":"(022)83815835","detail":1,"uid":"befdb29270787ff887154125","detail_info":{"tag":"购物;商铺","type":"shopping","detail_url":"http://api.map.baidu.com/place/detail?uid=befdb29270787ff887154125&output=html&source=placeapi_v2","price":"0","overall_rating":"5.0","service_rating":"3.4","environment_rating":"3.35","image_num":"10","comment_num":"9"}},{"name":"大胡同商贸中心","location":{"lat":39.155749,"lng":117.183544},"address":"天津市红桥区南运河北路","street_id":"1084ed2e58f7ef4e471a3206","telephone":"15900250148","detail":1,"uid":"1084ed2e58f7ef4e471a3206","detail_info":{"tag":"购物;商铺","type":"shopping","detail_url":"http://api.map.baidu.com/place/detail?uid=1084ed2e58f7ef4e471a3206&output=html&source=placeapi_v2","price":"0","overall_rating":"0","service_rating":"0","environment_rating":"0"}},{"name":"耐克(环球置地广场东北)","location":{"lat":39.12794,"lng":117.185029},"address":"南京路302号海光寺家乐福1楼","street_id":"1020060f1b4192bf1994c6ea","telephone":"022-27301997","detail":1,"uid":"1020060f1b4192bf1994c6ea","detail_info":{"tag":"购物;商铺","type":"shopping","detail_url":"http://api.map.baidu.com/place/detail?uid=1020060f1b4192bf1994c6ea&output=html&source=placeapi_v2","price":"332.0","overall_rating":"3.8","service_rating":"3.0","environment_rating":"3.0","image_num":"17","comment_num":"22"}},{"name":"耐克(紫金山路店)","location":{"lat":39.098958,"lng":117.192228},"address":"气象台路100号阳光新生活广场1楼","street_id":"a7c56ee057323baaa2acc4ea","detail":1,"uid":"a7c56ee057323baaa2acc4ea","detail_info":{"tag":"购物;商铺","type":"shopping","detail_url":"http://api.map.baidu.com/place/detail?uid=a7c56ee057323baaa2acc4ea&output=html&source=placeapi_v2","price":"320.0","overall_rating":"5.0","service_rating":"3.0","environment_rating":"3.0","image_num":"12","comment_num":"27"}},{"name":"乒缘体育","location":{"lat":39.128875,"lng":117.168542},"address":"南开区白堤路翠峰里底商(南开文化宫对过)","street_id":"341c35de83a48ba7e607386f","telephone":"(022)27465646","detail":1,"uid":"341c35de83a48ba7e607386f","detail_info":{"tag":"购物;商铺","type":"shopping","detail_url":"http://api.map.baidu.com/place/detail?uid=341c35de83a48ba7e607386f&output=html&source=placeapi_v2","price":"0","overall_rating":"4.0","service_rating":"3.3","environment_rating":"3.25","image_num":"5","comment_num":"3"}},{"name":"思凯乐(天津津奥广场专柜)","location":{"lat":39.08602,"lng":117.1619},"address":"天津市南开区宾水西道南侧、卫津南路西侧奥林匹克中心体育馆B馆首层天津津奥广场","street_id":"9cdd0c4bdd10877e4107c1f6","detail":1,"uid":"9cdd0c4bdd10877e4107c1f6","detail_info":{"tag":"购物;商铺","type":"shopping","detail_url":"http://api.map.baidu.com/place/detail?uid=9cdd0c4bdd10877e4107c1f6&output=html&source=placeapi_v2","price":"0","overall_rating":"2.1","service_rating":"0","environment_rating":"0"}},{"name":"回力","location":{"lat":39.13287,"lng":117.20836},"address":"天津市和平区和平路243号圣奥商厦3层","street_id":"d56195c8e044ec17badf8f44","detail":1,"uid":"d56195c8e044ec17badf8f44","detail_info":{"tag":"购物;商铺","type":"shopping","detail_url":"http://api.map.baidu.com/place/detail?uid=d56195c8e044ec17badf8f44&output=html&source=placeapi_v2","price":"0","overall_rating":"1.5","service_rating":"0","environment_rating":"0","image_num":"2"}},{"name":"vans范斯运动用品店","location":{"lat":39.130279,"lng":117.261615},"address":"津滨大道53号万达广场1层","street_id":"030eb21bed270cee9c09f7dd","detail":1,"uid":"030eb21bed270cee9c09f7dd","detail_info":{"tag":"购物;商铺","type":"shopping","detail_url":"http://api.map.baidu.com/place/detail?uid=030eb21bed270cee9c09f7dd&output=html&source=placeapi_v2","price":"0","overall_rating":"0","service_rating":"0","environment_rating":"0","image_num":"1"}},{"name":"耐克(山西路)","location":{"lat":39.12796,"lng":117.20385},"address":"滨江道211号麦购休闲广场B1楼（山西路口）","street_id":"46e86fddb6141676d13ce3d3","telephone":"(022)27122816","detail":1,"uid":"46e86fddb6141676d13ce3d3","detail_info":{"tag":"购物;商铺","type":"shopping","detail_url":"http://api.map.baidu.com/place/detail?uid=46e86fddb6141676d13ce3d3&output=html&source=placeapi_v2","price":"0","overall_rating":"5.0","service_rating":"3.65","environment_rating":"3.65","image_num":"39","comment_num":"17"}},{"name":"耐克(天津友谊新天地店)","location":{"lat":39.130272,"lng":117.205217},"address":"滨江道208号友谊新天地广场5层","street_id":"2cc3c8e8abda5ccf4d2fdfd3","detail":1,"uid":"2cc3c8e8abda5ccf4d2fdfd3","detail_info":{"tag":"购物;商铺","type":"shopping","detail_url":"http://api.map.baidu.com/place/detail?uid=2cc3c8e8abda5ccf4d2fdfd3&output=html&source=placeapi_v2","price":"0","overall_rating":"5.0","service_rating":"3.75","environment_rating":"3.7","image_num":"22","comment_num":"15"}}]
     */

    public int status;
    public String message;
    public int total;
    /**
     * name : 绍发科技体育用品
     * location : {"lat":39.109094,"lng":117.22971}
     * address : 大沽南路494号
     * street_id : befdb29270787ff887154125
     * telephone : (022)83815835
     * detail : 1
     * uid : befdb29270787ff887154125
     * detail_info : {"tag":"购物;商铺","type":"shopping","detail_url":"http://api.map.baidu.com/place/detail?uid=befdb29270787ff887154125&output=html&source=placeapi_v2","price":"0","overall_rating":"5.0","service_rating":"3.4","environment_rating":"3.35","image_num":"10","comment_num":"9"}
     */

    public List<ResultsEntity> results;

    public static class ResultsEntity {
        public String name;
        /**
         * lat : 39.109094
         * lng : 117.22971
         */

        public LocationEntity location;
        public String address;
        public String street_id;
        public String telephone;
        public int detail;
        public String uid;
        /**
         * tag : 购物;商铺
         * type : shopping
         * detail_url : http://api.map.baidu.com/place/detail?uid=befdb29270787ff887154125&output=html&source=placeapi_v2
         * price : 0
         * overall_rating : 5.0
         * service_rating : 3.4
         * environment_rating : 3.35
         * image_num : 10
         * comment_num : 9
         */

        public DetailInfoEntity detail_info;

        public static class LocationEntity {
            public double lat;
            public double lng;
        }

        public static class DetailInfoEntity {
            public String tag;
            public String type;
            public String detail_url;
            public String price;
            public String overall_rating;
            public String service_rating;
            public String environment_rating;
            public String image_num;
            public String comment_num;
        }
    }
}
