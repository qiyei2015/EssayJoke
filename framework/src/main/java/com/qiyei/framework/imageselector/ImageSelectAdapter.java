package com.qiyei.framework.imageselector;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.qiyei.framework.R;
import com.qiyei.sdk.util.FileUtil;
import com.qiyei.sdk.util.ToastUtil;
import com.qiyei.sdk.view.xrecycler.IMultiTypeLayout;
import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter;
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/8.
 * Version: 1.0
 * Description:
 */
public class ImageSelectAdapter extends BaseRecyclerAdapter<ImageEntity> {


    private Context mContext;
    private ArrayList<String> mSelectImages;
    private int mMaxCount;
    public final static int REQUEST_CAMERA = 0x0045;
    private int mMode;

    private UpdateSelectListener mListener;

    public void setOnUpdateSelectListener(UpdateSelectListener listener) {
        this.mListener = listener;
    }

    public ImageSelectAdapter(Context context, ArrayList<String> selectImages, int maxCount, int mode) {
        super(context, new ArrayList<ImageEntity>(), R.layout.media_chooser_item);
        this.mContext = context;
        mSelectImages = selectImages;
        this.mMaxCount = maxCount;
        this.mMode = mode;
    }


    public ImageSelectAdapter(Context context, List<ImageEntity> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public ImageSelectAdapter(Context context, List<ImageEntity> datas, IMultiTypeLayout typeLayout) {
        super(context, datas, typeLayout);
    }

    @Override
    public void convert(BaseViewHolder holder, final ImageEntity item, int position) {
        if (item == null) {
            holder.setVisibility(View.INVISIBLE, R.id.image, R.id.mask, R.id.media_selected_indicator);
            holder.setVisibility(View.VISIBLE, R.id.camera_tv);
            holder.setOnClickListener(R.id.image,new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCamera();
                }
            });
        } else {
            holder.setVisibility(View.VISIBLE, R.id.image, R.id.media_selected_indicator);
            holder.setVisibility(View.INVISIBLE, R.id.camera_tv);
            // 显示图片
            ImageView imageView = holder.getView(R.id.image);

            Glide.with(mContext)
                    .load(item.path)
                    .centerCrop()
                    .into(imageView);

            ImageView selectedIndicatorIv = holder.getView(R.id.media_selected_indicator);
            selectedIndicatorIv.setSelected(mSelectImages.contains(item.path));

            // 设置选中效果
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectImages.contains(item.path)) {
                        mSelectImages.remove(item.path);
                    } else {
                        // 判断是否到达最大
                        if (mMaxCount == mSelectImages.size()) {
                            ToastUtil.showLongToast("最多只能选择" +
                                    mMaxCount + "张图片");
                            return;
                        }
                        mSelectImages.add(item.path);
                    }
                    if (mListener != null) {
                        mListener.selector();
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * 打开相机拍照
     */
    private void openCamera() {
        try {
            File tmpFile = FileUtil.createTmpFile(mContext);
            if (mListener != null) {
                mListener.openCamera(tmpFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "相机打开失败", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 设置数据
     */
    public void setDatas(List<ImageEntity> images, boolean showCamera) {
        mDatas.clear();
        if (showCamera) {
            mDatas.add(null);
        }
        mDatas.addAll(images);
        notifyDataSetChanged();
    }

    public interface UpdateSelectListener {
        public void selector();

        public void openCamera(File file);
    }

}
