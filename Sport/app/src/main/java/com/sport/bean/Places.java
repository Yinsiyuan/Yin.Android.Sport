package com.sport.bean;

import java.io.Serializable;
import java.util.List;

public class Places implements Serializable {

    /**
     * status : 0
     * message : ok
     * total : 24
     * results : [{"name":"和平体育馆","location":{"lat":39.126273,"lng":117.212832},"address":"和平区新华路211号(保定道口)","street_id":"ca4550fd04237218f60e7769","telephone":"(022)23317148","detail":1,"uid":"ca4550fd04237218f60e7769","detail_info":{"tag":"运动健身;体育场馆","type":"life","detail_url":"http://api.map.baidu.com/place/detail?uid=ca4550fd04237218f60e7769&output=html&source=placeapi_v2","overall_rating":"4.3","service_rating":"0","environment_rating":"0","image_num":"43","comment_num":"47"}},{"name":"天津G5篮球训练基地","location":{"lat":39.138246,"lng":117.258126},"address":"河东区泰兴南路和成林道交口(武警河东支队对面)","street_id":"93e50c861022de9b5fd4fe5c","telephone":"13820662878","detail":1,"uid":"93e50c861022de9b5fd4fe5c","detail_info":{"tag":"运动健身;体育场馆","type":"life","detail_url":"http://api.map.baidu.com/place/detail?uid=93e50c861022de9b5fd4fe5c&output=html&source=placeapi_v2","overall_rating":"3.5","service_rating":"0","environment_rating":"0","image_num":"14","comment_num":"8"}},{"name":"IC运动空间","location":{"lat":39.115796,"lng":117.26867},"address":"河东区中山门北里7号楼对面","street_id":"f44dc2ab8e8f138746e274aa","telephone":"15522299272","detail":1,"uid":"f44dc2ab8e8f138746e274aa","detail_info":{"tag":"运动健身;体育场馆","type":"life","detail_url":"http://api.map.baidu.com/place/detail?uid=f44dc2ab8e8f138746e274aa&output=html&source=placeapi_v2","overall_rating":"5.0","service_rating":"0","environment_rating":"0","image_num":"47","comment_num":"23"}},{"name":"HOT运动磁场","location":{"lat":39.138975,"lng":117.14427},"address":"南开区咸阳路(与渭水道交口)66号兴旺钢结构院内","street_id":"f071011981da1b743686f498","telephone":"13672033053","detail":1,"uid":"f071011981da1b743686f498","detail_info":{"tag":"运动健身;体育场馆","type":"life","detail_url":"http://api.map.baidu.com/place/detail?uid=f071011981da1b743686f498&output=html&source=placeapi_v2","overall_rating":"3.7","service_rating":"0","environment_rating":"0","image_num":"10","comment_num":"29"}},{"name":"方形运动中心","location":{"lat":39.037397,"lng":117.203722},"address":"西青区梨双路2号梨兴温泉酒店院内(梨兴温泉酒店，梨园花卉)","street_id":"b050e4299429fdaf882fb5eb","telephone":"(022)83967878","detail":1,"uid":"b050e4299429fdaf882fb5eb","detail_info":{"tag":"运动健身;体育场馆","type":"life","detail_url":"http://api.map.baidu.com/place/detail?uid=b050e4299429fdaf882fb5eb&output=html&source=placeapi_v2","overall_rating":"5.0","service_rating":"0","environment_rating":"0","image_num":"29","comment_num":"6"}},{"name":"耀华中学室内篮球馆","location":{"lat":39.12565,"lng":117.207551},"address":"和平区南京路106号(近滨江道)","street_id":"6b1dc582e8e5ba05cae33cee","telephone":"13752066061","detail":1,"uid":"6b1dc582e8e5ba05cae33cee","detail_info":{"tag":"运动健身;体育场馆","type":"life","detail_url":"http://api.map.baidu.com/place/detail?uid=6b1dc582e8e5ba05cae33cee&output=html&source=placeapi_v2","overall_rating":"3.5","service_rating":"0","environment_rating":"0","image_num":"22","comment_num":"65"}},{"name":"天津美术学院-体育馆","location":{"lat":39.159063,"lng":117.200417},"address":"河北区天纬路4号(近大悲院)","street_id":"bff8fa7dc8d824a1c9213db0","detail":1,"uid":"bff8fa7dc8d824a1c9213db0","detail_info":{"tag":"运动健身;体育场馆","type":"life","detail_url":"http://api.map.baidu.com/place/detail?uid=bff8fa7dc8d824a1c9213db0&output=html&source=placeapi_v2","overall_rating":"3.7","service_rating":"0","environment_rating":"0","image_num":"3","comment_num":"8"}},{"name":"南大篮球场","location":{"lat":39.111664,"lng":117.178052},"address":"南开区卫津路94号南开大学内(近复康路)","street_id":"5fe7bc83d39595ab631c67ee","detail":1,"uid":"5fe7bc83d39595ab631c67ee","detail_info":{"tag":"运动健身;体育场馆","type":"life","detail_url":"http://api.map.baidu.com/place/detail?uid=5fe7bc83d39595ab631c67ee&output=html&source=placeapi_v2","overall_rating":"3.7","service_rating":"0","environment_rating":"0","image_num":"9","comment_num":"15"}},{"name":"篮球场(乐群北路)","location":{"lat":39.109712,"lng":117.168511},"address":"天津市南开区卫津路94号南开大学内","street_id":"da1eaeb3c6085fbe6fa3ccc1","detail":1,"uid":"da1eaeb3c6085fbe6fa3ccc1","detail_info":{"tag":"运动健身;体育场馆","type":"life","detail_url":"http://api.map.baidu.com/place/detail?uid=da1eaeb3c6085fbe6fa3ccc1&output=html&source=placeapi_v2","overall_rating":"0","service_rating":"0","environment_rating":"0"}},{"name":"天津医科大学-篮球场","location":{"lat":39.114165,"lng":117.190819},"address":"和平区气象台路22号天津医科大学内","street_id":"2183ed0c2930145357bfff9d","detail":1,"uid":"2183ed0c2930145357bfff9d","detail_info":{"tag":"运动健身;体育场馆","type":"life","detail_url":"http://api.map.baidu.com/place/detail?uid=2183ed0c2930145357bfff9d&output=html&source=placeapi_v2","overall_rating":"4.0","service_rating":"0","environment_rating":"0","image_num":"8","comment_num":"3"}}]
     */

    private int status;
    private String message;
    private int total;
    /**
     * name : 和平体育馆
     * location : {"lat":39.126273,"lng":117.212832}
     * address : 和平区新华路211号(保定道口)
     * street_id : ca4550fd04237218f60e7769
     * telephone : (022)23317148
     * detail : 1
     * uid : ca4550fd04237218f60e7769
     * detail_info : {"tag":"运动健身;体育场馆","type":"life","detail_url":"http://api.map.baidu.com/place/detail?uid=ca4550fd04237218f60e7769&output=html&source=placeapi_v2","overall_rating":"4.3","service_rating":"0","environment_rating":"0","image_num":"43","comment_num":"47"}
     */

    private List<ResultsBean> results;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String name;
        /**
         * lat : 39.126273
         * lng : 117.212832
         */

        private LocationBean location;
        private String address;
        private String street_id;
        private String telephone;
        private int detail;
        private String uid;
        /**
         * tag : 运动健身;体育场馆
         * type : life
         * detail_url : http://api.map.baidu.com/place/detail?uid=ca4550fd04237218f60e7769&output=html&source=placeapi_v2
         * overall_rating : 4.3
         * service_rating : 0
         * environment_rating : 0
         * image_num : 43
         * comment_num : 47
         */

        private DetailInfoBean detail_info;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStreet_id() {
            return street_id;
        }

        public void setStreet_id(String street_id) {
            this.street_id = street_id;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public int getDetail() {
            return detail;
        }

        public void setDetail(int detail) {
            this.detail = detail;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public DetailInfoBean getDetail_info() {
            return detail_info;
        }

        public void setDetail_info(DetailInfoBean detail_info) {
            this.detail_info = detail_info;
        }

        public static class LocationBean {
            private double lat;
            private double lng;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }

        public static class DetailInfoBean {
            private String tag;
            private String type;
            private String detail_url;
            private String overall_rating;
            private String service_rating;
            private String environment_rating;
            private String image_num;
            private String comment_num;

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDetail_url() {
                return detail_url;
            }

            public void setDetail_url(String detail_url) {
                this.detail_url = detail_url;
            }

            public String getOverall_rating() {
                return overall_rating;
            }

            public void setOverall_rating(String overall_rating) {
                this.overall_rating = overall_rating;
            }

            public String getService_rating() {
                return service_rating;
            }

            public void setService_rating(String service_rating) {
                this.service_rating = service_rating;
            }

            public String getEnvironment_rating() {
                return environment_rating;
            }

            public void setEnvironment_rating(String environment_rating) {
                this.environment_rating = environment_rating;
            }

            public String getImage_num() {
                return image_num;
            }

            public void setImage_num(String image_num) {
                this.image_num = image_num;
            }

            public String getComment_num() {
                return comment_num;
            }

            public void setComment_num(String comment_num) {
                this.comment_num = comment_num;
            }
        }
    }
}
