package com.sport.ui.frag;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.sport.R;
import com.sport.common.BaseFrag;
import com.sport.event.PlaceEvent;
import com.sport.widget.city.CityPickerActivity;
import com.sport.widget.city.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class PlaceFrag extends BaseFrag {

    @BindView(R.id.mTab)
    TabLayout mTab;
    @BindView(R.id.mPager)
    ViewPager mPager;

    @BindView(R.id.mSpinner)
    Spinner mSpinner;
    @BindView(R.id.mCity)
    TextView mCity;

    private String[] title = {"地图", "列表"};
    private String[] places = {"篮球场", "羽毛球", "游泳馆"};
    private List<Fragment> datas = new ArrayList<>();
    private String city="上海";
    private String type="篮球场";

    private AMapLocationClient mLocationClient;
    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.f_place;
    }

    @Override
    public void initView(View view) {
        datas.clear();
        datas.add(new MapFrag());
        datas.add(new PlaceListFrag());
        initLocation();

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                tv.setTextColor(Color.parseColor("#ffffff"));
                switch (position) {
                    case 0:
                        type="篮球场";
                        EventBus.getDefault().post(new PlaceEvent("篮球场",city));
                        break;
                    case 1:
                        type="羽毛球";
                        EventBus.getDefault().post(new PlaceEvent("羽毛球",city));
                        break;
                    case 2:
                        type="游泳馆";
                        EventBus.getDefault().post(new PlaceEvent("游泳馆",city));
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //viewPager
        mPager.setAdapter(new PagerAdapter(getChildFragmentManager()));
        mTab.setupWithViewPager(mPager);
        mTab.setTabMode(TabLayout.MODE_FIXED);
    }

    private void initLocation() {
        mLocationClient = new AMapLocationClient(getActivity());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String city1 = aMapLocation.getCity();
                        String district = aMapLocation.getDistrict();
                        Log.e("onLocationChanged", "city: " + city1);
                        Log.e("onLocationChanged", "district: " + district);
                        String location = StringUtils.extractLocation(city1, district);
                        city=location;
                        mCity.setText(city);
                        //Spinner设置
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, places);
                        mSpinner.setAdapter(adapter);
                        mLocationClient.stopLocation();
                    } else {
                        //定位失败
                        toast("定位失败");
                        //Spinner设置
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, places);
                        mSpinner.setAdapter(adapter);
                        mLocationClient.stopLocation();
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    @OnClick(R.id.mSelectCity)
    public void onClick() {
        startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),SELECT_CITY);
    }

    /**
     * 接受选择的城市
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==getActivity().RESULT_OK&&requestCode==SELECT_CITY){
            city = data.getStringExtra("city");
            mCity.setText(city);
            EventBus.getDefault().post(new PlaceEvent(type,city));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * viewpager的adapter
     */
    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return datas.get(position);
        }


        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

    }

}
