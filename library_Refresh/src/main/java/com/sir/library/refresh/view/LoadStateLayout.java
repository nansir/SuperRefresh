package com.sir.library.refresh.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sir.library.refresh.R;


/**
 * 加载数据状态
 * Created by zhuyinan on 2017/3/29.
 * Contact by 445181052@qq.com
 */
public class LoadStateLayout extends FrameLayout {

    private View contentView;

    private View emptyView;
    private View emptyContentView;

    private View errorView;
    private View errorContentView;

    private View progressView;
    private View progressContentView;

    private TextView emptyTextView;
    private TextView errorTextView;
    private TextView progressTextView;

    private ImageView errorImageView;
    private ImageView emptyImageView;
    private ProgressBar progressBar;

    private View currentShowingView;
    private boolean shouldPlayAnim = true;

    private Animation hideAnimation;
    private Animation showAnimation;

    public LoadStateLayout(Context context) {
        this(context, null);
    }

    public LoadStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        parseAttrs(context, attrs);

        emptyView.setVisibility(View.GONE);

        errorView.setVisibility(View.GONE);

        progressView.setVisibility(View.GONE);

        currentShowingView = contentView;
    }

    private void parseAttrs(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadStateLayout, 0, 0);

        int progressViewId;
        Drawable errorDrawable;
        Drawable emptyDrawable;

        try {
            errorDrawable = a.getDrawable(R.styleable.LoadStateLayout_errorDrawable);
            emptyDrawable = a.getDrawable(R.styleable.LoadStateLayout_emptyDrawable);
            progressViewId = a.getResourceId(R.styleable.LoadStateLayout_progressView, -1);
        } finally {
            a.recycle();
        }

        if (progressViewId != -1) {
            progressView = inflater.inflate(progressViewId, this, false);
        } else {
            progressView = inflater.inflate(R.layout.view_loading_progress, this, false);
            progressBar = (ProgressBar) progressView.findViewById(R.id.progress_wheel);
            progressTextView = (TextView) progressView.findViewById(R.id.progress_text_view);
            progressContentView = progressView.findViewById(R.id.progress_content);
        }

        addView(progressView);

        errorView = inflater.inflate(R.layout.view_loading_error, this, false);
        errorContentView = errorView.findViewById(R.id.error_content);
        errorTextView = (TextView) errorView.findViewById(R.id.error_text_view);
        errorImageView = (ImageView) errorView.findViewById(R.id.error_image_view);

        if (errorDrawable != null) {
            errorImageView.setImageDrawable(errorDrawable);
        } else {
            errorImageView.setImageResource(R.mipmap.ic_loading_failure);
        }
        addView(errorView);

        emptyView = inflater.inflate(R.layout.view_loading_empty, this, false);
        emptyContentView = emptyView.findViewById(R.id.empty_content);
        emptyTextView = (TextView) emptyView.findViewById(R.id.empty_text_view);
        emptyImageView = (ImageView) emptyView.findViewById(R.id.empty_image_view);
        if (emptyDrawable != null) {
            emptyImageView.setImageDrawable(emptyDrawable);
        } else {
            emptyImageView.setImageResource(R.mipmap.ic_loading_empty);
        }
        addView(emptyView);
    }

    /**
     * addView
     */
    @Override
    public void addView(View child) {
        checkIsContentView(child);
        super.addView(child);
    }

    private void checkIsContentView(View view) {
        if (contentView == null && view != errorView && view != progressView && view != emptyView) {
            contentView = view;
            currentShowingView = contentView;
        }
    }

    @Override
    public void addView(View child, int index) {
        checkIsContentView(child);
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int width, int height) {
        checkIsContentView(child);
        super.addView(child, width, height);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        super.addView(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        checkIsContentView(child);
        return super.addViewInLayout(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        checkIsContentView(child);
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }

    public LoadStateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ImageView getErrorImageView() {
        return errorImageView;
    }

    public ImageView getEmptyImageView() {
        return emptyImageView;
    }

    public boolean isShouldPlayAnim() {
        return shouldPlayAnim;
    }

    public void setShouldPlayAnim(boolean shouldPlayAnim) {
        this.shouldPlayAnim = shouldPlayAnim;
    }

    public Animation getShowAnimation() {
        return showAnimation;
    }

    public void setShowAnimation(Animation showAnimation) {
        this.showAnimation = showAnimation;
    }

    public Animation getHideAnimation() {
        return hideAnimation;
    }

    public void setHideAnimation(Animation hideAnimation) {
        this.hideAnimation = hideAnimation;
    }

    public void setInfoContentViewMargin(int left, int top, int right, int bottom) {
        setEmptyContentViewMargin(left, top, right, bottom);
        setErrorContentViewMargin(left, top, right, bottom);
        setProgressContentViewMargin(left, top, right, bottom);
    }

    public void setEmptyContentViewMargin(int left, int top, int right, int bottom) {
        ((LinearLayout.LayoutParams) emptyImageView.getLayoutParams()).setMargins(left, top, right, bottom);
    }

    public void setErrorContentViewMargin(int left, int top, int right, int bottom) {
        ((LinearLayout.LayoutParams) errorImageView.getLayoutParams()).setMargins(left, top, right, bottom);
    }

    public void setProgressContentViewMargin(int left, int top, int right, int bottom) {
        if (progressBar != null)
            ((LinearLayout.LayoutParams) progressBar.getLayoutParams()).setMargins(left, top, right, bottom);
    }

    public void showContentView() {
        switchWithAnimation(contentView);
    }

    private void switchWithAnimation(final View toBeShown) {
        final View toBeHided = currentShowingView;
        if (toBeHided == toBeShown)
            return;
        if (shouldPlayAnim) {
            if (toBeHided != null) {
                if (hideAnimation != null) {
                    hideAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            toBeHided.setVisibility(GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    hideAnimation.setFillAfter(false);
                    toBeHided.startAnimation(hideAnimation);
                } else
                    toBeHided.setVisibility(GONE);
            }
            if (toBeShown != null) {
                if (toBeShown.getVisibility() != VISIBLE)
                    toBeShown.setVisibility(VISIBLE);
                currentShowingView = toBeShown;
                if (showAnimation != null) {
                    showAnimation.setFillAfter(false);
                    toBeShown.startAnimation(showAnimation);
                }
            }
        } else {
            if (toBeHided != null) {
                toBeHided.setVisibility(GONE);
            }
            if (toBeShown != null) {
                currentShowingView = toBeShown;
                toBeShown.setVisibility(VISIBLE);
            }
        }

    }

    public void showEmptyView() {
        showEmptyView(null);
    }

    public void showEmptyView(String msg) {
        onHideContentView();
        if (!TextUtils.isEmpty(msg)) {
            emptyTextView.setText(msg);
        }
        switchWithAnimation(emptyView);
    }

    protected void onHideContentView() {
        //Override me
    }

    public void showErrorView() {
        showErrorView(null);
    }

    public void showErrorView(String msg) {
        onHideContentView();
        if (msg != null) {
            errorTextView.setText(msg);
        }
        switchWithAnimation(errorView);
    }

    public void showProgressView() {
        showProgressView(null);
    }

    public void showProgressView(String msg) {
        onHideContentView();
        if (msg != null) {
            progressTextView.setText(msg);
        }
        switchWithAnimation(progressView);
    }

    public void setErrorAction(final OnClickListener onErrorButtonClickListener) {
        errorView.setOnClickListener(onErrorButtonClickListener);
    }

    public void setEmptyAction(final OnClickListener onEmptyButtonClickListener) {
        emptyView.setOnClickListener(onEmptyButtonClickListener);
    }

    public void setErrorAndEmptyAction(final OnClickListener errorAndEmptyAction) {
        errorView.setOnClickListener(errorAndEmptyAction);
        emptyView.setOnClickListener(errorAndEmptyAction);
    }
}
