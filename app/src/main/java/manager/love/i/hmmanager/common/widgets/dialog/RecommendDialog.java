package manager.love.i.hmmanager.common.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import manager.love.i.hmmanager.R;
import manager.love.i.hmmanager.bean.ShopItem;
import manager.love.i.hmmanager.common.widgets.recycle.ThreeRecycleColorView;
import manager.love.i.hmmanager.common.widgets.recycle.ThreeRecycleView;
import manager.love.i.hmmanager.inter.Network;
import manager.love.i.hmmanager.ui.activity.ShopRecommendActivity;
import manager.love.i.hmmanager.utils.ActivityUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class RecommendDialog extends Dialog {
    private ThreeRecycleColorView color;
    private ThreeRecycleView size;

    private String clothes_id, stock_id, color_name, size_name, clothes_cz;


    private EditText recommendReason;
    private Map<String, List<ShopItem.BodyBean>> map = new HashMap<>();

    private String one;

    private Button mSure;

    public RecommendDialog(Context context, String id, String clo) {
        super(context, R.style.ActionSheetDialogStyle);
        clothes_id = id;
        clothes_cz = clo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_recommend);
        Window dialogWindow = getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        recommendReason.addTextChangedListener(watcher);
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason = recommendReason.getText().toString();
                Intent intent = new Intent(getContext(), ShopRecommendActivity.class);
                intent.putExtra("clothes_id", clothes_id);
                intent.putExtra("stock_id", stock_id);
                intent.putExtra("color_name", color_name);
                intent.putExtra("size_name", size_name);
                intent.putExtra("clothes_cz", clothes_cz);
                intent.putExtra("reason", reason);
                getContext().startActivity(intent);
                dismiss();
            }
        });
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String reason = s.toString();
            if (ActivityUtils.isEmpty(reason)) {
                if (ActivityUtils.isEmpty(color_name) && ActivityUtils.isEmpty(stock_id)) {
                    if (s.length() < 200) {
                        mSure.setEnabled(true);
                    } else {
                        Toast.makeText(getContext(), "亲，最多输入200字", Toast.LENGTH_SHORT).show();
                        mSure.setEnabled(false);
                    }
                } else {
                    mSure.setEnabled(false);
                }
            } else {
                mSure.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void initData() {
        Network.shopItemService().getClothesStockInfo(clothes_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(shopItem);
    }


    private void initView() {
        color = (ThreeRecycleColorView) this.findViewById(R.id.three_recycle_color);
        size = (ThreeRecycleView) this.findViewById(R.id.three_recycle_size);
        mSure = (Button) this.findViewById(R.id.dialog_recommend_sure);
        recommendReason = (EditText) this.findViewById(R.id.et_recommend_reason);
    }


    /*
       * 登录，获取用户信息
       * */
    Observer<ShopItem> shopItem = new Observer<ShopItem>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(ShopItem shopitem) {
            Set<String> colors = new HashSet<>();
            List<String> co = new ArrayList<>();

            for (ShopItem.BodyBean bean : shopitem.getBody()) {
                colors.add(bean.getColor().trim());
            }

            Iterator c = colors.iterator();
            int sum = 0;
            while (c.hasNext()) {
                List<ShopItem.BodyBean> list = new ArrayList<>();
                String s = (String) c.next();
                if (sum == 0)
                    one = s;
                co.add(s);
                for (ShopItem.BodyBean bean : shopitem.getBody()) {
                    if (s.equals(bean.getColor().trim())) {
                        list.add(bean);
                    }
                }

                map.put(s, list);
            }

            color.setFreshData(co, new ThreeRecycleColorView.SelectItem() {
                @Override
                public void selectItem(String s) {
                    size.reset(map.get(s));
                    color_name = s;
                   /* if (ActivityUtils.isEmpty(color_name) && ActivityUtils.isEmpty(stock_id)) {
                        mSure.setEnabled(true);
                    }*/
                }
            });
            size.setFreshData(map.get(one), new ThreeRecycleView.SelectItem() {
                @Override
                public void selectItem(ShopItem.BodyBean s) {
                    size_name = s.getSize();
                    stock_id = s.getStock_id();
                   /* if (ActivityUtils.isEmpty(color_name) && ActivityUtils.isEmpty(stock_id)) {
                        mSure.setEnabled(true);
                    }*/
                }
            });
        }
    };
}
