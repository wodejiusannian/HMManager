package manager.love.i.hmmanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.base.BaseActivity;
import manager.love.i.hmmanager.bean.ShopList;
import manager.love.i.hmmanager.bean.ShopTag;
import manager.love.i.hmmanager.bean.StringInt;
import manager.love.i.hmmanager.common.widgets.popupwindow.SelectPopupWindow;
import manager.love.i.hmmanager.common.widgets.recycle.BaseRecycleAdapter;
import manager.love.i.hmmanager.common.widgets.recycle.BaseViewHolder;
import manager.love.i.hmmanager.common.widgets.recycle.FourRecycleView;
import manager.love.i.hmmanager.common.widgets.recycle.ThreeRecycleView;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.sql.SQLCLODao;
import manager.love.i.hmmanager.utils.ActivityUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static manager.love.i.hmmanager.R.id.shop_rb_default_filter;


public class ShopActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    private SelectPopupWindow mPopupWindow = null;

    private static final String TAG = "ShopActivity";

    @BindView(R.id.shop_dl_right_filter)
    DrawerLayout dlRightFilter;

    @BindView(R.id.rg_shop_top_select)
    RadioGroup top;

    @BindView(R.id.recycle_shop_content)
    RecyclerView recycle;

    @BindViews({R.id.three_recycle_sex, R.id.three_recycle_reason, R.id.three_recycle_sort, R.id.three_recycle_style, R.id.three_recycle_color})
    FourRecycleView[] threeReasons;

    @BindView(R.id.shop_bga_refresh)
    BGARefreshLayout refreshLayout;

    @BindViews({R.id.shop_rb_default_filter, R.id.rb_main_order, R.id.rb_main_me, R.id.shop_rb_right_scroll_filter})
    RadioButton[] radioBtns;

    private List<ShopTag.BodyBean> mData = new ArrayList<>();

    private List<ShopList.BodyBean> mShops = new ArrayList<>();

    private BaseRecycleAdapter<ShopList.BodyBean> adapter;

    private int page_now = 1, whatRb;

    private String sex, season, type, style, color, yiji_name, copyType, copyStyle, copyColor, copySeason, copySex, sort, typeStr, styleStr;

    private SQLCLODao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

    }

    @Override
    protected void setListener() {

    }

    @OnClick({shop_rb_default_filter, R.id.rb_main_me, R.id.rb_main_order, R.id.shop_btn_sure, R.id.shop_rb_right_scroll_filter, R.id.shop_btn_reset})
    public void setOnEvent(View v) {
        switch (v.getId()) {
            case shop_rb_default_filter:
                whatRb = 0;
                mData.clear();
                List<ShopTag.BodyBean> me3 = dao.selectCloTAG("5");
                ShopTag.BodyBean be3 = new ShopTag.BodyBean();
                be3.setErji_jc("默认");
                be3.setErji_name("默认");
                me3.add(0, be3);
                mPopupWindow.setNotifyData(me3);
                mPopupWindow.showAsDropDown(top);
                break;
            case R.id.rb_main_order:
                whatRb = 1;
                mData.clear();
                List<ShopTag.BodyBean> me1 = dao.selectSort();
                ShopTag.BodyBean be1 = new ShopTag.BodyBean();
                be1.setErji_jc("分类");
                be1.setErji_name("分类");
                be1.setType("");
                be1.setYiji_name("分类");
                me1.add(0, be1);
                mPopupWindow.setNotifyData(me1);
                mPopupWindow.showAsDropDown(top);
                break;
            case R.id.rb_main_me:
                whatRb = 2;
                mData.clear();
                List<ShopTag.BodyBean> me = dao.selectStyle(yiji_name);
                ShopTag.BodyBean be = new ShopTag.BodyBean();
                be.setErji_jc("款式");
                be.setErji_name("款式");
                me.add(0, be);
                if (me != null && me.size() > 0) {
                    mPopupWindow.setNotifyData(me);
                    mPopupWindow.showAsDropDown(top);
                } else {
                    Toast.makeText(this, "请先选择分类", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.shop_rb_right_scroll_filter:
                dlRightFilter.openDrawer(Gravity.RIGHT);
                break;
            case R.id.shop_btn_reset:
                copyColor = null;
                copyStyle = null;
                copySex = null;
                copySeason = null;
                copyType = null;
                threeReasons[0].reset(dao.selectStyle("性别"));
                threeReasons[1].reset(dao.selectStyle("季节"));
                threeReasons[2].reset(dao.selectSort());
                threeReasons[3].reset(dao.selectStyle(null));
                threeReasons[4].reset(dao.selectStyle("厂家衣服颜色"));
                break;
            case R.id.shop_btn_sure:
                dlRightFilter.closeDrawer(Gravity.RIGHT);
                page_now = 1;
                //if (ActivityUtils.isEmpty(copyColor))
                color = copyColor;
                //if (ActivityUtils.isEmpty(copyStyle))
                style = copyStyle;
                // if (ActivityUtils.isEmpty(copySex))
                sex = copySex;
                // if (ActivityUtils.isEmpty(copySeason))
                season = copySeason;
                //if (ActivityUtils.isEmpty(copyType))
                type = copyType;

                if (style == null) {
                    radioBtns[2].setText("款式");
                } else {
                    if (styleStr.length() > 3) {
                        styleStr = styleStr.substring(0, 2);
                    }
                    radioBtns[2].setText(styleStr);
                }
                if (type == null) {
                    radioBtns[1].setText("分类");
                } else {
                    if (typeStr.length() > 3) {
                        typeStr = typeStr.substring(0, 2);
                    }
                    radioBtns[1].setText(typeStr);
                }

                if (color != null || style != null || sex != null || season != null)
                    radioBtns[3].setChecked(true);
                else
                    radioBtns[3].setChecked(false);
                goGetShopList();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        if (mData == null)
            mData = new ArrayList<>();
        if (mShops == null)
            mShops = new ArrayList<>();
        dao = new SQLCLODao(this);

        initRefresh();

        initPopupWindow();

        initAdapter();

        initNet();

        initRecycleView();

    }


    /**
     * 初始化recycleview
     */
    private void initRecycleView() {
        GridLayoutManager manager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        recycle.setLayoutManager(manager);
        recycle.setAdapter(adapter);
    }

    /*
    * 默认数据
    * */
    private void initNet() {
        goGetShopList();
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        adapter = new BaseRecycleAdapter<ShopList.BodyBean>(this, mShops, R.layout.item_shop_activity_clo) {
            @Override
            public void bindData(BaseViewHolder holder, final ShopList.BodyBean bean, int position) {
                LinearLayout root = (LinearLayout) holder.getRootView();
                ImageView cloCover = holder.getView(R.id.item_shop_activity_iv_clos_cover);
                TextView name = holder.getView(R.id.item_shop_activity_tv_clos_name);
                TextView price = holder.getView(R.id.item_shop_activity_tv_clos_price);
                TextView count = holder.getView(R.id.item_shop_activity_tv_clos_count);
                String xl = bean.getClothes_xl() + "";
                name.setText(bean.getClothes_name());
                price.setText(bean.getClothes_price_yh());
                if (ActivityUtils.isEmpty(bean.getClothes_tag()))
                    count.setText(bean.getClothes_xl() + "");
                else
                    count.setText("0");
                Glide.with(ShopActivity.this).load(bean.getClothes_pic()).into(cloCover);
                root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ShopActivity.this, ShopItemDetailsActivity.class);
                        intent.putExtra("clothes_cz", bean.getMianliao_id());
                        intent.putExtra("id", bean.getClothes_id());
                        startActivity(intent);
                    }
                });
            }
        };
    }

    /*
    * 初始化popwindow
    * */
    private void initPopupWindow() {
        if (mPopupWindow == null) {
            mPopupWindow = new SelectPopupWindow(ShopActivity.this, new SelectPopupWindow.SelectCategory() {
                @Override
                public void selectCategory(ShopTag.BodyBean selectPosition) {
                    switch (whatRb) {
                        case 0:
                            sort = selectPosition.getErji_code();
                            break;
                        case 1:
                            type = selectPosition.getErji_code();
                            yiji_name = selectPosition.getYiji_name();
                            radioBtns[2].setText("款式");
                            style = null;
                            if (style != null || type != null)
                                radioBtns[3].setChecked(true);
                            else
                                radioBtns[3].setChecked(false);
                            threeReasons[2].resetSort(dao.selectSort(), yiji_name);
                            threeReasons[3].reset(dao.selectStyle(null));
                            break;
                        case 2:
                            style = selectPosition.getErji_code();
                            if (style != null || type != null)
                                radioBtns[3].setChecked(true);
                            else
                                radioBtns[3].setChecked(false);
                            List<ShopTag.BodyBean> me = dao.selectStyle(yiji_name);
                            if (me != null && me.size() > 0) {
                                threeReasons[3].resetSort(me, selectPosition.getErji_name());
                            } else {
                                Toast.makeText(ShopActivity.this, "请先选择分类", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        default:
                            break;
                    }
                    page_now = 1;
                    String er_jc = selectPosition.getErji_jc();
                    if (er_jc.length() > 3) {
                        radioBtns[whatRb].setText(er_jc.substring(0, 2));
                    } else {
                        radioBtns[whatRb].setText(er_jc);
                    }
                    goGetShopList();
                }
            });
        }
    }

    @Override
    protected void setData() {
        threeReasons[0].setFreshData(dao.selectStyle("性别"), new FourRecycleView.SelectItem() {
            @Override
            public void selectItem(ShopTag.BodyBean s) {
                String s0 = s.getErji_code();
                if (ActivityUtils.isEmpty(s0)) {
                    copySex = s0;
                }
            }
        });
        threeReasons[1].setFreshData(dao.selectStyle("季节"), new FourRecycleView.SelectItem() {
            @Override
            public void selectItem(ShopTag.BodyBean s) {
                String s1 = s.getErji_code();
                if (ActivityUtils.isEmpty(s1)) {
                    copySeason = s1;
                }
            }
        });
        threeReasons[2].setFreshData(dao.selectSort(), new FourRecycleView.SelectItem() {
            @Override
            public void selectItem(ShopTag.BodyBean s) {
                String ej_name = s.getErji_name();
                copyType = s.getErji_code();
                typeStr = s.getErji_name();
                threeReasons[3].reset(dao.selectStyle(null));
                List<ShopTag.BodyBean> me = dao.selectStyle(ej_name);
                threeReasons[3].setFreshData(me, new FourRecycleView.SelectItem() {
                    @Override
                    public void selectItem(ShopTag.BodyBean s) {
                        String s3 = s.getErji_code();
                        if (ActivityUtils.isEmpty(s3)) {
                            copyStyle = s3;
                            styleStr = s.getErji_name();
                        }
                    }
                });
            }
        });
        threeReasons[4].setFreshData(dao.selectStyle("厂家衣服颜色"), new FourRecycleView.SelectItem() {

            @Override
            public void selectItem(ShopTag.BodyBean s) {
                String s4 = s.getErji_code();
                if (ActivityUtils.isEmpty(s4)) {
                    copyColor = s4;
                }
            }
        });
    }


    /*
    * 初始化上拉加载，下拉刷新
    * */
    private void initRefresh() {
        refreshLayout.setDelegate(this);
        BGANormalRefreshViewHolder holder = new BGANormalRefreshViewHolder(this, true);
        refreshLayout.setRefreshViewHolder(holder);
    }

    /*
    * 刷新
    * */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page_now = 1;
        goGetShopList();
        handler.sendEmptyMessageDelayed(0, 5000);
    }

    /*
    * 加载
    * */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        handler.sendEmptyMessageDelayed(1, 5000);
        goGetShopList();
        return true;
    }

    /*
     * 如果5s获取不到数据，刷新和加载自动消失
     * */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (refreshLayout != null)
                        refreshLayout.endRefreshing();
                    break;
                case 1:
                    if (refreshLayout != null && refreshLayout.isLoadingMore())
                        refreshLayout.endLoadingMore();
                    break;
                case 2:
                    if (refreshLayout != null)
                        refreshLayout.endLoadingMore();
                    break;
                default:
                    break;

            }
        }
    };

    /**
     * 这是获取商品数据的方法
     *
     * @param page_now 页数
     * @param sex      性别
     * @param season   季节
     * @param type     种类
     * @param style    样式
     * @param color    颜色
     * @param type_ck  衣服查看类型  1-推荐服饰（无上门整理套装）  2-商城购买（无小蓝盒x10/组）
     * @param sort     排序方式 价格-1:从底到高，2:从高到底    销量-3:从底到高，4:从高到底
     */
    private void goGetShopList(/*int page_now, String sex, String season, String type, String style, String color, int type_ck, int sort*/) {
        Network.shopService().getShopList(page_now, sex, season, type, style, color, 1, sort)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shopList);
    }

    /*
     * 工作室的残忍拒绝订单处理
     * */
    Observer<ShopList> shopList = new Observer<ShopList>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(ShopList shopList) {
            List<ShopList.BodyBean> beans = shopList.getBody();
            if (beans != null) {
                if (page_now == 1)
                    mShops.clear();

                if (refreshLayout != null) {
                    if (refreshLayout.isLoadingMore())
                        refreshLayout.endLoadingMore();
                    else
                        refreshLayout.endRefreshing();
                }

                page_now++;
                mShops.addAll(beans);
                adapter.notifyDataSetChanged();
            }
        }
    };

    public void back(View view) {
        finish();
    }
}
