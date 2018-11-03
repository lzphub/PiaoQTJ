package cn.dankal.home.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.dankal.address.R;
import cn.dankal.basiclib.base.fragment.BaseFragment;
import cn.dankal.basiclib.base.fragment.BaseRecyclerViewFragment;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewAdapter;
import cn.dankal.basiclib.base.recyclerview.BaseRecyclerViewPresenter;
import cn.dankal.basiclib.common.OnFinishLoadDataListener;
import cn.dankal.basiclib.protocol.HomeProtocol;

public class Home_fragment extends BaseFragment {
    private android.widget.ImageView logoImg;
    private android.widget.ImageView searchImg;
    private android.widget.LinearLayout releaseLl;
    private android.support.v4.view.ViewPager viewPager;
    private android.widget.TextView newDemand;
    private android.support.v7.widget.RecyclerView demandList;
    private android.widget.TextView loadMore;

    @Override
    protected int getLayoutId() {
        return R.layout.layout;
    }

    @Override
    public void initComponents() {

    }

    @Override
    protected void initComponents(View view) {
        initView(view);
        SpannableString spannableString=new SpannableString("最新需求");
        ForegroundColorSpan colorSpan=new ForegroundColorSpan(getResources().getColor(R.color.home_green));
        spannableString.setSpan(colorSpan,0,2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        newDemand.setText(spannableString);

        loadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ARouter.getInstance().build(HomeProtocol.HOMEDEMANDLIST).navigation();
                ARouter.getInstance().build(HomeProtocol.DEMANDDETA).navigation();
            }
        });
        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(HomeProtocol.HOMESEARCH).navigation();
            }
        });
        releaseLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(HomeProtocol.HOMERELEASE).navigation();
            }
        });
    }

    private void initView(View view) {
        logoImg = (ImageView) view.findViewById(R.id.logo_img);
        searchImg = (ImageView) view.findViewById(R.id.search_img);
        releaseLl = (LinearLayout) view.findViewById(R.id.release_ll);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        newDemand = (TextView) view.findViewById(R.id.new_demand);
        demandList = (RecyclerView) view.findViewById(R.id.demand_list);
        loadMore = (TextView) view.findViewById(R.id.load_more);
    }

}
