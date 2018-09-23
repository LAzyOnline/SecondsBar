package com.fx.secondbar.ui.order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btten.bttenlibrary.util.DensityUtil;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.OrderBean;
import com.fx.secondbar.http.HttpManager;
import com.fx.secondbar.ui.home.item.FragmentViewPagerBase;
import com.fx.secondbar.ui.purchase.AcPurchaseOrderDetail;

import java.util.List;

import rx.Subscriber;

/**
 * function:订单管理-订单项
 * author: frj
 * modify date: 2018/9/21
 */
public class FOrderItem extends FragmentViewPagerBase implements SwipeRefreshLayout.OnRefreshListener
{
    /**
     * 全部
     */
    public static final int TYPE_ALL = 0;
    /**
     * 待付款
     */
    public static final int TYPE_WAIT_PAY = 1;
    /**
     * 待履约
     */
    public static final int TYPE_PERFORMANCE = 2;
    /**
     * 履约中
     */
    public static final int TYPE_PERFORMANCING = 3;
    /**
     * 退款
     */
    public static final int TYPE_REFUND = 4;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AdOrder adapter;

    //当前页码
    private int currPage = -1;
    //当前页状态值
    private int status = 0;

    public static FOrderItem newInstance(int type)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY, type);
        FOrderItem fragment = new FOrderItem();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.f_order_item, container, false);
    }

    @Override
    public void onStarShow()
    {
        if (currPage == -1)
        {
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

    @Override
    protected void initView()
    {
        swipeRefreshLayout = findView(R.id.swipeRefreshLayout);
        recyclerView = findView(R.id.recyclerView);
    }

    @Override
    protected void initListener()
    {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData()
    {
        status = getArguments().getInt(KEY, TYPE_ALL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(DensityUtil.dip2px(getContext(), 15), true, false, true));
        adapter = new AdOrder();
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
//                Bundle bundle = new Bundle();
//                bundle.putParcelable(KEY, FOrderItem.this.adapter.getItem(position));
//                jump(AcPurchaseOrderDetail.class, bundle, false);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                getData(currPage + 1, status);
            }
        }, recyclerView);
        if (TYPE_ALL == status)
        {
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
        }
    }


    @Override
    public void onRefresh()
    {
        getData(PAGE_START, status);
    }

    /**
     * @param page
     * @param status
     */
    private void getData(final int page, int status)
    {
        HttpManager.getOrderList(page, PAGE_NUM, status, new Subscriber<List<OrderBean>>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
                e.printStackTrace();
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                ShowToast.showToast(HttpManager.checkLoadError(e));
            }

            @Override
            public void onNext(List<OrderBean> orderBeans)
            {
                if (isNetworkCanReturn())
                {
                    return;
                }
                if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }
                currPage = page;
                if (page == PAGE_START)
                {
                    adapter.setNewData(orderBeans);
                } else
                {
                    adapter.addData(orderBeans);
                }
                if (VerificationUtil.getSize(orderBeans) >= PAGE_NUM)
                {
                    adapter.loadMoreComplete();
                } else
                {
                    adapter.loadMoreEnd();
                }
            }
        });
    }
}
