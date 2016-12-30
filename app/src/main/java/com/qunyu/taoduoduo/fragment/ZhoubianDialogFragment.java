package com.qunyu.taoduoduo.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qunyu.taoduoduo.R;

/**
 * Created by Administrator on 2016/12/14.
 */

public class ZhoubianDialogFragment extends Dialog {

    public ZhoubianDialogFragment(Context context) {
        super(context);
    }

    public ZhoubianDialogFragment(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private View contentView;


        private OnClickListener sex1ClickListener;
        private OnClickListener sex2ClickListener;
        private OnClickListener cancelClickListener;

        private TextView tv_look, tv_jixuguang;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param
         * @return
         */
        public Builder setSex1ClickListener(
                OnClickListener listener) {
            this.sex1ClickListener = listener;
            return this;
        }

        public Builder setSex2ClickListener(
                OnClickListener listener) {
            this.sex2ClickListener = listener;
            return this;
        }

        public Builder setCancelClickListener(
                OnClickListener listener) {
            this.cancelClickListener = listener;
            return this;
        }

        public ZhoubianDialogFragment create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final ZhoubianDialogFragment dialog = new ZhoubianDialogFragment(context,
                    R.style.DialogStyleBottom);
            View layout = inflater.inflate(R.layout.zhoubian_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            tv_look = (TextView) layout.findViewById(R.id.tv_qulooklook);
            tv_jixuguang = (TextView) layout.findViewById(R.id.tv_jixuguang);


            if (sex1ClickListener != null) {

                tv_look.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        sex1ClickListener.onClick(dialog,
                                DialogInterface.BUTTON_POSITIVE);

                    }
                });

            }
            if (sex2ClickListener != null) {

                tv_jixuguang.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        sex2ClickListener.onClick(dialog,
                                DialogInterface.BUTTON_NEGATIVE);

                    }
                });

            }
            if (cancelClickListener != null) {


            }

            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(true);
            return dialog;
        }

    }
}

