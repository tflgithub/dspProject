package com.luzuzu.main.widget.custemerTablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.luzuzu.main.R;
import java.util.ArrayList;

/**
 * Created by fula on 2019/5/18.
 *
 * 滑动切换TabLayout,tab的文字动画变大变小
 */

public class SlidingScaleTabLayout extends HorizontalScrollView implements ViewPager.OnPageChangeListener{

    private static final int TOP = 0;
    private static final int BOTTOM = 1;
    private static final int CENTER = 2;

    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout mTabsContainer;
    private int mCurrentTab;
    private int mTabCount;

    private float mTabPadding;
    private boolean mTabSpaceEqual;
    private float mTabWidth;



    /**
     * title
     */
    private static final int TEXT_BOLD_NONE = 0;
    private static final int TEXT_BOLD_WHEN_SELECT = 1;
    private static final int TEXT_BOLD_BOTH = 2;
    private float mTextSelectSize;
    private float mTextUnSelectSize;
    private int mTextSelectColor;
    private int mTextUnSelectColor;
    private int mTextBold;
    private boolean mTextAllCaps;


    /**
     * tab的上下间距
     */
    private int mTabMarginTop;
    private int mTabMarginBottom;

    /**
     * tab摆放的位置，目前只支持top和bottom
     */
    private int mTabGravity;

    private TabScaleTransformer defaultTransformer;


    public SlidingScaleTabLayout(Context context) {
        this(context, null, 0);
    }

    public SlidingScaleTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingScaleTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFillViewport(true);//设置滚动视图是否可以伸缩其内容以填充视口
        setWillNotDraw(false);//重写onDraw方法,需要调用这个方法来清除flag
        setClipChildren(false);
        setClipToPadding(false);

        this.mContext = context;
        mTabsContainer = new LinearLayout(context);
        addView(mTabsContainer);

        obtainAttributes(context, attrs);

        //get layout_height
        String height = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");

        if (height.equals(ViewGroup.LayoutParams.MATCH_PARENT + "")) {
        } else if (height.equals(ViewGroup.LayoutParams.WRAP_CONTENT + "")) {
        } else {
            int[] systemAttrs = {android.R.attr.layout_height};
            TypedArray a = context.obtainStyledAttributes(attrs, systemAttrs);
            a.recycle();
        }
    }


    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlidingScaleTabLayout);
        mTextUnSelectSize = ta.getDimension(R.styleable.SlidingScaleTabLayout_tl_textUnSelectSize, sp2px(14));
        // 被选中的文字大小，默认额未选中的大小一样
        mTextSelectSize = ta.getDimension(R.styleable.SlidingScaleTabLayout_tl_textSelectSize, mTextUnSelectSize);

        mTextSelectColor = ta.getColor(R.styleable.SlidingScaleTabLayout_tl_textSelectColor, Color.parseColor("#ffffff"));
        mTextUnSelectColor = ta.getColor(R.styleable.SlidingScaleTabLayout_tl_textUnSelectColor, Color.parseColor("#AAffffff"));
        mTextBold = ta.getInt(R.styleable.SlidingScaleTabLayout_tl_textBold, TEXT_BOLD_NONE);
        mTextAllCaps = ta.getBoolean(R.styleable.SlidingScaleTabLayout_tl_textAllCaps, false);

        mTabSpaceEqual = ta.getBoolean(R.styleable.SlidingScaleTabLayout_tl_tab_space_equal, false);
        mTabWidth = ta.getDimension(R.styleable.SlidingScaleTabLayout_tl_tab_width, dp2px(-1));
        mTabPadding = ta.getDimension(R.styleable.SlidingScaleTabLayout_tl_tab_padding, mTabSpaceEqual || mTabWidth > 0 ? dp2px(0) : dp2px(20));
        // 得到设置的上下间距和gravity
        mTabMarginTop = ta.getDimensionPixelSize(R.styleable.SlidingScaleTabLayout_tl_tab_marginTop, 0);
        mTabMarginBottom = ta.getDimensionPixelSize(R.styleable.SlidingScaleTabLayout_tl_tab_marginBottom, 0);
        mTabGravity = ta.getInt(R.styleable.SlidingScaleTabLayout_tl_tab_gravity, CENTER);
        ta.recycle();
    }

    /**
     * 关联ViewPager
     */
    public void setViewPager(ViewPager vp) {
        if (vp == null || vp.getAdapter() == null) {
            throw new IllegalStateException("ViewPager or ViewPager adapter can not be NULL !");
        }

        this.mViewPager = vp;
        initViewPagerListener();
    }

    private void initViewPagerListener() {
        this.mViewPager.removeOnPageChangeListener(this);
        this.mViewPager.addOnPageChangeListener(this);
        initTransformer();
        notifyDataSetChanged();
    }

    private void initTransformer() {
        // 如果选中状态的文字大小和未选中状态的文字大小是不同的，开启缩放
        if (mTextUnSelectSize != mTextSelectSize) {
            defaultTransformer = new TabScaleTransformer(this, mViewPager.getAdapter(), mTextSelectSize, mTextUnSelectSize);
            this.mViewPager.setPageTransformer(true, defaultTransformer);
        }
    }

    /**
     * 更新数据
     */
    public void notifyDataSetChanged() {
        mTabsContainer.removeAllViews();
        this.mTabCount = mViewPager.getAdapter().getCount();
        View tabView;
        for (int i = 0; i < mTabCount; i++) {
            tabView = View.inflate(mContext, R.layout.layout_tab, null);
            TextView title = tabView.findViewById(R.id.tv_tab_title);
            // 设置tab的位置信息
            setTabLayoutParams(title);
            CharSequence pageTitle =mViewPager.getAdapter().getPageTitle(i);
            addTab(i, pageTitle.toString(), tabView);
        }
        updateTabStyles();
    }

    private void setTabLayoutParams(TextView title) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) title.getLayoutParams();
        params.topMargin = mTabMarginTop;
        params.bottomMargin = mTabMarginBottom;
        if (mTabGravity == TOP) {
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        } else if (mTabGravity == BOTTOM) {
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        } else {
            params.addRule(RelativeLayout.CENTER_VERTICAL);
        }
        title.setLayoutParams(params);
    }


    /**
     * 创建并添加tab
     */
    private void addTab(final int position, String title, View tabView) {
        TextView tv_tab_title = (TextView) tabView.findViewById(R.id.tv_tab_title);
        if (tv_tab_title != null) {
            tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, position == mCurrentTab ? mTextSelectSize : mTextUnSelectSize);
            if (title != null) tv_tab_title.setText(title);
        }
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mTabsContainer.indexOfChild(v);
                if (position != -1) {
                    if (mViewPager.getCurrentItem() != position) {
                        mViewPager.setCurrentItem(position);
                    }
                }
            }
        });

        /** 每一个Tab的布局参数 */
        LinearLayout.LayoutParams lp_tab = mTabSpaceEqual ?
                new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f) :
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        if (mTabWidth > 0) {
            lp_tab = new LinearLayout.LayoutParams((int) mTabWidth, LayoutParams.MATCH_PARENT);
        }
        mTabsContainer.addView(tabView, position, lp_tab);
    }

    private void updateTabStyles() {
        for (int i = 0; i < mTabCount; i++) {
            View v = mTabsContainer.getChildAt(i);
            TextView tv_tab_title = (TextView) v.findViewById(R.id.tv_tab_title);
            if (tv_tab_title != null) {
                tv_tab_title.setTextColor(i == mCurrentTab ? mTextSelectColor : mTextUnSelectColor);
                tv_tab_title.setPadding((int) mTabPadding, 0, (int) mTabPadding, 0);
                if (mTextAllCaps) {
                    tv_tab_title.setText(tv_tab_title.getText().toString().toUpperCase());
                }

                if (mTextBold == TEXT_BOLD_BOTH) {
                    tv_tab_title.getPaint().setFakeBoldText(true);
                }
                // 被选中设置为粗体
                else if (mTextBold == TEXT_BOLD_WHEN_SELECT && i == mCurrentTab) {
                    tv_tab_title.getPaint().setFakeBoldText(true);
                } else if (mTextBold == TEXT_BOLD_NONE) {
                    tv_tab_title.getPaint().setFakeBoldText(false);
                }
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        /**
         * position:当前View的位置
         */
        this.mCurrentTab = position;
    }

    @Override
    public void onPageSelected(int position) {
        updateTabSelection(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    private void updateTabSelection(int position) {
        for (int i = 0; i < mTabCount; ++i) {
            View tabView = mTabsContainer.getChildAt(i);
            final boolean isSelect = i == position;
            TextView tab_title =tabView.findViewById(R.id.tv_tab_title);
            if (tab_title != null) {
                tab_title.setTextColor(isSelect ? mTextSelectColor : mTextUnSelectColor);
                if (mTextBold == TEXT_BOLD_WHEN_SELECT) {
                    tab_title.getPaint().setFakeBoldText(isSelect);
                }
            }
        }
    }

    public TextView getTitle(int position) {
        if (position >= mTabCount) {
            position= mTabCount - 1;
        }
        View tabView = mTabsContainer.getChildAt(position);
        if (tabView == null) {
            return null;
        }
        return (TextView) tabView.findViewById(R.id.tv_tab_title);
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("mCurrentTab", mCurrentTab);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentTab = bundle.getInt("mCurrentTab");
            state = bundle.getParcelable("instanceState");
            if (mCurrentTab != 0 && mTabsContainer.getChildCount() > 0) {
                updateTabSelection(mCurrentTab);
            }
        }
        super.onRestoreInstanceState(state);
    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected int sp2px(float sp) {
        final float scale = this.mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}

