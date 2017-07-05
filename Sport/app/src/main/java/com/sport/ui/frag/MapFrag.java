package com.sport.ui.frag;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.overlay.PoiOverlay;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.sport.R;
import com.sport.common.BaseFrag;
import com.sport.event.PlaceEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;


public class MapFrag extends BaseFrag implements PoiSearch.OnPoiSearchListener {

    @BindView(R.id.mMap)
    MapView mMap;

    private Bundle savedInstanceState;
    private AMap aMap;

    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private PoiResult poiResult; // poi返回的结果

    @Override
    public int setContentView(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        return R.layout.f_map;
    }

    @Override
    public void initView(View view) {
        //event注册 传值用的
        EventBus.getDefault().register(this);
        mMap.onCreate(savedInstanceState);// 此方法必须重写
        init();
    }


    @Subscribe
    public void onPlaceEvent(PlaceEvent event) {
        doSearchQuery(event.getType(),event.getCity());
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
    protected void doSearchQuery(String type,String city) {
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(type, "", city);
        // 设置每页最多返回多少条poiitem
        query.setPageSize(1000);
        query.setCityLimit(true);

        poiSearch = new PoiSearch(getActivity(), query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mMap.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mMap.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMap.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mMap.onDestroy();
        super.onDestroy();
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
