package com.skyfz.vipplayer.loading;

import java.lang.ref.WeakReference;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.skyfz.vipplayer.R;

/**
 * Created by guoni on 2017/11/4.
 */

public class ZLoadingDialog {
    private final WeakReference<Context> mContext;
    private final int                    mThemeResId;
    private Z_TYPE mLoadingBuilderType;
    private       int                    mLoadingBuilderColor;
    private       String                 mHintText;
    private float   mHintTextSize           = -1;
    private int     mHintTextColor          = -1;
    private boolean mCancelable             = true;
    private boolean mCanceledOnTouchOutside = true;
    private Dialog mZLoadingDialog;
    private TextView zCustomTextView;

    public ZLoadingDialog(@NonNull Context context)
    {
        this(context, R.style.alert_dialog);
    }

    public ZLoadingDialog(@NonNull Context context, int themeResId)
    {
        this.mContext = new WeakReference<>(context);
        this.mThemeResId = themeResId;
    }

    public ZLoadingDialog setLoadingBuilder(@NonNull Z_TYPE type)
    {
        this.mLoadingBuilderType = type;
        return this;
    }

    public ZLoadingDialog setLoadingColor(int color)
    {
        this.mLoadingBuilderColor = color;
        return this;
    }

    public ZLoadingDialog setHintText(String text)
    {
        this.mHintText = text;
        return this;
    }

    /**
     * 设置了大小后，字就不会有动画了。
     *
     * @param size 大小
     * @return
     */
    public ZLoadingDialog setHintTextSize(float size)
    {
        this.mHintTextSize = size;
        return this;
    }

    public ZLoadingDialog setHintTextColor(int color)
    {
        this.mHintTextColor = color;
        return this;
    }

    public ZLoadingDialog setCancelable(boolean cancelable)
    {
        mCancelable = cancelable;
        return this;
    }

    public ZLoadingDialog setCanceledOnTouchOutside(boolean canceledOnTouchOutside)
    {
        mCanceledOnTouchOutside = canceledOnTouchOutside;
        return this;
    }

    private
    @NonNull
    View createContentView()
    {
        if (isContextNotExist())
        {
            throw new RuntimeException("Context is null...");
        }
        return View.inflate(this.mContext.get(), R.layout.z_loading_dialog, null);
    }

    public Dialog create()
    {
        if (isContextNotExist())
        {
            throw new RuntimeException("Context is null...");
        }
        if (mZLoadingDialog != null)
        {
            cancel();
        }
        mZLoadingDialog = new Dialog(this.mContext.get(), this.mThemeResId){
            @Override
            public void onBackPressed() {
                //do your stuff
            }
        };
        View contentView = createContentView();
        ZLoadingView zLoadingView = (ZLoadingView) contentView.findViewById(R.id.z_loading_view);
        zCustomTextView = (TextView) contentView.findViewById(R.id.z_custom_text_view);

        zCustomTextView.setVisibility(View.VISIBLE);
        zCustomTextView.setText(mHintText);
        zCustomTextView.setTextSize(this.mHintTextSize);
        zCustomTextView.setTextColor(this.mHintTextColor == -1 ? this.mLoadingBuilderColor : this.mHintTextColor);

        zLoadingView.setLoadingBuilder(this.mLoadingBuilderType);
        zLoadingView.setColorFilter(this.mLoadingBuilderColor);
        mZLoadingDialog.setContentView(contentView);
        mZLoadingDialog.setCancelable(this.mCancelable);
        mZLoadingDialog.setCanceledOnTouchOutside(this.mCanceledOnTouchOutside);
        return mZLoadingDialog;
    }

    public void show()
    {
        if (mZLoadingDialog != null)
        {
            mZLoadingDialog.show();
        }
        else
        {
            final Dialog zLoadingDialog = create();
            zLoadingDialog.show();
        }
    }

    public void cancel()
    {
        if (mZLoadingDialog != null)
        {
            mZLoadingDialog.cancel();
        }
        mZLoadingDialog = null;
    }

    public void dismiss()
    {
        if (mZLoadingDialog != null)
        {
            mZLoadingDialog.dismiss();
        }
        mZLoadingDialog = null;
    }

    public void setText(String text)
    {
        if (zCustomTextView != null)
        {
            zCustomTextView.setText(text);
        }
    }

    private boolean isContextNotExist()
    {
        Context context = this.mContext.get();
        return context == null;
    }
}
