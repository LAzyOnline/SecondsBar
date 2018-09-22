package com.fx.secondbar.ui.home.item.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.util.SpaceDecorationUtil;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.fx.secondbar.R;
import com.fx.secondbar.bean.CommodityBean;
import com.fx.secondbar.bean.InfomationBean;
import com.fx.secondbar.bean.PersonBean;
import com.fx.secondbar.util.GlideLoad;
import com.joooonho.SelectableRoundedImageView;

import java.util.List;

/**
 * function:资讯列表适配器
 * author: frj
 * modify date: 2018/9/7
 */
public class AdInfomation extends BaseMultiItemQuickAdapter<AdInfomation.InfomationEntity, BaseViewHolder>
{
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public AdInfomation()
    {
        super(null);
        addItemType(InfomationEntity.TYPE_SINGLE_IMG, R.layout.ad_infomation_single_img);
        addItemType(InfomationEntity.TYPE_MULTI_IMG, R.layout.ad_infomation_multi_img);
        addItemType(InfomationEntity.TYPE_COMMODITY, R.layout.ad_infomation_commodity);
        addItemType(InfomationEntity.TYPE_PERSON, R.layout.ad_infomation_person_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdInfomation.InfomationEntity item)
    {
        if (InfomationEntity.TYPE_COMMODITY == item.getItemType())
        {
            handlerCommodity(helper, item);
        } else if (InfomationEntity.TYPE_MULTI_IMG == item.getItemType())
        {
            handlerMultiImg(helper, item);
        } else if (InfomationEntity.TYPE_PERSON == item.getItemType())
        {
            handlerPerson(helper, item);
        } else if (InfomationEntity.TYPE_SINGLE_IMG == item.getItemType())
        {
            handlerSingleImg(helper, item);
        }
    }

    /**
     * 处理商品数据显示
     *
     * @param helper
     * @param item
     */
    private void handlerCommodity(BaseViewHolder helper, AdInfomation.InfomationEntity item)
    {
        TextView tv_tips = helper.getView(R.id.tv_tips);
        ImageView img = helper.getView(R.id.img);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_place = helper.getView(R.id.tv_place);
        if (item.commodityBean != null)
        {
            GlideLoad.load(img, item.commodityBean.getImage(), true);
            VerificationUtil.setViewValue(tv_title, item.commodityBean.getName());
            VerificationUtil.setViewValue(tv_price, item.commodityBean.getPrice());
            VerificationUtil.setViewValue(tv_time, item.commodityBean.getTimelength());
            VerificationUtil.setViewValue(tv_place, item.commodityBean.getAddress());
        } else
        {
            GlideApp.with(img).asBitmap().load(0).centerCrop().into(img);
            VerificationUtil.setViewValue(tv_title, "");
            VerificationUtil.setViewValue(tv_price, "");
            VerificationUtil.setViewValue(tv_time, "");
            VerificationUtil.setViewValue(tv_place, "");
        }

    }

    /**
     * 处理单条图片的资讯新闻数据显示
     *
     * @param helper
     * @param item
     */
    private void handlerSingleImg(BaseViewHolder helper, AdInfomation.InfomationEntity item)
    {
        SelectableRoundedImageView img = helper.getView(R.id.img);
        TextView tv_from = helper.getView(R.id.tv_from);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_count = helper.getView(R.id.tv_count);
        if (item.getInfomationBean() != null)
        {
            GlideLoad.load(img, item.getInfomationBean().getPicture(), true);
            VerificationUtil.setViewValue(tv_from, item.getInfomationBean().getShare_COPY());
            VerificationUtil.setViewValue(tv_title, item.getInfomationBean().getTitle());
            VerificationUtil.setViewValue(tv_count, item.getInfomationBean().getShare_TOTAL());
        } else
        {
            GlideLoad.load(img, "", true);
            VerificationUtil.setViewValue(tv_from, "");
            VerificationUtil.setViewValue(tv_title, "");
            VerificationUtil.setViewValue(tv_count, "");
        }
    }

    /**
     * 处理多条图片的资讯新闻数据显示
     *
     * @param helper
     * @param item
     */
    private void handlerMultiImg(BaseViewHolder helper, AdInfomation.InfomationEntity item)
    {
        SelectableRoundedImageView img1 = helper.getView(R.id.img1);
        SelectableRoundedImageView img2 = helper.getView(R.id.img2);
        SelectableRoundedImageView img3 = helper.getView(R.id.img3);
        TextView tv_from = helper.getView(R.id.tv_from);
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_count = helper.getView(R.id.tv_count);
        if (item.getInfomationBean() != null)
        {
            List<String> list = item.getInfomationBean().getPictures();
            if (list != null && list.size() >= 3)
            {
                GlideLoad.load(img1, list.get(0), true);
                GlideLoad.load(img2, list.get(1), true);
                GlideLoad.load(img3, list.get(2), true);
            } else
            {
                GlideLoad.load(img1, "", true);
                GlideLoad.load(img2, "", true);
                GlideLoad.load(img3, "", true);
            }
            VerificationUtil.setViewValue(tv_from, item.getInfomationBean().getShare_COPY());
            VerificationUtil.setViewValue(tv_title, item.getInfomationBean().getTitle());
            VerificationUtil.setViewValue(tv_count, item.getInfomationBean().getShare_TOTAL());
        } else
        {
            GlideLoad.load(img1, "", true);
            GlideLoad.load(img2, "", true);
            GlideLoad.load(img3, "", true);
            VerificationUtil.setViewValue(tv_from, "");
            VerificationUtil.setViewValue(tv_title, "");
            VerificationUtil.setViewValue(tv_count, "");
        }
    }

    /**
     * 处理名人显示
     *
     * @param helper
     * @param item
     */
    private void handlerPerson(BaseViewHolder helper, AdInfomation.InfomationEntity item)
    {
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), RecyclerView.HORIZONTAL, false));
        if (recyclerView.getItemDecorationCount() == 0)
        {
            recyclerView.addItemDecoration(SpaceDecorationUtil.getDecoration(recyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.home_infomation_item_sing_plr), false, false, true));
        }
        AdPersonItem adapter = new AdPersonItem();
        adapter.setNewData(item.getPersonBeans());
        adapter.bindToRecyclerView(recyclerView);
    }


    /**
     * 资讯的实体
     */
    public static class InfomationEntity implements MultiItemEntity
    {

        /**
         * 单个图片展示的资讯类型
         */
        public static final int TYPE_SINGLE_IMG = 1;

        /**
         * 多个图片展示的资讯类型
         */
        public static final int TYPE_MULTI_IMG = 2;

        /**
         * 标题
         */
        public static final int TYPE_TITLE = 3;

        /**
         * 推荐名人
         */
        public static final int TYPE_PERSON = 4;

        /**
         * 推荐商品
         */
        public static final int TYPE_COMMODITY = 5;

        /**
         * 项类型
         */
        private int itemType;
        /**
         * 推荐名人列表
         */
        private List<PersonBean> personBeans;
        /**
         * 资讯信息
         */
        private InfomationBean infomationBean;

        private CommodityBean commodityBean;

        public InfomationEntity(int type)
        {
            itemType = type;
        }

        public InfomationEntity(int type, InfomationBean bean)
        {
            this(type);
            infomationBean = bean;
        }

        public InfomationEntity(int type, CommodityBean bean)
        {
            this(type);
            commodityBean = bean;
        }

        public InfomationEntity(int type, List<PersonBean> beans)
        {
            this(type);
            personBeans = beans;
        }

        @Override
        public int getItemType()
        {
            return itemType;
        }

        public List<PersonBean> getPersonBeans()
        {
            return personBeans;
        }

        public void setPersonBeans(List<PersonBean> personBeans)
        {
            this.personBeans = personBeans;
        }

        public InfomationBean getInfomationBean()
        {
            return infomationBean;
        }

        public void setInfomationBean(InfomationBean infomationBean)
        {
            this.infomationBean = infomationBean;
        }

        public CommodityBean getCommodityBean()
        {
            return commodityBean;
        }

        public void setCommodityBean(CommodityBean commodityBean)
        {
            this.commodityBean = commodityBean;
        }
    }
}
