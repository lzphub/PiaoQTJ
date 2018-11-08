package cn.dankal.basiclib.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import cn.dankal.basiclib.R;
import cn.dankal.basiclib.base.callback.DKCallBackObject;
import cn.dankal.basiclib.widget.wheelview.OnWheelChangedListener;
import cn.dankal.basiclib.widget.wheelview.WheelView;
import cn.dankal.basiclib.widget.wheelview.adapters.NumericWheelAdapter;

/**
 * @author leaflc
 */
public class TimeDialog extends DialogFragment {
    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;
    private View view;
    private int START_YEAR = 1990, endYear = 2100;
    private DKCallBackObject<String> mListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_time, container, false);
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        initDateTimePicker(year, month, day);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        WindowManager.LayoutParams params = window.getAttributes();
        //获得window窗口的属性
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.getAttributes().gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }


    public void initDateTimePicker(int year, int month, int day) {
        String[] monthsBig = {"1", "3", "5", "7", "8", "10", "12"};
        String[] monthsLittle = {"4", "6", "9", "11"};

        final List<String> listBig = Arrays.asList(monthsBig);
        final List<String> listLittle = Arrays.asList(monthsLittle);

        view.findViewById(R.id.tv_cancel).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.tv_finish).setOnClickListener(v ->
        {
            dismiss();
            if (mListener == null) {
                return;
            }
            mListener.action(getTime());
        });

        // 年
        wvYear = view.findViewById(R.id.wv_year);
        //设置可见数量
        wvYear.setVisibleItems(7);
        // 设置"年"的显示数据
        wvYear.setViewAdapter(new NumericWheelAdapter(getContext(), START_YEAR, endYear));
        // 可循环滚动
        wvYear.setCyclic(true);
        // 初始化时显示的数据
        wvYear.setCurrentItem(year - START_YEAR);

        // 月
        wvMonth = view.findViewById(R.id.wv_month);
        wvMonth.setVisibleItems(7);
        wvMonth.setViewAdapter(new NumericWheelAdapter(getContext(), 1, 12));
        wvMonth.setCyclic(true);
        wvMonth.setCurrentItem(month);

        // 日
        wvDay = view.findViewById(R.id.wv_day);
        wvDay.setVisibleItems(7);
        wvDay.setCyclic(true);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (listBig.contains(String.valueOf(month + 1))) {
            wvDay.setViewAdapter(new NumericWheelAdapter(getContext(), 1, 31));
        } else if (listLittle.contains(String.valueOf(month + 1))) {
            wvDay.setViewAdapter(new NumericWheelAdapter(getContext(), 1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                wvDay.setViewAdapter((new NumericWheelAdapter(getContext(), 1, 29)));
            } else {
                wvDay.setViewAdapter(new NumericWheelAdapter(getContext(), 1, 28));
            }
        }
        wvDay.setCurrentItem(day - 1);

        // 添加"年"监听
        OnWheelChangedListener wheelListenerYear = (wheel, oldValue, newValue) -> {
            int yearNum = newValue + START_YEAR;
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (listBig
                    .contains(String.valueOf(wvMonth.getCurrentItem() + 1))) {
                wvDay.setViewAdapter(new NumericWheelAdapter(getContext(), 1, 31));
            } else if (listLittle.contains(String.valueOf(wvMonth
                    .getCurrentItem() + 1))) {
                wvDay.setViewAdapter(new NumericWheelAdapter(getContext(), 1, 30));
            } else {
                if ((yearNum % 4 == 0 && yearNum % 100 != 0) || yearNum % 400 == 0) {
                    wvDay.setViewAdapter(new NumericWheelAdapter(getContext(), 1, 29));
                } else {
                    wvDay.setViewAdapter(new NumericWheelAdapter(getContext(), 1, 28));
                }
            }
        };
        // 添加"月"监听
        OnWheelChangedListener wheelListenerMonth = (wheel, oldValue, newValue) -> {
            int monthNum = newValue + 1;
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (listBig.contains(String.valueOf(monthNum))) {
                wvDay.setViewAdapter(new NumericWheelAdapter(getContext(), 1, 31));
            } else if (listLittle.contains(String.valueOf(monthNum))) {
                wvDay.setViewAdapter(new NumericWheelAdapter(getContext(), 1, 30));
            } else {
                if (((wvYear.getCurrentItem() + START_YEAR) % 4 == 0 && (wvYear
                        .getCurrentItem() + START_YEAR) % 100 != 0)
                        || (wvYear.getCurrentItem() + START_YEAR) % 400 == 0) {
                    wvDay.setViewAdapter(new NumericWheelAdapter(getContext(), 1, 29));
                } else {
                    wvDay.setViewAdapter(new NumericWheelAdapter(getContext(), 1, 28));
                }
            }
        };
        wvYear.addChangingListener(wheelListenerYear);
        wvMonth.addChangingListener(wheelListenerMonth);

    }

    private String getTime() {
        StringBuffer sb = new StringBuffer();
        sb.append((wvYear.getCurrentItem() + START_YEAR)).append("-")
                .append((wvMonth.getCurrentItem() + 1)).append("-")
                .append((wvDay.getCurrentItem() + 1));
        return sb.toString();
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setListener(DKCallBackObject<String> mListener) {
        this.mListener = mListener;
    }
}
