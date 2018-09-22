package com.fx.secondbar.ui.person;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.btten.bttenlibrary.application.BtApplication;
import com.btten.bttenlibrary.base.ActivitySupport;
import com.btten.bttenlibrary.glide.GlideApp;
import com.btten.bttenlibrary.ui.img.ConstantValue;
import com.btten.bttenlibrary.ui.img.MultiImageSelectorActivity;
import com.btten.bttenlibrary.util.ShowToast;
import com.btten.bttenlibrary.util.VerificationUtil;
import com.bumptech.glide.Glide;
import com.fx.secondbar.R;
import com.fx.secondbar.ui.person.aboutus.AcAboutUs;
import com.fx.secondbar.util.Constants;
import com.fx.secondbar.util.GlideCacheUtil;
import com.joooonho.SelectableRoundedImageView;

import java.io.File;
import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * function:账号设置
 * author: frj
 * modify date: 2018/9/21
 */
public class AcAccountSet extends ActivitySupport
{

    private static final int REQUEST_CODE_HEAD_IMG = 10;

    private SelectableRoundedImageView img_avatar;
    private TextView tv_nickname;
    private TextView tv_level;
    private TextView tv_phone;
    private TextView tv_pay_pwd;
    private TextView tv_cache_size;
    private TextView tv_aboutus_tips;
    private Button btn_logout;

    private Subscription subscriptionCacheSize;
    private Subscription subscriptionClear;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.ac_account_set;
    }

    @Override
    protected void initView()
    {
        img_avatar = findView(R.id.img_avatar);
        tv_nickname = findView(R.id.tv_nickname);
        tv_level = findView(R.id.tv_level);
        tv_phone = findView(R.id.tv_phone);
        tv_pay_pwd = findView(R.id.tv_pay_pwd);
        tv_cache_size = findView(R.id.tv_cache_size);
        tv_aboutus_tips = findView(R.id.tv_aboutus_tips);
        btn_logout = findView(R.id.btn_logout);
        findView(R.id.ib_back).setOnClickListener(this);
        Toolbar toolbar = findView(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initListener()
    {
        img_avatar.setOnClickListener(this);
        tv_nickname.setOnClickListener(this);
        tv_level.setOnClickListener(this);
        tv_phone.setOnClickListener(this);
        tv_pay_pwd.setOnClickListener(this);
        tv_cache_size.setOnClickListener(this);
        tv_aboutus_tips.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        calCacheSize();
    }

    /**
     * 计算缓存大小
     */
    private void calCacheSize()
    {
        subscriptionCacheSize = Observable.create(new Observable.OnSubscribe<Long>()
        {
            @Override
            public void call(Subscriber<? super Long> subscriber)
            {
                File file = new File(getCacheDir() + File.separator + Constants.ROOT_DIR);
                long a = 0;
                if (!file.exists())
                {
                    try
                    {
                        a = GlideCacheUtil.getFolderSize(Glide.getPhotoCacheDir(BtApplication.getApplication()));
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                try
                {

                    long b = GlideCacheUtil.getFolderSize(file);
                    long all = a + b;
                    subscriber.onNext(all);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>()
        {
            @Override
            public void call(Long aLong)
            {
                if (aLong != null && tv_cache_size != null)
                {
                    tv_cache_size.setText(GlideCacheUtil.getFormatSize(aLong));
                }
            }
        });
    }

    /**
     * 清除缓存
     */
    private void clearCache()
    {
        subscriptionClear = Observable.create(new Observable.OnSubscribe<Void>()
        {
            @Override
            public void call(Subscriber<? super Void> subscriber)
            {
                GlideCacheUtil.getInstance().clearCacheDiskSelf();
                GlideCacheUtil.getInstance().cleanCatchDisk();
                GlideCacheUtil.deleteFolderFile(getCacheDir() + File.separator + Constants.ROOT_DIR, false);
                subscriber.onNext(null);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Void>()
        {
            @Override
            public void call(Void aVoid)
            {
                calCacheSize();
                ShowToast.showToast("清除成功");
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.img_avatar:
                Bundle bundle = new Bundle();
                bundle.putInt(ConstantValue.EXTRA_SELECT_MODE, ConstantValue.MODE_SINGLE);
                jump(MultiImageSelectorActivity.class, bundle, REQUEST_CODE_HEAD_IMG);
                break;
            case R.id.tv_nickname:
                break;
            case R.id.tv_level:
                break;
            case R.id.tv_phone:
                jump(AcBindPhone.class);
                break;
            case R.id.tv_pay_pwd:
                jump(AcBindPayPwd.class);
                break;
            case R.id.tv_cache_size:
                clearCache();
                break;
            case R.id.tv_aboutus_tips:
                jump(AcAboutUs.class);
                break;
            case R.id.btn_logout:
                break;
            case R.id.ib_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_HEAD_IMG == requestCode)
        {
            if (RESULT_OK == resultCode)
            {
                if (data == null)
                {
                    return;
                }
                ArrayList<String> list = data.getStringArrayListExtra(ConstantValue.EXTRA_RESULT);
                if (VerificationUtil.noEmpty(list))
                {
                    GlideApp.with(this).asBitmap().load(new File(list.get(0))).centerCrop().into(img_avatar);
                }
            }
        }
    }

    @Override
    protected String[] getPermissionArrays()
    {
        return new String[0];
    }

    @Override
    protected int[] getPermissionInfoTips()
    {
        return new int[0];
    }

    @Override
    protected void onDestroy()
    {
        if (subscriptionClear != null)
        {
            subscriptionClear.unsubscribe();
        }
        if (subscriptionCacheSize != null)
        {
            subscriptionCacheSize.unsubscribe();
        }
        super.onDestroy();
    }
}