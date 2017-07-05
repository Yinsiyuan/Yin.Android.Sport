package com.sport.ui;

import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.overlay.PoiOverlay;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.sport.R;
import com.sport.common.BaseAct;
import com.sport.widget.TopView;

import java.util.List;

import butterknife.BindView;


public class MapAct extends BaseAct implements PoiSearch.OnPoiSearchListener {

    @BindView(R.id.mMap)
    MapView mMap;

    @BindView(R.id.mTopView)
    TopView mTopView;

    private AMap aMap;

    private Bundle savedInstanceState;

    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private PoiResult poiResult; // poi返回的结果

    @Override
    public int setContentView(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        return R.layout.a_main;
    }

    @Override
    public void initView() {
        mMap = (MapView) findViewById(R.id.mMap);
        mTopView.init(true,0,"地图",0,0,0,null);
        mMap.onCreate(savedInstanceState);// 此方法必须重写
        init();
        doSearchQuery();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mMap.getMap();

        }

    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query("体育场", "", "天津");
        // 设置每页最多返回多少条poiitem
        query.setPageSize(1000);
        query.setCityLimit(true);

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMap.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMap.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMap.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMap.onDestroy();
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        aMap.clear();// 清理之前的图标
                        PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
                        poiOverlay.removeFromMap();
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        toast("建议城市");
                    } else {
                        toast("没有查到");
                    }
                }
            } else {
                toast("没有查到");
            }
        } else {
            toast(rCode + "");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
